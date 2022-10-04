package contacts.cli.state;

import contacts.cli.AppCLI;

/**
 * Search state of the {@link AppCLI}.
 */
public class SearchState extends CLIState {

    private static final String AGAIN = "again";
    private static final String BACK = "back";


    public SearchState(AppCLI cli) {
        super(cli);
    }

    @Override
    public void showActions() {
        String action = cli.getInput("[search] Enter action ([number], back, again): ");

        if (AGAIN.equals(action)) {
            cli.queryContacts();
            return;
        }
        if (BACK.equals(action)) {
            cli.onBack();
            return;
        }

        int index;
        try {
            index = Integer.parseInt(action);
            cli.loadAndShowContact(index);
        } catch (NumberFormatException e) {
            System.out.println("Invalid action!");
        }

        cli.showMessage("");
    }

    @Override
    public void onRecordSelected() {
        cli.changeState(new RecordState(cli));
    }

    @Override
    public void onBack() {
        cli.changeState(new MenuState(cli));
    }
}
