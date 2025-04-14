package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    String DATABASE_NAME = "flavor_db";
    String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
    String DB_USERNAME = "admin";
    String DB_PASSWORD = "admin";

    // currently does not work. needs to be fixed
    public void createDatabase() {
        DB_URL = "jdbc:mysql://localhost:3306/";
        String createDbQuery = "CREATE DATABASE " + DATABASE_NAME;
        String createUserTableQuery = "CREATE TABLE " + DATABASE_NAME + ".users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL, " +
                "password VARCHAR(255) NOT NULL)";

        String createRecipeTableQuery = "CREATE TABLE IF NOT EXISTS "+DATABASE_NAME+".Recipes ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(50) NOT NULL, "
                + "recipe_name VARCHAR(255) NOT NULL, "
                + "category VARCHAR(100), " // Category column
                + "ingredients TEXT, " // Ingredients column (comma-separated list)
                + "preparation_instruction TEXT, "
                + "serving_size VARCHAR(100), "
                + "cooking_time TEXT, "
                + "personal_note TEXT, "
                + "rating TEXT "
                + ")";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement statement = connection.createStatement()) {

            // Create the database
            statement.executeUpdate(createDbQuery);
            System.out.println("Database created: " + DATABASE_NAME);

            // Now, use the newly created database
            try (Connection dbConnection = DriverManager.getConnection(DB_URL + DATABASE_NAME, DB_USERNAME,
                    DB_PASSWORD)) {
                // Create the users table
                statement.executeUpdate(createUserTableQuery);
                System.out.println("Users table created.");
            }
            try (Connection dbConnection = DriverManager.getConnection(DB_URL + DATABASE_NAME, DB_USERNAME,
                    DB_PASSWORD)) {
                // Create the users table
                statement.executeUpdate(createRecipeTableQuery);
                System.out.println("Recipe table created.");
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("Failed to create database");
            // System.exit(0);
        }

    }

    public boolean databaseExists() {
        String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;

        String checkDbQuery = "SHOW DATABASES LIKE '" + DATABASE_NAME + "'";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(checkDbQuery);
            return resultSet.next(); // Returns true if the database exists
        } catch (SQLException e) {
            // e.printStackTrace();
        }
        return false;
    }

    // Function to execute a query and return the result (if applicable)
    public Object executeQuery(String query) {
        // Establish a connection using the database URL, username, and password
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement statement = connection.createStatement()) {

            // If the query is a SELECT statement, return the ResultSet
            if (query.trim().toUpperCase().startsWith("SELECT")) {
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet;
            } else {
                // Otherwise, execute the update and return the number of affected rows
                int affectedRows = statement.executeUpdate(query);
                return affectedRows;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if an error occurs
    }
}
