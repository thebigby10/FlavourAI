package TasteProfiling.AddRecipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import TasteProfiling.TasteProfilingUI;
import UserManager.UserInfo;

public class AddRecipe {

    // Variables to store the entered values
    private String recipeName = "";
    private String recipeCategory = "";
    private String ingredients = "";
    private String preparationInstructions = "";
    private String servingSize = "";
    private String cookingTime = "";
    private String personalNotes = "";

    // Method to display the recipe input window
    public void showRecipeInputWindow(WindowBasedTextGUI textGUI) {
        Window recipeWindow = new BasicWindow("Add New Recipe");

        Panel recipePanel = new Panel();
        recipePanel.setLayoutManager(new GridLayout(2));

        // Recipe Name
        recipePanel.addComponent(new Label("Recipe Name:"));
        Button recipeNameButton = new Button("Enter Recipe Name", new Runnable() {
            @Override
            public void run() {
                // Show input dialog for recipe name, pre-fill with previous value if exists
                recipeName = showInputDialog(textGUI, "Enter Recipe Name", "Recipe Name:", recipeName);
            }
        });
        recipePanel.addComponent(recipeNameButton);

        // Recipe Category
        recipePanel.addComponent(new Label("Recipe Category:"));
        Button recipeCategoryButton = new Button("Enter Recipe Category", new Runnable() {
            @Override
            public void run() {
                // Show input dialog for recipe category, pre-fill with previous value if exists
                recipeCategory = showInputDialog(textGUI, "Enter Recipe Category", "Category:", recipeCategory);
            }
        });
        recipePanel.addComponent(recipeCategoryButton);

        // Ingredients
        recipePanel.addComponent(new Label("Ingredients (Separate by Comma):"));
        Button ingredientsButton = new Button("Enter Ingredients", new Runnable() {
            @Override
            public void run() {
                // Show input dialog for ingredients, pre-fill with previous value if exists
                ingredients = showInputDialog(textGUI, "Enter Ingredients", "Ingredients:", ingredients);
            }
        });
        recipePanel.addComponent(ingredientsButton);

        // Preparation Instructions
        recipePanel.addComponent(new Label("Preparation Instructions:"));
        Button preparationButton = new Button("Enter Preparation Instructions", new Runnable() {
            @Override
            public void run() {
                // Show input dialog for preparation instructions, pre-fill with previous value if exists
                preparationInstructions = showInputDialog(textGUI, "Enter Preparation Instructions", "Instructions:", preparationInstructions);
            }
        });
        recipePanel.addComponent(preparationButton);

        // Serving Size
        recipePanel.addComponent(new Label("Serving Size:"));
        Button servingSizeButton = new Button("Enter Serving Size", new Runnable() {
            @Override
            public void run() {
                // Show input dialog for serving size, pre-fill with previous value if exists
                servingSize = showInputDialog(textGUI, "Enter Serving Size", "Serving Size:", servingSize);
            }
        });
        recipePanel.addComponent(servingSizeButton);

        // Cooking Time
        recipePanel.addComponent(new Label("Cooking Time (in minutes):"));
        Button cookingTimeButton = new Button("Enter Cooking Time", new Runnable() {
            @Override
            public void run() {
                // Show input dialog for cooking time, pre-fill with previous value if exists
                cookingTime = showInputDialog(textGUI, "Enter Cooking Time", "Cooking Time:", cookingTime);
            }
        });
        recipePanel.addComponent(cookingTimeButton);

        // Personal Notes
        recipePanel.addComponent(new Label("Personal Notes:"));
        Button personalNotesButton = new Button("Enter Personal Notes", new Runnable() {
            @Override
            public void run() {
                // Show input dialog for personal notes, pre-fill with previous value if exists
                personalNotes = showInputDialog(textGUI, "Enter Personal Notes", "Notes:", personalNotes);
            }
        });
        recipePanel.addComponent(personalNotesButton);

        // Save Recipe Button
        Button saveRecipeButton = new Button("Save Recipe", new Runnable() {
            @Override
            public void run() {
                String DATABASE_NAME = "flavor_db";
                String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
                String DB_USERNAME = "admin";
                String DB_PASSWORD = "admin";
                String sql = "INSERT INTO Recipes (recipe_name, username, category, ingredients, preparation_instruction, serving_size, cooking_time, personal_note) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); 
                    PreparedStatement statement = connection.prepareStatement(sql)) {

                // Set parameters for the prepared statement
                statement.setString(1, recipeName);
                statement.setString(2, UserInfo.username);
                statement.setString(3, recipeCategory);
                statement.setString(4, ingredients);
                statement.setString(5, preparationInstructions);
                statement.setString(6, servingSize);
                statement.setString(7, cookingTime);
                statement.setString(8, personalNotes);

                // Execute the insert query
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    showSuccessPopup(textGUI);
                    recipeWindow.close();
                    TasteProfilingUI tasteProfilingUI = new TasteProfilingUI();
                    tasteProfilingUI.showTasteProfilingUI(textGUI);
                }
            } catch (SQLException e) {
                // System.out.println("Error in inserting recipe");
                System.out.println(e.getMessage());
            }
    
        }
        });
        recipePanel.addComponent(saveRecipeButton);

        // Back Button
        Button backButton = new Button("Back", new Runnable() {
            @Override
            public void run() {
                recipeWindow.close();
                TasteProfilingUI tasteProfilingUI = new TasteProfilingUI();
                tasteProfilingUI.showTasteProfilingUI(textGUI);
            }
        });
        recipePanel.addComponent(backButton);

        // Set the panel as the content of the window
        recipeWindow.setComponent(recipePanel);

        // Add window to the GUI and wait for user interaction
        textGUI.addWindowAndWait(recipeWindow);
    }

    private void showSuccessPopup(WindowBasedTextGUI textGUI) {
        Window successWindow = new BasicWindow("Success!");
        Panel successPanel = new Panel();
        successPanel.setLayoutManager(new GridLayout(1));
        Label successLabel = new Label("Recipe added successfully!");
        successPanel.addComponent(successLabel);
        Button okButton = new Button("OK", new Runnable() {
            @Override
            public void run() {
                successWindow.close();
            }
        });
        successPanel.addComponent(okButton);
        successWindow.setComponent(successPanel);
        textGUI.addWindowAndWait(successWindow);
    }

    // Helper method to show input dialog with pre-filled text
    private String showInputDialog(WindowBasedTextGUI textGUI, String title, String label, String previousValue) {
        final String[] result = new String[1];
        final Window inputWindow = new BasicWindow(title);
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label(label));
        TextBox inputField = new TextBox();
        inputField.setText(previousValue); // Pre-fill with previous value
        panel.addComponent(inputField);

        Button okButton = new Button("OK", new Runnable() {
            @Override
            public void run() {
                result[0] = inputField.getText();
                inputWindow.close();
            }
        });

        panel.addComponent(okButton);
        inputWindow.setComponent(panel);

        textGUI.addWindowAndWait(inputWindow);

        return result[0];
    }
}
