package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class ConnexionProvider 
{
	
	private static final String BDD_NAME = "PATE_D_OR";
	private static final String BDD_USER = "USER_SQLSERVER";
	private static final String BDD_MDP = "PASSWORD_SQLSERVER";
	
	public static Connection getConnection () throws DALException
	{
		
				//url de connexion jbdc - bdd
				String url = "jdbc:sqlserver://localhost;databasename="+BDD_NAME+";trustservercertificate=true";
				
				try 
				{
					//getConnection(url, utilisateur, mot de passe)
					 return DriverManager.getConnection(url, BDD_USER, BDD_MDP);
					
					
				} 
				catch (SQLException error) 
				{
					
					throw new DALException("erreur de conexion à la base de donnée", error);
				}
		
	}

}