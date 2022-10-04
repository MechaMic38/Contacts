package contacts.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for input validation.
 */
public class DataValidator {

    private static final String PHONE_REGEX =
            "^\\+?([\\da-zA-Z]+[\\s-]?)?(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$";

    private static final String DATE_REGEX =
            "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";


    public static boolean isValidPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }

    public static boolean isValidBirthDate(String birthDate) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher matcher = pattern.matcher(birthDate);

        return matcher.matches();
    }

    public static boolean isValidGender(String gender) {
        return gender.equals("M") || gender.equals("F");
    }
}
