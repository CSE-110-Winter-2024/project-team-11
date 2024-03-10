package edu.ucsd.cse110.successorator;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.data.db.goals.RoomGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class MainViewModel extends ViewModel {
    private final GoalRepository ongoingGoalRepository;
    private final GoalRepository completedGoalRepository;

    private final TimeManager timeManager;
    private final MutableSubject<LocalDateTime> time;
    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(
                                app.getOngoingGoalRepository(),
                                app.getCompletedGoalRepository(),
                                app.getTimeManager());
                    });


    public MainViewModel(
            GoalRepository ongoingGoalRepository,
            GoalRepository completedGoalRepository,
            TimeManager timeManager)
    {

        this.ongoingGoalRepository = ongoingGoalRepository;
        this.completedGoalRepository = completedGoalRepository;

        this.timeManager = timeManager;
        this.time = new SimpleSubject<>();
        this.time.setValue(null);

        timeManager.getLocalDateTime().observe(time -> {
            if (time == null) return;

            this.time.setValue(time);

            LocalDateTime lastClearedTime = timeManager.getLastCleared();
//             if the date changed and the new date is after the old date
            if(time.isAfter(lastClearedTime)
                    && (time.getDayOfYear() != lastClearedTime.getDayOfYear()
                    || time.getYear() != lastClearedTime.getYear())) {
                clearCompleted();
            }

            timeManager.updateLastCleared(time);
        });

    }

    public Subject<List<Goal>> getOngoingGoals() {
        return ongoingGoalRepository.findAllContextSorted();
    }

    public Subject<List<Goal>> getCompletedGoals() {
        return completedGoalRepository.findAll();
    }

    public Subject<LocalDateTime> getTime() {
        return timeManager.getLocalDateTime();
    }

    public void append(Goal goal) {
        if (goal.isCompleted()) {
            completedGoalRepository.append(goal);
        } else {
            ongoingGoalRepository.append(goal);
        }
    }


    public void completeGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(true);

        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {
            ongoingGoalRepository.remove(goal.id());
            completedGoalRepository.prepend(completedGoal);
        }
    }

    public void unCompleteGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(false);
//
        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {
            completedGoalRepository.remove(goal.id());
            ongoingGoalRepository.prepend(completedGoal);
        }
    }

    public void nextDay() {
        timeManager.nextDay();

    }

    public void clearCompleted() {
        completedGoalRepository.clear();
    }
}
