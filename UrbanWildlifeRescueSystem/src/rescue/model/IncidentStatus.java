package rescue.model;

import rescue.exceptions.InvalidStatusException;

/** Life cycle of an incident. */
public enum IncidentStatus {
    PENDING_ASSIGNMENT, ASSIGNED, IN_PROGRESS, COMPLETED;

    public static IncidentStatus fromString(String text) throws InvalidStatusException {
        if (text != null) {
            for (IncidentStatus s : IncidentStatus.values()) {
                if (s.name().equalsIgnoreCase(text.trim())) {
                    return s;
                }
            }
        }
        throw new InvalidStatusException("Invalid incident status '" + text
                + "'. Use PENDING_ASSIGNMENT, ASSIGNED, IN_PROGRESS or COMPLETED.");
    }
}
