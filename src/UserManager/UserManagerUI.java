package UserManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserManagerUI {
    public UserManagerUI() {
        System.out.println("Enter username(To register enter '-1'):");
        String username = System.console().readLine();

        if(username.equals("-1")) {
            UserRegistration.registerUser();
        } else {
            System.out.println("Enter password:");
            String password = System.console().readLine();
            UserLogin login = new UserLogin(username, password);
        }    
    }

    
}
