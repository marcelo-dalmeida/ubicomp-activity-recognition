package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome;

import java.time.LocalTime;

public interface Device {

	public Float getComsumption(LocalTime time, Activity activity);
}