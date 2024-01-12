package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.RestaurantBLL;
import bll.ScheduleBLL;
import bll.TableBLL;
import bo.Restaurant;
import bo.Schedule;
import bo.Table;

public class CSVReader 
{
	private RestaurantBLL restaurantBLL;
	private ScheduleBLL scheduleBLL;
	private TableBLL tableBLL;
	
	
	public CSVReader() 
	{
		try 
		{
			this.restaurantBLL = new RestaurantBLL();
			this.scheduleBLL = new ScheduleBLL();
			this.tableBLL = new TableBLL();
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
	};
	
	//------------------------------------------------------------------
	
	public List<Restaurant> parseRestaurant(String path)
	{
		List<Restaurant> restaurants = new ArrayList<>();
		
		File fichier = new File(path);
		
		try (Scanner scan =new Scanner(fichier);)
		{
			
			while (scan.hasNext())
			{
				String[] datas = scan.nextLine().split(",");
				
				
				System.out.println(Arrays.toString(datas));
				
				if(datas.length != 8)
				{
					System.err.println("Données incomplètes");
					
				}
				else
				{
					if(!datas[0].equals( "nom"))
					{
						//restaurant
						Restaurant newRestaurant = new Restaurant(datas[0], datas[1], datas[2], datas[3]);
						
						try 
						{
							newRestaurant = restaurantBLL.insert(newRestaurant.getName(), newRestaurant.getAddress(), newRestaurant.getPostalCode(), newRestaurant.getTown(), 0);
							
							System.out.println(newRestaurant);
							
							//schedule
							String[] dataScheduleOpen = datas[4].split("/");
							String[] dataScheduleClose = datas[5].split("/");
							
							//table
							String[] dataTableNumber = datas[6].split("/");
							String[] dataTablePlace = datas[7].split("/");
							
							if(dataScheduleOpen.length != dataScheduleClose.length)
							{
								System.err.println("Données pour les horraires invalides");
								
							}
							else
							{
								for(int i=0; i<dataScheduleOpen.length; i++)
								{
									Schedule newSchedule = new Schedule(LocalTime.parse(dataScheduleOpen[i]), LocalTime.parse(dataScheduleClose[i]), newRestaurant.getId());
									
									newSchedule = this.scheduleBLL.insert(newSchedule.getOpenHour(), newSchedule.getCloseHour() , newSchedule.getIdRestaurant());
									
								}
							}
							
							if(dataTableNumber.length == 0)
							{
								System.err.println("Données pour les tables incomplètes");
								
							}
							else
							{
								for(int i=0; i<dataTableNumber.length; i++)
								{
									for(int j=0; j< Integer.parseInt(dataTableNumber[i]); j++)
									{
										Table newTable = new Table(Integer.parseInt(dataTablePlace[i]), null, newRestaurant.getId());
										
										newTable = this.tableBLL.insert(newTable.getNumberPlace(), newTable.getState(), newTable.getIdRestaurant());
										
									}
									
								}
								
							}
							
						} 
						catch (BLLException e) 
						{
							e.printStackTrace();
						}
					
					
						
					}
					
					
					
				}
				
					
			}
			
			
		} 
		catch (FileNotFoundException error) 
		{
			
			error.printStackTrace();
			
		}
		
		return restaurants;
	}
	
	//------------------------------------------------------------------

}
