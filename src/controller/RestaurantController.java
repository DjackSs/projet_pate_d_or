package controller;

import java.io.FileNotFoundException;
import java.util.List;

import bll.BLLException;
import bll.RestaurantBLL;
import bo.Card;
import bo.Restaurant;

public class RestaurantController 
{
	private RestaurantBLL restauranBLL;
	private ScheduleController scheduleController;
	private TableController tableController ;
  
	public RestaurantController() 
	{
		try 
		{
			this.restauranBLL = new RestaurantBLL();
			this.tableController = new TableController();
			this.scheduleController = new ScheduleController();
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
	}

	//------------------------------------------------------------------

	public void displayMenuAddResto()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UN RESTAURANT\n");
		System.out.printf("============================================\n");

		System.out.printf("1 - Ajouter un restaurant manuellement\n");
		System.out.printf("2 - Ajouter un restaurant à partir d'un fichier\n");
		System.out.printf("3 - Retour\n");
	}

	//------------------------------------------------------------------

	public void menuResto ()
	{

		int choice = 0;

		while (choice != 3)
		{			
			this.displayMenuAddResto();

			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();

			switch(choice)
			{

			case 1 :
				this.addRestaurant();
				break;
			case 2 :
				this.parseRestaurant();
				break;
			case 3 :
				System.out.println("Exit\n");
				break;
			default:
				System.out.printf("choix invalide ! \n");
				break;

			}

		}
	}

	//------------------------------------------------------------------

