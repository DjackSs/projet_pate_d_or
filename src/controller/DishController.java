package controller;

import java.util.InputMismatchException;
import java.util.List;

import bll.BLLException;
import bll.DishBLL;
import bo.Card;
import bo.Dish;

public class DishController 
{
	private DishBLL dishBLL;
	
	public DishController()
	{
		try 
		{
			this.dishBLL = new DishBLL();
		}
		catch (BLLException e)
		{
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------
	
	public void displayMenuAddDish()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UN PLAT\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter un plat\n");
		System.out.printf("2 - Retour à la création de carte\n");
	}
	
	//------------------------------------------------------------------
	
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
	
	//------------------------------------------------------------------
	
	public void addDish(Card card) {
		
		try
		{
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
			
		
				
			newDish = this.dishBLL.insert(newDish);
			
			System.out.println("L'élément " + nameDish + " a été ajouté à la carte");

		
		}
		catch(InputMismatchException e)
		{
			Menu.SCAN.nextLine();
			
			System.err.println("Saisissez un nombre s'il vous plaît");
			
		}
		catch (BLLException e) 
		{
			for(String message : e.getErrors())
			{
				System.err.println(message);
			}
		}
		
	}
	
	//------------------------------------------------------------------
	
    public void updateDishIntoCard(Card card) 
    {
        // Affiche la liste des tables disponibles pour modification
        System.out.println("Liste des plats pour modification :");
        
        try 
        {
            List<Dish> dishes = this.dishBLL.selectDishesByCardId(card.getId());
            
            for (int i= 0; i <= dishes.size(); i++) 
            {
            	if(i == dishes.size() )
            	{
            		System.out.println(i + 1 + " - Ajouter des plats");
            	}
            	else
            	{
            		System.out.println(i + 1 + " - " + dishes.get(i));
            	}
            }
            
            System.out.println("Choisissez le numéro du plat que vous souhaitez modifier (0 pour annuler) :");
            int choice = Menu.SCAN.nextInt();
            Menu.SCAN.nextLine();
            

            if (choice > 0 && choice <= dishes.size()) 
            {
                Dish selectedDish = dishes.get(choice - 1);
               
            	System.out.println("\t 1 Supprimer ce plat");
    			System.out.println("\t 2 Modifier ce plat");
    			
    			choice = Menu.SCAN.nextInt();
    			Menu.SCAN.nextLine();
    			
    			if(choice == 1)
    			{
    				this.deleteDish(selectedDish);
    			}
    			else
    			{
    				this.updateDish(selectedDish);
    			}
              
            }
            
            if(choice == dishes.size()+1)
            {
            	this.addDish(card);
            }
                 
        		
        }
        catch (BLLException e) 
        {
        	e.printStackTrace();
            System.err.println("Erreur lors de la récupération des plats : " + e.getMessage());
        }
    }
    
    //------------------------------------------------------------------
    
    private void deleteDish(Dish dish)
    {
    	try 
    	{
			this.dishBLL.delete(dish.getId());
		} 
    	catch (BLLException e) 
    	{
			e.printStackTrace();
		}
    	
    	System.out.println("Le plat: "+dish+" à été supprimé");
    	
    	
    }
    
    //------------------------------------------------------------------
    
    private void updateDish(Dish dish)
    {
    	  try 
	      {
	    	System.out.println("Saisissez le nom de l'élément à ajouter à la carte");
			String newNameDish = Menu.SCAN.nextLine();
			
			System.out.println("Saisissez le prix de l'élément ajouté (utilisez les ',' pour les décimales)");
			float newPriceDish = Menu.SCAN.nextFloat();
			Menu.SCAN.nextLine();
	
			System.out.println("Saisissez une description pour l'élément ajouté");
			String newDescriptionDish = Menu.SCAN.nextLine();
			
			System.out.println("Votre plat est-il une entrée[entry] ? Un plat[dish] ? Un dessert[desert] ? Une boisson[beverage] ?");
			String newCategoryDish = Menu.SCAN.nextLine();
			
	      
	        	dish.setName(newNameDish);
	        	dish.setPrice(newPriceDish);
	        	dish.setDescription(newDescriptionDish);
	        	dish.setCategory(newCategoryDish);
	        	
	            this.dishBLL.update(dish);
	            System.out.println("Plat mise à jour avec succès !");
	            
	        }
    	  	catch(InputMismatchException e)
	  		{
	  			Menu.SCAN.nextLine();
	  			
	  			System.err.println("Saisissez un nombre s'il vous plaît");
	  			
	  		}
	        catch (BLLException e) 
	        {
	        	for(String message : e.getErrors())
				{
					System.err.println(message);
				}
	        }
	   
  
    	
    }
    
    
}
