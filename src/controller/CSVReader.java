package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import bll.BLLException;
import bll.CardBLL;
import bll.DishBLL;
import bll.RestaurantBLL;
import bll.ScheduleBLL;
import bll.TableBLL;
import bo.Card;
import bo.Dish;
import bo.Restaurant;
import bo.Table;

public class CSVReader 
{
	private RestaurantBLL restaurantBLL;
	private ScheduleBLL scheduleBLL;
	private TableBLL tableBLL;
	
	
	private CardBLL cardBLL;
	private DishBLL dishBLL;
	
	public CSVReader() 
	{
		try 
		{
			this.restaurantBLL = new RestaurantBLL();
			this.scheduleBLL = new ScheduleBLL();
			this.tableBLL = new TableBLL();
			
			this.cardBLL = new CardBLL();
			this.dishBLL = new DishBLL();
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
						
						try 
						{
							newRestaurant = restaurantBLL.insert(newRestaurant);
							
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
						
									this.scheduleBLL.insert(dataScheduleOpen[i], dataScheduleClose[i] , newRestaurant.getId());
									
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
						
						Card newCard = new Card(datas[0]);
						
						try 
						{
							//card
							if(!datas[0].equals(currentCard.getName()))
							{
								newCard = cardBLL.insert(newCard);
								
								currentCard = newCard;
								
							}
							else
							{
								newCard = currentCard;
								
							}
							
							//dish
							Dish newDish = new Dish(datas[1], Float.parseFloat(datas[2]), datas[3], datas[4], newCard.getId());
							
							newDish = dishBLL.insert(newDish);
							
						
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

}
