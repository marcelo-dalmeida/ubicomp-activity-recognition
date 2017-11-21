package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;

public class Shower implements Device {
	private float consumption = (float) 3.5;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {
if (activity == Activity.BATHING) {
	return consumption;
} else {
	return (float) 0;
}
	}

}
