package br.com.uff.ubicomp.activityrecognition.client.smarthome;

import java.time.LocalTime;
import java.util.Random;

public class AirConditioningBedroom implements Device {
	private float consumption = (float) 8.333;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {

Random random = new Random();
	int probability = random.nextInt(101);
	if ((activity == Activity.WORKING_WITH_PC || activity == Activity.SLEEPING) && probability > 50) {
	return consumption;
} else {
	return (float) 0;
}
	}

}
