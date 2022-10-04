package contacts.contact;

import contacts.util.DataValidator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Base class for contact entities.
 */
abstract public class Contact implements Serializable {

    protected static final String NAME = "name";
    protected static final String NUMBER = "number";

    protected String name;
    protected String phone = "";

    protected LocalDateTime timeCreated;
    protected LocalDateTime timeLastEdit;


    public Contact() {
    }

    public Contact(String name, String phone) {
        this();
        this.setName(name);
        this.setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String phone) {
        if (DataValidator.isValidPhoneNumber(phone)) {
            this.phone = phone;
            return true;
        }

        this.phone = "";
        return false;
    }

    public boolean hasNumber() {
        return !phone.isBlank();
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeLastEdit() {
        return timeLastEdit;
    }

    public void setTimeLastEdit(LocalDateTime timeLastEdit) {
        this.timeLastEdit = timeLastEdit;
    }

    abstract public String getFullName();

    abstract public List<String> getContactFieldNames();

    abstract public String getFieldValue(String field);

    abstract public void setFieldValue(String field, String value);

    abstract public boolean matchesAny(String query);

    abstract public String toString();
}
