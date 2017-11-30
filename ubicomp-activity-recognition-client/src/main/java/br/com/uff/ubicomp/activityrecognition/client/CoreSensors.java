package br.com.uff.ubicomp.activityrecognition.client;
 
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.uff.ubicomp.activityrecognition.client.sensor.BathroomSensor;
import br.com.uff.ubicomp.activityrecognition.client.sensor.BedroomSensor;
import br.com.uff.ubicomp.activityrecognition.client.sensor.ExternalAreaSensor;
import br.com.uff.ubicomp.activityrecognition.client.sensor.KitchenSensor;
import br.com.uff.ubicomp.activityrecognition.client.sensor.LivingRoomSensor;
import br.com.uff.ubicomp.activityrecognition.client.sensor.UserPositionSensor;

public class CoreSensors
{
	  
    private static final String	   gatewayIP   = "127.0.0.1";
    private static final int       gatewayPort = 5500;
    
    private static CoreSensors me;
    
    private BathroomSensor     bathroomSensor;
    private BedroomSensor      bedroomSensor;
    private ExternalAreaSensor externalAreaSensor;
    private KitchenSensor      kitchenSensor;
    private LivingRoomSensor   livingRoomSensor;
    private UserPositionSensor userPositionSensor;
    
    public static void main(String[] args) 
    {
        Logger.getLogger("").setLevel(Level.OFF);
        String arg = "";
        if (args.length == 1) {
        	arg = args[0];
        }
        
        me = new CoreSensors(arg);
    }
	 
    public CoreSensors(String arg) 
    {
        InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
        System.out.println("Se conectando ao sensor " + arg);
        switch (arg) {
        	case "bathroom":
                bathroomSensor = new BathroomSensor(address);
                bathroomSensor.start();
        		break;
        	case "bedroom":
                bedroomSensor = new BedroomSensor(address);
        		bedroomSensor.start();
        		break;
        	case "externalArea":
                externalAreaSensor = new ExternalAreaSensor(address);
        		externalAreaSensor.start();
        		break;
        	case "kitchen":
                kitchenSensor = new KitchenSensor(address);
        		kitchenSensor.start();
        		break;
        	case "livingRoom":
                livingRoomSensor = new LivingRoomSensor(address);
        		livingRoomSensor.start();
        		break;
        	case "userPosition":
                userPositionSensor = new UserPositionSensor(address);
        		userPositionSensor.start();
        		break;
        	default:
        		System.out.println("Enter a sensor through argument");
        		System.out.println("Options:");
        		System.out.println("bathroom\n" + 
        						   "bedroom\n" + 
        				           "externalArea\n" + 
        				           "kitchen\n" + 
        				           "livingRoom\n" + 
        						   "userPosition");
        		break;
        }
    }  
}


