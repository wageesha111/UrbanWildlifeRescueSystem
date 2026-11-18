package rescue.model;

import rescue.exceptions.InvalidAnimalTypeException;
import rescue.exceptions.RescueException;

/**
 * Abstract class for the animals in an incident.
 * Bird, Reptile and Mammal extend it (inheritance) and each one
 * gives its own handling note (polymorphism).
 */
public abstract class Animal {
    private final String type;
    private final Condition condition;
    private final int count;

    protected Animal(String type, Condition condition, int count) {
        this.type = type;
        this.condition = condition;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getCount() {
        return count;
    }

    /** Each animal category has its own handling advice. */
    public abstract String getHandlingNote();

    public static boolean isValidType(String type) {
        return "bird".equals(type) || "reptile".equals(type) || "mammal".equals(type);
    }

    /** Creates the correct Animal object for the given type. */
    public static Animal create(String type, Condition condition, int count) throws RescueException {
        if (type == null || type.trim().isEmpty()) {
            throw new InvalidAnimalTypeException("Animal type cannot be empty.");
        }
        if (count < 1) {
            throw new RescueException("Number of animals must be at least 1.");
        }
        String t = type.trim().toLowerCase();
        if (t.equals("bird")) {
            return new Bird(condition, count);
        } else if (t.equals("reptile")) {
            return new Reptile(condition, count);
        } else if (t.equals("mammal")) {
            return new Mammal(condition, count);
        }
        throw new InvalidAnimalTypeException("Invalid animal type '" + type.trim()
                + "'. Valid types: bird, reptile, mammal.");
    }

    @Override
    public String toString() {
        return count + " x " + type + " (" + condition + ")";
    }
}
