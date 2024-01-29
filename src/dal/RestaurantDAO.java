package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bo.Card;
import bo.Restaurant;
import bo.Schedule;
import bo.Table;

public class RestaurantDAO implements GenericDAOInterface<Restaurant>
{
	private Connection cnx;
	
	//--------------------------------------------------------------
	
	
	//-------------select
	private static final String SELECT_RESTAURANTS = "select Restaurants.id, Restaurants.name, Restaurants.address, Restaurants.postal_code, Restaurants.town, "
													+"Schedules.id as id_schedule, Schedules.open_hour, Schedules.close_hour, "
													+"Tables.id as id_table, Tables.number_place, Tables.state "
													+"from Restaurants "
													+"inner join schedules on Restaurants.id = Schedules.id_restaurant "
													+"inner join Tables on restaurants.id = Tables.id_restaurant";
													
	
	private static final String SELECT_RESTAURANTS_BY_ID = "select Restaurants.id, Restaurants.name, Restaurants.address, Restaurants.postal_code, Restaurants.town, Restaurants.id_card, "
															+"Schedules.id as id_schedule, Schedules.open_hour, Schedules.close_hour, "
															+"Tables.id as id_table, Tables.number_place, Tables.state "
															+"from Restaurants "
															+"inner join schedules on Restaurants.id = Schedules.id_restaurant "
															+"inner join Tables on restaurants.id = Tables.id_restaurant "
															+"where Restaurants.id = ?";
	
	
	private static final String SELECT_RESTAURANTS_BY_FK = "select Restaurants.id, Restaurants.name, Restaurants.address, Restaurants.postal_code, Restaurants.town, "
															+"Cards.id as id_card, Cards.name as name_card "
															+"from Restaurants "
															+"inner join Cards on Cards.id = Restaurants.id_card "
															+"where Restaurants.id_card = ?";
	

	
	//-------------insert
	private static final String INSERT_INTO_RESTAURANTS = "insert into Restaurants ( name, address, postal_code, town, id_card) values ( ?, ?, ?, ?, ?)";
	private static final String INSERT_INTO_SCHEDULES = "INSERT INTO Schedules (open_hour, close_hour, id_restaurant) VALUES (?,?,?)";
	private static final String INSERT_INTO_TABLES = "INSERT INTO Tables (number_place, state, id_restaurant) VALUES (?,?,?)";
	
	
	
	//-------------update
	private static final String UPDATE_RESTAURANTS = "update Restaurants set Restaurants.name = ?, Restaurants.address = ?, Restaurants.postal_code = ?, Restaurants.town = ?, Restaurants.id_card = ? where Restaurants.id = ?";
	private static final String UPDATE_SCHEDULES = "UPDATE Schedules SET open_hour = ?, close_hour = ?, id_restaurant = ? WHERE id = ?";
	private static final String UPDATE_TABLES = "UPDATE Tables SET number_place = ?, state = ?, id_restaurant = ? WHERE id = ?";
	
	//-------------delete
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
			
			
			Restaurant restaurant = new Restaurant();
			Schedule schedule = new Schedule();
			
			List<Integer> id_table = new ArrayList<>();
			
			while(result.next())
			{
				
				if(result.getInt("id_schedule") != schedule.getId())
				{
					
					if(schedule.getId() != 0)
					{
						restaurant.addSchedule(schedule);
						
						schedule = new Schedule();
					}
					
					schedule.setId(result.getInt("id_schedule"));
					schedule.setOpenHour(result.getTime("open_hour").toLocalTime());
					schedule.setCloseHour(result.getTime("close_hour").toLocalTime());
					
				}
				
				if(result.getInt("id") != restaurant.getId())
				{
					
					if(restaurant.getId() != 0)
					{
						restaurants.add(restaurant);
						
						restaurant = new Restaurant();
					}
					
					restaurant.setId(result.getInt("id"));
					restaurant.setName(result.getString("name"));
					restaurant.setAddress(result.getString("address"));
					restaurant.setPostalCode(result.getString("postal_code"));
					restaurant.setTown(result.getString("town"));
					
					
			
				}
				
				
				
				if(!id_table.contains(result.getInt("id_table")))
				{
					Table table = new Table();
					table.setId(result.getInt("id_table"));
					table.setNumberPlace(result.getInt("number_place"));
					table.setState(result.getString("state"));
					
					restaurant.addTable(table);
					
					id_table.add(result.getInt("id_table"));
					
				}
				
				
		
			}
			
			restaurant.addSchedule(schedule);
			
			if(restaurant.getId() != 0)
			{
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
			
			query.setInt(1,id);
			
			ResultSet result = query.executeQuery();
			
			restaurant = new Restaurant();
			Schedule schedule = new Schedule();
			
			List<Integer> id_table = new ArrayList<>();
			
			while(result.next())
			{
				
				if(result.getInt("id_schedule") != schedule.getId())
				{
					
					if(schedule.getId() != 0)
					{
						restaurant.addSchedule(schedule);
						
						schedule = new Schedule();
					}
					
					schedule.setId(result.getInt("id_schedule"));
					schedule.setOpenHour(result.getTime("open_hour").toLocalTime());
					schedule.setCloseHour(result.getTime("close_hour").toLocalTime());
					
				}
				
				if(result.getInt("id") != restaurant.getId())
				{
					
					
					restaurant.setId(result.getInt("id"));
					restaurant.setName(result.getString("name"));
					restaurant.setAddress(result.getString("address"));
					restaurant.setPostalCode(result.getString("postal_code"));
					restaurant.setTown(result.getString("town"));
					
					if(result.getInt("id_card") != 0)
					{
						restaurant.getCard().setId(result.getInt("id_card"));		
					}
					
					
					
			
				}
				
				
				
				if(!id_table.contains(result.getInt("id_table")))
				{
					Table table = new Table();
					table.setId(result.getInt("id_table"));
					table.setNumberPlace(result.getInt("number_place"));
					table.setState(result.getString("state"));
					
					restaurant.addTable(table);
					
					id_table.add(result.getInt("id_table"));
					
				}
				
				
		
			}
			
			restaurant.addSchedule(schedule);
			
			
		} 
		catch (SQLException error) 
		{
			
			throw new DALException("Unable to recover datas", error);
		}
		
