package br.com.uff.ubicomp.activityrecognition.client.smarthome;

import java.time.LocalTime;

public class VacuumCleaner implements Device {
private float consumption = (float) 0.325;

@Override
public Float getComsumption(LocalTime time, Activity activity) {
if (activity == Activity.CLEANING_THE_HOUSE) {
return consumption;
} else {
return (float) 0;
}
}

}
