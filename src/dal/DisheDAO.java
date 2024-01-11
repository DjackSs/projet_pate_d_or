package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bo.Dishe;

public class DisheDAO implements GenericDAOInterface<Dishe> 
{
	private Connection cnx;
	
	private static final String SELECT_DISHES = "select Dishes.* from Dishes";
	
	private static final String SELECT_DISHES_BY_ID = "select Dishes.* from Dishes where Dishes.id in(?)";
	
	private static final String INSERT_INTO_DISHES = "insert into Dishes ( name, price, description, category, id_card) values ( ?, ?, ?, ?, ?)";

	private static final String UPDATE_DISHES = "update Dishes set Dishes.name = ?, Dishes.price = ?, Dishes.description = ?, Dishes.category = ?, Dishes.id_card = ? where Dishes.id = ?";
	
	private static final String DELETE_DISHES = "delete from Dishes where Dishes.id = ?";
		
		
	//==============================================================
	
	public DisheDAO() throws DALException
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
	
	public List<Dishe> selectAll() throws DALException
	{
		List<Dishe> dishes = new ArrayList<>();
		
		try 
		{
			PreparedStatement query;
			query = cnx.prepareStatement(SELECT_DISHES);
			
			ResultSet result = query.executeQuery();
			
			while(result.next())
			{
				Dishe dishe = new Dishe();
				dishe.setId(result.getInt("id"));
				dishe.setName(result.getString("name"));
				dishe.setPrice(result.getFloat("price"));
				dishe.setDescription(result.getString("description"));
				dishe.setCategory(result.getString("category"));
				dishe.setIdCard(result.getInt("id_card"));
				
				
				dishes.add(dishe);
				
			}
			
		} 
		catch (SQLException error) 
		{
			
			throw new DALException("Unable to recover datas", error);
		}
		
		return dishes;
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public Dishe selectById(int id) throws DALException
	{
		Dishe dishe = null;
		
		try 
		{
			
			PreparedStatement query;
			query = cnx.prepareStatement(SELECT_DISHES_BY_ID);
			
			query.setInt(1, id);
			
			
			ResultSet result = query.executeQuery();
			
			if(result.next())
			{
				dishe = new Dishe();
				dishe.setId(result.getInt("id"));
				dishe.setName(result.getString("name"));
				dishe.setPrice(result.getFloat("price"));
				dishe.setDescription(result.getString("description"));
				dishe.setCategory(result.getString("category"));
				dishe.setIdCard(result.getInt("id_card"));
				
				
				
				
			}
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("Unable to recover the data", error);
		}
		
		return dishe;
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public void insert(Dishe dishe) throws DALException
	{
		
		try 
		{
			PreparedStatement query;
			query = cnx.prepareStatement(INSERT_INTO_DISHES,PreparedStatement.RETURN_GENERATED_KEYS);
			
			query.setString(1, dishe.getName());
			query.setFloat(2, dishe.getPrice());
			query.setString(3, dishe.getDescription());
			query.setString(4, dishe.getCategory());
			
			if(dishe.getIdCard() != 0)
			{
				query.setInt(5, dishe.getIdCard());
				
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
				dishe.setId(primarykey);
				
			}
			
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("data to insert invalid", error);
		}
		
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public void update(Dishe dishe) throws DALException
	{
		
		try 
		{
			PreparedStatement query;
			
			query = cnx.prepareStatement(UPDATE_DISHES);
			
			query.setString(1, dishe.getName());
			query.setFloat(2, dishe.getPrice());
			query.setString(3, dishe.getDescription());
			query.setString(4, dishe.getCategory());
			
			if(dishe.getIdCard() != 0)
			{
				query.setInt(5, dishe.getIdCard());
				
			}
			else
			{
				query.setNull(5,Types.INTEGER); 
			}
			
			
			query.setInt(6, dishe.getId());
			
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
			query = cnx.prepareStatement(DELETE_DISHES);
	
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
