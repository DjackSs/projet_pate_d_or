package bll;

import java.util.List;

import bo.Restaurant;
import bo.RestaurantDAO;
import dal.DALException;
import dal.GenericDAOInterface;

public class RestaurantBLL 
{
	private GenericDAOInterface<Restaurant> dao;
	
	private static final int NAME_MAX_LENGTH = 50;
	private static final int ADRESS_MAX_LENGTH = 60;
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

	public Restaurant insert(String name, String adress, String postalCode, String town, int idCard) throws BLLException
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
		
		
		//adress
		if(adress.length() > ADRESS_MAX_LENGTH)
		{
			throw new BLLException("Restaurant's adress is too big", null);
					
		}
		
		if(adress.length() < MIN_LENGTH)
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
			restaurant.setAdress(adress);
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
	
	public void update(String name, String adress, String postalCode, String town, int idCard, Restaurant restaurant) throws BLLException
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
		
		
		//adress
		if(adress.length() > ADRESS_MAX_LENGTH)
		{
			throw new BLLException("Restaurant's adress is too big", null);
					
		}
		
		if(adress.length() < MIN_LENGTH)
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
		
		restaurant.setName(name);
		restaurant.setAdress(adress);
		restaurant.setPostalCode(postalCode);
		restaurant.setTown(town);
		restaurant.setIdCard(idCard);
		
		
		try 
		{
			dao.update(restaurant);
			
		} catch (DALException error)
		{
			throw new BLLException("DAO failed to update datas",error);
		}
		
	}
	
	//--------------------------------------------------------------
	
	
	public void delete(Restaurant restaurant) throws BLLException
	{
		
		try 
		{
			dao.delete(restaurant);
			
		} catch (DALException e)
		{
			throw new BLLException("DAO failed to delete datas",e);
		}
		
	}

}
