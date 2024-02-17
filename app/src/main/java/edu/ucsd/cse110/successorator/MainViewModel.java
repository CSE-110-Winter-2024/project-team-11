package edu.ucsd.cse110.successorator;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.ui.date.CalendarManager;

public class MainViewModel extends ViewModel {
    private final GoalRepository ongoingGoalRepository;
    private final GoalRepository completedGoalRepository;

    private final MutableSubject<List<Goal>> completedGoals;
    private final MutableSubject<List<Goal>> ongoingGoals;

    private final CalendarManager calendarManager;
    private final MutableSubject<Calendar> calendar;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(
                                app.getOngoingGoalRepository(),
                                app.getCompletedGoalRepository(),
                                CalendarManager.newInstance(Calendar.getInstance()));
                    });
    public MainViewModel(
            GoalRepository ongoingGoalRepository,
            GoalRepository completedGoalRepository,
            CalendarManager calendarManager) {
        this.ongoingGoalRepository = ongoingGoalRepository;
        this.completedGoalRepository = completedGoalRepository;

        this.completedGoals = new SimpleSubject<>();
        this.completedGoals.setValue(new ArrayList<>());

        this.ongoingGoals = new SimpleSubject<>();
        this.ongoingGoals.setValue(new ArrayList<>());

        this.calendarManager = calendarManager;
        this.calendar = new SimpleSubject<>();
        this.calendar.setValue(null);

        // not sure if this is repetitive code.....might be

        // When the list of ongoing goals changes, reset the ordering
        ongoingGoalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOngoingGoals = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());

            ongoingGoals.setValue(newOngoingGoals);
        });
        // When the list of completed goals changes, reset the ordering
        completedGoalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newCompletedGoals = goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());

            completedGoals.setValue(newCompletedGoals);
        });

        calendarManager.getCalendar().observe(calendar -> {
            if (calendar == null) return;

            this.calendar.setValue(calendar);
        });
    }

    public Subject<List<Goal>> getOngoingGoals() {
        return ongoingGoals;
    }

    public Subject<List<Goal>> getCompletedGoals() {
        return completedGoals;
    }

    public Subject<Calendar> getCalendar() {
        return calendar;
    }


    // need an append method for adding a goal
    public void append(Goal goal) {
        if (goal.isCompleted()) {
            completedGoalRepository.append(goal);
        } else {
            ongoingGoalRepository.append(goal);
        }
    }

    // prepend method for uncompleting a goal


    public void completeGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(true);
//        Goal completedGoal = temp.withSortOrder(0);

        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {
            ongoingGoalRepository.remove(goal.id());
            completedGoalRepository.prepend(completedGoal);
        }

//      Update the Lists
//        List<Goal> updatedOngoingGoals = new ArrayList<>(ongoingGoals.getValue());
//        updatedOngoingGoals.remove(goal);
//        ongoingGoals.setValue(updatedOngoingGoals);
//
//        List<Goal> updatedCompletedGoals = new ArrayList<>(completedGoals.getValue());
//        updatedCompletedGoals.add(0, completedGoal);
//        completedGoals.setValue(updatedCompletedGoals);

    }

    public void nextDay() {
        calendarManager.nextDay();
    }
}
