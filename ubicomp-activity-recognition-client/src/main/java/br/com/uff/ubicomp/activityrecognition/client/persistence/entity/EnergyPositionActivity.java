package br.com.uff.ubicomp.activityrecognition.client.persistence.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.uff.ubicomp.activityrecognition.client.smarthome.Activity;
import br.com.uff.ubicomp.activityrecognition.client.smarthome.Environment;

@Entity
@Table(name = "historic")
public class EnergyPositionActivity {

	@Id
	@GeneratedValue
	private int id;
	private int user_id;
	@Temporal(TemporalType.TIME)
	private Date time;
	@Enumerated(EnumType.STRING)
	private Environment user_position;
	@Enumerated(EnumType.STRING)
	private Activity activity;
	private Float livingRoomEnergy;
	private Float bedroomEnergy;
	private Float bathroomEnergy;
	private Float kitchenEnergy;
	private Float externalAreaEnergy;

	public EnergyPositionActivity(int user_id, Date time, Environment user_position, Activity activity, Float livingRoomEnergy, Float bedroomEnergy, Float bathroomEnergy, Float kitchenEnergy, Float externalAreaEnergy) {
		super();
		this.user_id = user_id;
		this.time = time;
		this.user_position = user_position;
		this.activity = activity;
		this.livingRoomEnergy = livingRoomEnergy;
		this.bedroomEnergy = bedroomEnergy;
		this.bathroomEnergy = bathroomEnergy;
		this.kitchenEnergy = kitchenEnergy;
		this.externalAreaEnergy = externalAreaEnergy;
	}

	public EnergyPositionActivity() {
		
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
		return livingRoomEnergy;
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

	public Float getMeasurementOfExternal_area() {
		return externalAreaEnergy;
	}
        
        public String toString()
        {
            return user_id+";"+ time.toString() +";"+ activity;
        }

}
