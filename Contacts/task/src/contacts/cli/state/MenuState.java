package contacts.cli.state;

import contacts.cli.AppCLI;

/**
 * Menu state of the {@link AppCLI}.
 */
public class MenuState extends CLIState {

    private static final String ADD = "add";
    private static final String COUNT = "count";
    private static final String EXIT = "exit";
    private static final String LIST = "list";
    private static final String SEARCH = "search";


    public MenuState(AppCLI cli) {
        super(cli);
    }

    @Override
    public void showActions() {
        String action = cli.getInput("[menu] Enter action (add, list, search, count, exit): ");

        switch (action) {
            case ADD -> onAddContact();
            case COUNT -> onCount();
            case EXIT -> onExit();
            case LIST -> onList();
            case SEARCH -> onSearchContact();
            default -> System.out.println("Invalid action!");
        }

        cli.showMessage("");
    }

    @Override
    public void onRecordSelected() {
        //Not implemented
    }

    @Override
    public void onBack() {
        //Not implemented
    }

    private void onAddContact() {
        cli.onAddContact();
    }

    private void onCount() {
        cli.onCount();
    }

    private void onList() {
        cli.showContactList();
        cli.changeState(new ListState(cli));
    }

    private void onSearchContact() {
        cli.queryContacts();
        cli.changeState(new SearchState(cli));
    }

    private void onExit() {
        cli.stop();
    }
}
