package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;

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
