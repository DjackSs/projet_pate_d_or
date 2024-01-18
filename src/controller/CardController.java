package controller;

import java.util.List;

import bll.BLLException;
import bll.CardBLL;
import bll.RestaurantBLL;
import bo.Card;
import bo.Restaurant;

public class CardController 
{
	private CardBLL cardBLL;
	private RestaurantBLL restaurantBLL;
	private DishController dishController;
	private RestaurantController restaurantController;
	
	public CardController()
	{
		try 
		{
			this.cardBLL = new CardBLL();
			this.restaurantBLL = new RestaurantBLL();
			this.dishController = new DishController ();
			this.restaurantController = new RestaurantController();
		} 
		catch (BLLException e)
		{

			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------
	
	public void displayMenuAddCard()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UNE CARTE\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter une carte manuellement\n");
		System.out.printf("2 - Ajouter une carte à partir d'un fichier\n");
		System.out.printf("3 - Retour\n");
	}
	
	//------------------------------------------------------------------
	
	public void menuCard ()
	{
		
		int choice = 0;
		
		while (choice != 3)
		{
			this.displayMenuAddCard();
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	this.addCard();
		            break;
		        case 2 :
		        	this.parseCard();
		            break;
		        case 3 :
		        	System.out.printf("exit\n");
		            break;
		        default:
		        	System.out.printf("choix invalide ! \n");
		        	break;
		        
	        }
			
		}
	}
	
	//------------------------------------------------------------------
	
	public void addCard ()
	{
		
		System.out.printf("Choisissez un nom pour votre carte :\n");
		String name = Menu.SCAN.nextLine();
		
		Card newCard = new Card ();
		newCard.setName(name);
		
		
		try 
		{	
			newCard = this.cardBLL.insert(name);
			
			System.out.println("nouvelle carte crée :"+ newCard);
			
			//Gestion de la création de nouveaux plats dans la carte
			this.dishController.menuDish(newCard);
			
			this.bindCard(newCard);
			
		}
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	//------------------------------------------------------------------
	
	public void parseCard ()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UNE CARTE A PARTIR D'UN CSV\n");
		System.out.printf("============================================\n");
		
		System.out.printf("Placez votre fichier CSV dans le dossier et indiquer son nom :\n");
		System.out.printf("Nom du fichier CSV ? \n");
		
		String data = Menu.SCAN.nextLine();
		String mime = ".csv";
		
		CSVReader reader = new CSVReader();
		
		reader.parseCard(data.concat(mime));
		
		
		
	}

	
	//------------------------------------------------------------------
	
	public void bindCardMenu()
	{
		System.out.printf("============================================\n");
		System.out.printf("    Affecter la carte à un restaurant ? \n");
		System.out.printf("============================================\n");
		System.out.printf("1 - Obtenir la liste des restaurant\n");
		System.out.printf("2 - Retour\n");
		
		
	}
	
	public void bindCard(Card card)
	{
		int choice = 0;
		
		while(choice != 2)
		{
			bindCardMenu();
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			switch(choice)
			{
				case 1:
					this.restaurantController.bindCard(card);
					break;
				case 2:
					break;
				default:
					System.out.println("Choix invalide");
					break;
				
			}
			
			
		}
			
		
		
	}
	
	//------------------------------------------------------------------
	
	public void updateCardList ()
	{
		
		System.out.printf("============================================\n");
		System.out.printf("    Choisissez une carte à modifier :\n");
		System.out.printf("============================================\n");
		
		
		int choice = 0;
		
		try 
		{
			List<Card> cards = this.cardBLL.selectAll();
			
			for(int i=0; i<=cards.size(); i++)
			{
				
				if(i < cards.size())
				{
					System.out.println(i+1+" - "+cards.get(i));
					
				}
				else
				{
					System.out.println(i+1+" - Quitter");		
				}
			}
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			

			if(choice >= 1 && choice <= cards.size())
			{
				
				this.updateCardMenu(cards.get(choice-1));
				
			}
			
		
		} 
		catch (BLLException e) 
		{

			e.printStackTrace();
		}
		
		
	}
	
	//------------------------------------------------------------------
	
	public void updateCardMenu (Card card)
	{
		int choice = 0;
		
		while(choice != 5)
		{
			System.out.printf("============================================\n");
			System.out.printf("    Choisissez votre valeur à modifier :\n");
			System.out.printf("============================================\n");
			
			System.out.println("1 - Nom : "+ card.getName());
			System.out.println("2 - Plats dans la carte");
			System.out.println("3 - Supprimer l'affectation de restaurant");
			System.out.println("4 - Affecter à un restaurant");
			System.out.println("5 - Quitter");
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			switch(choice)
			{
				case 1:
					System.out.printf("Choisissez un nouveau nom pour votre carte :\n");
					String newName = Menu.SCAN.nextLine();
					card.setName(newName);
					break;
				case 2:
					this.dishController.updateDishIntoCard(card);
					break;
				case 3:
					this.displayCardRestaurant(card);
					break;
				case 4:
					this.restaurantController.bindCard(card);
					break;
				case 5:
					break;
				default:
					System.out.println("Choix invalide");
					break;
				
			}
			
			try 
			{
				this.cardBLL.update(card);
			} 
			catch (BLLException e)
			{
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	//------------------------------------------------------------------
	
	public void displayCardRestaurant(Card card)
	{
		
		try 
		{
			List<Restaurant> bindedRestaurant = this.restaurantBLL.selectByFk(card.getId());
			
			if(bindedRestaurant.size() != 0)
			{
				deleteRestaurantBindedList(bindedRestaurant);
				
			}
			else
			{
				System.out.println("Cette carte n'est affectée à aucuns restaurant");
			}
			
			
		} catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	//------------------------------------------------------------------
	
	public void deleteRestaurantBindedList (List<Restaurant> restaurants)
	{
		
		System.out.printf("============================================\n");
		System.out.printf("    Choisissez un restaurant à dé-affecter :\n");
		System.out.printf("============================================\n");
		
		
		int choice = 0;
		
		try 
		{
			
			restaurantController.displayRestaurantList(restaurants);

			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			if(choice >= 1 && choice <= restaurants.size())
			{
				Restaurant restaurantToUnbind = restaurants.get(choice-1);
				restaurantToUnbind.setIdCard(0);
				
				this.restaurantBLL.update(restaurantToUnbind);
				
				System.out.println("Vous avez retirez la carte du restaurant "+restaurantToUnbind);
				
				
			}
				
			
		} 
		catch (BLLException e) 
		{
			
			e.printStackTrace();
		}
		
	
		
	}
	
	//------------------------------------------------------------------
	
	
	public void deleteCardList ()
	{
		
		System.out.printf("============================================\n");
		System.out.printf("    Choisissez une carte à supprimer :\n");
		System.out.printf("============================================\n");
		
		
		int choice = 0;
		
		try 
		{
			List<Card> cards = this.cardBLL.selectAll();
			
			for(int i=0; i<=cards.size(); i++)
			{
				if(i < cards.size())
				{
					System.out.println(i+1+" - "+cards.get(i));
					
				}
				else
				{
					System.out.println(i+1+" - Quitter");		
				}
			}
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			if(choice >= 1 && choice <= cards.size())
			{
				Card cardToDelete = cards.get(choice-1);
				
				System.out.println("Vous avez choisis du supprimer :"+cardToDelete);
				
				this.cardBLL.delete(cardToDelete.getId());
				
			}
				
			
		} 
		catch (BLLException e) 
		{
			
			e.printStackTrace();
		}
		
	
		
	}
	
	
	//------------------------------------------------------------------

}
