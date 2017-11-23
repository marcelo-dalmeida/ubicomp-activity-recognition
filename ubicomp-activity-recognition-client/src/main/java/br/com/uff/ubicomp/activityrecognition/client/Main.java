package br.com.uff.ubicomp.activityrecognition.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import br.com.uff.ubicomp.activityrecognition.client.persistence.entity.EnergyPositionActivity;
import br.com.uff.ubicomp.activityrecognition.client.persistence.repository.EnergyPositionActivityRepository;
import br.com.uff.ubicomp.activityrecognition.client.smarthome.Activity;
import br.com.uff.ubicomp.activityrecognition.client.smarthome.Environment;
import br.com.uff.ubicomp.activityrecognition.client.smarthome.Historic;

public class Main {

	public static void main(String[] args) throws Exception {
		Historic historic = new Historic();
		EnergyPositionActivity energyPositionActivity;
		EnergyPositionActivityRepository energyPositionActivityRepository = new EnergyPositionActivityRepository();
		TreeMap<LocalTime, Activity> userHistoric;
		List<LocalTime> times;
		// LocalTime time;
		Activity act;
		Environment user_position = null;
		// for (int i = 0; i < historic.getHistoric().size(); i++) {
		for (Integer codUser : historic.getHistoric().keySet()) {
			userHistoric = (TreeMap<LocalTime, Activity>) historic.getHistoric().get(codUser);
			times = new ArrayList<LocalTime>(userHistoric.keySet());
			// for (int j = 0; j < userHistoric.size(); j++) {
			for (LocalTime time : times) {
				// now = times.get(time);
				act = userHistoric.get(time);
				switch (act) {
				case READING:
					user_position = Environment.ROOM;
					break;
				case BATHING:
					user_position = Environment.BATHROOM;
					break;
				case COOKING:
					user_position = Environment.KITCHEN;
					break;
				case EATING:
					user_position = Environment.ROOM;
					break;
				case WATCHING_TV:
					user_position = Environment.ROOM;
					break;
				case WORKING_WITH_PC:
					user_position = Environment.BEDROOM;
					break;
				case LEAVING:
					user_position = Environment.EXTERNAL_AREA;
					break;
				case CLEANING_THE_HOUSE:
					Random random = new Random();
					int localrandom = random.nextInt(4);
					switch (localrandom) {
					case 0:
						user_position = Environment.ROOM;
						break;
					case 1:
						user_position = Environment.BEDROOM;
						break;
					case 2:
						user_position = Environment.BATHROOM;
						break;
					case 3:
						user_position = Environment.KITCHEN;
						break;
					}
					break;
				case WASHING_DISHES:
					user_position = Environment.KITCHEN;
					break;
				case SLEEPING:
					user_position = Environment.BEDROOM;
					break;
				}
				energyPositionActivity = new EnergyPositionActivity(codUser,
						Date.from(time.atDate(LocalDate.of(2017, 05, 22)).atZone(ZoneId.systemDefault()).toInstant()),
						user_position, act, Environment.getMeasurementOfEnvironment(Environment.ROOM, act, time),
						Environment.getMeasurementOfEnvironment(Environment.BEDROOM, act, time),
						Environment.getMeasurementOfEnvironment(Environment.BATHROOM, act, time),
						Environment.getMeasurementOfEnvironment(Environment.KITCHEN, act, time),
						Environment.getMeasurementOfEnvironment(Environment.EXTERNAL_AREA, act, time));
				energyPositionActivityRepository.save(energyPositionActivity);
			}
		}

	}
}