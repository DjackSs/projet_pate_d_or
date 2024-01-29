package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

import bll.BLLException;
import bll.CardBLL;
import bll.RestaurantBLL;
import bo.Card;
import bo.Dish;
import bo.Restaurant;
import bo.Schedule;
import bo.Table;

public class CSVReader 
{
	private RestaurantBLL restaurantBLL;
	private CardBLL cardBLL;
	
	
	public CSVReader() 
	{
		try 
		{
			this.restaurantBLL = new RestaurantBLL();
			this.cardBLL = new CardBLL();
			
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
	};
	
	//------------------------------------------------------------------
	
	public void parseRestaurant(String path)
	{
		
		File file = new File(path);
		
		try (Scanner scan =new Scanner(file);)
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
					
						//newRestaurant = restaurantBLL.insert(newRestaurant.getName(), newRestaurant.getAddress(), newRestaurant.getPostalCode(), newRestaurant.getTown(), 0);
						
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
								Schedule newSchedule = new Schedule(LocalTime.parse(dataScheduleOpen[i]), LocalTime.parse(dataScheduleClose[i]));
								
								newRestaurant.addSchedule(newSchedule);
								//newSchedule = this.scheduleBLL.insert(newSchedule.getOpenHour(), newSchedule.getCloseHour() , newSchedule.getIdRestaurant());
								
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
									Table newTable = new Table(Integer.parseInt(dataTablePlace[i]), null);
									
									newRestaurant.addTable(newTable);
									
									//newTable = this.tableBLL.insert(newTable.getNumberPlace(), newTable.getState(), newTable.getIdRestaurant());
									
								}
								
							}
							
						}
							
						try 
						{
							newRestaurant = this.restaurantBLL.insert(newRestaurant);
							
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
		
	}
	
	//------------------------------------------------------------------
	
	public void parseCard(String path)
	{
		
		File file = new File(path);
		
		Card currentCard = new Card();
		
		try (Scanner scan =new Scanner(file);)
		{
			
			while (scan.hasNext())
			{
				String[] datas = scan.nextLine().split(",");
				
				
				System.out.println(Arrays.toString(datas));
				
				if(datas.length != 5)
				{
					System.err.println("Données incomplètes");
					
				}
				else
				{
								
					if(!datas[0].equals( "nomCarte"))
					{
						
						//dish
						Dish newDish = new Dish(datas[1], Float.parseFloat(datas[2]), datas[3], datas[4]);
						currentCard.addDish(newDish);
					
						//card
						if(!datas[0].equals(currentCard.getName()) && currentCard.getName() != null)
						{
							try 
							{	
								currentCard = this.cardBLL.insert(currentCard);
								
								currentCard = new Card();
							
							} 
							catch (BLLException e) 
							{
								e.printStackTrace();
							}
							
							
						}
						
						
						currentCard.setName(datas[0]);
											
						
					}
					
					
					
				}
				
					
			}
			
			try 
			{	
				currentCard = this.cardBLL.insert(currentCard);
			
			} 
			catch (BLLException e) 
			{
				e.printStackTrace();
			}
			
			
			
		} 
		catch (FileNotFoundException error) 
		{
			
			error.printStackTrace();
			
		}
		
		
	}

}
