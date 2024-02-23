package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	
	//==================================================================
	
	public void parseRestaurant(String path) throws FileNotFoundException
	{
		
		File file = new File(path);
		
		try (Scanner scan =new Scanner(file);)
		{
			int count = 1;
			
			while (scan.hasNext())
			{
				//tableau attendu : [nom, adresse, codePotal, ville, ouverture, fermeture, nombreDeTable, nombreDePlace]
				String[] datas = scan.nextLine().split(",");
				
				List<String> errors = new ArrayList<>();
				
				
				System.out.println(Arrays.toString(datas));
				
				if(datas.length != 8)
				{
					System.err.println("Données incomplètes lign: "+count);
					
				}
				else
				{
					//la ligne qui contien le nom des colonnes est exclue
					if(!datas[0].equals( "nom"))
					{
						//restaurant
						Restaurant newRestaurant = new Restaurant(datas[0], datas[1], datas[2], datas[3]);
						
						//schedule
						String[] dataScheduleOpen = datas[4].split("/");
						String[] dataScheduleClose = datas[5].split("/");
						
						//table
						String[] dataTableNumber = datas[6].split("/");
						String[] dataTablePlace = datas[7].split("/");
						
						List<Integer> tablePlace = null;
						List<Integer> tableNumber = null;
						
						//---------------------------------------------------------------
						//gestion des erreurs
						
						//Le restaurant doit possèder au moins une heure d'ouverture
						if(dataScheduleOpen.length == 0)
						{
							errors.add("Données pour les horraires incomplètes ligne: "+count);
						}
						
						//chaque heure d'ouverture doit avoir une heure de fermeture
						 if(dataScheduleOpen.length != dataScheduleClose.length)
						{
							 errors.add("Données pour les horraires invalides ligne: "+count);
							
						}
						 else
						 {
							 LocalTime parseTestOpen = null;
							 LocalTime parseTestClose = null;
							 
							 //verification des dates(forma + cohérence)
							 try
							 {
								 for(int i=0; i<dataScheduleOpen.length; i++)
								 {
									 parseTestOpen = LocalTime.parse(dataScheduleOpen[i]);
									 parseTestClose = LocalTime.parse(dataScheduleClose[i]);
									 
									 if(parseTestOpen.isAfter(parseTestClose)) 
									{
										 errors.add("L'heure d'ouverture "+ parseTestOpen +" doit être avant l'heure de fermeture " + parseTestClose+" ligne: "+count);
									}
									 
								 }
								 
							 }
							 catch(DateTimeParseException e)
							{
								 errors.add("Formas de l'heure saisi incorrecte (HH:MM est attendu) ligne: "+count);
								
							}
							 
						 }
						 
						//Le restaurant doit possèder au moin une table
						 if(dataTableNumber.length == 0)
						{
							 errors.add("Données pour les tables incomplètes ligne: "+count);
							
						}
						 
						//chaque table doit avoir un nombre de places
						if(dataTableNumber.length != dataTablePlace.length)
						{
							errors.add("Données pour les tables invalides ligne: "+count);
						}
						else
						{
							//verification du parseInt
							try
							{
								tablePlace = new ArrayList<>();
								tableNumber = new ArrayList<>();
								
								for(int i=0; i<dataTableNumber.length; i++)
								{
									tablePlace.add(Integer.parseInt(dataTableNumber[i]));
									tableNumber.add(Integer.parseInt(dataTablePlace[i]));
								}
								
								
							}
							catch(NumberFormatException e)
							{
								errors.add("Le nombre de place ou le nombre de table n'est pas un nombre ligne: "+count);
							}
							
						}		
						
						if(errors.size() != 0)
						{
							for(String error : errors)
							{
								System.err.println(error);
							}
						}
						else
						{
							//---------------------------------------------------------------
							//insertions dans la base de donnée
							
							try 
							{
								newRestaurant = restaurantBLL.insert(newRestaurant);
						
								for(int i=0; i<dataScheduleOpen.length; i++)
								{
						
									this.scheduleBLL.insert(dataScheduleOpen[i], dataScheduleClose[i] , newRestaurant.getId());
									
								}
								
								
								for(int i=0; i<tablePlace.size(); i++)
								{
									for(int j=0; j< tableNumber.get(i); j++)
									{
										Table newTable = new Table(tablePlace.get(i), null, newRestaurant.getId());
										
										newTable = this.tableBLL.insert(newTable);
										
									}
									
								}
									
								
								
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
				}
				
				count++;	
			}
			
			
		} 
		catch (FileNotFoundException error) 
		{
			
			throw error;
			
		}
		
	}
	
	//==================================================================
	
	public void parseCard(String path) throws FileNotFoundException
	{
		
		File file = new File(path);
		
		Card currentCard = new Card();
		
		int count = 1;
		List<String> AllowedCategories = Arrays.asList("entry","dish","desert","beverage");
		
		try (Scanner scan =new Scanner(file);)
		{
			
			while (scan.hasNext())
			{
				//tableau attendu : [nomCarte, nomPlat, prixPlat, descriptionPlat, categoriePlat]
				String[] datas = scan.nextLine().split(",");
				
				List<String> errors = new ArrayList<>();
				
				System.out.println(Arrays.toString(datas));
				
				if(datas.length != 5)
				{
					System.err.println("Données incomplètes ligne: "+count);
					
				}
				else
				{
					//la ligne qui contien le nom des colonnes est exclue
					if(!datas[0].equals( "nomCarte"))
					{
						
						Card newCard = new Card(datas[0]);
						
						Float dishPrice = 0f;
						
						//---------------------------------------------------------------
						//gestion des erreurs
						
						//verification du parseFloat
						try
						{
							dishPrice = Float.parseFloat(datas[2]);
						}
						catch(NumberFormatException e)
						{
							errors.add("Le prix du plat n'est pas un nombre ligne: "+count);
						}
						
						if(!AllowedCategories.contains(datas[4]))
						{
							errors.add("La catégorie du plat est invalide ligne: "+count);
						}
						
						if(errors.size() != 0)
						{
							for(String error : errors)
							{
								System.err.println(error);
							}
						}
						else
						{
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
								Dish newDish = new Dish(datas[1], dishPrice, datas[3], datas[4], newCard.getId());
								
								newDish = dishBLL.insert(newDish);
								
							
							} 
							catch (BLLException e) 
							{
								e.printStackTrace();
							}
							
						}
								
					}
						
				}
				
				count++;
						
			}
			
			
		} 
		catch (FileNotFoundException error) 
		{
			throw error;
			
		}
		
	}

}
