package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.RestaurantBLL;
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
		System.out.printf("2 - Modifier un restaurant à partir d'un fichier\n");
		System.out.printf("3 - Retour\n");
	}
	
	public void menuResto (Scanner scan, Restaurant restaurant)
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
		
		System.out.printf("Entrez une addresse pour votre restaurant :\n");
		String adress = scan.nextLine();
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
		newRestaurant.setAdress(adress);
		newRestaurant.setPostalCode(postalCode);
		newRestaurant.setTown(town);
		
		//------------------------------------------------------------------
		//envoi au bll
		
		
		try 
		{
			RestaurantBLL restaurant = new RestaurantBLL();
			
			newRestaurant = restaurant.insert(name, adress, postalCode, town, 0);
			
			System.out.println("nouveau restaurant crée :"+ newRestaurant);
			
			TableController table = new TableController();
			table.addTable(scan, newRestaurant);
			//------------------------------------------------------------------
			//associer newrestaurant à un horraire
			
			//envoi dans SheldulesController
			//recupère newRestaurant
			
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
