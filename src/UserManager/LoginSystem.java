package UserManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DatabaseManager.DatabaseManager;

public class LoginSystem {
    // Simulate a login check with hardcoded credentials
    private String validUsername = "";
    private String validPassword = "";

    // Method to check if the login credentials are correct
    public boolean login(String username, String password) {
        // return true;
        String DATABASE_NAME = "flavor_db";
        String DB_URL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
        String DB_USERNAME = "admin";
        String DB_PASSWORD = "admin";
        String query = "select * from users where username = '"+username+"' and password = '"+password+"'";
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e) {
            System.out.println("Error in login");
            return false;
        }
        
    }

    // Placeholder method for registration (can be expanded later)
    public String register(String username, String password) {
        String DATABASE_NAME = "flavor_db";
        String DB_URL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
        String DB_USERNAME = "admin";
        String DB_PASSWORD = "admin";
        try{
            String checkQuery = "SELECT * FROM users WHERE username = '" + username + "'";
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkQuery);
            if(resultSet.next()) {
                return "Username already taken. Please choose a different username.";
            }
            else {
                String insertQuery = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
                int affectedRows = (int) statement.executeUpdate(insertQuery);
                if(affectedRows > 0) {
                    return "Registration successful! Please Login.";
                }
                else {
                    return "Error occurred during registration. Please try again.";
                }
            }
        } catch (SQLException e) { 
            return"Error in registration: " + e.getMessage();
        }
    }
}
