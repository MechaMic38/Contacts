package contacts.cli.state;

import contacts.cli.AppCLI;

/**
 * Record state of the {@link AppCLI}.
 */
public class RecordState extends CLIState {

    private static final String DELETE = "delete";
    private static final String EDIT = "edit";
    private static final String MENU = "menu";


    public RecordState(AppCLI cli) {
        super(cli);
    }

    @Override
    public void showActions() {
        String action = cli.getInput("[record] Enter action (edit, delete, menu): ");

        switch (action) {
            case EDIT -> onEditContact();
            case DELETE -> onDeleteContact();
            case MENU -> onBack();
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
        cli.changeState(new MenuState(cli));
    }

    private void onDeleteContact() {
        cli.onDeleteContact();
    }

    private void onEditContact() {
        cli.onEditContact();
    }
}
