package contacts.contact;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Contact entity of type organization.
 */
public class Organization extends Contact {

    private static final String ADDRESS = "address";

    private String address;


    public Organization() {
        super();
    }

    public Organization(String name, String address, String phone) {
        super(name, phone);
        this.setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean hasAddress() {
        return !address.isBlank();
    }

    @Override
    public String getFullName() {
        return name;
    }

    @Override
    public List<String> getContactFieldNames() {
        return List.of(
                NAME,
                ADDRESS,
                NUMBER
        );
    }

    @Override
    public String getFieldValue(String field) {
        return switch (field) {
            case NAME -> getName();
            case ADDRESS -> getAddress();
            case NUMBER -> getPhone();
            default -> throw new IllegalArgumentException("Invalid field for this contact type!");
        };
    }

    @Override
    public void setFieldValue(String field, String value) {
        switch (field) {
            case NAME -> setName(value);
            case ADDRESS -> setAddress(value);
            case NUMBER -> setPhone(value);
            default -> throw new IllegalArgumentException("Invalid field for this contact type!");
        }
    }

    @Override
    public boolean matchesAny(String query) {
        String pattern = "(?i).*" + query + ".*";

        return (!Objects.isNull(name) && name.matches(pattern)) ||
                (!Objects.isNull(address) && address.matches(pattern)) ||
                (!Objects.isNull(phone) && phone.matches(pattern));
    }

    @Override
    public String toString() {
        return String.format(
                "Organization name: %s\nAddress: %s\nNumber: %s\nTime created: %s\nTime last edit: %s",
                name,
                hasAddress() ? address : "[no data]",
                hasNumber() ? phone : "[no data]",
                timeCreated.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                timeLastEdit.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address)
                && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone);
    }
}
