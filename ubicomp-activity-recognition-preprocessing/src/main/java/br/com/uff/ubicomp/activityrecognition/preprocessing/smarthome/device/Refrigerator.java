package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device;

import java.time.LocalTime;

import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.Activity;

public class Refrigerator implements Device {
	private float consumption = (float) 2.803;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {
	return consumption;
	}
	

}
