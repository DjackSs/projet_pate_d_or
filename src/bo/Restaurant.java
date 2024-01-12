package bo;

public class Restaurant 
{
	private int id;
	private String name;
	private String address;
	private String postalCode;
	private String town;
	
	private int idCard;
	
	
	public Restaurant() {}
	
	public Restaurant(String name, String address, String postal, String town)
	{
		this.id =0;
		this.name = name;
		this.address = address;
		this.postalCode = postal;
		this.town = town;
		
		this.idCard = 0;
		
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

	
	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() 
	{
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", postal_code=" + postalCode
				+ ", town=" + town + ", idCard=" + idCard + "]";
	}
	
	
	
	
	
	
	

}
