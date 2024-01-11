package controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.ScheduleBLL;
import bo.Schedule;

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
			choix = displayMenu();
			
			switch (choix) {
			case 1:
				createTimeSlot();
				break;
			case 2:
				displayTimeSlots();
				break;
			case 3:
				deleteTimeSlot();
				break;
			case 4:
				System.out.println("Fermeture du menu.");
				System.out.println("Au revoir");
				break;
			default:
				System.out.println("Saisie non valide");
				break;
			}
			
		} while (choix != 4);
		
		
		scan.close();
		
	}
	
	private static void deleteTimeSlot() {
		try {
			List<Schedule> schedules = bll.selectAll();
			if (schedules.size() == 0) {
				System.out.println("Il n'existe aucun créneau horaire dans la base de données.");
				return;
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Vous avez choisi de supprimer un composant");
		System.out.println("Veuillez saisir l'id du composant à supprimer");
		
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			bll.delete(id);
			System.out.println("Le composant a bien été supprimé");
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
	
	private static void displayTimeSlots() {
		try {
			List<Schedule> schedules = bll.selectAll();
			for (Schedule current : schedules) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
	
	private static void createTimeSlot() {
		System.out.println("Vous avez choisi d'ajouter un créneau horaire");
		
		System.out.println("Veuillez saisir l'horaire d'ouverture");
		String openHour = scan.nextLine();
		
		System.out.println("Veuillez saisir l'horaire de fermeture");
		String closeHour = scan.nextLine();
		
		int choix;
		int idRestaurant = 0;
		
		do {
			choix = displaySetTimeSlotToRestaurantMenu();
			
			if (choix == 1) {				
				System.out.println("Veuillez saisir l'id du restaurant à associer à ce créneau horaire");
				idRestaurant = scan.nextInt();
			} else if (choix == 2) {
				System.out.println("Le créneau horaire créé n'est associé à aucun restaurant.");
				idRestaurant = 0;
			} else {
				System.out.println("Saisie invalide. Choisissez 1 pour confirmer ou 2 "
								+ "pour ne pas confirmer l'association du créneau horaire à un restaurant");
			}
			
		} while (choix != 1 && choix != 2);
		
		try {			
			Schedule scheduleAjoute = bll.insert(LocalTime.parse(openHour), LocalTime.parse(closeHour), idRestaurant);
			System.out.println("L'horaire suivante est ajoutée : " + scheduleAjoute);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue : ");
			e.printStackTrace();
		}
		
	}
	
	private static int displayMenu() {
		System.out.println("1. Créer un créneau horaire");
		System.out.println("2. Consulter les créneaux horaires");
		System.out.println("3. Supprimer un créneau horaire");
		System.out.println("4. Quitter");
		
		int choix = scan.nextInt();
		
		scan.nextLine();
		
		return choix;
	}
	
	private static int displaySetTimeSlotToRestaurantMenu() {
		System.out.println("Souhaitez-vous associer ce créneau horaire à un restaurant ?");
		System.out.println("1 = Oui");
		System.out.println("2 = Non");
		
		int choix = scan.nextInt();
		
		scan.nextLine();
		
		return choix;
	}
}
