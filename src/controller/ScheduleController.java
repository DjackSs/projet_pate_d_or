package controller;

import java.time.LocalTime;
import java.util.List;

import bo.Restaurant;
import bo.Schedule;

public class ScheduleController {
	
	public ScheduleController() {}
	
	public void updateRestaurantTimeSlots (Restaurant restaurant) 
	{
		int choice;
		
		List<Schedule> listTimeSlots = restaurant.getSchedules();
		
		this.displayRestaurantTimeSlotsUpdateMenu(restaurant, listTimeSlots);
			
		choice = Menu.SCAN.nextInt();
		Menu.SCAN.nextLine();
			
		
		switch (choice) 
		{
			case 0 :
				break;
			case 1 :
				choice -= 1; 

				updateSelectedRestaurantTimeSlot(choice, listTimeSlots);
				break;
			case 2 : 
				if(listTimeSlots.size() < 2) 
				{
					System.out.println("Ajout d'un nouveau créneau horaire ?");
					createRestaurantTimeSlots(restaurant);
				} 
				else 
				{
					choice -= 1;
					updateSelectedRestaurantTimeSlot(choice, listTimeSlots);
				}
				break;
			default: 
				break;
		}
	}
	
	private List<Schedule> displayRestaurantTimeSlotsUpdateMenu(Restaurant restaurant, List<Schedule> listTimeSlots) 
	{
		System.out.printf("=====================================================================\n");
		System.out.printf("    Choisissez un créneau horaire à modifier du restaurant " + restaurant.getName() + ":\n");
		System.out.printf("=====================================================================\n");
			
		for (int i = 0; i < listTimeSlots.size(); i++) {
			System.out.println("\t" + (i+1) + ". " 
								+ "Horaires d'ouverture : " 
								+ listTimeSlots.get(i).getOpenHour() 
								+ " - "
								+ listTimeSlots.get(i).getCloseHour());
		}
			
		if (listTimeSlots.size() < 2) 
		{
			System.out.println("\t" + (listTimeSlots.size()+1) + ". Ajouter un nouveau créneau horaire");
			System.out.println("\t" + (listTimeSlots.size()+2) + ". Quitter");
		} 
		else 
		{			
			System.out.println("\t" + (listTimeSlots.size()+1) + ". Quitter");
		}
		
		
		return listTimeSlots;
	}

	private void updateSelectedRestaurantTimeSlot(int choice, List<Schedule> listTimeSlots) 
	{
		Schedule scheduleSelected = listTimeSlots.get(choice);
		
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = Menu.SCAN.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = Menu.SCAN.nextLine();
		
		scheduleSelected.setOpenHour(LocalTime.parse(openHour));
		scheduleSelected.setCloseHour(LocalTime.parse(closeHour));
		
	}

	
	
	public void createRestaurantTimeSlots(Restaurant restaurant) 
	{
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = Menu.SCAN.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = Menu.SCAN.nextLine();
		
	
		Schedule newSchedule = new Schedule (LocalTime.parse(openHour), LocalTime.parse(closeHour));
		
		restaurant.addSchedule(newSchedule);
	
		System.out.println(restaurant);
		
		for (int i = 0; i < restaurant.getSchedules().size(); i++) 
		{
			System.out.println("\t" + (i+1) + ". " 
								+ "Horaires d'ouverture : " 
								+ restaurant.getSchedules().get(i).getOpenHour() 
								+ " - "
								+ restaurant.getSchedules().get(i).getCloseHour());
		}
			

	}
	
	public void addNewRestaurantTimeSlots( Restaurant restaurant) {
		
		this.createRestaurantTimeSlots(restaurant);
		
		int choice = 0;
		
		do {
			addNewRestaurantTimeSlotsMenu();
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			if (choice == 1) {
				createRestaurantTimeSlots(restaurant);
			} else if (choice == 2) {
				return;
			} else {
				return;
			}
			
		} while (choice != 2);
	}

	private void addNewRestaurantTimeSlotsMenu() 
	{		
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UN CRENEAU HORRAIRE?\n");
		System.out.printf("============================================\n");

		System.out.printf("1 - oui\n");
		System.out.printf("2 - non\n");
	
	}
	
}
