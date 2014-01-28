package com.omnimd.ws.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionHelper
{
	private static final String url = "jdbc:sqlserver://10.10.2.12:1433;DatabaseName=NPIDB;user=OmniPMS;password=0mn!PMS@#DB";
	private static ConnectionHelper instance;

	private ConnectionHelper()
	{
//    	String driver = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
            //ResourceBundle bundle = ResourceBundle.getBundle("directory");
            //driver = bundle.getString("jdbc.driver");
            //Class.forName(driver);
            //url=bundle.getString("jdbc.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new ConnectionHelper();
		}
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void close(Connection connection)
	{
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}