package Chatbot;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import MainMenu.MainMenuUI;
import TasteProfiling.Recipe;
import UserManager.UserInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class ChatbotUI {

    private List<String> conversation;  // List to store conversation history
    
    // Constructor to initialize conversation list
    public ChatbotUI() {
        conversation = new ArrayList<>();
    }

    // The method to start the chatbot interaction
    public void startChatbot() throws Exception {
        // Create the terminal using DefaultTerminalFactory
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        TerminalScreen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create a window-based text GUI to hold the chatbot interface
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

        // Create the main window
        Window chatbotWindow = new BasicWindow("Chatbot");

        // Create a panel to hold the conversation (input/output)
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));
        
        // Label to show conversation
        // Label conversationLabel = new Label(getConversation());
        // panel.addComponent(conversationLabel);

        Label conversationLabel = new Label("Chatbot: Hello! How can I assist you today?");
        panel.addComponent(conversationLabel);

        // TextBox for user input
        TextBox userInputBox = new TextBox();
        panel.addComponent(userInputBox);

        // Button to submit user input
        Button sendButton = new Button("Send", new Runnable() {
            @Override
            public void run() {
                String userInput = userInputBox.getText();
                if (!userInput.trim().isEmpty()) {
                    // Add user's message to the conversation
                    addToConversation("User: " + userInput);

                    // Get bot's response
                    String botResponse = getBotResponse(userInput);

                    // Add bot's response to the conversation
                    addToConversation("Chatbot: " + botResponse);

                    // Update the conversation label
                    conversationLabel.setText(getConversation());
                    userInputBox.setText("");  // Clear input field
                }
            }
        });
        panel.addComponent(sendButton);
        Button backButton = new Button("Back", new Runnable() {
            @Override
            public void run() {
                // Close the chatbot window
                chatbotWindow.close();
                MainMenuUI mainMenuUI = new MainMenuUI();
                mainMenuUI.showMainMenu(textGUI);
            }
        });
        
        panel.addComponent(backButton);

        // Set the panel as the content of the window
        chatbotWindow.setComponent(panel);

        // Add the chatbot window to the GUI and wait for user interaction
        textGUI.addWindowAndWait(chatbotWindow);

        // Clean up and stop the screen
        screen.stopScreen();
    }

    private final String DATABASE_NAME = "flavor_db";
    private final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
    private final String DB_USERNAME = "admin";
    private final String DB_PASSWORD = "admin";

    private String fetchAllRecipes() {
    StringBuilder allRecipes = new StringBuilder();  // Use StringBuilder for better performance when concatenating strings.
        String query = "SELECT * FROM Recipes WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the username parameter to prevent SQL injection
            statement.setString(1, UserInfo.username);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    // Format the recipe details as a string
                    String recipeDetails = String.format(
                            "ID: %d\nRecipe Name: %s\nCategory: %s\nIngredients: %s\nPreparation Instruction: %s\nServing Size: %s\nCooking Time: %s\nPersonal Note: %s\nRating: %s\n\n",
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
                    allRecipes.append(recipeDetails);  // Append the formatted details of each recipe
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allRecipes.toString();  // Return the concatenated string
    }

    

    // This method will return a simple bot response for the given input
    public String getBotResponse(String userInput) {
        String all_recipe_data = fetchAllRecipes();
    
        String apiKey = "AIzaSyAHvnjSUoUkfBRkXSDEYml11uIFWBSH8iI";
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;
    
        // Update the JSON input to include userInput
        String jsonInput = String.format("""
            {
              "contents": [{
                "parts":[{"text": "Here's detail of the saved recipes %s. Bases on these respond to : %s. Give answer in less than 100 chracters."}]
              }]
            }
        """, all_recipe_data,userInput);  // Use String.format to insert userInput into the JSON body
    
        try {
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
    
            // Write the request body
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
    
            // Read the response
            int status = con.getResponseCode();
            InputStream responseStream = (status < 400) ? con.getInputStream() : con.getErrorStream();
    
            try (BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return extractTextFromJson(response.toString());
            }
    
        } catch (Exception e) {
            // e.printStackTrace();
            return "Sorry, I couldn't generate a response. Please try again later.";
        }
    }

    public String extractTextFromJson(String jsonResponse) {
        try {
            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(jsonResponse);
            
            // Get the "candidates" array
            JSONArray candidates = jsonObject.getJSONArray("candidates");

            // Get the first candidate and extract the "content" part
            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");

            // Get the "parts" array
            JSONArray parts = content.getJSONArray("parts");

            // Extract the text from the first "part"
            String text = parts.getJSONObject(0).getString("text");

            return text; // Return the extracted text

        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting text.";
        }
    }

    // Adds a message to the conversation history
    private void addToConversation(String message) {
        conversation.add(message);
    }

    // Returns the full conversation as a string
    private String getConversation() {
        StringBuilder conversationText = new StringBuilder();
        for (String message : conversation) {
            conversationText.append(message).append("\n");
        }
        return conversationText.toString();
    }
}
