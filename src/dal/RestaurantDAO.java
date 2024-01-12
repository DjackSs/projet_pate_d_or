package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bo.Restaurant;

public class RestaurantDAO implements GenericDAOInterface<Restaurant>
{
	private Connection cnx;
		
	private static final String SELECT_RESTAURANTS = "select Restaurants.* from Restaurants";
	
	private static final String SELECT_RESTAURANTS_BY_ID = "select Restaurants.* from Restaurants where Restaurants.id in(?)";
	
	private static final String INSERT_INTO_RESTAURANTS = "insert into Restaurants ( name, address, postal_code, town, id_card) values ( ?, ?, ?, ?, ?)";

	private static final String UPDATE_RESTAURANTS = "update Restaurants set Restaurants.name = ?, Restaurants.address = ?, Restaurants.postal_code = ?, Restaurants.town = ?, Restaurants.id_card = ? where Restaurants.id = ?";
	
	private static final String DELETE_RESTAURANTS = "delete from Restaurants where Restaurants.id = ?";
		
		
	//==============================================================
	
	
	public RestaurantDAO() throws DALException
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
	

	public List<Restaurant> selectAll() throws DALException
	{
		List<Restaurant> restaurants = new ArrayList<>();
		
		try 
		{
			PreparedStatement query;
			query = cnx.prepareStatement(SELECT_RESTAURANTS);
			
			ResultSet result = query.executeQuery();
			
			while(result.next())
			{
				Restaurant restaurant = new Restaurant();
				restaurant.setId(result.getInt("id"));
				restaurant.setName(result.getString("name"));
				restaurant.setAddress(result.getString("address"));
				restaurant.setPostalCode(result.getString("postal_code"));
				restaurant.setTown(result.getString("town"));
				restaurant.setIdCard(result.getInt("id_card"));	
				
				restaurants.add(restaurant);
				
			}
			
		} 
		catch (SQLException error) 
		{
			
			throw new DALException("Unable to recover datas", error);
		}
		
		return restaurants;
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public Restaurant selectById(int id) throws DALException
	{
		Restaurant restaurant = null;
		
		try 
		{
			
			PreparedStatement query;
			query = cnx.prepareStatement(SELECT_RESTAURANTS_BY_ID);
			
			query.setInt(1, id);
			
			
			ResultSet result = query.executeQuery();
			
			if(result.next())
			{
				restaurant = new Restaurant();
				restaurant.setId(result.getInt("id"));
				restaurant.setName(result.getString("name"));
				restaurant.setAddress(result.getString("address"));
				restaurant.setPostalCode(result.getString("postal_code"));
				restaurant.setTown(result.getString("town"));
				restaurant.setIdCard(result.getInt("id_card"));	
				
				
			}
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("Unable to recover the data", error);
		}
		
		return restaurant;
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public void insert(Restaurant restaurant) throws DALException
	{
		
		try 
		{
			PreparedStatement query;
			query = cnx.prepareStatement(INSERT_INTO_RESTAURANTS,PreparedStatement.RETURN_GENERATED_KEYS);
			
			query.setString(1, restaurant.getName());
			query.setString(2, restaurant.getAddress());
			query.setString(3, restaurant.getPostalCode());
			query.setString(4, restaurant.getTown());
			
			if(restaurant.getIdCard() != 0)
			{
				query.setInt(5, restaurant.getIdCard());
				
			}
			else
			{
				query.setNull(5,Types.INTEGER); 
			}
			
		
			int result = query.executeUpdate();
			
			if(result == 0)
			{
				throw new DALException("insertion without effect", null);
			}
			
			
			ResultSet generatedKey = query.getGeneratedKeys();
			
			if(generatedKey.next()) 
			{
				int primarykey = generatedKey.getInt(1);
				restaurant.setId(primarykey);
				
			}
			
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("data to insert invalid", error);
		}
		
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public void update(Restaurant restaurant) throws DALException
	{
		
		try 
		{
			PreparedStatement query;
			
			query = cnx.prepareStatement(UPDATE_RESTAURANTS);
			
			query.setString(1, restaurant.getName());
			query.setString(2, restaurant.getAddress());
			query.setString(3, restaurant.getPostalCode());
			query.setString(4, restaurant.getTown());
			
			if(restaurant.getIdCard() != 0)
			{
				query.setInt(5, restaurant.getIdCard());
				
			}
			else
			{
				query.setNull(5,Types.INTEGER); 
			}
			
			
			query.setInt(6, restaurant.getId());
			
			int result = query.executeUpdate();
			
			if(result == 0)
			{
				throw new DALException("Data modification without effect", null);
				
			}
			
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("Data modification failed", error);
		}
		
	
	}
	
	//--------------------------------------------------------------
	
	public void delete(int id) throws DALException
	{
		
		try 
		{
			
			PreparedStatement query;
			query = cnx.prepareStatement(DELETE_RESTAURANTS);
	
			query.setInt(1, id);
					
			int result = query.executeUpdate();
			
			if(result == 0)
			{
				throw new DALException("Data deletion without effect", null);
				
			}
			
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("Data deletion failed", error);
		}
			
			
			
	}


}
