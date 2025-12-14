import ui.TerminalUI;

/**
 * Entry point of the application.
 * <p>
 * This class is responsible for starting the program execution.
 * It delegates control to the {@link ui.TerminalUI} class, which
 * handles the user interaction through a terminal-based interface.
 * </p>
 */
public class Main {
    /**
     * Main method of the application.
     * <p>
     * This method is invoked by the Java Virtual Machine (JVM) when
     * the program starts. It initializes the application by calling
     * {@link ui.TerminalUI#start()}.
     * </p>
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        TerminalUI.start();
    }
}