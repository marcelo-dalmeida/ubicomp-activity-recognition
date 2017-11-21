package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;

public class PC implements Device {
	private float consumption = (float) 1.157;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {
if (activity == Activity.WORKING_WITH_PC) {
	return consumption;
} else {
	return (float) 0;
}
	}

}
