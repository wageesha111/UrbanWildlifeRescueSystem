package rescue.service;

import rescue.exceptions.InvalidPriorityException;
import rescue.model.Condition;
import rescue.model.PriorityLevel;
import rescue.model.RescueIncident;


public class PriorityCalculator {
    private static final String[] HIGH_RISK_PLACES = {"highway", "road", "rooftop", "roof", "river",
        "canal", "railway", "construction", "drain"};
    private static final String[] LOW_RISK_PLACES = {"park", "garden", "backyard", "school"};

    private int conditionPoints(Condition condition) {
        switch (condition) {
            case INJURED:
                return 30;
            case TRAPPED:
                return 25;
            case DISPLACED:
                return 15;
            default:
                return 10; // ABANDONED
        }
    }

    private int locationRiskPoints(String location) {
        String text = location.toLowerCase();
        for (String place : HIGH_RISK_PLACES) {
            if (text.contains(place)) {
                return 20;
            }
        }
        for (String place : LOW_RISK_PLACES) {
            if (text.contains(place)) {
                return 5;
            }
        }
        return 10;
    }

    public int calculateScore(RescueIncident incident) {
        int score = conditionPoints(incident.getAnimal().getCondition());
        if (incident.isImmediateDanger()) {
            score += 30;
        }
        score += Math.min(incident.getAnimal().getCount(), 5) * 4; // up to 20 points
        score += locationRiskPoints(incident.getLocation());
        return score;
    }

    public PriorityLevel getLevel(int score) throws InvalidPriorityException {
        if (score < 0 || score > 100) {
            throw new InvalidPriorityException("Priority score " + score + " is outside the range 0-100.");
        }
        if (score >= 70) {
            return PriorityLevel.CRITICAL;
        } else if (score >= 50) {
            return PriorityLevel.HIGH;
        } else if (score >= 30) {
            return PriorityLevel.MEDIUM;
        }
        return PriorityLevel.LOW;
    }

    public void assignPriority(RescueIncident incident) throws InvalidPriorityException {
        int score = calculateScore(incident);
        incident.setPriority(score, getLevel(score));
    }
}
