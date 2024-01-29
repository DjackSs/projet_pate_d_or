package bo;

import java.util.ArrayList;
import java.util.List;

public class Card 
{
	private int id;
	private String name;
	
	List<Dish> dishes;
	
	public Card() 
	{
		this.dishes = new ArrayList<>();
	}
	
	public Card(String name) 
	{
		this.id = 0;
		this.name = name;
		this.dishes = new ArrayList<>();
	}
	
	public void addDish(Dish dish)
	{
		this.dishes.add(dish);
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
	
	
	
	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", name=" + name + ", dishes=" + dishes.size() + "]";
	}

	
}
