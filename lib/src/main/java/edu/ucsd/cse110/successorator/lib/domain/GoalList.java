package edu.ucsd.cse110.successorator.lib.domain;

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

        this.goals = goals;
    }

    /*
    Helper method to sort the goals
    @param none
    @return none
    */
    public void sortGoals()
    {

        // Sort the goals
        Collections.sort(goals, new Comparator<Goal>() {
            @Override
            public int compare(Goal goal1, Goal goal2) {
                return Integer.compare(goal1.sortOrder(), goal2.sortOrder());
            }
        });
    }
}
