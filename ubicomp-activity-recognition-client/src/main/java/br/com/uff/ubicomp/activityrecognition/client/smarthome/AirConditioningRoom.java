package br.com.uff.ubicomp.activityrecognition.client.smarthome;

import java.time.LocalTime;
import java.util.Random;

public class AirConditioningRoom  implements Device {
	private float consumption = (float) 8.333;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {

Random random = new Random();
	int probability = random.nextInt(101);
	if ((activity == Activity.EATING || activity == Activity.READING || activity == Activity.WATCHING_TV) && probability > 50) {
	return consumption;
} else {
	return (float) 0;
}
	}

}
