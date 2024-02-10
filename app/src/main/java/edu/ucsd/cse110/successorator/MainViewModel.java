package edu.ucsd.cse110.successorator;

import java.util.List;

public class MainViewModel extends ViewModel
{

    // Hold the domain state
    private final GoalRepository goalsRepository;

    // Hold the ongoing goals
    private final MutableSubject<List<Goal>> onGoingGoals;

    // Hold the completed goals
    private final MutableSubject<List<Goal>> completedGoals;

    /*
    Getter for the ongoing goals
    @param none
    @return List<Goal>
     */
    public Subject<List<Goal>> getOnGoingGoals()
    {
        return onGoingGoals;
    }

    public Subject<List<Goal>> getCompletedGoals()
    {
        return completedGoals;
    }
}
