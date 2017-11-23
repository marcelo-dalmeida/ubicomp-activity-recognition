package br.com.uff.ubicomp.activityrecognition.client;
 
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
 
public class CoreSensors implements NodeConnectionListener 
{
	  
    private static int totalSensores = 30;
    private static int totalClientes = 100;
    private int counter = 0;
        
    private static String	   gatewayIP   = "127.0.0.1";
    private static int		   gatewayPort = 5500;
    private MrUdpNodeConnection[]  connectionSensors = new MrUdpNodeConnection[totalSensores];
    private MrUdpNodeConnection[]  connectionClients = new MrUdpNodeConnection[totalClientes];
    
    private static CoreSensors me;
    
    public static void main(String[] args) 
    {
        Logger.getLogger("").setLevel(Level.OFF);
        me = new CoreSensors();
    }
	 
    public CoreSensors() 
    {
        try 
        {
            InetSocketAddress[] address = new InetSocketAddress[totalClientes];
        
            for (int i=0; i < totalClientes; i++)
            {
                address[i] = new InetSocketAddress(gatewayIP, gatewayPort);
                
                connectionClients[i] = new MrUdpNodeConnection();
                connectionClients[i].addNodeConnectionListener(this);
                connectionClients[i].connect(address[i]);
                
                sendMessage("Hello World - Cliente [" + i + "]", i);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
	 
    @Override
    public void connected(NodeConnection remoteCon) 
    {
        System.out.println("Mensagem [Conexão com o Servidor realizada com sucesso!!!]");
    }
    
    public void sendMessage (String pMessage, int pNumCliente)
    {
        ApplicationMessage message = new ApplicationMessage();
        String serializableContent = pMessage;
        Serializable strMessage;
        strMessage = serializableContent;
        message.setContentObject(strMessage);
        
        try 
        {
            connectionClients[pNumCliente].sendMessage(message); 

            //sleep(10);
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
        System.out.println("Mensagem [" + counter +"] ..:[" + message.getContentObject() + "]");
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


