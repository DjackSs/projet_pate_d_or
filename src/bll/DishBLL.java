package bll;

import java.util.Arrays;
import java.util.List;

import bo.Dish;
import dal.DALException;
import dal.DishDAO;
import dal.GenericDAOInterface;

public class DishBLL 
{
	
	private GenericDAOInterface<Dish> dao;
	
	private static final int NAME_MAX_LENGTH = 40;
	private static final int DESCRIPTION_MAX_LENGTH = 250;
	private static final int PRICE_MAX_VALUE = 10_000;
	private static final int MIN_LENGTH = 2;
	
	
	//==============================================================
	
	public DishBLL() throws BLLException
	{
		
		try
		{
			this.dao = new DishDAO();
			
		}
		catch (DALException error)
		{
			throw new BLLException("BLL failed to connect to DAO",error);
			
		}
		
		
	}
	
	//==============================================================
	
	
	public List<Dish> selectALl() throws BLLException
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
	
	public Dish selectById(int id) throws BLLException
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

	public Dish insert(String name, Float price, String description, String category, int idCard) throws BLLException
	{
		
		
		//name
		if(name.length() > NAME_MAX_LENGTH)
		{
			throw new BLLException("Dish's name is too big", null);
					
		}
		
		if(name.length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's name is too small", null);
			
		}
		
		//price
		if(price > PRICE_MAX_VALUE)
		{
			throw new BLLException("Dish's price is too high", null);
			
		}
		
		
		//description
		if(description.length() > DESCRIPTION_MAX_LENGTH)
		{
			throw new BLLException("Dish's description is too big", null);
					
		}
		
		if(description.length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's description is too small", null);
			
		}
		
			
		//category
		List<String> AllowedCategories = Arrays.asList("entry","dish","desert","beverage");
		
		if(!AllowedCategories.contains(category.toLowerCase()) )
		{
			throw new BLLException("Invalid Dish's category", null);
				
		}
		
		
		
		//idCard
			
		try
		{
			
			Dish Dish = new Dish();
			Dish.setName(name);
			Dish.setPrice(price);
			Dish.setDescription(description);
			Dish.setCategory(category.toLowerCase());
			Dish.setIdCard(idCard);
			
			dao.insert(Dish);
			
			return Dish;
			
			
			
		}
		catch (DALException error) 
		{
			throw new BLLException("Unable to creat a new restaurant to pass to the DAO",error);
		}
		
	}
	
	//--------------------------------------------------------------
	
	public void update(String name, Float price, String description, String category, int idCard, Dish Dish) throws BLLException
	{
			
		//name
		if(name.length() > NAME_MAX_LENGTH)
		{
			throw new BLLException("Dish's name is too big", null);
					
		}
		
		if(name.length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's name is too small", null);
			
		}
		
		//price
		if(price > PRICE_MAX_VALUE)
		{
			throw new BLLException("Dish's price is too high", null);
			
		}
		
		
		//description
		if(description.length() > DESCRIPTION_MAX_LENGTH)
		{
			throw new BLLException("Dish's description is too big", null);
					
		}
		
		if(description.length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's description is too small", null);
			
		}
		
			
		//category
		List<String> AllowedCategories = Arrays.asList("entry","dish","desert","beverage");
		
		if(!AllowedCategories.contains(category.toLowerCase()) )
		{
			throw new BLLException("Invalid Dish's category", null);
				
		}
		
		
		//idCard
		
		Dish.setName(name);
		Dish.setPrice(price);
		Dish.setDescription(description);
		Dish.setCategory(category.toLowerCase());
		Dish.setIdCard(idCard);
		
		
		try 
		{
			dao.update(Dish);
			
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
