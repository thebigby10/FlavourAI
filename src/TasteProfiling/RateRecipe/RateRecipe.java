package TasteProfiling.RateRecipe;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import TasteProfiling.TasteProfiling;
import UserManager.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import TasteProfiling.Recipe;

public class RateRecipe {

    private final String DATABASE_NAME = "flavor_db";
    private final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
    private final String DB_USERNAME = "admin";
    private final String DB_PASSWORD = "admin";

    public void showRatingUI(WindowBasedTextGUI textGUI) {
        Window recipeListWindow = new BasicWindow("Rate Recipes");

        Panel recipeListPanel = new Panel();
        recipeListPanel.setLayoutManager(new GridLayout(2));

        List<Recipe> recipes = fetchAllRecipes();

        for (Recipe recipe : recipes) {
            recipeListPanel.addComponent(new Label(recipe.name + " (Rating: " + recipe.rating + ")"));
            Button detailsButton = new Button("Rate", () -> {
                recipeListWindow.close();
                showRatingPopup(textGUI, recipe);
            });
            recipeListPanel.addComponent(detailsButton);
        }

        Button backButton = new Button("Back", () -> {
            recipeListWindow.close();
            TasteProfiling tasteProfilingUI = new TasteProfiling();
            tasteProfilingUI.showTasteProfilingUI(textGUI);
        });
        recipeListPanel.addComponent(backButton);

        recipeListWindow.setComponent(recipeListPanel);
        textGUI.addWindowAndWait(recipeListWindow);
    }

    private void showRatingPopup(WindowBasedTextGUI textGUI, Recipe recipe) {
        Window detailWindow = new BasicWindow("Rate: " + recipe.name);
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        panel.addComponent(new Label("Category: " + recipe.category));
        panel.addComponent(new Label("Ingredients: " + recipe.ingredients));
        panel.addComponent(new Label("Instructions: " + recipe.instructions));
        panel.addComponent(new Label("Serving Size: " + recipe.servingSize));
        panel.addComponent(new Label("Cooking Time: " + recipe.cookingTime));
        panel.addComponent(new Label("Notes: " + recipe.notes));
        panel.addComponent(new Label("Current Rating: " + recipe.rating));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("Enter New Rating:"));
        TextBox ratingBox = new TextBox();
        panel.addComponent(ratingBox);

        panel.addComponent(new Button("Rate", () -> {
            String newRating = ratingBox.getText();
            updateRating(recipe.id, newRating);
            MessageDialog.showMessageDialog(textGUI, "Updated", "Rating updated successfully.");
            detailWindow.close();
            showRatingUI(textGUI);
        }));

        panel.addComponent(new Button("Back", () -> {
            detailWindow.close();
            showRatingUI(textGUI);
        }));

        detailWindow.setComponent(panel);
        textGUI.addWindowAndWait(detailWindow);
    }

    private void updateRating(int id, String rating) {
        String query = "UPDATE Recipes SET rating = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, rating);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Recipe> fetchAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM Recipes where username='"+UserInfo.username+"'";

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
}
