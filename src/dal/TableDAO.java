package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Table;

//CRUD
public class TableDAO implements GenericDAOInterface<Table> {
	private static final String TABLE_NAME = " tables ";

	private static final String DELETE = "DELETE FROM"+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET nom = ?, nature = ?, date_sortie = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (number_place, state) VALUES (?,?)";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;

	private Connection cnx;

	public TableDAO() throws DALException {
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

	public Table selectById(int id) throws DALException {
		Table table = null;

		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				table = new Table();
				table.setId(rs.getInt("id"));
				table.setNumberPlace(rs.getInt("number_place"));
				table.setState(rs.getString("state"));

			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations pour l'id "+ id, e);
		}

		return table;
	}

	public void insert(Table table) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, table.getNumberPlace());
			ps.setString(2, table.getState());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				int id = rs.getInt(1);
				table.setId(id);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible d'inserer les donnees.", e);
		}
	}
	
	public void update(Table table) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setInt(1, table.getNumberPlace());
			ps.setString(2, table.getState());
			ps.setInt(3, table.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Impossible de mettre a jour les informations pour l'id "+ table.getId(), e);
		}
	}
	
	public void delete(int id) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(DELETE);
			ps.setInt(1, id);
			int nbLignesSupprimees = ps.executeUpdate();
			
			if (nbLignesSupprimees == 0) {
				throw new DALException("Echec de suppression du composant d'id " + id, null);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de supprimer le composant d'id "+ id, e);
		}
	}
}
