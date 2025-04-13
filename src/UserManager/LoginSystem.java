package UserManager;

public class LoginSystem {
    // Simulate a login check with hardcoded credentials
    private String validUsername = "";
    private String validPassword = "";

    // Method to check if the login credentials are correct
    public boolean login(String username, String password) {
        return username.equals(validUsername) && password.equals(validPassword);
    }

    // Placeholder method for registration (can be expanded later)
    public String register(String username, String password) {
        // In this example, it simply returns a confirmation message
        return "Registration feature coming soon.";
    }
}
