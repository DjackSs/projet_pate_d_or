package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.DisheBLL;
import bo.Dishe;

public class TestDishe 
{
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("nom du plat");
		String name =  scan.nextLine();
		
		System.out.println("prix du plat?");
		Float price = scan.nextFloat();
		scan.nextLine();
		
		System.out.println("description du plat?");
		String description = scan.nextLine();
		
		System.out.println("category du plat? (entry,dish,desert,beverage)");
		String category = scan.nextLine();
		
		try 
		{
			//-----------------------------------
			//insert
			DisheBLL dishe = new DisheBLL();
			
			dishe.insert(name, price, description, category, 0);
			
			//-----------------------------------
			//select all
			
			List<Dishe> dishes = dishe.selectALl();
			
			for(Dishe item : dishes)
			{
				System.out.println(item);
			}
			
			//-----------------------------------
			//select by id
			
			System.out.println("choisissez un id :");
			int id = scan.nextInt();
			scan.nextLine();
			
			System.out.println(dishe.selectById(id));
			
			//-----------------------------------
			//delete
			
			System.out.println("choisissez un id pour delete :");
			int deleteId = scan.nextInt();
			scan.nextLine();
			
			for(int i=0; i<dishes.size(); i++)
			{
				if(dishes.get(i).getId() == deleteId)
				{
					dishe.delete(dishes.get(i).getId());
				}
				
			}
			
			//-----------------------------------
			//update
			
			
			dishes = dishe.selectALl();
			
			for(Dishe item : dishes)
			{
				System.out.println(item);
			}
			
			
			
			System.out.println("choisissez un id pour update :");
			id = scan.nextInt();
			scan.nextLine();
			
			System.out.println("choisissez un nouveau nom de plat :");
			name = scan.nextLine();
			
			for(int i=0; i<dishes.size(); i++)
			{
				if(dishes.get(i).getId() == id)
				{
					dishe.update(name, price, description, category, 0, dishes.get(i));
				}
				
			}
			
			
			dishes = dishe.selectALl();
			
			for(Dishe item : dishes)
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
