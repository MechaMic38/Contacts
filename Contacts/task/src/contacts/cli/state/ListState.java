package contacts.cli.state;

import contacts.cli.AppCLI;

/**
 * List state of the {@link AppCLI}.
 */
public class ListState extends CLIState {

    private static final String BACK = "back";


    public ListState(AppCLI cli) {
        super(cli);
    }

    @Override
    public void showActions() {
        String action = cli.getInput("[list] Enter action ([number], back): ");

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
