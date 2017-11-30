package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device;

import java.time.LocalTime;

import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.Activity;

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
