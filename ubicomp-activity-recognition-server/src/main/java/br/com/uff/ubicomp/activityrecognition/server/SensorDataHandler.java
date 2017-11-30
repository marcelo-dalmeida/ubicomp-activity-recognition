package br.com.uff.ubicomp.activityrecognition.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.uff.ubicomp.activityrecognition.server.enums.Activity;
import br.com.uff.ubicomp.activityrecognition.server.enums.Environment;
import br.com.uff.ubicomp.activityrecognition.server.persistence.entity.EnergyPositionActivity;
import br.com.uff.ubicomp.activityrecognition.server.persistence.repository.EnergyPositionActivityRepository;

public class SensorDataHandler {

	EnergyPositionActivityRepository energyPositionActivityRepository = new EnergyPositionActivityRepository();
	
	private final Calendar WINDOW_SIZE;
	
	private final int WINDOW_HOUR = 0;
	private final int WINDOW_MINUTE = 5;
	private final int WINDOW_SECOND = 0;
	
	private Map<Date, Map<Integer, List<Float>>> energySensorData;
	private Map<Date, Map<Integer, Environment>> environmentSensorData;
	private Map<Date, Map<Integer, Activity>> activityData;
	
	public SensorDataHandler() {
		energySensorData = new HashMap<Date, Map<Integer, List<Float>>>();
		environmentSensorData = new HashMap<Date, Map<Integer, Environment>>();
		activityData = new HashMap<Date, Map<Integer, Activity>>();
		
		WINDOW_SIZE = Calendar.getInstance();
		WINDOW_SIZE.clear();
		WINDOW_SIZE.set(Calendar.HOUR, WINDOW_HOUR);
		WINDOW_SIZE.set(Calendar.MINUTE, WINDOW_MINUTE);
		WINDOW_SIZE.set(Calendar.SECOND, WINDOW_SECOND);

		Calendar currentTime = Calendar.getInstance();
		currentTime.clear();
		
		Calendar endOfDay = Calendar.getInstance();
		endOfDay.clear();
		endOfDay.add(Calendar.DAY_OF_YEAR, 1);
		endOfDay.add(Calendar.SECOND, -1);
		
		while (currentTime.before(endOfDay)) {
			energySensorData.put(currentTime.getTime(), new HashMap<Integer, List<Float>>());
			environmentSensorData.put(currentTime.getTime(), new HashMap<Integer, Environment>());
			activityData.put(currentTime.getTime(), new HashMap<Integer, Activity>());
			
			currentTime.add(Calendar.HOUR, WINDOW_HOUR);
			currentTime.add(Calendar.MINUTE, WINDOW_MINUTE);
			currentTime.add(Calendar.SECOND, WINDOW_SECOND);
		}
		
		
	}
	
	
	public void receiveData(String data) {
		
		String[] content = data.split(";");
		
		int id = Integer.parseInt(content[0]);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date time = null;
		try {
			time = sdf.parse(content[1]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar actualTime = Calendar.getInstance();
		actualTime.clear();
		actualTime.setTime(time);
		int actualMinute = actualTime.get(Calendar.MINUTE);
		int remainder = actualMinute % WINDOW_MINUTE;
		actualMinute -= remainder;
		actualTime.set(Calendar.MINUTE, actualMinute);
		time = actualTime.getTime();
		
		
		if (content.length == 4) {
			
			Environment environment = Environment.valueOf(content[2]);
			Map<Integer, Environment> environmentData = environmentSensorData.get(time);
			environmentData.put(id, environment);
			environmentSensorData.put(time, environmentData);
		
			Activity activity = Activity.valueOf(content[3]);
			Map<Integer, Activity> activityInfo = activityData.get(time);
			activityInfo.put(id, activity);
			activityData.put(time, activityInfo);
			
		} else if (content.length == 3){
			
			float energy = Float.valueOf(content[2]);
			
			Map<Integer, List<Float>> energyData = energySensorData.get(time);
			List<Float> energyList;
			
			energyList = energyData.get(id);
			if (energyList == null){
				energyList = new ArrayList<Float>();
			}

			energyList.add(energy);
			energyData.put(id, energyList);
			energySensorData.put(time, energyData);

		}
		if (isAllDataTransfered(time, id)) {
			persistSensorData(time, id);
		}
	}
	
	public boolean isAllDataTransfered(Date time, int id) {
		
		if (energySensorData.get(time) != null &&
			energySensorData.get(time).get(id) != null &&
			energySensorData.get(time).get(id).size() == WINDOW_MINUTE*5 &&
			environmentSensorData.get(time) != null &&
			environmentSensorData.get(time).get(id) != null) {
			return true;
		}
		
		return false;
	}
	
	public void persistSensorData(Date time, int id) {
		
		EnergyPositionActivity energyPositionActivity = new EnergyPositionActivity();
		
		Calendar startTime = Calendar.getInstance();
		startTime.clear();
		startTime.setTime(time);
		
		Calendar finishTime = (Calendar) startTime.clone();
		finishTime.add(Calendar.HOUR, WINDOW_HOUR);
		finishTime.add(Calendar.MINUTE, WINDOW_MINUTE);
		finishTime.add(Calendar.SECOND, WINDOW_SECOND);
		
		List<Float> energyList = energySensorData.get(time).get(id);
		
		float energySum = 0;
		
		for (float energy : energyList) {
			energySum += energy;
		}
		
		float averagePower =  energySum/energyList.size();
	
		energyPositionActivity.setUserId(id);
		energyPositionActivity.setStartTime(startTime.getTime());
		energyPositionActivity.setFinishTime(finishTime.getTime());
		energyPositionActivity.setAveragePower(averagePower);
		energyPositionActivity.setMedianPosition(environmentSensorData.get(time).get(id));
		energyPositionActivity.setActivity(activityData.get(time).get(id));
		
		energyPositionActivityRepository.save(energyPositionActivity);
			
	}
}
