package br.com.uff.ubicomp.activityrecognition.preprocessing.persistence.repository;

import br.com.uff.ubicomp.activityrecognition.preprocessing.persistence.entity.EnergyPositionActivity;

public class EnergyPositionActivityRepository extends AbstractRepository<EnergyPositionActivity, Integer> {

	public Class<EnergyPositionActivity> getDomainClass() {
		return EnergyPositionActivity.class;
	}
}
