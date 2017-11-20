package SmartHome;

import java.util.Date;

@Entity
public class Measurement {
	@Id
	private int id;
	@Temporal(TemporalType.TIME)
	private Date time;
	private Environment user_position;
private Float room;
private Float bedroom;
private Float bathroom;
private Float kitchen;
private Float external_area;

}
