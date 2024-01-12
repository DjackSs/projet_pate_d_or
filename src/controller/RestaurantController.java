package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.RestaurantBLL;
import bll.ScheduleBLL;
import bo.Restaurant;

public class RestaurantController 
{
	public RestaurantController() {}
	
	public void displayMenuAddResto()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UN RESTAURANT\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter un restaurant manuellement\n");
		System.out.printf("2 - Modifier un restaurant à aprtir d'un fichier\n");
		System.out.printf("3 - Retour\n");
	}
	
	public void menuResto (Scanner scan)
	{
		
		int choice = 0;
		
		while (choice != 3)
		{
			this.displayMenuAddResto();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	this.addRestaurant(scan);
		            break;
		        case 2 :
		        	System.out.printf("choix 2\n");
		            break;
		        case 3 :
		        	System.out.printf("exit\n");
		            break;
		        default:
		        	System.out.printf("choix invalide ! \n");
		        	break;
		        
	        }
			
		}
	}
	
	public void addRestaurant (Scanner scan)
	{
		//------------------------------------------------------------------
		//saisis utilisateur
		System.out.printf("Choisissez un nom pour votre restaurant :\n");
		String name = scan.nextLine();
		//scan.nextLine();
		
		System.out.printf("Entrez une adresse pour votre restaurant :\n");
		String address = scan.nextLine();
		//scan.nextLine();
		
		System.out.printf("Entrez le code postal de votre restaurant :\n");
		String postalCode = scan.nextLine();
		//scan.nextLine();
		
		System.out.printf("Entrez la ville de votre restaurant :\n");
		String town = scan.nextLine();
		//scan.nextLine();
		
		//------------------------------------------------------------------
		//création restaurant
		Restaurant newRestaurant = new Restaurant ();
		newRestaurant.setName(name);
		newRestaurant.setAddress(address);
		newRestaurant.setPostalCode(postalCode);
		newRestaurant.setTown(town);
		
		//------------------------------------------------------------------
		//envoi au bll
		
		
		try 
		{
			RestaurantBLL restaurant = new RestaurantBLL();
			
			newRestaurant = restaurant.insert(name, address, postalCode, town, 0);
			
			System.out.println("nouveau restaurant crée :"+ newRestaurant);
			
			
			//------------------------------------------------------------------
			//associer newrestaurant à une horaire
			
			//envoi dans SheldulesController
			//recupère newRestaurant
			ScheduleController restaurantSchedule = new ScheduleController();
			restaurantSchedule.addRestaurantTimeSlots(scan, newRestaurant);
			
			//------------------------------------------------------------------
			//associer newrestaurant à des tables
			
			//envoi dans TableController
			
			
			
		}
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
		
		
	}

}
