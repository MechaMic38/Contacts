package contacts.cli;

/**
 * CLI interface.
 */
public interface CLI {

    void launch();

    void stop();

    void showMessage(String message);

    String getInput(String message);
}
