package rescue.model;

import rescue.exceptions.RescueException;

/** Condition of the reported animal(s). */
public enum Condition {
    INJURED, TRAPPED, DISPLACED, ABANDONED;

    public static Condition fromString(String text) throws RescueException {
        if (text != null) {
            for (Condition c : Condition.values()) {
                if (c.name().equalsIgnoreCase(text.trim())) {
                    return c;
                }
            }
        }
        throw new RescueException("Invalid condition '" + text
                + "'. Use injured, trapped, displaced or abandoned.");
    }
}
