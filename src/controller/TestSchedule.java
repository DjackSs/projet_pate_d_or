package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.ScheduleBLL;

public class TestSchedule {
	private static Scanner scan;
	private static ScheduleBLL bll;
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des horaires");
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
		System.out.println("Choix 1");
	}
	
	private static int afficherMenu() {
		System.out.println("1. Créer une horaire");
		System.out.println("2. Consulter les horaires");
		System.out.println("3. Supprimer un horaires");
		System.out.println("4. Quitter");
		int choix = scan.nextInt();
		scan.nextLine();
		return choix;
	}
}
