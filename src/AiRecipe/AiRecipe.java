package AiRecipe;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

import TasteProfiling.AddRecipe.AddRecipe;
import TasteProfiling.RateRecipe.RateRecipe;
import TasteProfiling.ViewProfile.ViewProfile;

public class AiRecipe {
    public void showTasteProfilingUI(WindowBasedTextGUI textGUI) {
        // Create a window for the main menu
        Window mainMenuWindow = new BasicWindow("AI-Powered Recipe Generator");

        // Create a panel to hold the welcome message and buttons
        Panel mainMenuPanel = new Panel();
        mainMenuPanel.setLayoutManager(new GridLayout(1));  // 1 column layout

        // Add a label with the "Welcome to FlavorAI!" message
        Label welcomeLabel = new Label("AI-Powered Recipe Discovery.");
        mainMenuPanel.addComponent(welcomeLabel);

        // Add the new menu buttons
        Button addRecipeButton = new Button("AI-Powered Custom Recipe", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                AddRecipe recipeInputWindow = new AddRecipe();
                recipeInputWindow.showRecipeInputWindow(textGUI);
            }
        });

        Button viewProfileButton = new Button("Browse by Category – Your Taste, Your Way", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                ViewProfile viewProfileWindow = new ViewProfile();
                viewProfileWindow.showRecipesList(textGUI);
            }
        });

        Button rateDishButton = new Button("Find Recipes by Ingredient", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                RateRecipe rateRecipe = new RateRecipe();
                rateRecipe.showRatingUI(textGUI);
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
