package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;

public class LightRoom implements Device {
	private float consumption = (float) 0.448;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {
if ((activity == Activity.EATING || activity == Activity.READING || activity == Activity.WATCHING_TV) & (time.isAfter(LocalTime.of(18, 00)) || time.isBefore(LocalTime.of(6, 00)))) {
	return consumption;
} else {
	return (float) 0;
}
	}

}
