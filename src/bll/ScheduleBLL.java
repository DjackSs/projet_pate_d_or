package bll;

import java.time.LocalTime;
import java.util.List;

import bo.Schedule;
import dal.DALException;
import dal.ScheduleDAO;

public class ScheduleBLL {
	private ScheduleDAO dao;

	public ScheduleBLL() throws BLLException {
		try {
			dao = new ScheduleDAO();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexon", e);
		}
	}
	
	public List<Schedule> selectAll() throws BLLException {
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la récupération des horaires", e);
		}
	}
	
	public Schedule selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la récupération de l'horaire d'id " + id, e);
		}
	}
	
	public List<Schedule> selectAllByIdRestaurant(int id) throws BLLException {
		try {
			return dao.selectAllByIdRestaurant(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la récupération des horaires du restaurant d'id " + id, e);
		}
	}
	
	
	public Schedule insert(LocalTime openHour, LocalTime closeHour, int idRestaurant) throws BLLException {
		if(openHour.isAfter(closeHour)) {
			throw new BLLException("L'heure d'ouverture " + openHour
									+ "doit être avant l'heure de fermeture " + closeHour, null);
		}
		
		Schedule schedule = new Schedule(openHour, closeHour, idRestaurant);
		
		try {
			dao.insert(schedule);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		
		return schedule;
	}
	
	public void update(Schedule schedule) throws BLLException {
		try {
			dao.update(schedule);
			System.out.println("La modification a été effectué !");
		} catch (DALException e) {
			throw new BLLException("Echec de la mise à jour", e);		
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
