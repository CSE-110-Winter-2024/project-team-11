package edu.ucsd.cse110.successorator.lib.domain.goal;

public enum GoalContext {
    HOME, WORK, SCHOOL, ERRAND;

    public String getText() {
        return name().charAt(0) + name().toLowerCase().substring(1);
    }
}
