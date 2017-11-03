package util;

import java.time.LocalDate;
import javafx.scene.control.Alert;

/**
 *
 * @author Mahendra Tennakoon
 */
public class FormValidate {

    /**
     * Validate and show alert if text is empty
     *
     * @param text
     * @param fieldname
     * @return
     */
    public static boolean isEmptyField(String text, String fieldname) {
        int strlen = text.length();
        if (strlen == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(fieldname + " cannot be empty.");
            alert.showAndWait();

            return true;
        } else {
            return false;
        }
    }

    public static void showErrorForNull(String text, String fieldname) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(fieldname + " cannot be empty.");
        alert.showAndWait();
    }

    /**
     * returns true if ends with '.com' and contains only one '@'.
     *
     * @param input
     * @return
     */
    public static boolean validateEmail(String input) {
        if (input.endsWith(".com") && countCharacter(input, '@') == 1) {
            return true;
        }
        return false;
    }

    public static boolean validateEmailVerbose(String input) {
        if (input.endsWith(".com") && countCharacter(input, '@') == 1) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid email address.");
        alert.showAndWait();
        return false;
    }

    /**
     * return the number of occurrences of a given character in a string.
     *
     * @param text
     * @param key
     * @return
     */
    public static int countCharacter(String text, char key) {
        int count = 0;
        char c;

        for (int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            if (c == key) {
                count++;
            }
        }
        return count;
    }

    /**
     * returns true if the password is at least 8 characters long.
     *
     * @param pwd
     * @return
     */
    public static boolean validatePassword(String pwd) {
        if (pwd.length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password must be atleast 8 characters long.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * returns true if the given password is at least 8 characters long and
     * matches with the reentered password.
     *
     * @param pwd
     * @param cpwd
     * @return
     */
    public static boolean validatePassword(String pwd, String cpwd) {
        if (pwd.length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password must be atleast 8 characters long.");
            alert.showAndWait();
            return false;
        }
        if (!pwd.equals(cpwd)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The two passwords entered do not match.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    //    returns true if input only contains letters
    public static boolean isTextOnly(String input) {
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    //    returns true if NIC is valid
    public static boolean validateNIC(String input) {
        if (!(input.endsWith("V") || input.endsWith("v")) || input.length() != 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid NIC.");
            alert.showAndWait();
            return false;
        }
        for (int i = 0; i < input.length() - 1; i++) {
            char c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean ValidatePhone(String input) {
        if (input.length() != 10) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean ValidatePhoneVerbose(String input, String fieldname) {
        if (input.length() != 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(fieldname + " is an invalid phone number.");
            alert.showAndWait();
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // returns true if input only contains numeric
    public static boolean isNumericOnly(String input, String fieldname) {
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (!Character.isDigit(c)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(fieldname + " should be valid");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String input, String fieldname) {
        char[] arr = input.toCharArray();

        for (char c : arr) {
            if (!Character.isDigit(c) && (c != '.')) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(fieldname + " should be numeric");
                alert.showAndWait();
                return false;
            }
        }

        return true;
    }

    public static boolean isAfterDate(LocalDate d1, LocalDate d2, String date1, String date2) {
        if (d1.isAfter(d2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(date1 + " should be a date before " + date2);
            alert.showAndWait();
            return true;
        }
        return false;
    }

    public static boolean validateAge(String age_str) {
        if (isNumeric(age_str, "Age")) {
            int age = Integer.parseInt(age_str);

            if (age > 1 && age < 120) {
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Enter a valid age");
                alert.showAndWait();
                return false;
            }
        }
        return false;
    }

    public static boolean isNumericAndPoint(String input, String fieldname) {
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (!Character.isDigit(c) && c!='.') {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(fieldname + " should be valid");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }
    public static boolean isMinusValue(String input, String fieldname) {

        double v = Double.parseDouble(input);
        if (v <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(fieldname + " should be Positive");
            alert.showAndWait();
            return true;
        }
        return false;
    }
}
