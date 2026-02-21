/*
* Christopher DeMello
* Professor Conlan
* CS499
* Created: 1/20/25
*
* Part of Planned Enhancement - Category 1: Software Design
* Enhance security by adding input validation on user input features
*/
package com.CS360.weighttracker.util;

import android.os.Build;
import android.renderscript.ScriptGroup;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/*
 *This class is set up to add validation on all input used within the app by the user, such username,
 * password, weight entries, and dates. It is moved out Main activity to bolster reusability as well as
 * maintainability.
 */

public class InputValidator {
    private InputValidator() {
    }

    //Set min and max characters for username
    private static final int USERNAME_MIN = 5;
    private static final int USERNAME_MAX = 25;

    //Allow only letters and numbers. No weird characters.
    private static final Pattern USERNAME_ALLOWED = Pattern.compile("^[A-Za-z0-9._-]+$");

    //Set up date validation
    private static final Pattern Date_YYYY_MM_DD =
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static final class InputValidationResult {
        public final boolean ok;
        public final String value;
        public final String error;

        private InputValidationResult(boolean ok, String value, String error) {
            this.ok = ok;
            this.value = value;
            this.error = error;
        }

        //If input is correct, pass this function values
        public static InputValidationResult ok(String value) {
            return new InputValidationResult(true, value, null);
        }

        //If input is not correct, pass this function values
        public static InputValidationResult fail(String error) {
            return new InputValidationResult(false, null, error);
        }
    }

    //Call this function to ensure input for username is valid
    public static InputValidationResult validateUsername(String username) {
        if (username == null) {
            return InputValidationResult.fail("Username is required.");
        }
        //Get rid leading and trailing whitespace
        String u = username.trim();
        //Check for empty username input after trim
        if (u.isEmpty()) {
            return InputValidationResult.fail("Username cannot be empty.");
        }
        //Checks if not enough characters
        if (u.length() < USERNAME_MIN) {
            return InputValidationResult.fail("Username is too short. Must be greater than 5 characters.");
        }
        //Checks if too many characters
        if (u.length() > USERNAME_MAX) {
            return InputValidationResult.fail("Username is too long. Must be less than 25 characters.");
        }
        //Checks to make sure username contains valid characters
        if (!USERNAME_ALLOWED.matcher(u).matches()) {
            return InputValidationResult.fail("Username contains invalid characters.");
        }
        //returns valid username
        return InputValidationResult.ok(u);
    }

    //Checks to ensure input for password is valid
    public static InputValidationResult validatePassword(String password) {
        if (password == null) {
            return InputValidationResult.fail("Password is required.");
        }
        //Gets rid of leading and trailing whitespace
        String p = password.trim();
        //Checks to make sure input isn't empty
        if (p.isEmpty()) {
            return InputValidationResult.fail("Password cannot be empty.");
        }
        //Checks length is longer than characters
        if (p.length() < 6) {
            return InputValidationResult.fail("Password must be at least 6 characters.");
        }
        //Returns valid password
        return InputValidationResult.ok(p);
    }

    //Checks date input to ensure it is valid
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static InputValidationResult validateDate(String date) {
        //Checks if null
        if (date == null) {
            return InputValidationResult.fail("Date is required.");
        }
        //Clears leading and trailing whitespace
        String d = date.trim();
        //Checks if empty
        if (d.isEmpty()) {
            return InputValidationResult.fail("Date cannot be empty.");
        }
        //Checks format
        if (!d.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return InputValidationResult.fail("Date must be in YYYY-MM-DD format");
        }
        //Checks to see if accurate date. (For example: 2025-13-10 because there is no month higher than 12
        LocalDate dateFormat;
        //YYYY-MM-DD
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
            dateFormat = LocalDate.parse(d, fmt);
        } catch (DateTimeParseException e) {
            return InputValidationResult.fail("Date is not a valid calender day.");
        }
        //Set range rules date cannot be greater than 150 years ago and must prevent future entries
        LocalDate today = LocalDate.now();
        LocalDate oldestYear = today.minusYears(150);
        //Checks against years greater than 150
        if (dateFormat.isBefore(oldestYear)) {
            return InputValidationResult.fail("Year must be within the last 150 years.");
        }
        //Checks against future dates
        if (dateFormat.isAfter(today)) {
            return InputValidationResult.fail("Date cannot be in the future.");
        }
        return InputValidationResult.ok(date);
    }

    //Ensures weight input values exist and are within reasonable range
    public static InputValidationResult validateWeight(String weight) {
        //Checks weight is not null
        if (weight == null) {
            return InputValidationResult.fail("Weight is required.");
        }
        //Clears leading and trailing whitespace
        String w = weight.trim();
        //Checks to make sure weight isn't empty
        if (w.isEmpty()) {
            return InputValidationResult.fail("Weight cannot be empty.");
        }
        //Checks the weight range
        try {
            double lbs = Double.parseDouble(w);
            if (lbs < 30 || lbs > 1000) {
                return InputValidationResult.fail("Weight is out of range.");
            }
            //sets back to string
            return InputValidationResult.ok(Double.toString(lbs));
            //catches if characters are not a number
        } catch (NumberFormatException e) {
            return InputValidationResult.fail("Weight must be a number.");
        }
    }

    //Ensures height values exist and within a reasonable range
    public static InputValidationResult validateHeightFeetInches(String feet, String inches) {
        //checks if null
        if (feet == null | inches == null) {
            return InputValidationResult.fail("Height is required.");
        }
        //Clears whitespace before and after
        String f = feet.trim();
        String i = inches.trim();

        //checks if empty
        if (f.isEmpty() || i.isEmpty()) {
            return InputValidationResult.fail("Enter both feet and inches.");
        }

        //checks to make sure only numbers are accepted
        int ft;
        int in;

        try {
            ft = Integer.parseInt(f);
            in = Integer.parseInt(i);
        } catch (NumberFormatException e) {
            return InputValidationResult.fail("Height must be numeric.");
        }

        //Checks for reasonable range
        if (ft < 2 || ft > 9) {
            return InputValidationResult.fail("Feet must be between 2 and 9.");
        }

        if (in < 0 || in > 11) {
            return InputValidationResult.fail("Inches must be between 0 and 11");
        }
        //Calculate total inches
        int totalInches = ft * 12 + in;

        return InputValidationResult.ok(Integer.toString(totalInches));
    }

}
