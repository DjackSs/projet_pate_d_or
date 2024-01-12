package controller;

import java.time.LocalTime;
import java.util.List;
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
	
	
	
	public void createRestaurantTimeSlots(Scanner scan, Restaurant restaurantScheduleOff) {
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = scan.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = scan.nextLine();
		System.out.println(closeHour);
		
		try {			
			scheduleBLL.insert(LocalTime.parse(openHour), LocalTime.parse(closeHour), restaurantScheduleOff.getId());
			
			Restaurant restaurantScheduleOn = restaurantBLL.selectById(restaurantScheduleOff.getId());
			
			List<Schedule> restaurantListScheduleOn = scheduleBLL.selectAllByIdRestaurant(restaurantScheduleOff.getId());
			
			System.out.println(restaurantScheduleOn);
			
			for (int i = 0; i < restaurantListScheduleOn.size(); i++) {
				System.out.println("\t" + (i+1) + ". " 
									+ "Horaires d'ouverture : " 
									+ restaurantListScheduleOn.get(i).getOpenHour() 
									+ " - "
									+ restaurantListScheduleOn.get(i).getCloseHour());
			}
			
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue : ");
			e.printStackTrace();
		}
	}
	
	public void addNewRestaurantTimeSlots(Scanner scan, Restaurant restaurant) {
		
		int choix = 0;
		
		do {
			choix = addNewRestaurantTimeSlotsMenu(scan);
			
			if (choix == 1) {
				createRestaurantTimeSlots(scan, restaurant);
			} else if (choix == 2) {
				return;
			} else {
				return;
			}
			
		} while (choix != 2);
	}

	private static int addNewRestaurantTimeSlotsMenu(Scanner scan) {
		int choix;
		System.out.println("Souhaitez-vous rajouter un nouveau créneau horaire ?");
		System.out.println("1 = Oui");
		System.out.println("2 = Non");
		
		choix = scan.nextInt();
		
		scan.nextLine();
		
		return choix;
	}
	
}
