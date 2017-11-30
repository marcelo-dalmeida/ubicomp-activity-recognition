/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uff.ubicomp.activityrecognition.client.sensor;

import br.com.uff.ubicomp.activityrecognition.client.persistence.entity.EnergyPositionActivity;
import br.com.uff.ubicomp.activityrecognition.client.persistence.repository.EnergyPositionActivityRepository;
import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
/**
 *
 * @author hilario
 */

public class KitchenSensor extends Thread implements NodeConnectionListener
{
    private int counter = 0; //Contador de Conexões
        
    private MrUdpNodeConnection    v_connection  = null;
    private InetSocketAddress      v_address = null;
    
    EnergyPositionActivity energyPositionActivity;
    EnergyPositionActivityRepository energyPositionActivityRepository;
            
    public KitchenSensor(InetSocketAddress address) 
    { 
        this.v_address = address;
        
        energyPositionActivityRepository = new EnergyPositionActivityRepository();
    }
    
    public void run() 
    {
        try 
        {
            v_connection = new MrUdpNodeConnection();
            v_connection.addNodeConnectionListener(this);
            v_connection.connect(v_address);
            
            String hoursTmp = "";
            String minutsTmp = "";
            
            String horaCompleta = "";
            
            for (int hora=0; hora<=23; hora++)
            {
                for (int minuto=0; minuto<=59; minuto++)
                {
                    for (int idUser=1; idUser <= 22; idUser++)
                    {
                        NumberFormat formatter = new DecimalFormat("00");
                        String h = formatter.format(hora);
                        String m = formatter.format(minuto);
                        
                        horaCompleta = h + ":" + m  + ":00";
                        
                        String mensagemSensor = energyPositionActivityRepository.findOne(idUser, horaCompleta, "KITCHEN");
                        
                        sendMessage(mensagemSensor);
                        
                        sleep(2000);    
                    }
                }
            }
            
            v_connection.disconnect();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void connected(NodeConnection remoteCon) 
    {
        System.out.println("Mensagem [O Sensor "+ this.getName() + " se conectou com o Servidor com sucesso!!!]");
    }
    
    public void sendMessage (String pMessage)
    {
        ApplicationMessage message = new ApplicationMessage();
        String serializableContent = pMessage;
        Serializable strMessage;
        strMessage = serializableContent;
        message.setContentObject(strMessage);
        
        try 
        {
            if(v_connection !=null)
            {
                v_connection.sendMessage(message); 
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
	 
    @Override
    public void newMessageReceived(NodeConnection remoteCon, Message message) 
    {
        counter++;
        //System.out.println("Mensagem [" + counter +"] ..:[" + message.getContentObject() + "]");
    }

    // other methods

    @Override
    public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {}

    @Override
    public void disconnected(NodeConnection remoteCon) {}

    @Override
    public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {}

    @Override
    public void internalException(NodeConnection remoteCon, Exception e) {}
}

