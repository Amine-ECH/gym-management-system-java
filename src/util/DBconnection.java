package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
	private static final String URL =
	        "jdbc:mysql://localhost:3306/db_gym?useSSL=false&serverTimezone=UTC";

	    private static final String USER = "root";
	    private static final String PASSWORD = ""; 

	    public static Connection getConnection() {
	        Connection cn = null;

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            cn = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println(" Database connected");
	        } catch (ClassNotFoundException e) {
	            System.out.println(" JDBC Driver not found");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.out.println("Database connection error");
	            e.printStackTrace();
	        }

	        return cn;
	    }
}
