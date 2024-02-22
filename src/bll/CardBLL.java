package bll;

import java.util.List;

import bo.Card;
import dal.DALException;
import dal.GenericDAOInterface;
import dal.CardDAO;

public class CardBLL {
	private GenericDAOInterface<Card> dao;
	
	private static final int MIN_LENGTH = 2;
	private static final int NAME_MAX_LENGTH = 30;
	
	public CardBLL() throws BLLException {
		try {
			dao = new CardDAO();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexion", e);
		}
	}
	
	public List<Card> selectAll() throws BLLException {
		
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation des cartes", e);
		}
	}
	
	public Card selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation de la carte d'id " + id, e);
		}
		
	}
	
	
	
	public Card insert(Card card) throws BLLException {
		
		BLLException bllException = new BLLException();
		
		this.controleCard(card, bllException);
		
		
		if(bllException.getErrors().size() != 0)
		{
			throw bllException;
		}
		
		try {
			dao.insert(card);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		return card;
	}
	
	public void update(Card card) throws BLLException {
		
		BLLException bllException = new BLLException();
		
		this.controleCard(card, bllException);
		
		
		if(bllException.getErrors().size() != 0)
		{
			throw bllException;
		}
		
		try {
			dao.update(card);
		} catch (DALException e) {
			throw new BLLException("Echec de la mise a jour", e);
		}
	}
	
	public void delete(int id) throws BLLException {
		try {
			dao.delete(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la suppression", e);
		}
	}
	
	private void controleCard(Card card, BLLException error)
	{
		if (card.getName().length() < MIN_LENGTH) {
			error.addError("Le nom de la carte :"+card.getName()+" est trop court");
		}
		
		if (card.getName().length() > NAME_MAX_LENGTH) {
			error.addError("Le nom de la carte :"+card.getName()+" est trop long");
		}
		
	}
	
}