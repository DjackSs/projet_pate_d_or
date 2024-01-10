package bo;

public class Table {
	private int id;
	private int numberPlace;
	private String state;
	
	
	public Table() {
	}


	public Table(int id, int numberPlace, char state) {
		this.id = id;
		this.numberPlace = numberPlace;
		this.state = state;
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


	@Override
	public String toString() {
		return "Table [id=" + id + ", numberPlace=" + numberPlace + ", state=" + state + "]";
	}
	
	
	
}
