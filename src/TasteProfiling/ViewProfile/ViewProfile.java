package TasteProfiling.ViewProfile;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import TasteProfiling.TasteProfilingUI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewProfile {

    private final String DATABASE_NAME = "flavor_db";
    private final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
    private final String DB_USERNAME = "admin";
    private final String DB_PASSWORD = "admin";

    public void showRecipesList(WindowBasedTextGUI textGUI) {
        Window recipeListWindow = new BasicWindow("Your Recipes");

        Panel recipeListPanel = new Panel();
        recipeListPanel.setLayoutManager(new GridLayout(2));  // name + details button

        List<Recipe> recipes = fetchAllRecipes();

        for (Recipe recipe : recipes) {
            recipeListPanel.addComponent(new Label(recipe.name));
            Button detailsButton = new Button("Details", () -> showDetailsPopup(textGUI, recipe));
            recipeListPanel.addComponent(detailsButton);
        }

        Button backButton = new Button("Back", new Runnable() {
            @Override
            public void run() {
                recipeListWindow.close();
                TasteProfilingUI tasteProfilingUI = new TasteProfilingUI();
                tasteProfilingUI.showTasteProfilingUI(textGUI);
            }
        });
        recipeListPanel.addComponent(backButton);

        recipeListWindow.setComponent(recipeListPanel);
        textGUI.addWindowAndWait(recipeListWindow);
    }

    private List<Recipe> fetchAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM Recipes";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getInt("id"),
                        resultSet.getString("recipe_name"),
                        resultSet.getString("category"),
                        resultSet.getString("ingredients"),
                        resultSet.getString("preparation_instruction"),
                        resultSet.getString("serving_size"),
                        resultSet.getString("cooking_time"),
                        resultSet.getString("personal_note"),
                        resultSet.getString("rating")
                );
                recipes.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    private void showDetailsPopup(WindowBasedTextGUI textGUI, Recipe recipe) {
        Window detailWindow = new BasicWindow("Recipe Details: " + recipe.name);

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        panel.addComponent(new Label("Category: " + recipe.category));
        panel.addComponent(new Label("Ingredients: " + recipe.ingredients));
        panel.addComponent(new Label("Instructions: " + recipe.instructions));
        panel.addComponent(new Label("Serving Size: " + recipe.servingSize));
        panel.addComponent(new Label("Cooking Time: " + recipe.cookingTime));
        panel.addComponent(new Label("Notes: " + recipe.notes));
        panel.addComponent(new Label("Rating: " + recipe.rating));

        panel.addComponent(new EmptySpace());

        panel.addComponent(new Button("Edit", () -> {
            MessageDialog.showMessageDialog(textGUI, "Edit Recipe", "Feature not implemented yet.");
        }));

        panel.addComponent(new Button("Delete", () -> {
            deleteRecipe(recipe.id);
            fetchAllRecipes();
            MessageDialog.showMessageDialog(textGUI, "Deleted", "Recipe deleted.");
            detailWindow.close();
        }));

        panel.addComponent(new Button("Back", new Runnable() {
            @Override
            public void run() {
                fetchAllRecipes();
                detailWindow.close();
                ViewProfile viewProfileWindow = new ViewProfile();
                viewProfileWindow.showRecipesList(textGUI);
            }
        }));

        detailWindow.setComponent(panel);
        textGUI.addWindowAndWait(detailWindow);
    }

    private void deleteRecipe(int id) {
        String query = "DELETE FROM Recipes WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inner class to store recipe info
    private static class Recipe {
        int id;
        String name;
        String category;
        String ingredients;
        String instructions;
        String servingSize;
        String cookingTime;
        String notes;
        String rating;

        public Recipe(int id, String name, String category, String ingredients, String instructions,
                      String servingSize, String cookingTime, String notes, String rating) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.ingredients = ingredients;
            this.instructions = instructions;
            this.servingSize = servingSize;
            this.cookingTime = cookingTime;
            this.notes = notes;
            this.rating = rating;
        }
    }
}
