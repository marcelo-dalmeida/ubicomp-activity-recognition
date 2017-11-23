package br.com.uff.ubicomp.activityrecognition.client.persistence.repository;

import br.com.uff.ubicomp.activityrecognition.client.persistence.entity.EnergyPositionActivity;

public class EnergyPositionActivityRepository extends AbstractRepository<EnergyPositionActivity, Integer> {

	public Class<EnergyPositionActivity> getDomainClass() {
		return EnergyPositionActivity.class;
	}
}
