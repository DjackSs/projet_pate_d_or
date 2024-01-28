package controller;

import java.util.Scanner;


public class Menu 
{
	public static final Scanner SCAN = new Scanner(System.in);
	
	public Menu (){}

	public static void main(String[] args) 
	{
		RestaurantController restaurantController = new RestaurantController();
		CardController cardController = new CardController();
		
		int choice = 0;
		
		//------------------------------------------------------------------
		
		while (choice != 7)
		{
			
			displayMenu();
			
			choice = SCAN.nextInt();
			SCAN.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	restaurantController.menuResto();
		            break;
		        case 2 :
		        	restaurantController.updateRestaurantList();
		            break;
		        case 3 :
		        	restaurantController.deleteRestaurantList();
		            break;
		        case 4 :
		        	cardController.menuCard();
		            break;
		        case 5 :
		        	cardController.updateCardList();
		            break;
		        case 6 :
		        	cardController.deleteCardList();
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
		
	
		SCAN.close();
			
		
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


