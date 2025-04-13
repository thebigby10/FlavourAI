package TasteProfiling.RecipeManager;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;

import TasteProfiling.TasteProfilingUI;

import java.util.ArrayList;
import java.util.List;

public class RecipeInputWindow {
    // Method to display the recipe input window
    public void showRecipeInputWindow(WindowBasedTextGUI textGUI) {
        Window recipeWindow = new BasicWindow("Add New Recipe");

        Panel recipePanel = new Panel();
        recipePanel.setLayoutManager(new GridLayout(2));

        // Recipe Name
        recipePanel.addComponent(new Label("Recipe Name:"));
        TextBox recipeNameField = new TextBox();
        recipePanel.addComponent(recipeNameField);

        // Recipe Category
        recipePanel.addComponent(new Label("Recipe Category:"));
        TextBox recipeCategoryField = new TextBox();
        recipePanel.addComponent(recipeCategoryField);

        // Ingredients
        recipePanel.addComponent(new Label("Ingredients:"));
        TextBox ingredientFields = new TextBox();
        recipePanel.addComponent(ingredientFields);

        // Preparation Instructions
        recipePanel.addComponent(new Label("Preparation Instructions:"));
        TextBox preparationField = new TextBox();
        recipePanel.addComponent(preparationField);

        // Serving Size
        recipePanel.addComponent(new Label("Serving Size:"));
        TextBox servingSizeField = new TextBox();
        recipePanel.addComponent(servingSizeField);

        // Cooking Time
        recipePanel.addComponent(new Label("Cooking Time (in minutes):"));
        TextBox cookingTimeField = new TextBox();
        recipePanel.addComponent(cookingTimeField);

        // Personal Notes
        recipePanel.addComponent(new Label("Personal Notes:"));
        TextBox personalNotesField = new TextBox();
        recipePanel.addComponent(personalNotesField);

        // Save Recipe Button
        Button saveRecipeButton = new Button("Save Recipe", new Runnable() {
            @Override
            public void run() {
                //save logic
            }
        });
        Button backButton = new Button("Back", new Runnable() {
            @Override
            public void run() {
                recipeWindow.close();
                TasteProfilingUI tasteProfilingUI = new TasteProfilingUI();
                tasteProfilingUI.showTasteProfilingUI(textGUI);
            }
        });
        recipePanel.addComponent(backButton);
        recipePanel.addComponent(saveRecipeButton);

        // Set the panel as the content of the window
        recipeWindow.setComponent(recipePanel);

        // Add window to the GUI and wait for user interaction
        textGUI.addWindowAndWait(recipeWindow);
    }
}
