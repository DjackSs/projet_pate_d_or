package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bo.Schedule;

public class ScheduleDAO implements GenericDAOInterface<Schedule> {
	private Connection cnx;
	
	private static final String TABLE_NAME = "Schedules";
	private static final String DELETE = "DELETE FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET open_hour = ?, close_hour = ?, id_restaurant = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (open_hour, close_hour, id_restaurant) VALUES (?,?,?)";
	private static final String SELECT_ALL_BY_ID_RESTAURANT = "SELECT * FROM Schedules s INNER JOIN Restaurants r ON r.id = id_restaurant Where r.id =  ?";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;

	public ScheduleDAO() throws DALException {
		cnx = ConnexionProvider.getConnection();
	}

	@Override
	public List<Schedule> selectAll() throws DALException {
		List<Schedule> schedules = new ArrayList<>();
		
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setId(rs.getInt("id"));
				schedule.setOpenHour(rs.getTime("open_hour").toLocalTime());
				schedule.setCloseHour(rs.getTime("close_hour").toLocalTime());
				schedule.setIdRestaurant(rs.getInt("id_restaurant"));
				
				schedules.add(schedule);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de récupérer les informations des horaires", e);
		}
		
		return schedules;
	}

	@Override
	public Schedule selectById(int id) throws DALException {
		Schedule schedule = null;
		
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				schedule = new Schedule();
				schedule.setId(rs.getInt("id"));
				schedule.setOpenHour(rs.getTime("open_hour").toLocalTime());
				schedule.setCloseHour(rs.getTime("close_hour").toLocalTime());
				schedule.setIdRestaurant(rs.getInt("id_restaurant"));
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de récupérer les informations de l'horaire d'id " + id, e);
		}
		
		return schedule;
	}
	
	public List<Schedule> selectAllByIdRestaurant(int id) throws DALException {
		List<Schedule> schedules = new ArrayList<>();
		
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_ALL_BY_ID_RESTAURANT);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setId(rs.getInt("id"));
				schedule.setOpenHour(rs.getTime("open_hour").toLocalTime());
				schedule.setCloseHour(rs.getTime("close_hour").toLocalTime());
				schedule.setIdRestaurant(rs.getInt("id_restaurant"));
				
				schedules.add(schedule);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de récupérer les informations des horaires du restaurant d'id " + id, e);
		}
		
		return schedules;
	}

	@Override
	public void insert(Schedule schedule) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			ps.setTime(1, Time.valueOf(schedule.getOpenHour()));
			ps.setTime(2, Time.valueOf(schedule.getCloseHour()));
			
			if(schedule.getIdRestaurant() != 0) {
				ps.setInt(3, schedule.getIdRestaurant());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
				int id = rs.getInt(1);
				schedule.setId(id);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible d'insérer les données", e);
		}
		
	}

	@Override
	public void update(Schedule schedule) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setTime(1, Time.valueOf(schedule.getOpenHour()));
			ps.setTime(2, Time.valueOf(schedule.getCloseHour()));
			
			if (schedule.getIdRestaurant() != 0) {				
				ps.setInt(3, schedule.getIdRestaurant());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			ps.setInt(4, schedule.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Impossible de mettre à jour l'horaire d'id " + schedule.getId(), e);
		}
		
	}

	@Override
	public void delete(int id) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(DELETE);
			ps.setInt(1, id);
			
			int nbLignesSupprimmees = ps.executeUpdate();
			
			if (nbLignesSupprimmees == 0 ) {
				throw new DALException("Echec de suppression du composant d'id", null);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de supprimer l'horaire d'id " + id, e);
		}
	}}
