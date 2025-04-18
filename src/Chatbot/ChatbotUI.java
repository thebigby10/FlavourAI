package Chatbot;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import MainMenu.MainMenuUI;

import java.util.ArrayList;
import java.util.List;

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

    // This method will return a simple bot response for the given input
    public String getBotResponse(String userInput) {
        // For simplicity, we'll have some predefined responses
        switch (userInput.toLowerCase()) {
            case "hello":
                return "Hi there! How can I help you today?";
            case "how are you?":
                return "I'm just a bot, but I'm doing fine. How can I assist you?";
            case "bye":
                return "Goodbye! Have a great day!";
            default:
                return "Sorry, I didn't quite understand that. Could you ask something else?";
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

    // Main method to run the chatbot UI
    public static void main(String[] args) {
        ChatbotUI chatbotUI = new ChatbotUI();
        try {
            chatbotUI.startChatbot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
