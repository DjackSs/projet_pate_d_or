package bo;

import java.time.LocalTime;

public class Schedule {
	private int id;
	private LocalTime openHour;
	private LocalTime closeHour;
	
	
	public Schedule() {}

	public Schedule(LocalTime open_hour, LocalTime close_hour) {
		this.openHour = open_hour;
		this.closeHour = close_hour;
	}

	public Schedule(int id, LocalTime open_hour, LocalTime close_hour) {
		this.id = id;
		this.openHour = open_hour;
		this.closeHour = close_hour;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalTime getOpenHour() {
		return openHour;
	}

	public void setOpenHour(LocalTime openHour) {
		this.openHour = openHour;
	}

	public LocalTime getCloseHour() {
		return closeHour;
	}

	public void setCloseHour(LocalTime closeHour) {
		this.closeHour = closeHour;
	}

	@Override
	public String toString()
	{
		return "Schedule [id=" + id + ", openHour=" + openHour + ", closeHour=" + closeHour + "]";
	}

	

	
	
	
	
}
