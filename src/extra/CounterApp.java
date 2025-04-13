package extra;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyType;

public class CounterApp {
    public static void main(String[] args) throws Exception {
        // Create the terminal using DefaultTerminalFactory
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        TerminalScreen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create a basic window
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        Window window = new BasicWindow("Counter App");

        // Counter variable
        final int[] counter = {0};

        // Create a panel and add a label, increment button, and exit button
        Panel panel = new Panel();
        Label counterLabel = new Label("Counter: " + counter[0]);

        // Button to increment the counter
        Button incrementButton = new Button("Increment", new Runnable() {
            @Override
            public void run() {
                counter[0]++;  // Increment counter
                counterLabel.setText("Counter: " + counter[0]);  // Update the label text
            }
        });

        // Button to exit the app
        Button exitButton = new Button("Exit", new Runnable() {
            @Override
            public void run() {
                // Exit the app by closing the window
                window.close();
            }
        });

        // Add components to the panel
        panel.addComponent(counterLabel);
        panel.addComponent(incrementButton);
        panel.addComponent(exitButton);

        // Set the panel as the content of the window
        window.setComponent(panel);

        // Add window to the GUI and wait for user interaction
        textGUI.addWindowAndWait(window);

        // Clean up and stop the screen
        screen.stopScreen();
    }
}
