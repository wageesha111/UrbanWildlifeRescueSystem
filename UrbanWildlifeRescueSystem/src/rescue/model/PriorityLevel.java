package rescue.model;

import rescue.exceptions.InvalidPriorityException;

/** Priority categories. A higher rank is handled first. */
public enum PriorityLevel {
    CRITICAL(4), HIGH(3), MEDIUM(2), LOW(1);

    private final int rank;

    PriorityLevel(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public static PriorityLevel fromString(String text) throws InvalidPriorityException {
        if (text != null) {
            for (PriorityLevel p : PriorityLevel.values()) {
                if (p.name().equalsIgnoreCase(text.trim())) {
                    return p;
                }
            }
        }
        throw new InvalidPriorityException("Invalid priority level '" + text + "'.");
    }
}
