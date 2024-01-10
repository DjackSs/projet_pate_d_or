package bll;

import bo.Table;
import dal.GenericDAOInterface;

public class TableBLL {
	private GenericDAOInterface<Table> dao;
	
	public TableBLL() throws BLLException {
		dao = new TableDAOJdbcImpl();
	}
}
