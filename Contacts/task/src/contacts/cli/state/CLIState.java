package contacts.cli.state;

import contacts.cli.AppCLI;

/**
 * Abstract class representing the state of the {@link AppCLI}.
 */
abstract public class CLIState {

    protected final AppCLI cli;


    public CLIState(AppCLI cli) {
        this.cli = cli;
    }

    public abstract void showActions();

    public abstract void onRecordSelected();

    public abstract void onBack();
}
