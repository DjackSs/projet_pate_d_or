package controller;

import java.util.List;

import bo.Card;
import bo.Dish;

public class DishController 
{

	public DishController(){}
	
	public void displayMenuAddDish()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UN PLAT\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter un plat\n");
		System.out.printf("2 - Retour à la création de carte\n");
	}
	
	public void menuDish ( Card card)
	{
		this.addDish(card);
		
		int choice = 0;
		
		while (choice != 2)
		{
			this.displayMenuAddDish();
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	this.addDish(card);
		            break;
		        case 2 :
		            break;
		        default:
		        	System.out.printf("choix invalide ! \n");
		        	break;
	        }	
		}
	}
	
	
	public void addDish(Card card) {
		
		System.out.println("Saisissez le nom de l'élément à ajouter à la carte");
		String nameDish = Menu.SCAN.nextLine();
		
		System.out.println("Saisissez le prix de l'élément ajouté (utilisez les ',' pour les décimales)");
		float priceDish = Menu.SCAN.nextFloat();
		Menu.SCAN.nextLine();

		System.out.println("Saisissez une description pour l'élément ajouté");
		String descriptionDish = Menu.SCAN.nextLine();
		
		System.out.println("Votre plat est-il une entrée[entry] ? Un plat[dish] ? Un dessert[desert] ? Une boisson[beverage] ?");
		String categoryDish = Menu.SCAN.nextLine();
		
		
		Dish newDish = new Dish();
		newDish.setName(nameDish);
		newDish.setPrice(priceDish);
		newDish.setDescription(descriptionDish);
		newDish.setCategory(categoryDish);
		
		card.addDish(newDish);
	
		System.out.println("L'élément " + nameDish + " a été ajouté à la carte");
	}
	
    public void updateDishIntoCard(Card card) 
    {
        // Affiche la liste des tables disponibles pour modification
        System.out.println("Liste des plats pour modification :");
       
        List<Dish> dishes = card.getDishes();
        
        for (int i = 0; i < dishes.size(); i++) {
            System.out.println(i + 1 + " - " + dishes.get(i));
        }

        // Demande à l'utilisateur de choisir une table à modifier
        System.out.println("Choisissez le numéro du plat que vous souhaitez modifier (0 pour annuler) :");
        int choice = Menu.SCAN.nextInt();
        Menu.SCAN.nextLine();

        if (choice > 0 && choice <= dishes.size()) 
        {
            Dish selectedDish = dishes.get(choice - 1);

            // Demande à l'utilisateur de saisir les nouvelles informations
    		System.out.println("Saisissez le nom de l'élément à ajouter à la carte");
    		String newNameDish = Menu.SCAN.nextLine();
    		
    		System.out.println("Saisissez le prix de l'élément ajouté (utilisez les ',' pour les décimales)");
    		float newPriceDish = Menu.SCAN.nextFloat();
    		Menu.SCAN.nextLine();

    		System.out.println("Saisissez une description pour l'élément ajouté");
    		String newDescriptionDish = Menu.SCAN.nextLine();
    		
    		System.out.println("Votre plat est-il une entrée[entry] ? Un plat[dish] ? Un dessert[desert] ? Une boisson[beverage] ?");
    		String newCategoryDish = Menu.SCAN.nextLine();
    		
    		selectedDish.setName(newNameDish);
    		selectedDish.setPrice(newPriceDish);
    		selectedDish.setDescription(newDescriptionDish);
    		selectedDish.setCategory(newCategoryDish);
    		

        } 
        else 
        {
            System.out.println("Opération annulée.");
        }
   
   }
    
}
