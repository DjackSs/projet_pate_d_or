package bll;

import java.util.Arrays;
import java.util.List;

import bo.Table;
import dal.DALException;
import dal.GenericDAOInterface;
import dal.TableDAO;

public class TableBLL {
	private GenericDAOInterface<Table> dao;
	
	public TableBLL() throws BLLException {
		try {
			dao = new TableDAO();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexion", e);
		}
	}
	
	public List<Table> selectAll() throws BLLException {
		
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation des tables", e);
		}
	}
	
	public Table selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation de la table d'id " + id, e);
		}
		
	}
	
    public List<Table> selectTablesByRestaurantId(int restaurantId) throws BLLException {
        try {
            return ((TableDAO) dao).selectByForeignKey("id_restaurant", restaurantId);
        } catch (DALException e) {
            throw new BLLException("Echec de la récupération des tables par id_restaurant", e);
        }
    }
	
	public Table insert(Table table) throws BLLException {
		
		BLLException bllException = new BLLException();
		
		if (table.getNumberPlace() < 2) {
			bllException.addError("Le nombre de place d'une table doit être au minimum de 2.");
		}
		
		List<String> checkState = Arrays.asList(null, "PRES");
		if (!checkState.contains(table.getState())) {
			bllException.addError("Le statut de la table est soit nul soit PRES");
		}
		
		if (bllException.getErrors().size() > 0) {
			throw bllException;
		}
		
		try {
			dao.insert(table);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		return table;
	}
	
	public void update(Table table) throws BLLException {
		
		try {
			dao.update(table);
		} catch (DALException e) {
			throw new BLLException("Echec de la mise a jour", e);
		}
	}
	
    public void updateTable(Table table) throws BLLException {
        BLLException bllException = new BLLException();

        if (table.getNumberPlace() < 2) {
            bllException.addError("Le nombre de place d'une table doit être au minimum de 2.");
        }

        List<String> checkState = Arrays.asList(null, "PRES");
        if (!checkState.contains(table.getState())) {
            bllException.addError("Le statut de la table est soit nul soit PRES");
        }

        if (bllException.getErrors().size() > 0) {
            throw bllException;
        }


        try {
            dao.update(table);
        } catch (DALException e) {
            throw new BLLException("Echec de la mise a jour de la table d'id " + table.getId(), e);
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
