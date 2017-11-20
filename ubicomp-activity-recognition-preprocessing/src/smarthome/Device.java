package smarthome;

import java.util.HashMap;
import java.util.Map;

public enum Device {
	LIGHT_ROOM, LIGHT_BEDROOM, LIGHT_BATHROOM, LIGHT_KITCHEN, AIR_CONDITIONING_ROOM, AIR_CONDITIONING_BEDROOM, TV, PC, MICROWAVE, VACUUM_CLEANER, REFRIGERATOR, SHOWER;

	private static Map<Device, Float> comsumption = new HashMap<Device, Float>();

	static {
		comsumption.put(LIGHT_ROOM, (float) 0.448);
		comsumption.put(Device.LIGHT_BEDROOM, (float) 0.448);
		comsumption.put(Device.LIGHT_BATHROOM, (float) 0.448);
		comsumption.put(LIGHT_KITCHEN, (float) 0.448);
		comsumption.put(AIR_CONDITIONING_ROOM, (float) 8.333);
		comsumption.put(AIR_CONDITIONING_BEDROOM, (float) 8.333);
		comsumption.put(SHOWER, (float) 3.5);
		comsumption.put(PC, (float) 1.157);
		comsumption.put(Device.MICROWAVE, (float) 0.301);
		comsumption.put(REFRIGERATOR, (float) 2.803);
		comsumption.put(Device.VACUUM_CLEANER, (float) 0.325);
		comsumption.put(TV, (float) 1.910);
	}

	public static Map<Device, Float> getComsumption() {
		return comsumption;
	}

}
