package rescue.service;

import java.util.ArrayList;
import java.util.List;

import rescue.exceptions.InvalidAnimalTypeException;
import rescue.exceptions.InvalidTeamException;
import rescue.exceptions.MissingLocationException;
import rescue.exceptions.RescueException;
import rescue.model.Animal;
import rescue.model.Equipment;

public class Validator {

    private static String clean(String text) {
        return text == null ? "" : text.trim();
    }

    public static String animalType(String text) throws InvalidAnimalTypeException {
        String t = clean(text).toLowerCase();
        if (t.isEmpty()) {
            throw new InvalidAnimalTypeException("Animal type cannot be empty.");
        }
        if (!Animal.isValidType(t)) {
            throw new InvalidAnimalTypeException("Invalid animal type '" + clean(text)
                    + "'. Valid types: bird, reptile, mammal.");
        }
        return t;
    }

    public static int count(String text) throws RescueException {
        int n;
        try {
            n = Integer.parseInt(clean(text));
        } catch (NumberFormatException e) {
            throw new RescueException("Number of animals must be a whole number.");
        }
        if (n < 1 || n > 100) {
            throw new RescueException("Number of animals must be between 1 and 100.");
        }
        return n;
    }

    public static String location(String text) throws MissingLocationException {
        String t = clean(text);
        if (t.isEmpty()) {
            throw new MissingLocationException("Location is missing. Please enter the approximate location.");
        }
        return t;
    }

    public static boolean yesNo(String text) throws RescueException {
        String t = clean(text).toLowerCase();
        if (t.equals("y") || t.equals("yes")) {
            return true;
        }
        if (t.equals("n") || t.equals("no")) {
            return false;
        }
        throw new RescueException("Please answer yes or no.");
    }

    /** Contact must be a phone number (9-12 digits, may start with +) or an e-mail address. */
    public static String contact(String text) throws RescueException {
        String t = clean(text);
        if (isPhoneNumber(t) || isEmail(t)) {
            return t;
        }
        throw new RescueException("Reporter contact must be a phone number (9-12 digits) or an e-mail address.");
    }

    private static boolean isPhoneNumber(String text) {
        String phone = text.replace(" ", "").replace("-", "");
        if (phone.startsWith("+")) {
            phone = phone.substring(1);
        }
        if (phone.length() < 9 || phone.length() > 12) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /** A simple e-mail check, something before "@", and a "." somewhere after it. */
    private static boolean isEmail(String text) {
        int at = text.indexOf('@');
        if (at < 1 || text.contains(" ")) {
            return false;
        }
        int dot = text.lastIndexOf('.');
        return dot > at + 1 && dot < text.length() - 1;
    }

    /** Splits a list typed by the user into separate items. */
    public static List<String> splitList(String text, String separatorRegex) {
        List<String> result = new ArrayList<String>();
        String t = clean(text);
        if (t.isEmpty()) {
            return result;
        }
        for (String part : t.split(separatorRegex)) {
            if (!part.trim().isEmpty()) {
                result.add(part.trim());
            }
        }
        return result;
    }

    public static List<Equipment> equipmentList(String text, String separatorRegex) {
        List<Equipment> result = new ArrayList<Equipment>();
        for (String item : splitList(text, separatorRegex)) {
            result.add(new Equipment(item));
        }
        return result;
    }

    // ----- team validation -----

    /** Team ID must be 2-10 letters or digits */
    public static String teamId(String text) throws InvalidTeamException {
        String t = clean(text);
        boolean valid = t.length() >= 2 && t.length() <= 10;
        for (int i = 0; i < t.length(); i++) {
            if (!Character.isLetterOrDigit(t.charAt(i))) {
                valid = false;
            }
        }
        if (!valid) {
            throw new InvalidTeamException("Team ID must be 2-10 letters or digits (e.g. T05).");
        }
        return t.toUpperCase();
    }

    public static List<String> teamMembers(String text) throws InvalidTeamException {
        List<String> members = splitList(text, ",");
        if (members.isEmpty()) {
            throw new InvalidTeamException("A team needs at least one member.");
        }
        return members;
    }

    public static List<String> teamAnimalTypes(String text) throws InvalidTeamException {
        List<String> types = splitList(text, ",");
        if (types.isEmpty()) {
            throw new InvalidTeamException("A team must handle at least one animal category.");
        }
        List<String> result = new ArrayList<String>();
        for (String type : types) {
            if (!Animal.isValidType(type.toLowerCase())) {
                throw new InvalidTeamException("Invalid animal category '" + type
                        + "'. Valid categories: bird, reptile, mammal.");
            }
            result.add(type.toLowerCase());
        }
        return result;
    }

    public static List<String> teamSkills(String text) throws InvalidTeamException {
        List<String> skills = splitList(text, ",");
        if (skills.isEmpty()) {
            throw new InvalidTeamException("A team needs at least one rescue skill.");
        }
        return skills;
    }

    public static int maxIncidents(String text) throws InvalidTeamException {
        try {
            int n = Integer.parseInt(clean(text));
            if (n < 1 || n > 10) {
                throw new InvalidTeamException("Maximum simultaneous incidents must be between 1 and 10.");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new InvalidTeamException("Maximum simultaneous incidents must be a whole number.");
        }
    }
}
