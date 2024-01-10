package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Table;

//CRUD
public class TableDAOJdbcImpl implements GenericDAOInterface<Table> {
	private static final String TABLE_NAME = " tables ";
	
	private static final String DELETE = "DELETE FROM"+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET nom = ?, nature = ?, date_sortie = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (number_place, state) VALUES (?,?)";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;
	
	private Connection cnx;
	
	public TableDAOJdbcImpl() throws DALException {
		cnx = ConnexionProvider.getConnection();
	}
	
	public List<Table> selectAll() throws DALException {
		List<Table> tables = new ArrayList<>();
		
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Table table = new Table();
				table.setId(rs.getInt("id"));
				table.setNumberPlace(rs.getInt("number_place"));
				table.setState(rs.getString("state"));
				
				tables.add(table);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations", e);
		}
		
		return tables;
	}
}
