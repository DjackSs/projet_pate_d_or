package controller;

import java.util.List;
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
		
		while (choice != 2)
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
		        	createRestaurant.menuResto(scan);
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
	
    public void updateTable(Scanner scan, Restaurant restaurant) {
        // Affiche la liste des tables disponibles pour modification
        System.out.println("Liste des tables pour modification :");
        try {
            List<Table> tables = new TableBLL().selectTablesByRestaurantId(restaurant.getId()); // Utilise la BLL pour récupérer la liste des tables
            for (int i = 0; i < tables.size(); i++) {
                System.out.println(i + 1 + " - " + tables.get(i));
            }

            // Demande à l'utilisateur de choisir une table à modifier
            System.out.println("Choisissez le numéro de la table que vous souhaitez modifier (0 pour annuler) :");
            int choice = scan.nextInt();
            scan.nextLine();

            if (choice > 0 && choice <= tables.size()) {
                Table selectedTable = tables.get(choice - 1);

                // Demande à l'utilisateur de saisir les nouvelles informations
                System.out.println("Saisissez le nouveau nombre de places :");
                int newNumberPlace = scan.nextInt();
                scan.nextLine();

                String newState = null;
                int restaurantId = restaurant.getId();

                // Appelle la méthode de BLL pour effectuer la mise à jour
                try {
                    new TableBLL().updateTable(selectedTable.getId(), newNumberPlace, newState, restaurantId);
                    System.out.println("Table mise à jour avec succès !");
                } catch (BLLException e) {
                    System.err.println("Erreur lors de la mise à jour de la table : " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Opération annulée.");
            }
        } catch (BLLException e) {
            System.err.println("Erreur lors de la récupération des tables : " + e.getMessage());
        }
    }
	
	
}