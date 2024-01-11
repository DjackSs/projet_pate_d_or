package dal;

import java.util.List;

public interface GenericDAOInterface<T> 
{
	
	List<T> selectAll() throws DALException;
	
	T selectById(int id) throws DALException;
	
	void insert(T objet) throws DALException;
	
	void update(T objet) throws DALException;
	
	void delete(int id) throws DALException;

}
