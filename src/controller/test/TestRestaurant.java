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
		
		try 
		{
			System.out.println("hello");
			
			RestaurantBLL restaurantBLL = new RestaurantBLL();
			
			List<Restaurant> restaurants = restaurantBLL.selectAll();
			
			for(Restaurant restaurant : restaurants)
			{
				System.out.println(restaurant);
			}
			
			Restaurant resto = restaurantBLL.selectById(restaurants.get(0).getId());
			
			System.out.println(resto);
			

			
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
		
		scan.close();
		
		

	}

}
