package bo;

import java.util.ArrayList;
import java.util.List;

public class Restaurant 
{
	private int id;
	private String name;
	private String address;
	private String postalCode;
	private String town;
	
	private Card card;
	private List<Schedule> schedules;
	private List<Table> tables;
	
	
	public Restaurant() 
	{
		this.id =0;
		this.schedules = new ArrayList<>();
		this.tables = new ArrayList<>();
	}
	
	public Restaurant(String name, String address, String postal, String town)
	{
		this.id =0;
		this.name = name;
		this.address = address;
		this.postalCode = postal;
		this.town = town;
		
		this.card = null;
		this.schedules = new ArrayList<>();
		this.tables = new ArrayList<>();
		
	}
	
	public void addSchedule(Schedule schedule)
	{
		this.schedules.add(schedule);
	}
	
	public void addTable(Table table)
	{
		this.tables.add(table);
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

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() 
	{
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", postalCode=" + postalCode
				+ ", town=" + town + ", card=" + card + ", schedules=" + schedules.size() + ", tables=" + tables.size() + "]";
	}

	

	
	
	
	
	
	
	
	
	

}
