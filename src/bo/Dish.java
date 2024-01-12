package bo;

public class Dish 
{
	private int id;
	private String name;
	private float price;
	private String description;
	private String category;
	private int idCard;
	
	
	public Dish () {}
	

	public Dish(String name, float price, String description, String category, int idCard) 
	{
		this.id = 0;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
		this.idCard = idCard;
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


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public int getIdCard() {
		return idCard;
	}


	public void setIdCard(int idCard) {
		this.idCard = idCard;
	};
	
	

	@Override
	public String toString() 
	{
		return "Dish [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", category=" + category + ", idCard=" + idCard + "]";
	}

	
	
	

}
