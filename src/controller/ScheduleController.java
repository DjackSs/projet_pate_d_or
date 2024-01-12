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
	
	public void updateRestaurantTimeSlots (Scanner scan, Restaurant restaurant) {
		int choice;
		
		List<Schedule> listTimeSlots = displayRestaurantTimeSlotsUpdateMenu(restaurant);
			
		choice = scan.nextInt();
			
		scan.nextLine();
			
		if(choice == 0 || choice >= listTimeSlots.size()) {
			return;
		} else {
			choice -= choice;
			
			Schedule scheduleSelected = listTimeSlots.get(choice);
			
			Schedule newSchedule = null;
			try {
				newSchedule = scheduleBLL.selectById(scheduleSelected.getId());
			} catch (BLLException e) {
				e.printStackTrace();
			}
			
			System.out.println("Veuillez saisir l'horaire d'ouverture");
			String openHour = scan.nextLine();
			
			System.out.println("Veuillez saisir l'horaire de fermeture");
			String closeHour = scan.nextLine();
			
			newSchedule.setOpenHour(LocalTime.parse(openHour));
			newSchedule.setCloseHour(LocalTime.parse(closeHour));
			
			try {
				scheduleBLL.update(newSchedule);
			} catch (BLLException e) {
				e.printStackTrace();
			}
			
			return;
		}
	}

	private List<Schedule> displayRestaurantTimeSlotsUpdateMenu(Restaurant restaurant) {
		System.out.printf("=====================================================================\n");
		System.out.printf("    Choisissez un créneau horaire à modifier du restaurant " + restaurant.getName() + ":\n");
		System.out.printf("=====================================================================\n");
		
		List<Schedule> listTimeSlots = null;
		
		try {
			listTimeSlots = scheduleBLL.selectAllByIdRestaurant(restaurant.getId());
		} catch (BLLException e) {
			e.printStackTrace();
		}
			
		for (int i = 0; i < listTimeSlots.size(); i++) {
			System.out.println("\t" + (i+1) + ". " 
								+ "Horaires d'ouverture : " 
								+ listTimeSlots.get(i).getOpenHour() 
								+ " - "
								+ listTimeSlots.get(i).getCloseHour());
		}
			
		System.out.println("\t" + (listTimeSlots.size()+1) + ". Quitter");
		
		return listTimeSlots;
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
		
		int choice = 0;
		
		do {
			choice = addNewRestaurantTimeSlotsMenu(scan);
			
			if (choice == 1) {
				createRestaurantTimeSlots(scan, restaurant);
			} else if (choice == 2) {
				return;
			} else {
				return;
			}
			
		} while (choice != 2);
	}

	private static int addNewRestaurantTimeSlotsMenu(Scanner scan) {
		int choice;
		System.out.println("Souhaitez-vous rajouter un nouveau créneau horaire ?");
		System.out.println("1 = Oui");
		System.out.println("2 = Non");
		
		choice = scan.nextInt();
		
		scan.nextLine();
		
		return choice;
	}
	
}
