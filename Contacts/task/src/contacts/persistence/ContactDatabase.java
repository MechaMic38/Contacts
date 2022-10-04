package contacts.persistence;

import contacts.contact.Contact;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Database for storing and retrieving contacts.
 */
public class ContactDatabase implements IContactDatabase {

    private final File contactFile;


    public ContactDatabase(String fileName) {
        contactFile = new File(fileName);
        createFile();
    }

    private void createFile() {
        try {
            Path categoryFile = Paths.get(contactFile.toURI());
            if (!Files.exists(categoryFile)) {
                Files.createFile(categoryFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(List<Contact> contacts) throws DatabaseException {
        try (
                FileOutputStream fos = new FileOutputStream(contactFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos)
        ) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            throw new DatabaseException("[ContactDatabase] Something went wrong...", e);
        }
    }

    @Override
    public List<Contact> load() throws DatabaseException {
        try (
                FileInputStream fis = new FileInputStream(contactFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis)
        ) {
            return (List<Contact>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Contacts not found in the selected file \"" + contactFile + "\"!");
        } catch (IOException e) {
            throw new DatabaseException("[ContactDatabase] Something went wrong...", e);
        }
    }
}
