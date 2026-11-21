package rescue.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rescue.model.Animal;
import rescue.model.Condition;
import rescue.model.RescueIncident;
import rescue.model.RescueTeam;

public class TeamMatcher {

    /** The rescue skill needed for each animal condition. */
    public String getRequiredSkill(Condition condition) {
        switch (condition) {
            case INJURED:
                return "first aid";
            case TRAPPED:
                return "extraction";
            case DISPLACED:
                return "relocation";
            default:
                return "animal care";
        }
    }

    public boolean isSuitable(RescueTeam team, RescueIncident incident) {
        Animal animal = incident.getAnimal();
        return team.isAvailable()
                && team.canHandle(animal.getType())
                && team.hasSkill(getRequiredSkill(animal.getCondition()))
                && team.hasEquipment(incident.getSpecialRequirements());
    }

    public List<RescueTeam> findSuitableTeams(RescueIncident incident, List<RescueTeam> teams) {
        List<RescueTeam> result = new ArrayList<RescueTeam>();
        for (RescueTeam team : teams) {
            if (isSuitable(team, incident)) {
                result.add(team);
            }
        }
        Collections.sort(result, new Comparator<RescueTeam>() {
            @Override
            public int compare(RescueTeam a, RescueTeam b) {
                int byCapacity = b.getRemainingCapacity() - a.getRemainingCapacity();
                if (byCapacity != 0) {
                    return byCapacity;
                }
                return a.getTeamId().compareTo(b.getTeamId());
            }
        });
        return result;
    }
}
