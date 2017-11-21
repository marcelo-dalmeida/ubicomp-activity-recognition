package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Environment {
	ROOM, KITCHEN, BEDROOM, BATHROOM, EXTERNAL_AREA;
private static Map<Environment, List<Device>> deviceMap = new HashMap<Environment, List<Device>>();

static {
	deviceMap.put(Environment.ROOM, new ArrayList<Device>(Arrays.asList(new LightRoom(), new AirConditioningRoom(), new TV(), new VacuumCleaner())));
	deviceMap.put(Environment.BATHROOM, new ArrayList<Device>(Arrays.asList(new LightBathroom(), new Shower(), new VacuumCleaner())));
deviceMap.put(Environment.BEDROOM, new ArrayList<Device>(Arrays.asList(new LightBedroom(), new AirConditioningBedroom(), new PC(), new VacuumCleaner())));
deviceMap.put(Environment.KITCHEN, new ArrayList<Device>(Arrays.asList(new LightKitchen(), new MicroWave(), new Refrigerator(), new VacuumCleaner())));
}

public static float getMeasurementOfEnvironment(Environment environment, Activity activity, LocalTime time){
float measurement = (float) 0;
List<Device> devices = deviceMap.get(environment);
if (activity != Activity.LEAVING) {
for (int i = 0; i < devices.size(); i++) {
measurement = measurement + devices.get(i).getComsumption(time, activity);
}
}
return measurement;
}

}
