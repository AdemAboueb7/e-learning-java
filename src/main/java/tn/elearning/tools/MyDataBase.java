package tn.elearning.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

    private static MyDataBase myDataBase;
    private Connection cnx;

    private final String URL = "jdbc:mysql://localhost:3307/pidev";
    private final String USER = "root";
    private final String PWD = "";

    public MyDataBase() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the connection
            cnx = DriverManager.getConnection(URL, USER, PWD);

            if (cnx != null) {
                System.out.println("✅ Database connection established successfully!");
            } else {
                System.err.println("⚠️ WARNING: Database connection is null");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static MyDataBase getInstance() {
        if (myDataBase == null) {
            myDataBase = new MyDataBase();
        }
        return myDataBase;
    }

    public Connection getConnection() {
        return cnx;
    }
}
