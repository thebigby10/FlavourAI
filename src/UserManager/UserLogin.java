package UserManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {
    public UserLogin(String username, String password) {
        
    }

    public static boolean authenticate(String username, String password) {
        // SQL query to check if the user exists with the given username and password
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the username and password parameters in the query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If there's at least one result, the credentials are correct
            if (resultSet.next()) {
                return true; // User authenticated
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the exception if any occurs
        }

        return false; // Invalid credentials
    }

    private static boolean databaseExists(String DATABASE_NAME, String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        String checkDbQuery = "SHOW DATABASES LIKE '" + DATABASE_NAME + "'";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(checkDbQuery);
            return resultSet.next(); // Returns true if the database exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
