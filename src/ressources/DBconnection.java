package ressources;

import java.sql.*;

public class DBconnection {

    private static DBconnection instance;
    private Connection connection;
    String url = "jdbc:postgresql://localhost:5432/baticuisine";
    String user = "postgres";
    String password = "azerty123";


    private DBconnection() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBconnection getInstance() {
        if (instance == null) {
            instance = new DBconnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}

