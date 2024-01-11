package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CardBLL;
import bo.Card;

public class TestCard {
	private static Scanner scan;
	private static CardBLL bll;
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des cartes");
		scan = new Scanner(System.in);
		try {
			bll = new CardBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veuillez choisir l'action à réaliser");
		
		int choice;
		do {
			choice = displayMenu();
			
			switch (choice) {
			case 1:
				createCard();
				break;
			case 2:
				listCards();
				break;
			case 3:
				deleteCard();
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
	
	private static void deleteCard() {
		try {
			List<Card> cards = bll.selectAll();
			if (cards.size() == 0) {
				System.out.println("Il n'existe aucune carte en base");
				return;
			}
		} catch (BLLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Vous avez choisi de supprimer une carte");
		System.out.println("Veuillez saisir l'id de la carte à supprimer");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			bll.delete(id);
			System.out.println("La carte a bien été supprimée");
		} catch (BLLException e) {
			System.out.println("L'id saisi n'existe pas en base de données");
		}
	}

	private static void listCards() {
		try {
			List<Card> cards = bll.selectAll();
			for (Card current : cards) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void createCard() {
		
		System.out.println("Saisir le nom de la carte");
		String name = scan.nextLine();
	
		
		try {
			Card addCard = bll.insert(name);
			System.out.println("Carte ajoutée " + addCard);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			for (String error : e.getErrors()) {
				System.out.println("\t" + error);
			}
			e.printStackTrace();
		}
		
	}
	
	private static int displayMenu() {
		System.out.println("1. Créer une carte");
		System.out.println("2. Consulter les cartes");
		System.out.println("3. Supprimer une carte");
		System.out.println("4. Quitter");
		int choice = scan.nextInt();
		scan.nextLine();
		return choice;
	}
}