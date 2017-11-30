package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.AirConditioningBedroom;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.AirConditioningRoom;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.BathroomLight;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.BedroomLight;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.Device;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.KitchenLight;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.LivingRoomLight;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.Microwave;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.PC;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.Refrigerator;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.Shower;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.TV;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device.VacuumCleaner;

public enum Environment {
	
	LIVING_ROOM, 
	KITCHEN, 
	BEDROOM, 
	BATHROOM, 
	EXTERNAL_AREA;
	
	private static Map<Environment, List<Device>> deviceMap = new HashMap<Environment, List<Device>>();

	static {
		deviceMap.put(Environment.LIVING_ROOM, new ArrayList<Device>(
				Arrays.asList(new LivingRoomLight(), new AirConditioningRoom(), new TV(), new VacuumCleaner())));
		deviceMap.put(Environment.BATHROOM,
				new ArrayList<Device>(Arrays.asList(new BathroomLight(), new Shower(), new VacuumCleaner())));
		deviceMap.put(Environment.BEDROOM, new ArrayList<Device>(
				Arrays.asList(new BedroomLight(), new AirConditioningBedroom(), new PC(), new VacuumCleaner())));
		deviceMap.put(Environment.KITCHEN, new ArrayList<Device>(
				Arrays.asList(new KitchenLight(), new Microwave(), new Refrigerator(), new VacuumCleaner())));
	}

	public static float getMeasurementOfEnvironment(Environment environment, Activity activity, LocalTime time) {
		float measurement = (float) 0;
		if ((activity != Activity.LEAVING) && (environment != Environment.EXTERNAL_AREA)) {
			List<Device> devices = deviceMap.get(environment);
			for (Device device : devices) {
				measurement = measurement + device.getComsumption(time, activity);
			}
		}
		return measurement;
	}

}
