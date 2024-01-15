package controller;

import java.util.List;

import bll.BLLException;
import bll.DishBLL;
import bo.Card;
import bo.Dish;

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
		
		System.out.printf("1 - Ajouter un plat\n");
		System.out.printf("2 - Retour à la création de carte\n");
	}
	
	public void menuDish ( Card card)
	{
		
		int choice = 0;
		
		while (choice != 2)
		{
			this.displayMenuAddDish();
			
			CardController createCard = new CardController();
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	this.addDish(card);
		            break;
		        case 2 :
		        	System.out.printf("Retour \n");
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
	
		System.out.println("L'élément " + nameDish + " a été ajouté à la carte");
		menuDish(card);
	}
	
    public void updateDishIntoCard(Card card) {
        // Affiche la liste des tables disponibles pour modification
        System.out.println("Liste des plats pour modification :");
        try {
            List<Dish> dishes = new DishBLL().selectDishesByCardId(card.getId()); // Utilise la BLL pour récupérer la liste des tables
            for (int i = 0; i < dishes.size(); i++) {
                System.out.println(i + 1 + " - " + dishes.get(i));
            }

            // Demande à l'utilisateur de choisir une table à modifier
            System.out.println("Choisissez le numéro du plat que vous souhaitez modifier (0 pour annuler) :");
            int choice = Menu.SCAN.nextInt();
            Menu.SCAN.nextLine();

            if (choice > 0 && choice <= dishes.size()) {
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
        		
        		int IdCard = card.getId();

                // Appelle la méthode de BLL pour effectuer la mise à jour
                try {
                	selectedDish.setName(newNameDish);
                	selectedDish.setPrice(newPriceDish);
                	selectedDish.setDescription(newDescriptionDish);
                	selectedDish.setCategory(newCategoryDish);
                	selectedDish.setIdCard(IdCard);
                    new DishBLL().update(selectedDish);
                    System.out.println("Plat mise à jour avec succès !");
                } catch (BLLException e) {
                    System.err.println("Erreur lors de la mise à jour du plat : " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Opération annulée.");
            }
        } catch (BLLException e) {
        	e.printStackTrace();
            System.err.println("Erreur lors de la récupération des plats : " + e.getMessage());
        }
    }
}
