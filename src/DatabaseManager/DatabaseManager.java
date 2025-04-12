package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    String DATABASE_NAME;
    String DB_URL;]
    String DB_USERNAME;
    String DB_PASSWORD;

    public DatabaseManager(String DATABASE_NAME, String DB_USERNAME, String DB_PASSWORD) {
        this.DATABASE_NAME = DATABASE_NAME;
        this.DB_URL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
        this.DB_USERNAME = DB_USERNAME;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public void createDatabase() {
        String createDbQuery = "CREATE DATABASE " + DATABASE_NAME;
        String createTableQuery = "CREATE TABLE " + DATABASE_NAME + ".users (" +
                                  "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                  "username VARCHAR(50) NOT NULL, " +
                                  "password VARCHAR(255) NOT NULL)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Create the database
            statement.executeUpdate(createDbQuery);
            System.out.println("Database created: " + DATABASE_NAME);

            // Now, use the newly created database
            try (Connection dbConnection = DriverManager.getConnection(DB_URL + DATABASE_NAME, DB_USERNAME, DB_PASSWORD)) {
                // Create the users table
                statement.executeUpdate(createTableQuery);
                System.out.println("Users table created.");
            }

        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }
    public boolean databaseExists() {
        String DB_URL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
        
        String checkDbQuery = "SHOW DATABASES LIKE '" + DATABASE_NAME + "'";
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(checkDbQuery);
            return resultSet.next(); // Returns true if the database exists
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }
}
