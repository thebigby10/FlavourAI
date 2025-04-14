package UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import DatabaseManager.DatabaseManager;

public class LoginSystem {
    // Simulate a login check with hardcoded credentials
    private String validUsername = "";
    private String validPassword = "";

    // Method to check if the login credentials are correct
    public boolean login(String username, String password) {
        return true;
        String query = "select * from users where username = "+username+" and password = "+password;
        DatabaseManager dbmanager = new DatabaseManager();
        ResultSet resultSet = (ResultSet) dbmanager.executeQuery(query);
        try{
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
        DatabaseManager dbmanager = new DatabaseManager();
        
        // Check if the username already exists
        String checkQuery = "SELECT * FROM users WHERE username = '" + username + "'";
        ResultSet resultSet = (ResultSet) dbmanager.executeQuery(checkQuery);
        try {
            if (resultSet.next()) {
                // If the username already exists, return a failure message
                return "Username already taken. Please choose a different username.";
            } else {
                // If the username doesn't exist, insert the new user into the database
                String insertQuery = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
                int affectedRows = (int) dbmanager.executeQuery(insertQuery);
                
                if (affectedRows > 0) {
                    return "Registration successful! Please Login.";
                } else {
                    return "Error occurred during registration. Please try again.";
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in registration: " + e.getMessage());
            return "Error occurred during registration. Please try again.";
        }
    }
    }
}
