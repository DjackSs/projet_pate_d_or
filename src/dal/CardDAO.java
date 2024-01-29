package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Card;
import bo.Dish;

public class CardDAO implements GenericDAOInterface<Card> 
{
	
	//-------------select
	private static final String SELECT_CARDS = "select Cards.id, Cards.name, "
												+"Dishes.id as id_dish, Dishes.name as name_dish, Dishes.price, Dishes.description, Dishes.category "
												+"from Cards "
												+"inner join Dishes on Cards.id = Dishes.id_card";
	
	private static final String SELECT_CARDS_BY_ID = "select Cards.id, Cards.name, "
													+"Dishes.id as id_dish, Dishes.name as name_dish, Dishes.price, Dishes.description, Dishes.category "
													+"from Cards "
													+"inner join Dishes on Cards.id = Dishes.id_card "
													+"where Cards.id = ?";
	
	//-------------insert
	private static final String INSERT_INTO_CARD = "INSERT INTO Cards (name) VALUES (?)";
	private static final String INSERT_INTO_DISH = "insert into DISHES ( name, price, description, category, id_card) values ( ?, ?, ?, ?, ?)";
	
	//-------------update
	private static final String UPDATE_CARDS = "UPDATE Cards SET name = ? WHERE id = ?";
	private static final String UPDATE_DISH = "update DISHES set DISHES.name = ?, DISHES.price = ?, DISHES.description = ?, DISHES.category = ?, DISHES.id_card = ? where DISHES.id = ?";
	
	//-------------delete
	private static final String DELETE = "DELETE FROM Cards WHERE id = ?";
	

	private Connection cnx;
	
	//==============================================================

	public CardDAO() throws DALException 
	{
		try 
		{
			cnx = ConnexionProvider.getConnection();
		
		
			if(!cnx.isClosed())
			{
				System.out.println("Connected to the database");
			}
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("Unable to connect to database", error);
		}
	}
	
	//==============================================================

	public List<Card> selectAll() throws DALException 
	{
		List<Card> cards = new ArrayList<>();

		try 
		{
			PreparedStatement ps = cnx.prepareStatement(SELECT_CARDS);
			ResultSet rs = ps.executeQuery();
			
			Card card = new Card();

			while(rs.next()) 
			{
				if(rs.getInt("id") != card.getId())
				{
					if(card.getId() != 0)
					{
						cards.add(card);
						
						card = new Card();		
					}
					
					card.setId(rs.getInt("id"));
					card.setName(rs.getString("name"));
					
				}
				
				Dish dish = new Dish();
				dish.setId(rs.getInt("id_dish"));
				dish.setName(rs.getString("name_dish"));
				dish.setPrice(rs.getFloat("price"));
				dish.setDescription(rs.getString("description"));
				dish.setCategory(rs.getString("category"));
				
				card.addDish(dish);
	
			}
			
			if(card.getId() != 0)
			{
				cards.add(card);
			}
			
		} 
		catch (SQLException e) 
		{
			throw new DALException("Impossible de recuperer les informations", e);
		}
		
		return cards;
	}
	
	//--------------------------------------------------------------

	public Card selectById(int id) throws DALException 
	{
		Card card = null;

		try 
		{
			PreparedStatement ps = cnx.prepareStatement(SELECT_CARDS_BY_ID);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			card = new Card();
			
			while(rs.next()) 
			{
				if(rs.getInt("id") != card.getId())
				{
					
					card.setId(rs.getInt("id"));
					card.setName(rs.getString("name"));
					
				}
				
				Dish dish = new Dish();
				dish.setId(rs.getInt("id_dish"));
				dish.setName(rs.getString("name_dish"));
				dish.setPrice(rs.getFloat("price"));
				dish.setDescription(rs.getString("description"));
				dish.setCategory(rs.getString("category"));
				
				card.addDish(dish);
	
			}

		} 
		catch (SQLException e) 
		{
			throw new DALException("Impossible de recuperer les informations pour l'id "+ id, e);
		}
		
		if(card.getId() != 0)
		{
			return card;
		}
		else
		{
			return null;
		}
		
	}
	
	//--------------------------------------------------------------

	public void insert(Card card) throws DALException {
		try 
		{
			PreparedStatement ps;
			
			//-------------card
			ps = cnx.prepareStatement(INSERT_INTO_CARD, PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, card.getName());
			
			int result = ps.executeUpdate();
			
			if(result == 0)
			{
				throw new DALException("insertion into Cards without effect", null);
			}

			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) 
			{
				int id = rs.getInt(1);
				card.setId(id);
			}
			
			//-------------dish
			ps = cnx.prepareStatement(INSERT_INTO_DISH, PreparedStatement.RETURN_GENERATED_KEYS);
			
			for(Dish dish : card.getDishes())
			{
				ps.setString(1, dish.getName());
				ps.setFloat(2, dish.getPrice());
				ps.setString(3, dish.getDescription());
				ps.setString(4, dish.getCategory());
				ps.setInt(5, card.getId());
					
				ps.executeUpdate();
				
				ResultSet generatedKey = ps.getGeneratedKeys();
				
				if(generatedKey.next()) 
				{
					int primarykey = generatedKey.getInt(1);
					dish.setId(primarykey);
					
				}
				
			}
			
			
			
		} catch (SQLException e) {
			throw new DALException("Impossible d'inserer les donnees.", e);
		}
	}
	
	//--------------------------------------------------------------
	
	public void update(Card card) throws DALException {
		try 
		{
			PreparedStatement ps;
			
			//-------------card
			ps = cnx.prepareStatement(UPDATE_CARDS);
			
			ps.setString(1, card.getName());
			
			ps.setInt(2, card.getId());
			
			ps.executeUpdate();
			
			//-------------dish
			ps = cnx.prepareStatement(UPDATE_DISH);
			
			for(Dish dish : card.getDishes())
			{
				ps.setString(1, dish.getName());
				ps.setFloat(2, dish.getPrice());
				ps.setString(3, dish.getDescription());
				ps.setString(4, dish.getCategory());
				ps.setInt(5, card.getId());
				
				ps.setInt(6, dish.getId());
				
				ps.executeUpdate();
			}
		} 
		catch (SQLException e) 
		{
			throw new DALException("Impossible de mettre a jour les informations pour l'id "+ card.getId(), e);
		}
	}
	
	//--------------------------------------------------------------
	
	public void delete(int id) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(DELETE);
			ps.setInt(1, id);
			int nbDeleteLine = ps.executeUpdate();
			if(nbDeleteLine == 0) {
				throw new DALException("Echec de suppression du composant d'id " + id, null);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de supprimer le composant d'id "+ id, e);
		}
	}
}
