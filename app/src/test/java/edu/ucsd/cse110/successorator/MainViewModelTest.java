package edu.ucsd.cse110.successorator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.GoalInMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.data.RecurringGoalInMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeManager;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.SimpleRecurringGoalRepository;

public class MainViewModelTest {
    MainViewModel model;
    MainViewModel appResetModel;

    @Before
    public void setUp() throws Exception {
        SimpleGoalRepository todayOngoingRepo = new SimpleGoalRepository(new GoalInMemoryDataSource());
        SimpleGoalRepository todayCompletedRepo = new SimpleGoalRepository(new GoalInMemoryDataSource());

        SimpleGoalRepository tmrwOngoingRepo = new SimpleGoalRepository(new GoalInMemoryDataSource());
        SimpleGoalRepository tmrwCompletedRepo = new SimpleGoalRepository(new GoalInMemoryDataSource());

        SimpleGoalRepository pendingRepo = new SimpleGoalRepository(new GoalInMemoryDataSource());

        RecurringGoalRepository recurringRepo = new SimpleRecurringGoalRepository(new RecurringGoalInMemoryDataSource());

        TimeManager timeManager = new SimpleTimeManager();
        LocalDateTime beforeReset = LocalDateTime.now()
                .withHour(1)
                .withMinute(59)
                .withSecond(57);
        TimeManager resetTimeManager = new SimpleTimeManager(beforeReset);
        model = new MainViewModel(
                todayOngoingRepo,
                todayCompletedRepo,
                tmrwOngoingRepo,
                tmrwCompletedRepo,
                pendingRepo,
                recurringRepo,
                timeManager
        );
        appResetModel = new MainViewModel(
                todayOngoingRepo,
                todayCompletedRepo,
                tmrwOngoingRepo,
                tmrwCompletedRepo,
                pendingRepo,
                recurringRepo,
                timeManager
        );
    }

    public String randomString(int maxLen) {
        StringBuilder text = new StringBuilder();
        for (int i = (int)(Math.random()*maxLen); i > -1; i--) {
            text.append((char)(Math.random()*26+'a'));
        }
        return text.toString();
    }

    @Test
    public void append() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String text = randomString(100);
            boolean isCompleted = Math.random() > 0.5;
            List<Goal> addTo = isCompleted ? completedList : ongoingList;

            Goal g = new Goal(i, text, addTo.size(), isCompleted);

            addTo.add(g);
            model.todayAppend(g);

