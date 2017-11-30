package br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.device;

import java.time.LocalTime;

import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.Activity;

public interface Device {

	public Float getComsumption(LocalTime time, Activity activity);
}