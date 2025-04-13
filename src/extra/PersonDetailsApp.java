package extra;
// import com.googlecode.lanterna.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyType;

public class PersonDetailsApp {
    public static void main(String[] args) throws Exception {
        // Create the terminal using DefaultTerminalFactory
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        TerminalScreen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create a basic window for the list of names
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        Window mainWindow = new BasicWindow("Select a Person");

        // Panel for the main window
        Panel mainPanel = new Panel();

        // Creating names as buttons
        Button person1Button = new Button("John Doe", new Runnable() {
            @Override
            public void run() {
                // When "John Doe" is clicked, show his details in a new window
                showPersonDetails(textGUI, "John Doe", "Age: 30\nOccupation: Software Developer\nLocation: New York");
            }
        });

        Button person2Button = new Button("Jane Smith", new Runnable() {
            @Override
            public void run() {
                // When "Jane Smith" is clicked, show her details in a new window
                showPersonDetails(textGUI, "Jane Smith", "Age: 25\nOccupation: Data Scientist\nLocation: San Francisco");
            }
        });

        Button person3Button = new Button("Alice Johnson", new Runnable() {
            @Override
            public void run() {
                // When "Alice Johnson" is clicked, show her details in a new window
                showPersonDetails(textGUI, "Alice Johnson", "Age: 28\nOccupation: UX Designer\nLocation: Chicago");
            }
        });

        // Adding buttons for each person to the main panel
        mainPanel.addComponent(person1Button);
        mainPanel.addComponent(person2Button);
        mainPanel.addComponent(person3Button);

        // Set the panel as the content of the main window
        mainWindow.setComponent(mainPanel);

        // Add the main window to the GUI
        textGUI.addWindowAndWait(mainWindow);

        // Clean up and stop the screen
        screen.stopScreen();
    }

    // Method to show the person's details in a new window
    private static void showPersonDetails(WindowBasedTextGUI textGUI, String name, String details) {
        // Create a new window for showing details
        Window detailsWindow = new BasicWindow(name + " Details");

        // Create a panel to display details
        Panel detailsPanel = new Panel();
        detailsPanel.addComponent(new Label(details));

        // Add an "Exit" button to close the details window
        Button exitButton = new Button("Exit", new Runnable() {
            @Override
            public void run() {
                detailsWindow.close();  // Close the details window
            }
        });
        detailsPanel.addComponent(exitButton);

        // Set the panel as the content of the details window
        detailsWindow.setComponent(detailsPanel);

        // Add the details window to the GUI and wait for the user to interact
        textGUI.addWindowAndWait(detailsWindow);
    }
}
