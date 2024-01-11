package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.TableBLL;
import bo.Table;

public class TestTable {
	private static Scanner scan;
	private static TableBLL bll;
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des tables");
		scan = new Scanner(System.in);
		try {
			bll = new TableBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veuillez choisir l'action à réaliser");
		
		int choice;
		do {
			choice = displayMenu();
			
			switch (choice) {
			case 1:
				createTable();
				break;
			case 2:
				listTable();
				break;
			case 3:
				deleteTable();
				break;
			case 4:
				System.out.println("Byebye");
				break;
			default:
				System.out.println("Saisie non valide");
				break;
			}
		} while (choice != 4);
		
		
		scan.close();
		
	}
	
	private static void deleteTable() {
		try {
			List<Table> tables = bll.selectAll();
			if (tables.size() == 0) {
				System.out.println("Il n'existe aucun composant en base");
				return;
			}
		} catch (BLLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Vous avez choisi de supprimer une table");
		System.out.println("Veuillez saisir l'id de la table à supprimer");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			bll.delete(id);
			System.out.println("La table a bien été supprimée");
		} catch (BLLException e) {
			System.out.println("L'id saisi n'existe pas en base de données");
		}
	}

	private static void listTable() {
		try {
			List<Table> tables = bll.selectAll();
			for (Table current : tables) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void createTable() {
		
		System.out.println("Saisir le nbre de place pour une table");
		int numberPlace = scan.nextInt();
		scan.nextLine();
		
		System.out.println("Saisir le statut de la table");
		String state = scan.nextLine();
		
		System.out.println("Saisir l'index de votre restaurant");
		int idRestaurant = scan.nextInt();

	
		
		try {
			Table addTable = bll.insert(numberPlace, state, idRestaurant);
			System.out.println("Table ajoutée " + addTable);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			for (String error : e.getErrors()) {
				System.out.println("\t" + error);
			}
			e.printStackTrace();
		}
		
	}
	
	private static int displayMenu() {
		System.out.println("1. Créer une table");
		System.out.println("2. Consulter les tables");
		System.out.println("3. Supprimer une table");
		System.out.println("4. Quitter");
		int choice = scan.nextInt();
		scan.nextLine();
		return choice;
	}
}
