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

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class MainViewModel extends ViewModel {
    private final GoalRepository ongoingHomeGoalRepository;
    private final GoalRepository ongoingWorkGoalRepository;
    private final GoalRepository ongoingSchoolGoalRepository;
    private final GoalRepository ongoingErrandGoalRepository;
    private final GoalRepository completedGoalRepository;

    private final MutableSubject<List<Goal>> completedGoals;
    private final MutableSubject<List<Goal>> ongoingHomeGoals;

    private final MutableSubject<List<Goal>> ongoingWorkGoals;

    private final MutableSubject<List<Goal>> ongoingSchoolGoals;

    private final MutableSubject<List<Goal>> ongoingErrandGoals;

    private final TimeManager timeManager;
    private final MutableSubject<LocalDateTime> time;
    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(
                                app.getOngoingHomeGoalRepository(),
                                app.getOngoingWorkGoalRepository(),
                                app.getOngoingSchoolGoalRepository(),
                                app.getOngoingErrandGoalRepository(),
                                app.getCompletedGoalRepository(),
                                app.getTimeManager());
                    });


    public MainViewModel(
            GoalRepository ongoingHomeGoalRepository,
            GoalRepository ongoingWorkGoalRepository,
            GoalRepository ongoingSchoolGoalRepository,
            GoalRepository ongoingErrandGoalRepository,
            GoalRepository completedGoalRepository,
            TimeManager timeManager)
    {

        this.ongoingHomeGoalRepository = ongoingHomeGoalRepository;
        this.ongoingWorkGoalRepository = ongoingWorkGoalRepository;
        this.ongoingSchoolGoalRepository = ongoingSchoolGoalRepository;
        this.ongoingErrandGoalRepository = ongoingErrandGoalRepository;
        this.completedGoalRepository = completedGoalRepository;

        this.completedGoals = new SimpleSubject<>();
        this.completedGoals.setValue(new ArrayList<>());

        this.ongoingHomeGoals = new SimpleSubject<>();
        this.ongoingHomeGoals.setValue(new ArrayList<>());
        this.ongoingWorkGoals = new SimpleSubject<>();
        this.ongoingWorkGoals.setValue(new ArrayList<>());
        this.ongoingSchoolGoals = new SimpleSubject<>();
        this.ongoingSchoolGoals.setValue(new ArrayList<>());
        this.ongoingErrandGoals = new SimpleSubject<>();
        this.ongoingErrandGoals.setValue(new ArrayList<>());

        this.timeManager = timeManager;
        this.time = new SimpleSubject<>();
        this.time.setValue(null);

        // When the list of ongoing home goals changes, reset the ordering
        ongoingHomeGoalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOngoingGoals = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());
            ongoingHomeGoals.setValue(newOngoingGoals);
        });

        // When the list of ongoing work goals changes, reset the ordering
        ongoingWorkGoalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOngoingGoals = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());
            ongoingWorkGoals.setValue(newOngoingGoals);
        });

        // When the list of ongoing school goals changes, reset the ordering
        ongoingSchoolGoalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOngoingGoals = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());
            ongoingSchoolGoals.setValue(newOngoingGoals);
        });

        // When the list of ongoing errands goals changes, reset the ordering
        ongoingErrandGoalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOngoingGoals = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());
            ongoingErrandGoals.setValue(newOngoingGoals);
        });

        // When the list of completed goals changes, reset the ordering
        completedGoalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newCompletedGoals = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());

            completedGoals.setValue(newCompletedGoals);
        });
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

            System.out.println(time + " " + lastClearedTime);

            timeManager.updateLastCleared(time);
        });

    }

    public Subject<List<Goal>> getOngoingHomeGoals() {
        return ongoingHomeGoals;
    }

    public Subject<List<Goal>> getOngoingWorkGoals() {
        return ongoingWorkGoals;
    }

    public Subject<List<Goal>> getOngoingSchoolGoals() {
        return ongoingSchoolGoals;
    }

    public Subject<List<Goal>> getOngoingErrandGoals() {
        return ongoingErrandGoals;
    }

    public Subject<List<Goal>> getCompletedGoals() {
        return completedGoals;
    }

    public Subject<LocalDateTime> getTime() {
        return timeManager.getLocalDateTime();
    }

    public void append(Goal goal)
    {
        if (goal.isCompleted()) {
            completedGoalRepository.append(goal);
        } else {

            switch (goal.getContext())
            {
                case "HOME":
                    ongoingHomeGoalRepository.append(goal);
                    break;
                case "WORK":
                    ongoingWorkGoalRepository.append(goal);
                    break;
                case "SCHOOL":
                    ongoingSchoolGoalRepository.append(goal);
                    break;
                case "ERRAND":
                    ongoingErrandGoalRepository.append(goal);
                    break;
            }
        }
    }


    public void completeGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(true);

        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {

            switch (goal.getContext())
            {
                case "HOME":
                    ongoingHomeGoalRepository.remove(goal.id());
                    break;
                case "WORK":
                    ongoingWorkGoalRepository.remove(goal.id());
                    break;
                case "SCHOOL":
                    ongoingSchoolGoalRepository.remove(goal.id());
                    break;
                case "ERRAND":
                    ongoingErrandGoalRepository.remove(goal.id());
                    break;

            }
            completedGoalRepository.prepend(completedGoal);
        }
    }

    public void unCompleteGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(false);
//
        // Remove old goal, add new Completed Goal
        if (goal.id() != null)
        {
            completedGoalRepository.remove(goal.id());
            switch (goal.getContext())
            {
                case "HOME":
                    ongoingHomeGoalRepository.prepend(completedGoal);
                    break;
                case "WORK":
                    ongoingWorkGoalRepository.prepend(completedGoal);
                    break;
                case "SCHOOL":
                    ongoingSchoolGoalRepository.prepend(completedGoal);
                    break;
                case "ERRAND":
                    ongoingErrandGoalRepository.prepend(completedGoal);
                    break;
            }
        }
    }

    public void nextDay() {
        timeManager.nextDay();

    }

    public void clearCompleted() {
        completedGoalRepository.clear();
    }
}
