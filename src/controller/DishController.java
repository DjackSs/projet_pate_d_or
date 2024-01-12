package controller;

import java.util.Scanner;

import bll.BLLException;
import bll.DishBLL;
import bo.Card;
import bo.Dish;
import bo.Restaurant;

public class DishController 
{
	private DishBLL DishBLL;
	
	public DishController()
	{
		try 
		{
			this.DishBLL = new DishBLL();
		}
		catch (BLLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void displayMenuAddDish()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UN PLAT\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter une plat\n");
		System.out.printf("2 - Retour à la création de carte\n");
	}
	
	public void menuDish (Scanner scan, Card card)
	{
		
		int choice = 0;
		
		while (choice != 3)
		{
			this.displayMenuAddDish();
			
			CardController createCard = new CardController();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	this.addDish(scan, card);
		            break;
		        case 2 :
		        	createCard.menuCard(scan);
		            break;
		        default:
		        	System.out.printf("choix invalide ! \n");
		        	break;
	        }	
		}
	}
	
	
	public void addDish(Scanner scan, Card card) {
		displayMenuAddDish();
		
		System.out.println("Saisissez le nom de l'élément à ajouter");
		String nameDish = scan.nextLine();
		
		System.out.println("Saisissez le prix de l'élément ajouté");
		float priceDish = scan.nextFloat();
		scan.nextLine();

		System.out.println("Saisissez une description pour l'élément ajouté");
		String descriptionDish = scan.nextLine();
		
		System.out.println("Votre plat est-il une entrée[entry] ? Un plat[dish] ? Un dessert[desert] ? Une boisson[beverage] ?");
		String categoryDish = scan.nextLine();
		
		int idCard = card.getId();
		
		Dish newDish = new Dish();
		newDish.setName(nameDish);
		newDish.setPrice(priceDish);
		newDish.setDescription(descriptionDish);
		newDish.setCategory(categoryDish);
		newDish.setIdCard(idCard);
		
		try {
			DishBLL dish = new DishBLL();
			newDish = dish.insert(nameDish, priceDish, descriptionDish, categoryDish, idCard);
		
		} catch (BLLException e) {
			e.printStackTrace();
		}
	
		System.out.println("L'élément " + nameDish + " a été ajouté à la base de donnée");
	}
}
