package it.objectmethod.webapp.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/cometa_easy";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "rootroot";

	public static Connection getConnection() {
		Connection conn = null;

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}


}
