package tn.elearning.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    public final String URL ="jdbc:mysql://localhost:3306/pidev";
    public final String USER ="root";
    public final String PWD ="";
    private Connection cnx;
    static MyDataBase myDataBase;
    
    public MyDataBase() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create the connection
            cnx = DriverManager.getConnection(URL, USER, PWD);
            
            if (cnx != null) {
                System.out.println("Database connection established successfully!");
            } else {
                System.err.println("WARNING: Database connection is null");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static MyDataBase getInstance() {
        if (myDataBase == null || myDataBase.getCnx() == null) {
            myDataBase = new MyDataBase();
        } else {
            try {
                if (myDataBase.getCnx().isClosed()) {
                    myDataBase = new MyDataBase();
                }
            } catch (SQLException e) {
                System.err.println("Error checking connection: " + e.getMessage());
                myDataBase = new MyDataBase();
            }
        }
        return myDataBase;
    }

    public Connection getCnx() {

        return cnx;
    }
    
    public boolean isConnected() {
        try {
            return cnx != null && !cnx.isClosed();
        } catch (SQLException e) {
            System.err.println("Error checking connection state: " + e.getMessage());
            return false;
        }
    }
}