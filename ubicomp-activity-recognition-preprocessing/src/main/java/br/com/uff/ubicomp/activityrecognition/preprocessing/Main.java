package br.com.uff.ubicomp.activityrecognition.preprocessing;

import br.com.uff.ubicomp.activityrecognition.preprocessing.persistence.entity.EnergyPositionActivity;
import br.com.uff.ubicomp.activityrecognition.preprocessing.persistence.repository.EnergyPositionActivityRepository;

public class Main {

	public static void main (String[] args) throws Exception {
		
		EnergyPositionActivity energyPositionActivity = new EnergyPositionActivity();
		
		EnergyPositionActivityRepository energyPositionActivityRepository = new EnergyPositionActivityRepository();
		energyPositionActivityRepository.save(energyPositionActivity);
	}
}
