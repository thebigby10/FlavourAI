import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DatabaseManager.*;



public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to FlavourAI!");

        DatabaseManager dbmanager = new DatabaseManager("flavor_db", "thebigby01", "~cc++404");

        if(dbmanager.databaseExists()){
            System.out.println("Database exists");
        }
        else{
            System.out.println("Database does not exist");
        }
        
    }

    
}
