package bll;

import java.util.List;

import bo.Restaurant;
import dal.DALException;
import dal.RestaurantDAO;

public class RestaurantBLL 
{
	private RestaurantDAO dao;
	
	private static final int NAME_MAX_LENGTH = 50;
	private static final int ADDRESS_MAX_LENGTH = 60;
	private static final int TOWN_MAX_LENGTH = 40;
	private static final int POSTAL_CODE_LENGTH = 5;
	private static final int MIN_LENGTH = 2;
	
	
	//==============================================================
	
	public RestaurantBLL() throws BLLException
	{
		
		try
		{
			this.dao = new RestaurantDAO();
			
		}
		catch (DALException error)
		{
			throw new BLLException("BLL failed to connect to DAO",error);
			
		}
		
		
	}
	
	//==============================================================
	
	
	public List<Restaurant> selectALl() throws BLLException
	{

		try 
		{
			return dao.selectAll();
			
		} 
		catch (DALException error)
		{

			throw new BLLException("Unable to recover datas from DAO",error);
		}
		
	}
	
	//--------------------------------------------------------------
	
	public Restaurant selectById(int id) throws BLLException
	{
		
		try 
		{
			return dao.selectById(id);
		} 
		catch (DALException error)
		{
			
			throw new BLLException("Unable to recover the data from the DAO",error);
		}
		
		
	}
	
	//--------------------------------------------------------------
	
	public List<Restaurant> selectByFk(int fk) throws BLLException
	{
		
		try
		{
				
			return dao.selectByFk(fk);
			
		}
		catch (DALException e) 
		{
			throw new BLLException("Echec de la recuperation des cartes associées au restaurant "+fk, e);
		}
		
	
	}
	
	//--------------------------------------------------------------

	public Restaurant insert(String name, String address, String postalCode, String town, int idCard) throws BLLException
	{
		
		
		//name
		if(name.length() > NAME_MAX_LENGTH)
		{
			throw new BLLException("Restaurant's name is too big", null);
					
		}
		
		if(name.length() < MIN_LENGTH)
		{
			throw new BLLException("Restaurant's name is too small", null);
			
		}
		
		
		//address
		if(address.length() > ADDRESS_MAX_LENGTH)
		{
			throw new BLLException("Restaurant's address is too big", null);
					
		}
		
		if(address.length() < MIN_LENGTH)
		{
			throw new BLLException("Restaurant's adress is too small", null);
			
		}
		
		
		//postalCode
		if(postalCode.length() != POSTAL_CODE_LENGTH)
		{
			throw new BLLException("Restaurant's postal code is not valid", null);
					
		}
		
		
		//town
		if(town.length() > TOWN_MAX_LENGTH)
		{
			throw new BLLException("Restaurant town's name is too big", null);
					
		}
		
		if(town.length() < MIN_LENGTH)
		{
			throw new BLLException("Restaurant town's name is too small", null);
			
		}
		
		
		//idCard
		
		
		
			
		try
		{
			
			Restaurant restaurant = new Restaurant();
			restaurant.setName(name);
			restaurant.setAddress(address);
			restaurant.setPostalCode(postalCode);
			restaurant.setTown(town);
			restaurant.setIdCard(idCard);
			
			dao.insert(restaurant);
			
			return restaurant;
			
			
			
		}
		catch (DALException error) 
		{
			throw new BLLException("Unable to creat a new restaurant to pass to the DAO",error);
		}
		
	}
	
	//--------------------------------------------------------------
	
	public void update(Restaurant restaurant) throws BLLException
	{
			
		//name
		if(restaurant.getName().length() > NAME_MAX_LENGTH)
		{
			throw new BLLException("Restaurant's name is too big", null);
					
		}
		
		if(restaurant.getName().length() < MIN_LENGTH)
		{
			throw new BLLException("Restaurant's name is too small", null);
			
		}
		
		
		//address
		if(restaurant.getAddress().length() > ADDRESS_MAX_LENGTH)
		{
			throw new BLLException("Restaurant's address is too big", null);
					
		}
		
		if(restaurant.getAddress().length() < MIN_LENGTH)
		{
			throw new BLLException("Restaurant's address is too small", null);
			
		}
		
		
		//postalCode
		if(restaurant.getPostalCode().length() != POSTAL_CODE_LENGTH)
		{
			throw new BLLException("Restaurant's postal code is not valid", null);
					
		}
		
		
		//town
		if(restaurant.getTown().length() > TOWN_MAX_LENGTH)
		{
			throw new BLLException("Restaurant town's name is too big", null);
					
		}
		
		if(restaurant.getTown().length() < MIN_LENGTH)
		{
			throw new BLLException("Restaurant town's name is too small", null);
			
		}
		
		
		try 
		{
			dao.update(restaurant);
			
		} catch (DALException error)
		{
			throw new BLLException("DAO failed to update datas",error);
		}
		
	}
	
	//--------------------------------------------------------------
	
	
	public void delete(int id) throws BLLException
	{
		
		try 
		{
			dao.delete(id);
			
		} catch (DALException e)
		{
			throw new BLLException("DAO failed to delete datas",e);
		}
		
	}

}
