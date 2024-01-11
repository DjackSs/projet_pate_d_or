package bo;

public class Card {
	private int id;
	private String name;
	
	public Card() {
	}
	
	public Card(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Card [id=" + id + ", name=" + name + "]";
	}
}
