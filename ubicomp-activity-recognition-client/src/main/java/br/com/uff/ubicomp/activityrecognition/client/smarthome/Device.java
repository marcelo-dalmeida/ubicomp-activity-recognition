package br.com.uff.ubicomp.activityrecognition.client.smarthome;

import java.time.LocalTime;

public interface Device {

	public Float getComsumption(LocalTime time, Activity activity);
}