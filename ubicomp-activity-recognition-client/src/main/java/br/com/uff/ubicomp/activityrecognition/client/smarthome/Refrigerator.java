package br.com.uff.ubicomp.activityrecognition.client.smarthome;

import java.time.LocalTime;

public class Refrigerator implements Device {
	private float consumption = (float) 2.803;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {
	return consumption;
	}
	

}
