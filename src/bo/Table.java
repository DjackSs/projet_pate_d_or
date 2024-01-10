package bo;

public class Table {
	private int id;
	private int numberPlace;
	private String state;
	private int idRestaurant;
	
	
	public Table() {
	}


	public Table(int numberPlace, String state, int idRestaurant) {
		this.numberPlace = numberPlace;
		this.state = state;
		this.idRestaurant = idRestaurant;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getNumberPlace() {
		return numberPlace;
	}


	public void setNumberPlace(int numberPlace) {
		this.numberPlace = numberPlace;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public int getIdRestaurant() {
		return idRestaurant;
	}


	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}
	
	@Override
	public String toString() {
		return "Table [id=" + id + ", numberPlace=" + numberPlace + ", state=" + state + "]";
	}
	
	
	
}
