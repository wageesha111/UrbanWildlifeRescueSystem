package rescue.model;

import rescue.exceptions.InvalidTeamException;

/** Current status of a rescue team. */
public enum TeamStatus {
    AVAILABLE, ASSIGNED, UNAVAILABLE;

    public static TeamStatus fromString(String text) throws InvalidTeamException {
        if (text != null) {
            for (TeamStatus s : TeamStatus.values()) {
                if (s.name().equalsIgnoreCase(text.trim())) {
                    return s;
                }
            }
        }
        throw new InvalidTeamException("Invalid team status '" + text + "'.");
    }
}
