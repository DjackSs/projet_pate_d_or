package bll;

import java.util.List;

import bo.Card;
import dal.DALException;
import dal.GenericDAOInterface;
import dal.CardDAO;

public class CardBLL {
	private GenericDAOInterface<Card> dao;
	
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
	
	
	
	public Card insert(String name) throws BLLException {
		
		BLLException bllException = new BLLException();
		
		if (name.length() < 2) {
			bllException.addError("Le nom doit faire au moins 2 caractères");
		}
		
		if (name.length() > 30) {
			bllException.addError("Le nom doit faire maximum 30 caractères");
		}
		
		if (bllException.getErrors().size() > 0) {
			throw bllException;
		}
		
		Card card = new Card(name);
		try {
			dao.insert(card);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		return card;
	}
	
	public void update(Card card) throws BLLException {
		
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
	
}