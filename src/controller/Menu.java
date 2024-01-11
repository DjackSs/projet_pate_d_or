package controller;

import java.util.Scanner;



public class Menu 
{
	private static Scanner scan;

	public static void main(String[] args) 
	{
		
		
		scan = new Scanner(System.in);
		
		RestaurantController newRestaurant = new RestaurantController();
		
		int choice = 0;
		
		
		//------------------------------------------------------------------
		
		while (choice != 6)
		{
			
			displayMenu();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	newRestaurant.menuResto(scan);
		            break;
		        case 2 :
		        	System.out.printf("2\n");
		            break;
		        case 3 :
		        	System.out.printf("3\n");
		            break;
		        case 4 :
		        	System.out.printf("4\n");
		            break;
		        case 5 :
		        	System.out.printf("5\n");
		            break;
		        case 6 :
		        	System.out.printf("6\n");
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
		System.out.printf("6 - Quitter en apuyant sur 6\n");
		
	
		
	}
	
	
	
	
	//==============================================================================
	
	
	
	
	
	
	

}


