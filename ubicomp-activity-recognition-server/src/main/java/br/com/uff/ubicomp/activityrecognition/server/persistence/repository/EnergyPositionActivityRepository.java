package br.com.uff.ubicomp.activityrecognition.server.persistence.repository;

import br.com.uff.ubicomp.activityrecognition.server.persistence.entity.EnergyPositionActivity;

public class EnergyPositionActivityRepository extends AbstractRepository<EnergyPositionActivity, Integer> {

	public Class<EnergyPositionActivity> getDomainClass() {
		return EnergyPositionActivity.class;
	}
}
