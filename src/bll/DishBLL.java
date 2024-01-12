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


	public List<Dish> selectAll() throws BLLException
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


	public List<Dish> selectDishesByCardId(int idCard) throws BLLException {
		try {
			return ((DishDAO) dao).selectByForeignKey("id_card", idCard);
		} catch (DALException e) {
			throw new BLLException("Echec de la récupération des tables par id_card", e);
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

			Dish dish = new Dish();
			dish.setName(name);
			dish.setPrice(price);
			dish.setDescription(description);
			dish.setCategory(category.toLowerCase());
			dish.setIdCard(idCard);

			dao.insert(dish);

			return dish;



		}
		catch (DALException error) 
		{
			throw new BLLException("Unable to creat a new restaurant to pass to the DAO",error);
		}

	}

	//--------------------------------------------------------------

	public void update(Dish dish) throws BLLException
	{

		//name
		if(dish.getName().length() > NAME_MAX_LENGTH)
		{
			throw new BLLException("Dish's name is too big", null);

		}

		if(dish.getName().length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's name is too small", null);

		}

		//price
		if(dish.getPrice() > PRICE_MAX_VALUE)
		{
			throw new BLLException("Dish's price is too high", null);

		}


		//description
		if(dish.getDescription().length() > DESCRIPTION_MAX_LENGTH)
		{
			throw new BLLException("Dish's description is too big", null);

		}

		if(dish.getDescription().length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's description is too small", null);

		}


		//category
		List<String> AllowedCategories = Arrays.asList("entry","dish","desert","beverage");

		if(!AllowedCategories.contains(dish.getCategory().toLowerCase()) )
		{
			throw new BLLException("Invalid Dish's category", null);

		}


		try 
		{
			dao.update(dish);

		} catch (DALException error)
		{
			throw new BLLException("Echec de la mise a jour", error);
		}

	}

	//--------------------------------------------------------------

	public void updateDish(int id, String newNameDish, float newPriceDish, String newDescriptionDish, String newCategoryDish, int cardId) throws BLLException {
		//name
		if(newNameDish.length() > NAME_MAX_LENGTH)
		{
			throw new BLLException("Dish's name is too big", null);		
		}

		if(newNameDish.length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's name is too small", null);
		}

		//price
		if(newPriceDish > PRICE_MAX_VALUE)
		{
			throw new BLLException("Dish's price is too high", null);
		}

		//description
		if(newDescriptionDish.length() > DESCRIPTION_MAX_LENGTH)
		{
			throw new BLLException("Dish's description is too big", null);
		}

		if(newDescriptionDish.length() < MIN_LENGTH)
		{
			throw new BLLException("Dish's description is too small", null);
		}

		//category
		List<String> AllowedCategories = Arrays.asList("entry","dish","desert","beverage");

		if(!AllowedCategories.contains(newCategoryDish.toLowerCase()) )
		{
			throw new BLLException("Invalid Dish's category", null);

		}

		Dish updatedDish = new Dish();
		updatedDish.setId(id);
		updatedDish.setName(newNameDish);
		updatedDish.setPrice(newPriceDish);
		updatedDish.setDescription(newDescriptionDish);
		updatedDish.setCategory(newCategoryDish);
		updatedDish.setIdCard(cardId);

		try {
			dao.update(updatedDish);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Echec de la mise a jour de la table d'id " + id, e);
		}
	}


	//---------------------------------------------------------------

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
