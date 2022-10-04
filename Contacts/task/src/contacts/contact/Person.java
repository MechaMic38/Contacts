package contacts.contact;

import contacts.util.DataValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Contact entity of type person.
 */
public class Person extends Contact {

    private static final String SURNAME = "surname";
    private static final String BIRTH = "birth";
    private static final String GENDER = "gender";

    private String surname;
    private LocalDate birthDate;
    private Gender gender;


    public Person() {
        super();
    }

    public Person(String name, String surname, String phone) {
        super(name, phone);
        this.setSurname(surname);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean setBirthDate(String birthDate) {
        if (DataValidator.isValidBirthDate(birthDate)) {
            this.birthDate = LocalDate.parse(birthDate);
            return true;
        }

        this.birthDate = null;
        return false;
    }

    public boolean hasBirthDate() {
        return this.birthDate != null;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean setGender(String gender) {
        if (DataValidator.isValidGender(gender)) {
            this.gender = Gender.toGender(gender);
            return true;
        }

        this.gender = Gender.UNSPECIFIED;
        return false;
    }

    @Override
    public String getFullName() {
        return name + " " + surname;
    }

    @Override
    public List<String> getContactFieldNames() {
        return List.of(
                NAME,
                SURNAME,
                BIRTH,
                GENDER,
                NUMBER
        );
    }

    @Override
    public String getFieldValue(String field) {
        return switch (field) {
            case NAME -> getName();
            case SURNAME -> getSurname();
            case BIRTH -> getBirthDate().toString();
            case GENDER -> getGender().toString();
            case NUMBER -> getPhone();
            default -> throw new IllegalArgumentException("Invalid field for this contact type!");
        };
    }

    @Override
    public void setFieldValue(String field, String value) {
        switch (field) {
            case NAME -> setName(value);
            case SURNAME -> setSurname(value);
            case BIRTH -> setBirthDate(value);
            case GENDER -> setGender(value);
            case NUMBER -> setPhone(value);
            default -> throw new IllegalArgumentException("Invalid field for this contact type!");
        }
    }

    @Override
    public boolean matchesAny(String query) {
        String pattern = "(?i).*" + query + ".*";

        return (!Objects.isNull(name) && name.matches(pattern)) ||
                (!Objects.isNull(surname) && surname.matches(pattern)) ||
                (!Objects.isNull(birthDate) && birthDate.toString().matches(pattern)) ||
                (!Objects.isNull(gender) && gender.i18n.matches(pattern)) ||
                (!Objects.isNull(phone) && phone.matches(pattern));
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s\nSurname: %s\nBirth date: %s\nGender: %s\nNumber: %s\nTime created: %s\nTime last edit: %s",
                name,
                surname,
                hasBirthDate() ? birthDate.toString() : "[no data]",
                gender.i18n,
                hasNumber() ? phone : "[no data]",
                timeCreated.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                timeLastEdit.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(surname, person.surname)
                && Objects.equals(phone, person.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, phone);
    }

    /**
     * Enum representing a person gender.
     */
    public enum Gender {
        MALE("Male"),
        FEMALE("Female"),
        UNSPECIFIED("[no data]");

        private final String i18n;

        Gender(String i18n) {
            this.i18n = i18n;
        }

        static Gender toGender(String gender) {
            return switch (gender) {
                case "M" -> MALE;
                case "F" -> FEMALE;
                default -> throw new IllegalArgumentException("Invalid gender");
            };
        }

        @Override
        public String toString() {
            return i18n;
        }
    }
}
