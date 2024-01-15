package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.RestaurantBLL;
import bo.Restaurant;

public class TestRestaurant 
{

	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("nom du restaurant?");
		String name =  scan.nextLine();
		
		System.out.println("adresse du restaurant?");
		String adress = scan.nextLine();
		
		System.out.println("code postal du restaurant?");
		String postal = scan.nextLine();
		
		System.out.println("Ville du restaurant?");
		String town = scan.nextLine();
		
		try 
		{
			RestaurantBLL restaurant = new RestaurantBLL();
			
			restaurant.insert(name, adress, postal, town, 0);
			
			List<Restaurant> restaurants = restaurant.selectALl();
			
			for(Restaurant item : restaurants)
			{
				System.out.println(item);
			}
			
			System.out.println("choisissez un id :");
			int id = scan.nextInt();
			scan.nextLine();
			
			System.out.println(restaurant.selectById(id));
			
			System.out.println("choisissez un id pour delete :");
			int deleteId = scan.nextInt();
			scan.nextLine();
			
			for(int i=0; i<restaurants.size(); i++)
			{
				if(restaurants.get(i).getId() == deleteId)
				{
					restaurant.delete(restaurants.get(i).getId());
				}
				
			}
			
			restaurants = restaurant.selectALl();
			
			for(Restaurant item : restaurants)
			{
				System.out.println(item);
			}
			
			
			
			System.out.println("choisissez un id pour update :");
			id = scan.nextInt();
			scan.nextLine();
			
			System.out.println("choisissez un nouveau nom:");
			name = scan.nextLine();
			
			for(int i=0; i<restaurants.size(); i++)
			{
				if(restaurants.get(i).getId() == id)
				{
					restaurant.update(restaurants.get(i));
				}
				
			}
			
			
			restaurants = restaurant.selectALl();
			
			for(Restaurant item : restaurants)
			{
				System.out.println(item);
			}
			
			
			
			
			
			
			
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
		
		scan.close();
		
		

	}

}
