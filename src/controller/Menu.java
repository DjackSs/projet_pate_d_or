package controller;

import java.util.Scanner;

import bo.Restaurant;


public class Menu 
{
	private static Scanner scan;

	public static void main(String[] args) 
	{
		
		
		scan = new Scanner(System.in);
		
		RestaurantController restaurantController = new RestaurantController();
		CardController cardController = new CardController();
		
		int choice = 0;
		
		
		//------------------------------------------------------------------
		
		while (choice != 7)
		{
			
			displayMenu();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	restaurantController.menuResto(scan);
		            break;
		        case 2 :
		        	restaurantController.updateRestaurantList(scan);
		            break;
		        case 3 :
		        	restaurantController.deleteRestaurantList(scan);
		            break;
		        case 4 :
		        	cardController.menuCard(scan);
		            break;
		        case 5 :
		        	cardController.updateCardList(scan);
		            break;
		        case 6 :
		        	cardController.deleteCardList(scan);
		            break;
		        case 7 :
		        	System.out.printf("Au revoir\n");
		            break;
		        default:
		        	System.out.printf("choix invalide ! \n");
		        	break;
		        
	        }
			
			
		}
			
		//------------------------------------------------------------------
		
	
		scan.close();
			
		
	}
	
	//==============================================================================
	
	public static void displayMenu()
	{
		
		System.out.printf("============================================\n");
		System.out.printf("    BONJOUR\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter un restaurant\n");
		System.out.printf("2 - Modifier un restaurant\n");
		System.out.printf("3 - Supprimer un restaurant\n");
		System.out.printf("4 - Creer une carte\n");
		System.out.printf("5 - Modifier une carte\n");
		System.out.printf("6 - Supprimer une carte\n");
		System.out.printf("7 - Quitter en apuyant sur 7\n");
		
	
		
	}
	
	
	

}