	public void addRestaurant ()
	{

		System.out.printf("Choisissez un nom pour votre restaurant :\n");
		String name = Menu.SCAN.nextLine();

		System.out.printf("Entrez une adresse pour votre restaurant :\n");
		String address = Menu.SCAN.nextLine();

		System.out.printf("Entrez le code postal de votre restaurant :\n");
		String postalCode = Menu.SCAN.nextLine();


		System.out.printf("Entrez la ville de votre restaurant :\n");
		String town = Menu.SCAN.nextLine();

		Restaurant newRestaurant = new Restaurant ();
		newRestaurant.setName(name);
		newRestaurant.setAddress(address);
		newRestaurant.setPostalCode(postalCode);
		newRestaurant.setTown(town);
		newRestaurant.setIdCard(0);

		try 
		{
			newRestaurant = this.restauranBLL.insert(newRestaurant);
			
			System.out.println("nouveau restaurant crée :"+ newRestaurant);
			
			//Gestion de la création de nouveaux créneaux horaires
			scheduleController.addNewRestaurantTimeSlots(newRestaurant);

			//Gestion de la création de nouvelles tables
			tableController.menuTable(newRestaurant);


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
	
	public void parseRestaurant ()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UN RESTAURANT A PARTIR D'UN CSV\n");
		System.out.printf("============================================\n");
		
		System.out.printf("Indiquer le chemin d'accès de votre fichier CSV :\n");
		
		String data = Menu.SCAN.nextLine();
		String mime = ".csv";
		
		CSVReader reader = new CSVReader();
		
		
		try
		{
			reader.parseRestaurant(data.concat(mime));
		}
		catch(FileNotFoundException error)
		{
			System.err.println("Le fichier est introuvable ou invalide");
			
		}
		
		
		
		
	}

	//------------------------------------------------------------------

	public void updateRestaurantList ()
	{

		System.out.printf("============================================\n");
		System.out.printf("    Choisissez un restaurant à modifier :\n");
		System.out.printf("============================================\n");


		int choice = 0;

		try 
		{
			List<Restaurant> restaurants = this.restauranBLL.selectALl();

			this.displayRestaurantList(restaurants);

			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();

			if(choice >= 1 && choice <= restaurants.size())
			{
				updateRestaurantMenu(restaurants.get(choice-1));
			}
		} 
		catch (BLLException e) 
		{

			e.printStackTrace();
		}


	}

	//------------------------------------------------------------------

	public void updateRestaurantMenu (Restaurant restaurant)
	{

		int choice = 0;

		while(choice != 7)
		{
			System.out.printf("============================================\n");
			System.out.printf("    Choisissez votre valeur à modifier :\n");
			System.out.printf("============================================\n");

			System.out.println("1 - Nom : "+ restaurant.getName());
			System.out.println("2 - Adresse : "+ restaurant.getAddress());
			System.out.println("3 - Code postal : "+ restaurant.getPostalCode());
			System.out.println("4 - Ville : "+ restaurant.getTown());
			System.out.println("5 - Horaires");
			System.out.println("6 - Disposition des tables");
			System.out.println("7 - Quitter");

			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();

			switch(choice)
			{
				case 1:
					System.out.printf("Choisissez un nouveau nom pour votre restaurant :\n");
					String newName = Menu.SCAN.nextLine();
					restaurant.setName(newName);
					break;
				case 2:
					System.out.printf("Choisissez une nouvelle adresse pour votre restaurant :\n");
					String newAddress = Menu.SCAN.nextLine();
					restaurant.setAddress(newAddress);
					break;
				case 3:
					System.out.printf("Choisissez un nouveau code postal pour votre restaurant :\n");
					String newPostalCode = Menu.SCAN.nextLine();
					restaurant.setPostalCode(newPostalCode);
					break;
				case 4:
					System.out.printf("Choisissez une nouvelleville pour votre restaurant :\n");
					String newTown = Menu.SCAN.nextLine();
					restaurant.setTown(newTown);
					break;
				case 5:
					scheduleController.updateRestaurantTimeSlots(restaurant);
					break;
		        case 6:
		        	tableController.updateTable(restaurant);
			        break;
		        case 7:
		        	break;
		        default:
		        	System.out.println("Choix invalide");
		        	break;
			}

			try 
			{
				this.restauranBLL.update(restaurant);
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

	//------------------------------------------------------------------

	public void deleteRestaurantList ()
	{

		System.out.printf("============================================\n");
		System.out.printf("    Choisissez un restaurant à supprimer :\n");
		System.out.printf("============================================\n");


		int choice = 0;

		try 
		{
			List<Restaurant> restaurants = this.restauranBLL.selectALl();

			this.displayRestaurantList(restaurants);

			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			if(choice >= 1 && choice <= restaurants.size())
			{
				Restaurant restaurantToDelete = restaurants.get(choice-1);

				System.out.println("Vous avez choisis du supprimer :"+restaurantToDelete);

				this.restauranBLL.delete(restaurantToDelete.getId());

			}


		} 
		catch (BLLException e) 
		{

			e.printStackTrace();
		}



	}


	//------------------------------------------------------------------

	
	public void bindCard(Card card)
	{
		
		System.out.printf("======================================================\n");
		System.out.printf("    Choisissez un restaurant à associer à votre carte :\n");
		System.out.printf("======================================================\n");
		
		
		int choice = 0;
		
		try 
		{
			List<Restaurant> restaurants = this.restauranBLL.selectALl();
			
			this.displayRestaurantList(restaurants);
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			if(choice >= 1 && choice <= restaurants.size())
			{
				Restaurant restaurantToBind = restaurants.get(choice-1);
				
				restaurantToBind.setIdCard(card.getId());
				
				restauranBLL.update(restaurantToBind);
				
				System.out.println("Vous avez affecté la carte "+card.getName()+" au restaurant "+restaurantToBind.getName());
				
			}
				
			
		} 
		catch (BLLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	
	//------------------------------------------------------------------
	
	public void displayRestaurantList (List<Restaurant> restaurants)
	{
		for(int i=0; i<=restaurants.size(); i++)
		{
			if(i < restaurants.size())
			{
				System.out.println(i+1+" - "+restaurants.get(i));
				
			}
			else
			{
				System.out.println(i+1+" - Quitter");		
			}
		}
		
	}
	


}
