package br.com.uff.ubicomp.activityrecognition.server.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.uff.ubicomp.activityrecognition.server.enums.Activity;

@Entity
@Table(name = "energy_position_activity")
public class EnergyPositionActivity {

	@Id
    @GeneratedValue      
	@Column(name = "id")
	private int id; 
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "finish_time")
	private Date finishTime;
	
	@Column(name = "average_power")
	private Float averagePower;
	
	@Column(name = "median_position")
	private Float medianPosition;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "activity", columnDefinition="enum (Types#CHAR)")
	private Activity activity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Float getAveragePower() {
		return averagePower;
	}

	public void setAveragePower(Float averagePower) {
		this.averagePower = averagePower;
	}

	public Float getMedianPosition() {
		return medianPosition;
	}

	public void setMedianPosition(Float medianPosition) {
		this.medianPosition = medianPosition;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
