package rescue.model;

import java.util.ArrayList;
import java.util.List;

import rescue.exceptions.InvalidTeamException;
import rescue.exceptions.TeamUnavailableException;


public class RescueTeam {
    private final String teamId;
    private final List<String> members;
    private final List<String> animalTypes;
    private final List<String> skills;
    private final List<Equipment> equipment;
    private TeamStatus status;
    private final int maxIncidents;
    private int currentWorkload;


    public RescueTeam(String teamId, List<String> members, List<String> animalTypes, List<String> skills,
                      List<Equipment> equipment, TeamStatus status, int maxIncidents,
                      int currentWorkload) throws InvalidTeamException {
        if (teamId == null || teamId.trim().isEmpty()) {
            throw new InvalidTeamException("Team ID cannot be empty.");
        }
        if (members.isEmpty()) {
            throw new InvalidTeamException("Team " + teamId + " must have at least one member.");
        }
        if (animalTypes.isEmpty()) {
            throw new InvalidTeamException("Team " + teamId + " must handle at least one animal category.");
        }
        if (skills.isEmpty()) {
            throw new InvalidTeamException("Team " + teamId + " must have at least one rescue skill.");
        }
        if (maxIncidents < 1) {
            throw new InvalidTeamException("Maximum simultaneous incidents must be at least 1.");
        }
        if (currentWorkload < 0 || currentWorkload > maxIncidents) {
            throw new InvalidTeamException("Current workload of team " + teamId + " is invalid.");
        }
        this.teamId = teamId.trim().toUpperCase();
        this.members = new ArrayList<String>(members);
        this.animalTypes = new ArrayList<String>();
        for (String type : animalTypes) {
            String t = type.trim().toLowerCase();
            if (!Animal.isValidType(t)) {
                throw new InvalidTeamException("Team " + teamId + " has an invalid animal category '" + type + "'.");
            }
            this.animalTypes.add(t);
        }
        this.skills = new ArrayList<String>();
        for (String skill : skills) {
            this.skills.add(skill.trim().toLowerCase());
        }
        this.equipment = new ArrayList<Equipment>(equipment);
        this.maxIncidents = maxIncidents;
        this.currentWorkload = currentWorkload;
        // keep the status consistent with the workload
        if (status == TeamStatus.UNAVAILABLE) {
            this.status = TeamStatus.UNAVAILABLE;
        } else if (currentWorkload > 0) {
            this.status = TeamStatus.ASSIGNED;
        } else {
            this.status = TeamStatus.AVAILABLE;
        }
    }

    /** Constructor for a newly registered team (no workload yet). */
    public RescueTeam(String teamId, List<String> members, List<String> animalTypes, List<String> skills,
                      List<Equipment> equipment, boolean available, int maxIncidents) throws InvalidTeamException {
        this(teamId, members, animalTypes, skills, equipment,
                available ? TeamStatus.AVAILABLE : TeamStatus.UNAVAILABLE, maxIncidents, 0);
    }

    public String getTeamId() {
        return teamId;
    }

    public List<String> getMembers() {
        return new ArrayList<String>(members);
    }

    public List<String> getAnimalTypes() {
        return new ArrayList<String>(animalTypes);
    }

    public List<String> getSkills() {
        return new ArrayList<String>(skills);
    }

    public List<Equipment> getEquipment() {
        return new ArrayList<Equipment>(equipment);
    }

    public synchronized TeamStatus getStatus() {
        return status;
    }

    public int getMaxIncidents() {
        return maxIncidents;
    }

    public synchronized int getCurrentWorkload() {
        return currentWorkload;
    }

    public boolean canHandle(String animalType) {
        return animalTypes.contains(animalType.toLowerCase());
    }

    public boolean hasSkill(String skill) {
        return skills.contains(skill.toLowerCase());
    }

    /** Checks one piece of equipment. */
    public boolean hasEquipment(Equipment item) {
        return equipment.contains(item);
    }

    /** Checks a list of equipment (method overloading). */
    public boolean hasEquipment(List<Equipment> required) {
        return getMissingEquipment(required).isEmpty();
    }

    public List<Equipment> getMissingEquipment(List<Equipment> required) {
        List<Equipment> missing = new ArrayList<Equipment>();
        for (Equipment item : required) {
            if (!hasEquipment(item)) {
                missing.add(item);
            }
        }
        return missing;
    }

    /** A team can take a new incident if it is not unavailable and has not reached its maximum. */
    public synchronized boolean isAvailable() {
        return status != TeamStatus.UNAVAILABLE && currentWorkload < maxIncidents;
    }

    public synchronized int getRemainingCapacity() {
        return maxIncidents - currentWorkload;
    }

    /** Adds one incident to the team workload. */
    public synchronized void takeIncident() throws TeamUnavailableException {
        if (!isAvailable()) {
            throw new TeamUnavailableException("Team " + teamId + " is not available (status "
                    + status + ", workload " + currentWorkload + "/" + maxIncidents + ").");
        }
        currentWorkload++;
        status = TeamStatus.ASSIGNED;
    }

    /** Removes one incident from the team workload. */
    public synchronized void finishIncident() {
        if (currentWorkload > 0) {
            currentWorkload--;
        }
        if (currentWorkload == 0 && status == TeamStatus.ASSIGNED) {
            status = TeamStatus.AVAILABLE;
        }
    }

    @Override
    public String toString() {
        return teamId + " (" + status + ", workload " + getCurrentWorkload() + "/" + maxIncidents + ")";
    }
}
