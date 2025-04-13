package MainMenu;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

import TasteProfiling.*;

public class MainMenuUI {
    // Method to show the main menu window
    public void showMainMenu(WindowBasedTextGUI textGUI) {
        // Create a window for the main menu
        Window mainMenuWindow = new BasicWindow("Main Menu");

        // Create a panel to hold the welcome message and buttons
        Panel mainMenuPanel = new Panel();
        mainMenuPanel.setLayoutManager(new GridLayout(1));  // 1 column layout

        // Add a label with the "Welcome to FlavorAI!" message
        Label welcomeLabel = new Label("Welcome to FlavorAI!");
        mainMenuPanel.addComponent(welcomeLabel);

        // Add a random recipe button
        Button randomRecipeButton = new Button("Explore a Dish", new Runnable() {
            @Override
            public void run() {
                
            }
        });

        // Add the new menu buttons
        Button personalTasteButton = new Button("Personal Taste Profiling", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                TasteProfilingUI tasteProfilingUI = new TasteProfilingUI();
                tasteProfilingUI.showTasteProfilingUI(textGUI);
            }
        });

        Button aiRecipeButton = new Button("AI-Powered Recipe Generator", new Runnable() {
            @Override
            public void run() {
                // Add the action for AI-Powered Recipe Generator
                System.out.println("Navigating to AI-Powered Recipe Generator...");
                mainMenuWindow.close();
            }
        });

        Button interactiveModeButton = new Button("Interactive Mode (Chat with AI Chef)", new Runnable() {
            @Override
            public void run() {
                // Add the action for Interactive Mode (Chat with AI Chef)
                System.out.println("Entering Interactive Mode with AI Chef...");
                mainMenuWindow.close();
            }
        });

        Button exitButton = new Button("Exit", new Runnable() {
            @Override
            public void run() {
                // Close the main menu window
                mainMenuWindow.close();
            }
        });

        // Add all the buttons to the panel
        mainMenuPanel.addComponent(randomRecipeButton);
        mainMenuPanel.addComponent(personalTasteButton);
        mainMenuPanel.addComponent(aiRecipeButton);
        mainMenuPanel.addComponent(interactiveModeButton);
        mainMenuPanel.addComponent(exitButton);

        // Set the panel as the content of the main menu window
        mainMenuWindow.setComponent(mainMenuPanel);

        // Add the main menu window to the GUI and wait for user interaction
        textGUI.addWindowAndWait(mainMenuWindow);
    }
}
