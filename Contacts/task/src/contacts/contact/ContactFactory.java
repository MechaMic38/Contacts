package contacts.contact;

/**
 * Factory class for {@link Contact}.
 */
public class ContactFactory {

    public Contact getContact(ContactType type) {
        return switch (type) {
            case PERSON -> new Person();
            case ORGANIZATION -> new Organization();
        };
    }

    public enum ContactType {
        PERSON,
        ORGANIZATION
    }
}
