package generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
		public static Connection dbConnection = null;
	
	public static void sqlConnection() throws ClassNotFoundException, SQLException {
		Class.forName(UtilConfig.driver);
		dbConnection = DriverManager.getConnection(UtilConfig.url,UtilConfig.userName,UtilConfig.password);
			
		if(!dbConnection.isClosed()) {
			System.out.println("Successfully connected to SQL server");
		}else
			System.out.println("Cannot connect to server");
			
		}
	
	
}
