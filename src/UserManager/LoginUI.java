package UserManager;


import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import MainMenu.MainMenuUI;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyType;

public class LoginUI {
    public void showLogin(WindowBasedTextGUI textGUI) {
        // System.out.println("indie showLogin...");
        // Create a window for the login screen
        Window loginWindow = new BasicWindow("Login");

        // Create panel to hold UI components
        Panel loginPanel = new Panel();
        loginPanel.setLayoutManager(new GridLayout(2));

        // Add a Label and TextBox for the Username
        Label usernameLabel = new Label("Username:");
        TextBox usernameField = new TextBox();
        
        // Add a Label and TextBox for the Password
        Label passwordLabel = new Label("Password:");
        TextBox passwordField = new TextBox().setMask('*');  // Mask for password input

        // Create a label to display login result
        Label loginResult = new Label("");

        // Create the Login button
        Button loginButton = new Button("Login", new Runnable() {
            @Override
            public void run() {
                // Instantiate LoginSystem class
                LoginSystem loginSystem = new LoginSystem();
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Check if credentials are valid using the LoginSystem
                if (loginSystem.login(username, password)) {
                    System.out.println("Login successful!");
                    UserInfo.username = username;
                    // If login is successful, change to the main menu
                    loginWindow.close();  // Close the login window
                    MainMenuUI mainmenu = new MainMenuUI();
                    mainmenu.showMainMenu(textGUI); // Show the main menu window
                } else {
                    System.out.println("Invalid username or password.");
                    loginResult.setText("Invalid username or password.");
                }
            }
        });

        // Create the Register button (doesn't do anything specific in this example)
        Button registerButton = new Button("Register", new Runnable() {
            @Override
            public void run() {
                // Instantiate LoginSystem class for registration
                LoginSystem loginSystem = new LoginSystem();
                String username = usernameField.getText();
                String password = passwordField.getText();
                
                // Simulate registration process
                String registrationMessage = loginSystem.register(username, password);
                loginResult.setText(registrationMessage);
            }
        });

        // Add components to the login panel
        loginPanel.addComponent(usernameLabel);
        loginPanel.addComponent(usernameField);
        loginPanel.addComponent(passwordLabel);
        loginPanel.addComponent(passwordField);
        loginPanel.addComponent(loginButton);
        loginPanel.addComponent(registerButton);
        loginPanel.addComponent(loginResult);

        // Set the login panel as the content of the login window
        loginWindow.setComponent(loginPanel);

        // Add the login window to the GUI and wait for user interaction
        textGUI.addWindowAndWait(loginWindow);
    }

}
