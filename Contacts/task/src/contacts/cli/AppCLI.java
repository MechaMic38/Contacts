package contacts.cli;

import contacts.cli.state.CLIState;
import contacts.cli.state.MenuState;
import contacts.contact.Contact;
import contacts.contact.ContactFactory;
import contacts.contact.PhoneBook;

import java.util.List;
import java.util.Scanner;

/**
 * Application Command Line Interface.
 */
public class AppCLI implements CLI {

    private static final String PERSON = "person";
    private static final String ORG = "organization";

    private final PhoneBook phoneBook;
    private final Scanner scanner;
    private final ContactFactory contactFactory;

    private CLIState state;
    private boolean active;

    private List<Contact> queryContacts;
    private Contact workingContact;


    public AppCLI(PhoneBook phoneBook, ContactFactory contactFactory, Scanner scanner) {
        this.phoneBook = phoneBook;
        this.contactFactory = contactFactory;
        this.scanner = scanner;

        this.state = new MenuState(this);
        this.queryContacts = List.of();
    }

    @Override
    public void launch() {
        active = true;
        while (active) {
            showActions();
        }
    }

    @Override
    public void stop() {
        this.active = false;
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public void changeState(CLIState state) {
        this.state = state;
    }

    public void showActions() {
        state.showActions();
    }

    public void loadAndShowContact(int index) {
        Contact contact = queryContacts.get(index - 1);

        showMessage(contact.toString());
        setWorkingContact(contact);
        state.onRecordSelected();
    }

    public void queryContacts() {
        String query = getInput("Enter search query: ");
        List<Contact> contacts = phoneBook.findByQuery(query);

        if (contacts.size() == 0) {
            showMessage("No results found!");
            return;
        }

        String message = String.format(
                "Found %d %s:",
                contacts.size(),
                contacts.size() == 1 ? "result" : "results"
        );
        showMessage(message);

        showContacts(contacts);
        setQueriedContacts(contacts);
    }

    public void showContactList() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to show!");
            return;
        }

        List<Contact> contacts = phoneBook.getContacts();
        showContacts(contacts);
        setQueriedContacts(contacts);
    }

    public void onAddContact() {
        String type = getInput("Enter the type (person, organization): ");

        Contact contact = switch (type) {
            case PERSON -> contactFactory.getContact(ContactFactory.ContactType.PERSON);
            case ORG -> contactFactory.getContact(ContactFactory.ContactType.ORGANIZATION);
            default -> null;
        };

        if (contact == null) {
            showMessage("Invalid contact type!");
            return;
        }

        List<String> fields = contact.getContactFieldNames();
        for (String field : fields) {
            String value = getInput("Enter the " + field + ": ");
            contact.setFieldValue(field, value);
        }

        phoneBook.addContact(contact);
        showMessage("The record added.");
    }

    public void onDeleteContact() {
        phoneBook.removeContact(workingContact);
        showMessage("The record removed!");

        onBack();
    }

    public void onEditContact() {
        List<String> contactFields = workingContact.getContactFieldNames();

        String field = getInput(selectFieldMessage(contactFields));
        if (!contactFields.contains(field)) {
            showMessage("Invalid field!");
            return;
        }

        String value = getInput("Enter " + field + ": ");
        workingContact.setFieldValue(field, value);

        showMessage("Saved");
    }

    public void onCount() {
        String message = String.format(
                "The Phone Book has %d %s.",
                phoneBook.getSize(),
                phoneBook.getSize() == 1 ? "record" : "records"
        );
        showMessage(message);
    }

    public void onBack() {
        state.onBack();
    }

    private void showContacts(List<Contact> contacts) {
        for (int index = 0; index < contacts.size(); index++) {
            String message = String.format(
                    "%d. %s",
                    index + 1,
                    contacts.get(index).getFullName()
            );
            showMessage(message);
        }
    }

    private String selectFieldMessage(List<String> contactFields) {
        return String.format(
                "Select a field (%s): ",
                String.join(", ", contactFields)
        );
    }

    private void setWorkingContact(Contact contact) {
        this.workingContact = contact;
    }

    private void setQueriedContacts(List<Contact> contacts) {
        this.queryContacts = contacts;
    }
}
