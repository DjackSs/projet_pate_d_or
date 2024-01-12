package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bo.Dish;

public class DishDAO implements GenericDAOInterface<Dish> 
{
	private Connection cnx;
	
	private static final String SELECT_DISH = "select DISHES.* from DISHES";
	
	private static final String SELECT_DISH_BY_ID = "select DISHES.* from DISHES where DISHES.id in(?)";
	
	private static final String INSERT_INTO_DISH = "insert into DISHES ( name, price, description, category, id_card) values ( ?, ?, ?, ?, ?)";

	private static final String UPDATE_DISH = "update DISHES set DISHES.name = ?, DISHES.price = ?, DISHES.description = ?, DISHES.category = ?, DISHES.id_card = ? where DISHES.id = ?";
	
	private static final String DELETE_DISH = "delete from DISHES where DISHES.id = ?";
		
		
	//==============================================================
	
	public DishDAO() throws DALException
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
	
	public List<Dish> selectAll() throws DALException
	{
		List<Dish> DISH = new ArrayList<>();
		
		try 
		{
			PreparedStatement query;
			query = cnx.prepareStatement(SELECT_DISH);
			
			ResultSet result = query.executeQuery();
			
			while(result.next())
			{
				Dish Dish = new Dish();
				Dish.setId(result.getInt("id"));
				Dish.setName(result.getString("name"));
				Dish.setPrice(result.getFloat("price"));
				Dish.setDescription(result.getString("description"));
				Dish.setCategory(result.getString("category"));
				Dish.setIdCard(result.getInt("id_card"));
				
				
				DISH.add(Dish);
				
			}
			
		} 
		catch (SQLException error) 
		{
			
			throw new DALException("Unable to recover datas", error);
		}
		
		return DISH;
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public Dish selectById(int id) throws DALException
	{
		Dish Dish = null;
		
		try 
		{
			
			PreparedStatement query;
			query = cnx.prepareStatement(SELECT_DISH_BY_ID);
			
			query.setInt(1, id);
			
			
			ResultSet result = query.executeQuery();
			
			if(result.next())
			{
				Dish = new Dish();
				Dish.setId(result.getInt("id"));
				Dish.setName(result.getString("name"));
				Dish.setPrice(result.getFloat("price"));
				Dish.setDescription(result.getString("description"));
				Dish.setCategory(result.getString("category"));
				Dish.setIdCard(result.getInt("id_card"));
				
				
				
				
			}
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("Unable to recover the data", error);
		}
		
		return Dish;
		
		
		
	}
	
    public List<Dish> selectByForeignKey(String foreignKeyName, int foreignKeyId) throws DALException {
        List<Dish> dishes = new ArrayList<>();

        try {
            String query = SELECT_DISH + " WHERE " + foreignKeyName + " = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, foreignKeyId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getFloat("price"));
                dish.setDescription(rs.getString("description"));
                dish.setCategory(rs.getString("category"));
                dish.setIdCard(rs.getInt("id_card"));

                dishes.add(dish);
            }
        } catch (SQLException e) {
            throw new DALException("Impossible de récupérer les informations par foreign key", e);
        }

        return dishes;
    }

	
	//--------------------------------------------------------------
	
	public void insert(Dish Dish) throws DALException
	{
		
		try 
		{
			PreparedStatement query;
			query = cnx.prepareStatement(INSERT_INTO_DISH,PreparedStatement.RETURN_GENERATED_KEYS);
			
			query.setString(1, Dish.getName());
			query.setFloat(2, Dish.getPrice());
			query.setString(3, Dish.getDescription());
			query.setString(4, Dish.getCategory());
			
			if(Dish.getIdCard() != 0)
			{
				query.setInt(5, Dish.getIdCard());
				
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
				Dish.setId(primarykey);
				
			}
			
			
		} 
		catch (SQLException error) 
		{
			throw new DALException("data to insert invalid", error);
		}
		
		
		
		
	}
	
	//--------------------------------------------------------------
	
	public void update(Dish dish) throws DALException
	{
		try 
		{
			PreparedStatement ps = cnx.prepareStatement(UPDATE_DISH);
			
			ps.setString(1, dish.getName());
			ps.setFloat(2, dish.getPrice());
			ps.setString(3, dish.getDescription());
			ps.setString(4, dish.getCategory());
			ps.setInt(5, dish.getIdCard());
			ps.setInt(6, dish.getId());
			ps.executeUpdate();

		} catch (SQLException error) {
			throw new DALException("Impossible d'inserer les donnees.", error);
		}
	}
	
	//--------------------------------------------------------------
	
	public void delete(int id) throws DALException
	{
		
		try 
		{
			
			PreparedStatement query;
			query = cnx.prepareStatement(DELETE_DISH);
	
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
