package extra;

import java.net.*;
import java.io.*;
import org.json.*;

public class MealApp {

    public static void main(String[] args) {
        try {
            // Step 1: Fetch and display all categories     
            String categoriesJson = fetchFromAPI("https://www.themealdb.com/api/json/v1/1/categories.php");
            JSONArray categoriesArray = new JSONObject(categoriesJson).getJSONArray("categories");

            System.out.println("Categories:");
            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject category = categoriesArray.getJSONObject(i);
                String categoryName = category.getString("strCategory");
                System.out.println((i+1) + ". " + categoryName);
            }

            // Step 2: Assume user selects category 1 (for example)
            int selectedCategoryIndex = 0; // You could replace this with user input in real-world scenario

            // Step 3: Fetch recipes for the selected category
            String categoryName = categoriesArray.getJSONObject(selectedCategoryIndex).getString("strCategory");
            String recipesJson = fetchFromAPI("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + categoryName);
            JSONArray mealsArray = new JSONObject(recipesJson).getJSONArray("meals");

            // Step 4: Display recipes for the selected category
            System.out.println("\nRecipes in " + categoryName + " Category:");
            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject meal = mealsArray.getJSONObject(i);
                String mealName = meal.getString("strMeal");
                String mealId = meal.getString("idMeal");
                System.out.println((i+1) + ". " + mealName + " (ID: " + mealId + ")");
            }

            // Step 5: Assume user selects recipe 1 (for example)
            int selectedRecipeIndex = 0; // You can replace this with actual user input in a real-world scenario
            String selectedMealId = mealsArray.getJSONObject(selectedRecipeIndex).getString("idMeal");

            // Step 6: Fetch details for the selected recipe
            String recipeDetailsJson = fetchFromAPI("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + selectedMealId);
            JSONArray mealDetailsArray = new JSONObject(recipeDetailsJson).getJSONArray("meals");
            JSONObject mealDetails = mealDetailsArray.getJSONObject(0);

            // Step 7: Display recipe details
            System.out.println("\nRecipe Details for " + mealDetails.getString("strMeal") + ":");
            System.out.println("Category: " + mealDetails.getString("strCategory"));
            System.out.println("Area: " + mealDetails.getString("strArea"));
            System.out.println("Instructions: " + mealDetails.getString("strInstructions"));
            System.out.println("Ingredients:");

            // Display ingredients
            for (int i = 1; i <= 20; i++) {
                String ingredient = mealDetails.optString("strIngredient" + i, "");
                String measure = mealDetails.optString("strMeasure" + i, "");
                if (!ingredient.isEmpty()) {
                    System.out.println(ingredient + " - " + measure);
                }
            }

            // Display the image URL
            System.out.println("\nImage: " + mealDetails.getString("strMealThumb"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to fetch data from the API
    private static String fetchFromAPI(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
