package contacts.contact;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Phone book containing a list of {@link Contact}.
 */
public class PhoneBook implements Serializable {

    private final List<Contact> contacts;


    public PhoneBook() {
        this.contacts = new LinkedList<>();
    }

    public PhoneBook(Contact contact) {
        this();
        this.addContact(contact);
    }

    public PhoneBook(List<Contact> contacts) {
        this();
        this.addContacts(contacts);
    }

    public void addContacts(List<Contact> contacts) {
        contacts.forEach(this::addContact);
    }

    public void removeContacts(List<Contact> contacts) {
        contacts.forEach(this::removeContact);
    }

    public void addContact(Contact contact) {
        contact.setTimeCreated(LocalDateTime.now());
        contact.setTimeLastEdit(LocalDateTime.now());
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<Contact> findByQuery(String query) {
        return contacts.stream()
                .filter(contact -> contact.matchesAny(query))
                .toList();
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public int getSize() {
        return contacts.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < contacts.size(); i++) {
            builder.append(
                    String.format(
                            (i == contacts.size() - 1) ? "%d. %s" : "%d. %s\n",
                            i + 1,
                            contacts.get(i).getFullName()
                    )
            );
        }

        return builder.toString();
    }
}
