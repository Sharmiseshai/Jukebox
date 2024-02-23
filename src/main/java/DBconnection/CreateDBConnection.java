package DBconnection;
import java.sql.*;

public class CreateDBConnection {

    Connection con;
    public Connection getConnection()
    {
        try {
            //loading a driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //create connectivity
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dps","root","root");
            System.out.println("Connection Established");
        }
        catch (Exception e1) {
            System.out.println("Connection Error " + e1);
        }
        return con;


    }

    public static void main(String[] args) {
        CreateDBConnection b1=new CreateDBConnection();
        b1.getConnection();
    }
}



