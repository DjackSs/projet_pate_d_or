package controller;

import java.util.InputMismatchException;
import java.util.List;

import bll.BLLException;
import bll.TableBLL;
import bo.Restaurant;
import bo.Table;

public class TableController 
{
	private TableBLL tableBLL;
	
	public TableController() 
	{
		try 
		{
			this.tableBLL = new TableBLL();
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------
	
	public void displayMenuAddTable()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UNE TABLE\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter une table\n");
		System.out.printf("2 - Retour\n");
	}
	
	//------------------------------------------------------------------
	
	public void menuTable (Restaurant restaurant)
	{ 
		this.addTable(restaurant);
		
		int choice = 0;
		
		while(choice != 2)
		{
			this.displayMenuAddTable();
			
			choice = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			switch(choice)
			{
				case 1 :
					this.addTable(restaurant);
					break;
				case 2 :
					break;
				default:
					System.out.println("Choix invalid");
					break;
			}
		}
	}
	
	//------------------------------------------------------------------
	
	public void addTable(Restaurant restaurant) {
		
		boolean AtLeastOneTable = false;
		
		try 
		{
			System.out.println("Saisissez le nombre de table souhaitée");
			int nbTable = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
			
			System.out.println("Combien de place souhaitez-vous pour cette table ?");
			int numberPlace = Menu.SCAN.nextInt();
			Menu.SCAN.nextLine();
	
			String state = null;
		
			
		
			for (int i = 0; i < nbTable; i++) 
			{
				Table newTable = new Table();
				newTable.setNumberPlace(numberPlace);
				newTable.setState(state);
				newTable.setIdRestaurant(restaurant.getId());
			
			
				
				newTable = this.tableBLL.insert(numberPlace, state, restaurant.getId());
				
				AtLeastOneTable = true;
			}
			
			System.out.println(nbTable + " tables de " + numberPlace + " places ajoutées.");
				
		}
		catch(InputMismatchException e)
		{
			Menu.SCAN.nextLine();
			
			System.err.println("Saisissez un nombre s'il vous plaît");
			
			if(AtLeastOneTable != true)
			{
				this.addTable(restaurant);
			}
		}
		catch (BLLException e) 
		{
			for(String message : e.getErrors())
			{
				System.err.println(message);
			}
			
			if(AtLeastOneTable != true)
			{
				this.addTable(restaurant);
			}
		}
			
		
	}
	
	//------------------------------------------------------------------
	
    public void updateTable( Restaurant restaurant) 
    {
        // Affiche la liste des tables disponibles pour modification
        System.out.println("Liste des tables pour modification :");
        
        try 
        {
            List<Table> tables = new TableBLL().selectTablesByRestaurantId(restaurant.getId());
            
            for (int i = 0; i <= tables.size(); i++) 
            {
            	if(i == tables.size())
            	{
            		System.out.println(i + 1 + " - Ajouter des tables");
            	}
            	else
            	{
            		System.out.println(i + 1 + " - " + tables.get(i));
            	}
                
            }

            System.out.println("Choisissez le numéro de la table que vous souhaitez modifier (0 pour annuler) :");
            int choice = Menu.SCAN.nextInt();
            Menu.SCAN.nextLine();

            if (choice > 0 && choice <= tables.size()) 
            {
                Table selectedTable = tables.get(choice - 1);
                
                
                if(tables.size() > 1)
                {
                	System.out.println("\t 1 Supprimer cette table");
        			System.out.println("\t 2 Modifier cette table");
        			
        			choice = Menu.SCAN.nextInt();
        			Menu.SCAN.nextLine();
        			
        			if(choice == 1)
        			{
        				this.deleteTable(selectedTable);
        			}
        			else
        			{
        				this.updateTable(selectedTable);
        			}
                }
                else
                {
                	this.updateTable(selectedTable);
                }

               
            }
            
            if(choice == tables.size()+1)
            {
            	this.addTable(restaurant);
            }
            
           
        } 
        catch (BLLException e) 
        {
            System.err.println("Erreur lors de la récupération des tables : " + e.getMessage());
        }
    }
    
  //------------------------------------------------------------------
    
    private void deleteTable(Table table)
    {
    	try 
    	{
			this.tableBLL.delete(table.getId());
		} 
    	catch (BLLException e) 
    	{
			e.printStackTrace();
		}
    	
    	System.out.println("Table "+table+" supprimée");
    	
    	
    }
    
    //------------------------------------------------------------------
    
    private void updateTable(Table table)
    {

        try 
        {
	        System.out.println("Saisissez le nouveau nombre de places :");
	        int newNumberPlace = Menu.SCAN.nextInt();
	        Menu.SCAN.nextLine();
	        
	        table.setNumberPlace(newNumberPlace);

      
            new TableBLL().updateTable(table);
            System.out.println("Table mise à jour avec succès !");
        }
        catch(InputMismatchException e)
  		{
  			Menu.SCAN.nextLine();
  			
  			System.err.println("Saisissez un nombre s'il vous plaît");
  			
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