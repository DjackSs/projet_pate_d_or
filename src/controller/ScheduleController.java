package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.RestaurantBLL;
import bll.ScheduleBLL;
import bo.Restaurant;
import bo.Schedule;

public class ScheduleController {
	private ScheduleBLL scheduleBLL;
	private RestaurantBLL restaurantBLL;

	public ScheduleController() {
		try {
			scheduleBLL = new ScheduleBLL();
			restaurantBLL = new RestaurantBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
	
	public void addRestaurantTimeSlots(Scanner scan, Restaurant restaurant) {
		// Le but est de créer l'horaire d'ouverture crée et l'afficher,
		// pour demander si le client souhaite en rajouter d'autres ou non.
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = scan.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = scan.nextLine();
		
		System.out.println(restaurant.getId() + "L'id du restaurant crée");
	}
}
