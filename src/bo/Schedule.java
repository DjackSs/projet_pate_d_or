package bo;

import java.time.LocalTime;

public class Schedule {
	private int id;
	private LocalTime openHour;
	private LocalTime closeHour;
	private int idRestaurant;
	
	
	public Schedule() {}

	public Schedule(LocalTime open_hour, LocalTime close_hour, int id_restaurant) {
		this.openHour = open_hour;
		this.closeHour = close_hour;
		this.idRestaurant = id_restaurant;
	}

	public Schedule(int id, LocalTime open_hour, LocalTime close_hour, int id_restaurant) {
		this.id = id;
		this.openHour = open_hour;
		this.closeHour = close_hour;
		this.idRestaurant = id_restaurant;
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

	public int getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", openHour=" + openHour + ", closeHour=" + closeHour + ", idRestaurant="
				+ idRestaurant + "]";
	}
	
	
	
}
