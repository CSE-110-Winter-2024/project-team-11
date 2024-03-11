package edu.ucsd.cse110.successorator;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoal;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoalRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;

public class MainViewModel extends ViewModel {
    // Today
    private final GoalRepository todayOngoingGoalRepository, todayCompletedGoalRepository;

    // Tomorrow
    private final GoalRepository tmrwOngoingGoalRepository, tmrwCompletedGoalRepository;

    // Pending
    private final GoalRepository pendingGoalRepository;

    // Recurring
    private final RecurringGoalRepository recurringGoalRepository;

    public enum ViewEnum { TODAY, TMRW, PENDING, RECURRING }
    private final MutableSubject<ViewEnum> currentView = new SimpleSubject<>();
    private DateFragment.DisplayTextLogic dateDisplayTextLogic;

    private final TimeManager timeManager;
    private final MutableSubject<LocalDate> displayTime = new SimpleSubject<>();
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

        currentView.setValue(ViewEnum.TODAY);

        this.timeManager = timeManager;

        timeManager.getDate().observe(time -> {
            if (time == null) return;

            clearCompleted();
            displayTime.setValue(time.plusDays(currentView.getValue()==ViewEnum.TMRW ? 1 : 0));
        });

        getCurrentView().observe(view -> {
            displayTime.setValue(timeManager.getDate().getValue().plusDays(view==ViewEnum.TMRW ? 1 : 0));
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

    public Subject<ViewEnum> getCurrentView() {
        return currentView;
    }

    public void setCurrentView(ViewEnum viewEnum) {
        if (viewEnum == currentView.getValue()) {
            return;
        }

        currentView.setValue(viewEnum);
    }

    public String getDateDisplayText(String dateText) {
        return dateDisplayTextLogic.fromDateText(dateText);
    }

    public void setDateDisplayTextLogic(DateFragment.DisplayTextLogic dateDisplayTextLogic) {
        this.dateDisplayTextLogic = dateDisplayTextLogic;
    }

    public Subject<LocalDate> getDate() {
        return displayTime;
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
    public void tmrwCompleteGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(true);

        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {
            tmrwOngoingGoalRepository.remove(goal.id());
            tmrwCompletedGoalRepository.prepend(completedGoal);
        }
    }

    public void tmrwUncompleteGoal(Goal goal) {
        // Set the goal as completed
        Goal completedGoal = goal.withIsCompleted(false);

        // Remove old goal, add new Completed Goal
        if (goal.id() != null) {
            tmrwCompletedGoalRepository.remove(goal.id());
            tmrwOngoingGoalRepository.prepend(completedGoal);
        }
    }

    public void recurringAppend(RecurringGoal goal) {
        recurringGoalRepository.add(goal);
    }

    public void pendingAppend(Goal goal) {
        pendingGoalRepository.append(goal);
    }

    public void nextDay() {
        timeManager.nextDay();
    }

    public void clearCompleted() {
        todayCompletedGoalRepository.clear();
        tmrwCompletedGoalRepository.clear();
    }
}
