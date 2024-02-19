package edu.ucsd.cse110.successorator.lib.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class GoalList
{

    // Hold the list of goals
    private final List<Goal> goals;

    /*
    Constructor for the goal list
    @param List<Goal> the list of goals
    @return GoalList the GoalList object
    */
    public GoalList(List<Goal> goals)
    {

        // Set the goals for the goal list
        this.goals = goals;
    }

    /*
    Helper method to sort the goals
    @param none
    @return void
    */
    public void sortGoals()
    {

        // Sort the goals
        Collections.sort(goals, new Comparator<Goal>() {
            public int compare(Goal goal1, Goal goal2) {
                return Integer.compare(goal1.sortOrder(), goal2.sortOrder());
            }
        });
    }

    /*
    Getter method for the goals
    @param none
    @return List<Goal>
     */
    public List<Goal> getGoals()
    {
        return this.goals;
    }
}