		if(restaurant.getId() != 0)
		{
			return restaurant;
		}
		else
		{
			return null;
		}
		
		
		
		
	}
	
	//--------------------------------------------------------------
	
		public List<Restaurant> selectByFk(int fk) throws DALException
		{
			List<Restaurant> restaurants = new ArrayList<>();
			
			try 
			{
				PreparedStatement query;
				query = cnx.prepareStatement(SELECT_RESTAURANTS_BY_FK);
				
				query.setInt(1, fk);
				
				ResultSet result = query.executeQuery();
				
				while(result.next())
				{
					Restaurant restaurant = new Restaurant();
					restaurant.setId(result.getInt("id"));
					restaurant.setName(result.getString("name"));
					restaurant.setAddress(result.getString("address"));
					restaurant.setPostalCode(result.getString("postal_code"));
					restaurant.setTown(result.getString("town"));
					
					Card card = new Card();
					card.setId(result.getInt("id_card"));
					card.setName(result.getString("name_card"));
					
					restaurant.setCard(card);
					
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
	
	public void insert(Restaurant restaurant) throws DALException
	{
		
		try 
		{
			PreparedStatement query;
			
			//-------------restaurant
			query = cnx.prepareStatement(INSERT_INTO_RESTAURANTS,PreparedStatement.RETURN_GENERATED_KEYS);
			
			query.setString(1, restaurant.getName());
			query.setString(2, restaurant.getAddress());
			query.setString(3, restaurant.getPostalCode());
			query.setString(4, restaurant.getTown());
			
			if(restaurant.getCard() != null)
			{
				query.setInt(5, restaurant.getCard().getId());
				
			}
			else
			{
				query.setNull(5,Types.INTEGER); 
			}
			
		
			int result = query.executeUpdate();
			
			if(result == 0)
			{
				throw new DALException("insertion into Restaurants without effect", null);
			}
			
			
			ResultSet generatedKey = query.getGeneratedKeys();
			
			if(generatedKey.next()) 
			{
				int primarykey = generatedKey.getInt(1);
				restaurant.setId(primarykey);
				
			}
			
			//-------------schedule
			query = cnx.prepareStatement(INSERT_INTO_SCHEDULES, Statement.RETURN_GENERATED_KEYS);
			
			for (Schedule schedule : restaurant.getSchedules())
			{
				query.setTime(1, Time.valueOf(schedule.getOpenHour()));
				query.setTime(2, Time.valueOf(schedule.getCloseHour()));
				query.setInt(3, restaurant.getId());
				
				query.executeUpdate();
				
				generatedKey = query.getGeneratedKeys();
				
				if(generatedKey.next()) 
				{
					int primarykey = generatedKey.getInt(1);
					schedule.setId(primarykey);
					
				}
				
			}
			
			//-------------table
			query = cnx.prepareStatement(INSERT_INTO_TABLES, Statement.RETURN_GENERATED_KEYS);
			
			for (Table table : restaurant.getTables())
			{
				query.setInt(1, table.getNumberPlace());
				query.setString(2, table.getState());
				query.setInt(3, restaurant.getId());
				
				query.executeUpdate();

				generatedKey = query.getGeneratedKeys();

				if (generatedKey.next()) 
				{
					int primarykey = generatedKey.getInt(1);
					table.setId(primarykey);
				}
				
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
			
			//-------------restaurant
			query = cnx.prepareStatement(UPDATE_RESTAURANTS);
			
			query.setString(1, restaurant.getName());
			query.setString(2, restaurant.getAddress());
			query.setString(3, restaurant.getPostalCode());
			query.setString(4, restaurant.getTown());
			
			if(restaurant.getCard() != null)
			{
				query.setInt(5, restaurant.getCard().getId());
				
			}
			else
			{
				query.setNull(5,Types.INTEGER); 
			}
			
			
			query.setInt(6, restaurant.getId());
			
			int result = query.executeUpdate();
			
			if(result == 0)
			{
				throw new DALException("Data modification without effect for restaurant", null);
				
			}
			
			//-------------schedule
			query = cnx.prepareStatement(UPDATE_SCHEDULES);
			
			for(Schedule schedule : restaurant.getSchedules())
			{
				query.setTime(1, Time.valueOf(schedule.getOpenHour()));
				query.setTime(2, Time.valueOf(schedule.getCloseHour()));
				query.setInt(3, restaurant.getId());
				
				query.setInt(4, schedule.getId());
				
				result = query.executeUpdate();
				
				if(result == 0)
				{
					throw new DALException("Data modification without effect for schedule", null);
					
				}
			
			}
			
			//-------------table
			query = cnx.prepareStatement(UPDATE_TABLES);
			
			for(Table table : restaurant.getTables())
			{
				query.setInt(1, table.getNumberPlace());
				query.setString(2, table.getState());
				query.setInt(3, restaurant.getId());
				
				query.setInt(4, table.getId());
				
				result = query.executeUpdate();
				
				if(result == 0)
				{
					throw new DALException("Data modification without effect for table", null);
					
				}
				
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
