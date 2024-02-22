package bll;

import java.util.Arrays;
import java.util.List;

import bo.Dish;
import dal.DALException;
import dal.DishDAO;

public class DishBLL 
{

	private DishDAO dishDAO;

	private static final int NAME_MAX_LENGTH = 40;
	private static final int DESCRIPTION_MAX_LENGTH = 250;
	private static final int PRICE_MAX_VALUE = 10_000;
	private static final int MIN_LENGTH = 2;


	//==============================================================

	public DishBLL() throws BLLException
	{

		try
		{
			this.dishDAO = new DishDAO();

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
			return dishDAO.selectAll();

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
			return dishDAO.selectById(id);
		} 
		catch (DALException error)
		{

			throw new BLLException("Unable to recover the data from the DAO",error);
		}	
	}


	public List<Dish> selectDishesByCardId(int idCard) throws BLLException {
		try {
			return ((DishDAO) dishDAO).selectByForeignKey("id_card", idCard);
		} catch (DALException e) {
			throw new BLLException("Echec de la récupération des tables par id_card", e);
		}
	}
	//--------------------------------------------------------------

	public Dish insert(Dish newDish) throws BLLException
	{
		BLLException error = new BLLException ();

		//name
		if(newDish.getName().length() > NAME_MAX_LENGTH)
		{
			error.addError("Le nom du plat: "+newDish.getName()+" est trop long");

		}

		if(newDish.getName().length() < MIN_LENGTH)
		{
			error.addError("Le nom du plat: "+newDish.getName()+" est trop court");

		}

		//price
		if(newDish.getPrice() > PRICE_MAX_VALUE)
		{
			error.addError("Le prix du plat: "+newDish.getPrice()+" est trop élevé");

		}


		//description
		if(newDish.getDescription().length() > DESCRIPTION_MAX_LENGTH)
		{
			error.addError("La déscription du plat: "+newDish.getDescription()+" est trop longue");

		}

		if(newDish.getDescription().length() < MIN_LENGTH)
		{
			error.addError("La déscription du plat: "+newDish.getDescription()+" est trop courte");

		}


		//category
		List<String> AllowedCategories = Arrays.asList("entry","dish","desert","beverage");

		if(!AllowedCategories.contains(newDish.getCategory().toLowerCase()) )
		{
			error.addError("La catégorie du plat: "+newDish.getCategory()+" est invalide (entry, dish, desert, beverag sont attendus)");

		}
		
		if(error.getErrors().size() != 0)
		{
			throw error;
		}


		try
		{

			this.dishDAO.insert(newDish);

			return newDish;

		}
		catch (DALException e) 
		{
			throw new BLLException("Unable to creat a new restaurant to pass to the DAO",e);
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
			this.dishDAO.update(dish);

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
			this.dishDAO.update(updatedDish);
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
			this.dishDAO.delete(id);

		} catch (DALException e)
		{
			throw new BLLException("DAO failed to delete datas",e);
		}

	}

}
