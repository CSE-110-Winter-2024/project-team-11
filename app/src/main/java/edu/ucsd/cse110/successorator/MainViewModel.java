package edu.ucsd.cse110.successorator;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final GoalRepository ongoingGoalRepository;
    private final GoalRepository completedGoalRepository;

    private final MutableSubject<List<Goal>> completedGoals;
    private final MutableSubject<List<Goal>> ongoingGoals;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getOngoingGoalRepository(), app.getCompletedGoalRepository());
                    });
    public MainViewModel(GoalRepository ongoingGoalRepository, GoalRepository completedGoalRepository) {
        this.ongoingGoalRepository = ongoingGoalRepository;
        this.completedGoalRepository = completedGoalRepository;

        this.completedGoals = new SimpleSubject<>();
        this.ongoingGoals = new SimpleSubject<>();

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
    }

    public Subject<List<Goal>> getOngoingList() {
        return ongoingGoalRepository.findAll();
    }

    public Subject<List<Goal>> getCompletedList() {
        return completedGoalRepository.findAll();
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
}
