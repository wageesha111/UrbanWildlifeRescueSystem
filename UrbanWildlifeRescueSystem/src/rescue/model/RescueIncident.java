package rescue.model;

import java.util.ArrayList;
import java.util.List;

import rescue.exceptions.MissingLocationException;
import rescue.exceptions.RescueException;


public class RescueIncident {
    private final String incidentId;
    private final Animal animal;
    private final String location;
    private final boolean immediateDanger;
    private final List<Equipment> specialRequirements;
    private final String reporterContact;
    private int priorityScore;
    private PriorityLevel priorityLevel;
    private IncidentStatus status;
    private RescueTeam assignedTeam; // association with the team doing the rescue (0..1)


    public RescueIncident(String incidentId, Animal animal, String location, boolean immediateDanger,
                          List<Equipment> specialRequirements, String reporterContact) throws RescueException {
        this(incidentId, animal, location, immediateDanger, specialRequirements, reporterContact,
                0, PriorityLevel.LOW, IncidentStatus.PENDING_ASSIGNMENT, null);
    }


    public RescueIncident(String incidentId, Animal animal, String location, boolean immediateDanger,
                          List<Equipment> specialRequirements, String reporterContact,
                          int priorityScore, PriorityLevel priorityLevel, IncidentStatus status,
                          RescueTeam assignedTeam) throws RescueException {
        if (incidentId == null || incidentId.trim().isEmpty()) {
            throw new RescueException("Incident ID cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new MissingLocationException("Location is missing.");
        }
        this.incidentId = incidentId.trim();
        this.animal = animal;
        this.location = location.trim();
        this.immediateDanger = immediateDanger;
        this.specialRequirements = new ArrayList<Equipment>(specialRequirements);
        this.reporterContact = reporterContact;
        this.priorityScore = priorityScore;
        this.priorityLevel = priorityLevel;
        this.status = status;
        this.assignedTeam = assignedTeam;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public Animal getAnimal() {
        return animal;
    }

    public String getLocation() {
        return location;
    }

    public boolean isImmediateDanger() {
        return immediateDanger;
    }

    public List<Equipment> getSpecialRequirements() {
        return new ArrayList<Equipment>(specialRequirements);
    }

    public String getReporterContact() {
        return reporterContact;
    }

    public synchronized int getPriorityScore() {
        return priorityScore;
    }

    public synchronized PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public synchronized void setPriority(int score, PriorityLevel level) {
        this.priorityScore = score;
        this.priorityLevel = level;
    }

    public synchronized IncidentStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public synchronized RescueTeam getAssignedTeam() {
        return assignedTeam;
    }


    public synchronized void setAssignedTeam(RescueTeam team) {
        this.assignedTeam = team;
    }


    public synchronized String getAssignedTeamId() {
        if (assignedTeam == null) {
            return null;
        }
        return assignedTeam.getTeamId();
    }

    @Override
    public String toString() {
        return incidentId + " | " + animal + " | " + location + " | " + priorityLevel
                + " (" + priorityScore + ") | " + status;
    }
}
