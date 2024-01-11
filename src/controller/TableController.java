package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.TableBLL;
import bo.Restaurant;
import bo.Table;

public class TableController 
{
	public TableController() {}
	
	public void addTable(Scanner scan, Restaurant restaurant) {
		
		
		System.out.println("Saisissez le nombre de table souhaitée");
		int nbTable = scan.nextInt();
		scan.nextLine();
		
		System.out.println("Combien de place souhaitez-vous pour cette table ?");
		int numberPlace = scan.nextInt();
		scan.nextLine();

		String state = null;
		
		for (int i = 1; i < nbTable; i++) {
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
		System.out.println(nbTable + " tables de " + numberPlace + " places ajoutées au restaurant.");
	}
	
	
}