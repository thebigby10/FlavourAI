package MainMenu;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

import AiRecipe.AiRecipe;
import Chatbot.ChatbotUI;
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
        Button personalTasteButton = new Button("Personal Taste Profiling.", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                TasteProfiling tasteProfilingUI = new TasteProfiling();
                tasteProfilingUI.showTasteProfilingUI(textGUI);
            }
        });

        Button aiRecipeButton = new Button("AI-Powered Recipe Discovery.", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                AiRecipe aiRecipe = new AiRecipe();
                aiRecipe.showTasteProfilingUI(textGUI);
            }
        });

        Button interactiveModeButton = new Button("Interactive Mode (Chat with AI Chef)", new Runnable() {
            @Override
            public void run() {
                mainMenuWindow.close();
                ChatbotUI chatbotUI = new ChatbotUI();
                try{
                    chatbotUI.startChatbot();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
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
