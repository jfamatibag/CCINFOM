package connection;

import java.sql.*;

public class DatabaseConnection {
	static final String URL = "jdbc:mysql://localhost:3306/dbprojectapp";
    static final String USER = "root";
    static final String PASSWORD = "Vince0715";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);  
    }
}
