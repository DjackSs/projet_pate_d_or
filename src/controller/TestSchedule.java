package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.ScheduleBLL;

public class TestSchedule {
	private static Scanner scan;
	private static ScheduleBLL bll;
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des créneaux horaires");
		scan = new Scanner(System.in);
		try {
			bll = new ScheduleBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veuillez choisir l'action à réaliser");
		
		int choix;
		
		do {
			choix = afficherMenu();
			
			switch (choix) {
			case 1:
				creerHoraires();
				break;
			case 2:
				listerHoraires();
				break;
			case 3:
				supprimerHoraires();
				break;
			case 4:
				System.out.println("Byebye");
				break;
			default:
				System.out.println("Saisie non valide");
				break;
			}
		} while (choix != 4);
		
		
		scan.close();
		
	}
	
	private static void supprimerHoraires() {
		System.out.println("Choix 1");
	}
	
	private static void listerHoraires() {
		System.out.println("Choix 1");
	}
	
	private static void creerHoraires() {
		System.out.println("Vous avez choisi d'ajouter un créneau horaire");
		
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = scan.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = scan.nextLine();
		
		System.out.println("Souhaitez-vous associer ce créneau horaire à un restaurant ?");
		System.out.println("Oui / Non");
		boolean isAssociatedToRestaurant = false;
		String associated = scan.nextLine();
		if(associated.equals("Oui")) {
			
		}
		
	}
	
	private static int afficherMenu() {
		System.out.println("1. Créer un créneau horaire");
		System.out.println("2. Consulter les créneaux horaires");
		System.out.println("3. Supprimer un créneau horaire");
		System.out.println("4. Quitter");
		int choix = scan.nextInt();
		scan.nextLine();
		return choix;
	}
}
