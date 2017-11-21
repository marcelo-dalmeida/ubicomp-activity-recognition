package br.com.uff.ubicomp.activityrecognition.preprocessing.persistence.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.Activity;
import br.com.uff.ubicomp.activityrecognition.preprocessing.smarthome.Environment;

@Entity
@Table(name = "energy_position_activity")
public class EnergyPositionActivity {

	@Id
	@GeneratedValue
	private int id;
private int user_id;
	@Temporal(TemporalType.TIME)
	private Date time;
	private Environment user_position;
	private Activity activity;
	private Float roomEnergy;
	private Float bedroomEnergy;
	private Float bathroomEnergy;
	private Float kitchenEnergy;
	private Float externalAreaEnergy;

	public EnergyPositionActivity(int user_id, Date time, Environment user_position, Activity activity, Float roomEnergy, Float bedroomEnergy, Float bathroomEnergy, Float kitchenEnergy, Float externalAreaEnergy) {
		super();
		this.user_id = user_id;
		this.time = time;
		this.user_position = user_position;
		this.activity = activity;
		this.roomEnergy = roomEnergy;
		this.bedroomEnergy = bedroomEnergy;
		this.bathroomEnergy = bathroomEnergy;
		this.kitchenEnergy = kitchenEnergy;
		this.externalAreaEnergy = externalAreaEnergy;
	}

	public int getUserId() {
		return user_id;
	}

	public Date getTime() {
		return time;
	}

	public Environment getUser_position() {
		return user_position;
	}

	public Activity getActivity() {
		return activity;
	}

	public Float getMeasurementOfRoom() {
		return roomEnergy;
	}

	public Float getMeasurementOfBedroom() {
		return bedroomEnergy;
	}

	public Float getMeasurementOfBathroom() {
		return bathroomEnergy;
	}

	public Float getMeasurementOfKitchen() {
		return kitchenEnergy;
	}

	public Float getMeasurementOfExternal_area(Float external_area) {
		return externalAreaEnergy;
	}

}
