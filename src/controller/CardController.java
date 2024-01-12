package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CardBLL;
import bo.Card;

public class CardController 
{
	private CardBLL cardBLL;
	
	public CardController()
	{
		try 
		{
			this.cardBLL = new CardBLL();
		} 
		catch (BLLException e)
		{

			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------
	
	public void displayMenuAddCard()
	{
		System.out.printf("============================================\n");
		System.out.printf("    AJOUTER UNE CARTE\n");
		System.out.printf("============================================\n");
		
		System.out.printf("1 - Ajouter une carte manuellement\n");
		System.out.printf("2 - Modifier une carte à partir d'un fichier\n");
		System.out.printf("3 - Retour\n");
	}
	
	//------------------------------------------------------------------
	
	public void menuCard (Scanner scan)
	{
		
		int choice = 0;
		
		while (choice != 3)
		{
			this.displayMenuAddCard();
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
	        {

		        case 1 :
		        	this.addCard(scan);
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
	
	public void addCard (Scanner scan)
	{
		
		System.out.printf("Choisissez un nom pour votre carte :\n");
		String name = scan.nextLine();
		
		Card newCard = new Card ();
		newCard.setName(name);
		
		
		try 
		{	
			newCard = this.cardBLL.insert(name);
			
			System.out.println("nouvelle carte crée :"+ newCard);
			
			//ajouter des plat avec disheController
			
		}
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	//------------------------------------------------------------------
	
	public void updateCardList (Scanner scan)
	{
		
		System.out.printf("============================================\n");
		System.out.printf("    Choisissez une carte à modifier :\n");
		System.out.printf("============================================\n");
		
		
		int choice = 0;
		
		try 
		{
			List<Card> cards = this.cardBLL.selectAll();
			
			for(int i=0; i<=cards.size(); i++)
			{
				
				if(i < cards.size())
				{
					System.out.println(i+1+" - "+cards.get(i));
					
				}
				else
				{
					System.out.println(i+1+" - Quitter");		
				}
			}
			
			choice = scan.nextInt();
			scan.nextLine();
			

			if(choice >= 1 && choice <= cards.size())
			{
				
				updateCardMenu(cards.get(choice-1), scan);
				
			}
			
		
		} 
		catch (BLLException e) 
		{

			e.printStackTrace();
		}
		
		
	}
	
	//------------------------------------------------------------------
	
	public void updateCardMenu (Card card, Scanner scan)
	{
		
		int choice = 0;
		
		while(choice != 3)
		{
			System.out.printf("============================================\n");
			System.out.printf("    Choisissez votre valeure à modifier :\n");
			System.out.printf("============================================\n");
			
			System.out.println("1 - Nom : "+ card.getName());
			System.out.println("2 - Plats dans la carte");
			System.out.println("3 - Quitter");
			
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice)
			{
				case 1:
					System.out.printf("Choisissez un nouveau nom pour votre carte :\n");
					String newName = scan.nextLine();
					card.setName(newName);
					break;
				case 2:
					//disheController
					break;
				case 3:
					break;
				default:
					System.out.println("Choix invalide");
					break;
				
			}
			
			try 
			{
				this.cardBLL.update(card);
			} 
			catch (BLLException e)
			{
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	//------------------------------------------------------------------
	
	
	public void deleteCardList (Scanner scan)
	{
		
		System.out.printf("============================================\n");
		System.out.printf("    Choisissez une carte à supprimer :\n");
		System.out.printf("============================================\n");
		
		
		int choice = 0;
		
		try 
		{
			List<Card> cards = this.cardBLL.selectAll();
			
			for(int i=0; i<=cards.size(); i++)
			{
				if(i < cards.size())
				{
					System.out.println(i+1+" - "+cards.get(i));
					
				}
				else
				{
					System.out.println(i+1+" - Quitter");		
				}
			}
			
			choice = scan.nextInt();
			scan.nextLine();
			
			if(choice >= 1 && choice <= cards.size())
			{
				Card cardToDelete = cards.get(choice-1);
				
				System.out.println("Vous avez choisis du supprimer :"+cardToDelete);
				
				this.cardBLL.delete(cardToDelete.getId());
				
			}
				
			
		} 
		catch (BLLException e) 
		{
			
			e.printStackTrace();
		}
		
	
		
	}
	
	
	//------------------------------------------------------------------

}
