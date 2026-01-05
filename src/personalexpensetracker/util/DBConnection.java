package personalexpensetracker.util;

import java.sql.*;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
	private static final String USER = "root";
	private static final String PASSWORD = "Your Password";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
