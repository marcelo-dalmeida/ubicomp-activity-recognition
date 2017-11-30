package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device;

import java.time.LocalTime;

import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.Activity;

public class TV implements Device {
	private float consumption = (float) 1.910;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {
if (activity == Activity.WATCHING_TV) {
	return consumption;
} else {
	return (float) 0;
}
	}

}
