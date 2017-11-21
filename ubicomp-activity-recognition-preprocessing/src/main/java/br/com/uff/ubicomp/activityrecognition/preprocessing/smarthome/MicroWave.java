package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;

public class MicroWave implements Device {
	private float consumption = (float) 0.301;

@Override
	public Float getComsumption(LocalTime time, Activity activity) {
if (activity == Activity.COOKING) {
	return consumption;
} else {
	return (float) 0;
}
	}

}
