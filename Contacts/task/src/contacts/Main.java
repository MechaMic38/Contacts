package contacts;

import contacts.cli.AppCLI;
import contacts.contact.ContactFactory;
import contacts.contact.PhoneBook;
import contacts.persistence.ContactDatabase;
import contacts.persistence.DatabaseException;
import contacts.persistence.IContactDatabase;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            launchWithDatabase(args[0]);
        } else {
            launchWithoutDatabase();
        }
    }

    private static void launchWithDatabase(String filePath) {
        IContactDatabase db = new ContactDatabase(filePath);
        PhoneBook phoneBook = loadContacts(db);

        Scanner scanner = new Scanner(System.in);
        ContactFactory contactFactory = new ContactFactory();

        new AppCLI(
                phoneBook,
                contactFactory,
                scanner
        ).launch();

        saveContacts(db, phoneBook);
    }

    private static void launchWithoutDatabase() {
        Scanner scanner = new Scanner(System.in);
        ContactFactory contactFactory = new ContactFactory();

        new AppCLI(
                new PhoneBook(),
                contactFactory,
                scanner
        ).launch();
    }

    private static void saveContacts(IContactDatabase db, PhoneBook phoneBook) {
        try {
            db.save(phoneBook.getContacts());
        } catch (DatabaseException e) {
            System.err.println(e.getMessage());
        }
    }

    private static PhoneBook loadContacts(IContactDatabase db) {
        try {
            return new PhoneBook(db.load());
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
            return new PhoneBook();
        }
    }
}
