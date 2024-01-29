package controller.test;

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
		
		/*
		System.out.println("nom du restaurant?");
		String name =  scan.nextLine();
		
		System.out.println("adresse du restaurant?");
		String adress = scan.nextLine();
		
		System.out.println("code postal du restaurant?");
		String postal = scan.nextLine();
		
		System.out.println("Ville du restaurant?");
		String town = scan.nextLine();
		*/
		try 
		{
			System.out.println("hello");
			
			RestaurantBLL restaurantBLL = new RestaurantBLL();
			
			List<Restaurant> restaurants = restaurantBLL.selectALl();
			
			for(Restaurant restaurant : restaurants)
			{
				System.out.println(restaurant);
			}
			

			
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
		
		scan.close();
		
		

	}

}
