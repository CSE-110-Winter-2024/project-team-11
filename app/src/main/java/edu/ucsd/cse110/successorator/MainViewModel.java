package edu.ucsd.cse110.successorator;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDateTime;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoalRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class MainViewModel extends ViewModel {
    // Today
    private final GoalRepository todayOngoingGoalRepository, todayCompletedGoalRepository;

    // Tomorrow
    private final GoalRepository tmrwOngoingGoalRepository, tmrwCompletedGoalRepository;

    // Pending
    private final GoalRepository pendingGoalRepository;

    // Recurring
    private final RecurringGoalRepository recurringGoalRepository;

    private final TimeManager timeManager;
    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(
                                app.getTodayOngoingGoalRepository(),
                                app.getTodayCompletedGoalRepository(),
                                app.getTmrwOngoingGoalRepository(),
                                app.getTmrwCompletedGoalRepository(),
                                app.getPendingGoalRepository(),
                                app.getRecurringGoalRepository(),
                                app.getTimeManager());
                    });


    public MainViewModel(
            GoalRepository todayOngoingGoalRepository,
            GoalRepository todayCompletedGoalRepository,
            GoalRepository tmrwOngoingGoalRepository,
            GoalRepository tmrwCompletedGoalRepository,
            GoalRepository pendingGoalRepository,
            RecurringGoalRepository recurringGoalRepository,
            TimeManager timeManager) {
        this.todayOngoingGoalRepository = todayOngoingGoalRepository;
        this.todayCompletedGoalRepository = todayCompletedGoalRepository;

        this.tmrwOngoingGoalRepository = tmrwOngoingGoalRepository;
        this.tmrwCompletedGoalRepository = tmrwCompletedGoalRepository;

        this.pendingGoalRepository = pendingGoalRepository;

        this.recurringGoalRepository = recurringGoalRepository;

        this.timeManager = timeManager;

        timeManager.getLocalDateTime().observe(time -> {
            if (time == null) return;

            LocalDateTime lastClearedTime = timeManager.getLastCleared();

            // if the date changed and the new date is after the old date
            if(time.isAfter(lastClearedTime)
                    && (time.getDayOfYear() != lastClearedTime.getDayOfYear()
                    || time.getYear() != lastClearedTime.getYear())) {
                clearCompleted();
            }

            timeManager.updateLastCleared(time);
        });

    }

    public Subject<List<Goal>> getTodayOngoingGoals() {
        return todayOngoingGoalRepository.findAll();
    }

    public Subject<List<Goal>> getTodayCompletedGoals() {
        return todayCompletedGoalRepository.findAll();
    }

    public Subject<List<Goal>> getTmrwOngoingGoals() {
        return tmrwOngoingGoalRepository.findAll();
    }

    public Subject<List<Goal>> getTmrwCompletedGoals() {
        return tmrwCompletedGoalRepository.findAll();
    }

    public Subject<List<Goal>> getPendingGoals() {
        return pendingGoalRepository.findAll();
    }

    public Subject<List<RecurringGoal>> getRecurringGoals() {
        return recurringGoalRepository.findAll();
    }

    public Subject<LocalDateTime> getTime() {
        return timeManager.getLocalDateTime();
    }

    public void todayAppend(Goal goal) {
        if (goal.isCompleted()) {
            todayCompletedGoalRepository.append(goal);
        } else {
            todayOngoingGoalRepository.append(goal);
        }
    }


    public void todayCompleteGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(true);
//        Goal completedGoal = temp.withSortOrder(0);

        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {
            todayOngoingGoalRepository.remove(goal.id());
            todayCompletedGoalRepository.prepend(completedGoal);
        }
    }

    public void todayUncompleteGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(false);
//        Goal completedGoal = temp.withSortOrder(0);

        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {
            todayCompletedGoalRepository.remove(goal.id());
            todayOngoingGoalRepository.prepend(completedGoal);
        }
    }

    public void nextDay() {
        timeManager.nextDay();
    }

    public void clearCompleted() {
        todayCompletedGoalRepository.clear();
        tmrwCompletedGoalRepository.clear();
    }
}