            assertEquals(ongoingList, model.getTodayOngoingGoals().getValue());
            assertEquals(completedList, model.getTodayCompletedGoals().getValue());
        }
    }

    @Test
    public void ListMovetest1() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal simple = new Goal(1, "Hello", ongoingList.size(), false);
        completedList.add(simple.withIsCompleted(true));
        model.todayAppend(simple);
        model.todayCompleteGoal(simple);
        assertEquals(ongoingList, model.getTodayOngoingGoals().getValue());
        assertEquals(completedList, model.getTodayCompletedGoals().getValue());

    }

    @Test
    public void ListMovetest2() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal simple = new Goal(1, "Hello", ongoingList.size(), false);
        Goal done = new Goal(2, "Wow", completedList.size(), true);
        completedList.add(simple.withIsCompleted(true));
        completedList.add(done);
        model.todayAppend(done);
        model.todayAppend(simple);
        model.todayCompleteGoal(simple);
        assertEquals(ongoingList, model.getTodayOngoingGoals().getValue());
        assertEquals(completedList, model.getTodayCompletedGoals().getValue());

    }

    @Test
    public void ListMovetest3() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal incomplete1 = new Goal(1, "Hello", ongoingList.size(), false);
        ongoingList.add(incomplete1);
        Goal complete1 = new Goal(2, "Wow", completedList.size(), true);
        completedList.add(complete1);
        Goal incomplete2 = new Goal(3, "My", ongoingList.size(), false);
        completedList.add(0, incomplete2.withIsCompleted(true));
        Goal complete2 = new Goal(4, "Name", completedList.size(), true);
        completedList.add(complete2);
        Goal incomplete3 = new Goal(5, "Is", ongoingList.size(), false);
        ongoingList.add(incomplete3);
        Goal complete3 = new Goal(6, "Not", completedList.size(), true);
        completedList.add(complete3);
        model.todayAppend(incomplete1);
        model.todayAppend(incomplete2);
        model.todayAppend(incomplete3);
        model.todayAppend(complete1);
        model.todayAppend(complete2);
        model.todayAppend(complete3);
        model.todayCompleteGoal(incomplete2);
        assertEquals(ongoingList, model.getTodayOngoingGoals().getValue());
        assertEquals(completedList, model.getTodayCompletedGoals().getValue());

    }

    @Test
    public void uncompleteGoal1() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal simple = new Goal(1, "Hello", ongoingList.size(), true);
        ongoingList.add(simple.withIsCompleted(false));
        model.todayAppend(simple);
        model.todayUncompleteGoal(simple);
        assertEquals(ongoingList, model.getTodayOngoingGoals().getValue());
        assertEquals(completedList, model.getTodayCompletedGoals().getValue());

    }

    @Test
    public void uncompleteGoal2() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal simple = new Goal(1, "Hello", ongoingList.size(), true);
        Goal done = new Goal(2, "Wow", completedList.size(), true);
        ongoingList.add(simple.withIsCompleted(false));
        completedList.add(done);
        model.todayAppend(done);
        model.todayAppend(simple);
        model.todayUncompleteGoal(simple);
        assertEquals(ongoingList, model.getTodayOngoingGoals().getValue());
        assertEquals(completedList, model.getTodayCompletedGoals().getValue());
    }

    @Test
    public void uncompleteGoal3() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal incomplete1 = new Goal(1, "Hello", ongoingList.size(), false);
        ongoingList.add(incomplete1);
        Goal complete1 = new Goal(2, "Wow", completedList.size(), true);
        completedList.add(complete1);
        Goal incomplete2 = new Goal(3, "My", ongoingList.size(), false);
        ongoingList.add(incomplete2);
        Goal complete2 = new Goal(4, "Name", completedList.size(), true);
        ongoingList.add(0, complete2.withIsCompleted(false));
        Goal incomplete3 = new Goal(5, "Is", ongoingList.size(), false);
        ongoingList.add(incomplete3);
        Goal complete3 = new Goal(6, "Not", completedList.size(), true);
        completedList.add(complete3);
        model.todayAppend(incomplete1);
        model.todayAppend(incomplete2);
        model.todayAppend(incomplete3);
        model.todayAppend(complete1);
        model.todayAppend(complete2);
        model.todayAppend(complete3);
        model.todayUncompleteGoal(complete2);
        assertEquals(ongoingList, model.getTodayOngoingGoals().getValue());
        assertEquals(completedList, model.getTodayCompletedGoals().getValue());

    }
  
    @Test
    public void nextDay() {
        LocalDateTime expected = model.getTime().getValue();
        for(int i = 0; i < 100; i++) {

            expected = expected.plusDays(1);
            model.nextDay();

            LocalDateTime actual = model.getTime().getValue();
            assertEquals(expected.getDayOfMonth(), actual.getDayOfMonth());
            assertEquals(expected.getDayOfWeek(), actual.getDayOfWeek());
            assertEquals(expected.getMonth(), actual.getMonth());
        }
    }
    // 0 ongoing, 2 completed, next day -> 0 completed
    @Test
    public void clearList() {
        ArrayList<Goal> listExpected = new ArrayList<>();
        Goal test1 = new Goal(1, "", 1, true);
        Goal test2 = new Goal(2, "", 2, true);
        for(int i = 0; i < 100; i++) {
            model.todayAppend(test1);
            model.todayAppend(test2);

            model.nextDay();

            assertEquals(listExpected, model.getTodayCompletedGoals().getValue());
        }
    }
    // time is 1:59am, 2 completed goals & 1 ongoing -> 2:00am, 0 completed 1 ongoing
    // Note: cannot directly test going from 1:59am to 2:00am since our code
    // only has the feature for advancing 24 hours later
    @Test
    public void beforeAppReset() {
        ArrayList<Goal> ongoingList = new ArrayList<>();
        ArrayList<Goal> completedList = new ArrayList<>();
        Goal test1 = new Goal(1, "", 1, true);
        Goal test2 = new Goal(2, "", 2, true);
        Goal test3 = new Goal(1, "", 1, false);
        ongoingList.add(test3);

        for(int i = 0; i < 100; i++) {
            appResetModel.todayAppend(test1);
            appResetModel.todayAppend(test2);
            appResetModel.todayAppend(test3);

            appResetModel.nextDay();

            assertEquals(ongoingList, appResetModel.getTodayOngoingGoals().getValue());
            assertEquals(completedList, appResetModel.getTodayCompletedGoals().getValue());
        }


    }
}