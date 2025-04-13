
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import DatabaseManager.*;
import UserManager.LoginUI;
import UserManager.LoginUI.*;



public class App {
    // public static void main(String[] args) {
    //     System.out.println("Welcome to FlavourAI!");

    //     // DatabaseManager dbmanager = new DatabaseManager("flavor_db", "thebigby01", "~cc++404");

    //     // if(dbmanager.databaseExists()){
    //     //     System.out.println("Database exists");
    //     // }
    //     // else{
    //     //     System.out.println("Database does not exist");
    //     //     dbmanager.createDatabase();
    //     // }
        
    // }
    public static void main(String[] args) throws Exception {
        // Create the terminal using DefaultTerminalFactory
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        TerminalScreen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create a window-based text GUI to hold the login and main menu
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

        LoginUI userinterface = new LoginUI();
        // Call showLogin method to display the login screen
        userinterface.showLogin(textGUI);

        // Clean up and stop the screen
        screen.stopScreen();
    }

    
}
