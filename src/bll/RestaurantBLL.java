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

	public Restaurant insert(Restaurant restaurant) throws BLLException
	{
		
		BLLException error = new BLLException();
		
		
		this.controleRestaurant(restaurant, error);
		
		
		if(error.getErrors().size() != 0)
		{
			throw error;
		}
		
			
		try
		{		
			dao.insert(restaurant);
			
			return restaurant;

		}
		catch (DALException e) 
		{
			throw new BLLException("Unable to creat a new restaurant to pass to the DAO",e);
		}
		
	}
	
	//--------------------------------------------------------------
	
	public void update(Restaurant restaurant) throws BLLException
	{
			
		BLLException error = new BLLException();
		
		this.controleRestaurant(restaurant, error);
		
		if(error.getErrors().size() != 0)
		{
			throw error;
		}
		
		try 
		{
			dao.update(restaurant);
			
		} catch (DALException e)
		{
			throw new BLLException("DAO failed to update datas",e);
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
	
	//--------------------------------------------------------------
	
	private void controleRestaurant(Restaurant restaurant, BLLException error)
	{
		
		//name
		if(restaurant.getName().length() > NAME_MAX_LENGTH)
		{
			error.addError("Le nom du restaurant :"+ restaurant.getName()+" est trop long");		
		}
		
		if(restaurant.getName().length() < MIN_LENGTH)
		{
			error.addError("Le nom du restaurant :"+ restaurant.getName()+" est trop court");
			
		}
		
		
		//address
		if(restaurant.getAddress().length() > ADDRESS_MAX_LENGTH)
		{
			error.addError("L'adresse du restaurant: "+restaurant.getAddress()+" est trop longue");
					
		}
		
		if(restaurant.getAddress().length() < MIN_LENGTH)
		{
			error.addError("L'adresse du restaurant: "+restaurant.getAddress()+" est trop courte");
			
		}
		
		
		//postalCode
		if(restaurant.getPostalCode().length() != POSTAL_CODE_LENGTH)
		{
			error.addError("Le code postal du restaurant: "+restaurant.getPostalCode()+" n'est pas valide");
					
		}
		
		
		//town
		if(restaurant.getTown().length() > TOWN_MAX_LENGTH)
		{
			error.addError("Le nom de la ville du restaurant: "+restaurant.getTown()+" est trop long");
					
		}
		
		if(restaurant.getTown().length() < MIN_LENGTH)
		{
			error.addError("Le nom de la ville du restaurant: "+restaurant.getTown()+" est trop court");
			
		}
				
		
	}

}
