package TasteProfiling;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

import TasteProfiling.AddRecipe.AddRecipe;
import TasteProfiling.ViewProfile.ViewProfile;

public class TasteProfilingUI {
    public void showTasteProfilingUI(WindowBasedTextGUI textGUI) {
        // Create a window for the main menu
        Window mainMenuWindow = new BasicWindow("Taste Profile");

        // Create a panel to hold the welcome message and buttons
        Panel mainMenuPanel = new Panel();
        mainMenuPanel.setLayoutManager(new GridLayout(1));  // 1 column layout

        // Add a label with the "Welcome to FlavorAI!" message
        Label welcomeLabel = new Label("Personal Taste Profiling.");
        mainMenuPanel.addComponent(welcomeLabel);

        // Add the new menu buttons
        Button addRecipeButton = new Button("Add a new Recipe.", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                AddRecipe recipeInputWindow = new AddRecipe();
                recipeInputWindow.showRecipeInputWindow(textGUI);
            }
        });

        Button viewProfileButton = new Button("View Your Taste Profile", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                ViewProfile viewProfileWindow = new ViewProfile();
                viewProfileWindow.showRecipesList(textGUI);
            }
        });

        Button rateDishButton = new Button("Rate Dish", new Runnable() {
            @Override
            public void run() {
                // Add the action for Flavour Pairing Predictor
                System.out.println("Navigating to Flavour Pairing Predictor...");
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
        mainMenuPanel.addComponent(addRecipeButton);
        mainMenuPanel.addComponent(viewProfileButton);
        mainMenuPanel.addComponent(rateDishButton);
        mainMenuPanel.addComponent(exitButton);

        // Set the panel as the content of the main menu window
        mainMenuWindow.setComponent(mainMenuPanel);

        // Add the main menu window to the GUI and wait for user interaction
        textGUI.addWindowAndWait(mainMenuWindow);
    }
}
