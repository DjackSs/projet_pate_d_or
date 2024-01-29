package controller.test;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CardBLL;
import bo.Card;
import bo.Restaurant;

public class TestCard 
{
	
	
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		
		
		try 
		{
			System.out.println("hello");
			
			CardBLL cardBLL = new CardBLL();
			
			List<Card> cards = cardBLL.selectAll();
			
			for(Card card : cards)
			{
				System.out.println(card);
			}
			
			Card card = cardBLL.selectById(cards.get(0).getId());
			
			System.out.println(card);

			
		} 
		catch (BLLException e) 
		{
			e.printStackTrace();
		}
		
		
		scan.close();
		
		
	}
	
}