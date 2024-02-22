package controller;

import java.util.List;

import bll.BLLException;
import bll.ScheduleBLL;
import bo.Restaurant;
import bo.Schedule;

public class ScheduleController {
	private ScheduleBLL scheduleBLL;

	public ScheduleController() {
		try {
			scheduleBLL = new ScheduleBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------
	
	public void updateRestaurantTimeSlots (Restaurant restaurant) 
	{
		int choice;
		
		List<Schedule> listTimeSlots = displayRestaurantTimeSlotsUpdateMenu(restaurant);
			
		choice = Menu.SCAN.nextInt();
		Menu.SCAN.nextLine();
		
		if(choice > 0 && choice <= listTimeSlots.size())
		{
			choice -= 1; 
			updateSelectedRestaurantTimeSlot(choice, listTimeSlots);
		}
		
		if(choice == listTimeSlots.size()+1)
		{
			createRestaurantTimeSlots(restaurant);
		}
		
	
	}
	//------------------------------------------------------------------
	
	private void deleteSchedule(Schedule schedule) throws BLLException
	{
		this.scheduleBLL.delete(schedule.getId());
		
		System.out.println("\t" 
				+ "Horaires d'ouverture : " 
				+ schedule.getOpenHour() 
				+ " - "
				+ schedule.getCloseHour()
				+" supprimé");
	}
	
	//------------------------------------------------------------------
	
	private void updateSchedule(Schedule schedule)
	{
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = Menu.SCAN.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = Menu.SCAN.nextLine();
		
		try 
		{
			scheduleBLL.update(schedule, openHour, closeHour);
			System.out.println("La modification a été effectué !");
			
		} 
		catch (BLLException e) 
		{
			for(String message : e.getErrors())
			{
				System.err.println(message);
			}
		}
		
	}
	
	//------------------------------------------------------------------

	private void updateSelectedRestaurantTimeSlot(int choice, List<Schedule> listTimeSlots) 
	{
		Schedule scheduleSelected = listTimeSlots.get(choice);
		

		if(listTimeSlots.size() > 1)
		{
			System.out.println("\t 1 Supprimer ce créneau horaire");
			System.out.println("\t 2 Modifier ce créneau horaire");
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			if(choice == 1)
			{
				try 
				{
					this.deleteSchedule(scheduleSelected);
				} 
				catch (BLLException e) 
				{
					e.printStackTrace();
				}
			}
			else
			{
				this.updateSchedule(scheduleSelected);
			}
			
		}
		else
		{
			this.updateSchedule(scheduleSelected);
		}
		
	
		
	}
	
	//------------------------------------------------------------------

	private List<Schedule> displayRestaurantTimeSlotsUpdateMenu(Restaurant restaurant) 
	{
		System.out.printf("=====================================================================\n");
		System.out.printf("    Choisissez un créneau horaire à modifier du restaurant " + restaurant.getName() + ":\n");
		System.out.printf("=====================================================================\n");
		
		List<Schedule> listTimeSlots = null;
		
		try 
		{
			listTimeSlots = scheduleBLL.selectAllByIdRestaurant(restaurant.getId());
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
			
		for (int i = 0; i <= listTimeSlots.size(); i++) 
		{
			if(i == listTimeSlots.size())
			{
				System.out.println("\t" + (listTimeSlots.size()+1) + ". Ajouter un nouveau créneau horaire");
				System.out.println("\t" + (listTimeSlots.size()+2) + ". Quitter");
			}
			else
			{
				System.out.println("\t" + (i+1) + ". " 
						+ "Horaires d'ouverture : " 
						+ listTimeSlots.get(i).getOpenHour() 
						+ " - "
						+ listTimeSlots.get(i).getCloseHour());
				
			}
			
		}
		
		return listTimeSlots;
	}
	
	//------------------------------------------------------------------
	
	public void createRestaurantTimeSlots(Restaurant restaurant) {
		
		boolean AtLeastOneSchedule = false;
		
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = Menu.SCAN.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = Menu.SCAN.nextLine();

		
		try 
		{			
			scheduleBLL.insert(openHour, closeHour, restaurant.getId());
			
			AtLeastOneSchedule = true;
			
			List<Schedule> restaurantListScheduleOn = scheduleBLL.selectAllByIdRestaurant(restaurant.getId());
			
			System.out.println(restaurant);
			
			for (int i = 0; i < restaurantListScheduleOn.size(); i++) {
				System.out.println("\t" + (i+1) + ". " 
									+ "Horaires d'ouverture : " 
									+ restaurantListScheduleOn.get(i).getOpenHour() 
									+ " - "
									+ restaurantListScheduleOn.get(i).getCloseHour());
			}
			
		} 
		catch (BLLException e) 
		{
			for(String message : e.getErrors())
			{
				System.err.println(message);
			}
			
			if(AtLeastOneSchedule != true)
			{
				this.createRestaurantTimeSlots(restaurant);
			}
			
		}
	}
	
	//------------------------------------------------------------------
	
	public void addNewRestaurantTimeSlots( Restaurant restaurant) {
		
		this.createRestaurantTimeSlots(restaurant);
		
		int choice = 0;
		
		do {
			choice = addNewRestaurantTimeSlotsMenu();
			
			if (choice == 1) {
				createRestaurantTimeSlots(restaurant);
			} else if (choice == 2) {
				return;
			} else {
				return;
			}
			
		} while (choice != 2);
	}

	private static int addNewRestaurantTimeSlotsMenu() {
		int choice;
		System.out.println("Souhaitez-vous rajouter un nouveau créneau horaire ?");
		System.out.println("1 = Oui");
		System.out.println("2 = Non");
		
		choice = Menu.SCAN.nextInt();
		
		Menu.SCAN.nextLine();
		
		return choice;
	}
	
}
