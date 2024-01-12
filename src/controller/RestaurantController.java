package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.RestaurantBLL;
import bll.ScheduleBLL;
import bo.Restaurant;

public class RestaurantController 
{
	private RestaurantBLL restauranBLL;

	public RestaurantController() 
	{
		try 
		{
			this.restauranBLL = new RestaurantBLL();
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
		System.out.printf("2 - Modifier un restaurant à partir d'un fichier\n");
		System.out.printf("3 - Retour\n");
	}

	//------------------------------------------------------------------

	public void menuResto (Scanner scan)
	{

		int choice = 0;

		while (choice != 3)
		{
			this.displayMenuAddResto();

			choice = scan.nextInt();
			scan.nextLine();

			switch(choice)
			{

			case 1 :
				this.addRestaurant(scan);
				break;
			case 2 :
				System.out.printf("choix 2\n");
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

	public void addRestaurant (Scanner scan)
	{

		System.out.printf("Choisissez un nom pour votre restaurant :\n");
		String name = scan.nextLine();

		System.out.printf("Entrez une adresse pour votre restaurant :\n");
		String address = scan.nextLine();

		System.out.printf("Entrez le code postal de votre restaurant :\n");
		String postalCode = scan.nextLine();


		System.out.printf("Entrez la ville de votre restaurant :\n");
		String town = scan.nextLine();

		Restaurant newRestaurant = new Restaurant ();
		newRestaurant.setName(name);
		newRestaurant.setAddress(address);
		newRestaurant.setPostalCode(postalCode);
		newRestaurant.setTown(town);

		try 
		{
			newRestaurant = this.restauranBLL.insert(name, address, postalCode, town, 0);
			
			System.out.println("nouveau restaurant crée :"+ newRestaurant);

			//ScheduleController
			ScheduleController restaurantSchedule = new ScheduleController();
			restaurantSchedule.addRestaurantTimeSlots(scan, newRestaurant);

			//TableController
			TableController table = new TableController();
			table.addTable(scan, newRestaurant);


		}
		catch (BLLException e) 
		{
			e.printStackTrace();
		}



	}

	//------------------------------------------------------------------

	public void updateRestaurantList (Scanner scan)
	{

		System.out.printf("============================================\n");
		System.out.printf("    Choisissez un restaurant à modifier :\n");
		System.out.printf("============================================\n");


		int choice = 0;

		try 
		{
			List<Restaurant> restaurants = this.restauranBLL.selectALl();

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

			choice = scan.nextInt();
			scan.nextLine();

			if(choice >= 1 && choice <= restaurants.size())
			{
				updateRestaurantMenu(restaurants.get(choice-1), scan);
			}
		} 
		catch (BLLException e) 
		{

			e.printStackTrace();
		}


	}

	//------------------------------------------------------------------

	public void updateRestaurantMenu (Restaurant restaurant, Scanner scan)
	{

		int choice = 0;

		while(choice != 7)
		{
			System.out.printf("============================================\n");
			System.out.printf("    Choisissez votre valeure à modifier :\n");
			System.out.printf("============================================\n");

			System.out.println("1 - Nom : "+ restaurant.getName());
			System.out.println("2 - Adresse : "+ restaurant.getAddress());
			System.out.println("3 - Code postal : "+ restaurant.getPostalCode());
			System.out.println("4 - Ville : "+ restaurant.getTown());
			System.out.println("5 - Horraires");
			System.out.println("6 - Disposition des tables");
			System.out.println("7 - Quitter");

			choice = scan.nextInt();
			scan.nextLine();

			switch(choice)
			{
			case 1:
				System.out.printf("Choisissez un nouveau nom pour votre restaurant :\n");
				String newName = scan.nextLine();
				restaurant.setName(newName);
				break;
			case 2:
				System.out.printf("Choisissez une nouvelle adresse pour votre restaurant :\n");
				String newAddress = scan.nextLine();
				restaurant.setAddress(newAddress);
				break;
			case 3:
				System.out.printf("Choisissez un nouveau code postal pour votre restaurant :\n");
				String newPostalCode = scan.nextLine();
				restaurant.setPostalCode(newPostalCode);
				break;
			case 4:
				System.out.printf("Choisissez une nouvelleville pour votre restaurant :\n");
				String newTown = scan.nextLine();
				restaurant.setTown(newTown);
				break;
			case 5:
				//SchedulesController
				break;
			case 6:
				//TableController
				System.out.printf("Modifier la disposition des tables du restaurant :\n");
				TableController table = new TableController();
				table.updateTable(scan, restaurant);
				break;
			case 7:
				break;
			default:
				System.out.println("Choix invalide");
				break;
			}

			try 
			{
				this.restauranBLL.update(restaurant.getName(), restaurant.getAddress(), restaurant.getPostalCode(), restaurant.getTown(), restaurant.getIdCard(), restaurant);
			} 
			catch (BLLException e)
			{
				e.printStackTrace();
			}

		}


	}

	//------------------------------------------------------------------

	public void deleteRestaurantList (Scanner scan)
	{

		System.out.printf("============================================\n");
		System.out.printf("    Choisissez un restaurant à supprimer :\n");
		System.out.printf("============================================\n");


		int choice = 0;

		try 
		{
			List<Restaurant> restaurants = this.restauranBLL.selectALl();

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

			choice = scan.nextInt();
			scan.nextLine();
			
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



}
