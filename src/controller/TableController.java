package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.TableBLL;
import bo.Restaurant;
import bo.Table;

public class TableController 
{
	public TableController() {}
	
	public void displayMenuAddTable()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UNE TABLE\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter une table\n");
		System.out.printf("2 - Retour\n");
	}
	
	
	public void menuTable (Scanner scan, Restaurant restaurant)
	{
		
		int choice = 0;
		
		while (choice != 3)
		{
			this.displayMenuAddTable();
			
			RestaurantController createRestaurant = new RestaurantController();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	this.addTable(scan, restaurant);
		            break;
		        case 2 :
		        	createRestaurant.menuResto(scan, restaurant);
		            break;
		        default:
		        	System.out.printf("choix invalide ! \n");
		        	break;
	        }	
		}
	}
	
	public void addTable(Scanner scan, Restaurant restaurant) {
		
		
		System.out.println("Saisissez le nombre de table souhaitée");
		int nbTable = scan.nextInt();
		scan.nextLine();
		
		System.out.println("Combien de place souhaitez-vous pour cette table ?");
		int numberPlace = scan.nextInt();
		scan.nextLine();

		String state = null;
		
		for (int i = 0; i < nbTable; i++) {
			Table newTable = new Table();
			newTable.setNumberPlace(numberPlace);
			newTable.setState(state);
			newTable.setIdRestaurant(restaurant.getId());
			
			try {
				TableBLL table = new TableBLL();
				newTable = table.insert(numberPlace, state, restaurant.getId());
			
			} catch (BLLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(nbTable + " tables de " + numberPlace + " places ajoutées.");
		
		menuTable(scan, restaurant);
	}
	
	
}