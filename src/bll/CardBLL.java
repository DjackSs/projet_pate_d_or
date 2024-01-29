package bll;

import java.util.Arrays;
import java.util.List;

import bo.Card;
import bo.Dish;
import dal.CardDAO;
import dal.DALException;
import dal.GenericDAOInterface;

public class CardBLL {
	private GenericDAOInterface<Card> dao;
	
	//dish values
	private static final int DISH_NAME_MAX_LENGTH = 40;
	private static final int DISH_DESCRIPTION_MAX_LENGTH = 250;
	private static final int DISH_PRICE_MAX_VALUE = 10_000;
	
	//card values
	private static final int CARD_NAME_MAX_LENGTH = 30;
	
	private static final int MIN_LENGTH = 2;
	
	//==============================================================
	
	public CardBLL() throws BLLException {
		try {
			dao = new CardDAO();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexion", e);
		}
	}
	
	//--------------------------------------------------------------
	
	public List<Card> selectAll() throws BLLException {
		
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation des cartes", e);
		}
	}
	
	//--------------------------------------------------------------
	
	public Card selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation de la carte d'id " + id, e);
		}
		
	}
	
	//--------------------------------------------------------------
	
	public Card insert(Card card) throws BLLException 
	{
	
		if (card.getName().length() > CARD_NAME_MAX_LENGTH) 
		{
			throw new BLLException("Le nom doit faire maximum 30 caractères", null);
		}
		
		if (card.getName().length() < MIN_LENGTH) 
		{
			throw new BLLException("Le nom doit faire au moins 2 caractères", null);
		}
		
		this.controlDish(card.getDishes());
		
		try 
		{
			dao.insert(card);
		} 
		catch (DALException e) 
		{
			throw new BLLException("Echec de l'insertion", e);
		}
		return card;
	}
	
	//--------------------------------------------------------------
	
	public void update(Card card) throws BLLException 
	{
		if (card.getName().length() > CARD_NAME_MAX_LENGTH) 
		{
			throw new BLLException("Le nom doit faire maximum 30 caractères", null);
		}
		
		if (card.getName().length() < MIN_LENGTH) 
		{
			throw new BLLException("Le nom doit faire au moins 2 caractères", null);
		}
		
		this.controlDish(card.getDishes());
		
		try {
			dao.update(card);
		} catch (DALException e) {
			throw new BLLException("Echec de la mise a jour", e);
		}
	}
	
	//--------------------------------------------------------------
	
	public void delete(int id) throws BLLException {
		try {
			dao.delete(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la suppression", e);
		}
	}
	
	//--------------------------------------------------------------
	
	public void controlDish(List<Dish> dishes) throws BLLException
	{
		for(Dish dish : dishes)
		{
			//name
			if(dish.getName().length() > DISH_NAME_MAX_LENGTH)
			{
				throw new BLLException("Dish's name is too big", null);

			}

			if(dish.getName().length() < MIN_LENGTH)
			{
				throw new BLLException("Dish's name is too small", null);

			}

			//price
			if(dish.getPrice() > DISH_PRICE_MAX_VALUE)
			{
				throw new BLLException("Dish's price is too high", null);

			}


			//description
			if(dish.getDescription().length() > DISH_DESCRIPTION_MAX_LENGTH)
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
			
		}
	}
	
}