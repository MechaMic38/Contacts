package contacts.persistence;

import contacts.contact.Contact;

import java.util.List;

/**
 * Interface for the contact database.
 */
public interface IContactDatabase {

    void save(List<Contact> contacts) throws DatabaseException;

    List<Contact> load() throws DatabaseException;
}
