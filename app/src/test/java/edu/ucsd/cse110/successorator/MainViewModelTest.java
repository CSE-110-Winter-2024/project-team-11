package edu.ucsd.cse110.successorator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.GoalInMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.data.RecurringGoalInMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalContext;
import edu.ucsd.cse110.successorator.lib.domain.goal.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeManager;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoal;
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

            Goal g = new Goal(i, text, GoalContext.HOME, addTo.size(), isCompleted);

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
        Goal simple = new Goal(1, "Hello", GoalContext.HOME, ongoingList.size(), false);
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
        Goal simple = new Goal(1, "Hello", GoalContext.HOME, ongoingList.size(), false);
        Goal done = new Goal(2, "Wow", GoalContext.HOME, completedList.size(), true);
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
        Goal incomplete1 = new Goal(1, "Hello", GoalContext.HOME, ongoingList.size(), false);
        ongoingList.add(incomplete1);
        Goal complete1 = new Goal(2, "Wow", GoalContext.HOME, completedList.size(), true);
        completedList.add(complete1);
        Goal incomplete2 = new Goal(3, "My", GoalContext.HOME, ongoingList.size(), false);
        completedList.add(0, incomplete2.withIsCompleted(true));
        Goal complete2 = new Goal(4, "Name", GoalContext.HOME, completedList.size(), true);
        completedList.add(complete2);
        Goal incomplete3 = new Goal(5, "Is", GoalContext.HOME, ongoingList.size(), false);
        ongoingList.add(incomplete3);
        Goal complete3 = new Goal(6, "Not", GoalContext.HOME, completedList.size(), true);
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
        Goal simple = new Goal(1, "Hello", GoalContext.HOME, ongoingList.size(), true);
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
        Goal simple = new Goal(1, "Hello", GoalContext.HOME, ongoingList.size(), true);
        Goal done = new Goal(2, "Wow", GoalContext.HOME, completedList.size(), true);
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
        Goal incomplete1 = new Goal(1, "Hello", GoalContext.HOME, ongoingList.size(), false);
        ongoingList.add(incomplete1);
        Goal complete1 = new Goal(2, "Wow", GoalContext.HOME, completedList.size(), true);
        completedList.add(complete1);
        Goal incomplete2 = new Goal(3, "My", GoalContext.HOME, ongoingList.size(), false);
        ongoingList.add(incomplete2);
        Goal complete2 = new Goal(4, "Name", GoalContext.HOME, completedList.size(), true);
        ongoingList.add(0, complete2.withIsCompleted(false));
        Goal incomplete3 = new Goal(5, "Is", GoalContext.HOME, ongoingList.size(), false);
        ongoingList.add(incomplete3);
        Goal complete3 = new Goal(6, "Not", GoalContext.HOME, completedList.size(), true);
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
        LocalDate expected = model.getDate().getValue();
        for(int i = 0; i < 100; i++) {

            expected = expected.plusDays(1);
            model.nextDay();

            LocalDate actual = model.getDate().getValue();
            assertEquals(expected.getDayOfMonth(), actual.getDayOfMonth());
            assertEquals(expected.getDayOfWeek(), actual.getDayOfWeek());
            assertEquals(expected.getMonth(), actual.getMonth());
        }
    }
    // 0 ongoing, 2 completed, next day -> 0 completed
    @Test
    public void clearList() {
        ArrayList<Goal> listExpected = new ArrayList<>();
        Goal test1 = new Goal(1, "", GoalContext.HOME, 1, true);
        Goal test2 = new Goal(2, "", GoalContext.HOME, 2, true);
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
        Goal test1 = new Goal(1, "", GoalContext.HOME, 1, true);
        Goal test2 = new Goal(2, "", GoalContext.HOME, 2, true);
        Goal test3 = new Goal(1, "", GoalContext.HOME, 1, false);
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

    // Test that goals for today, tmrw, pending, and recurring are separated
    @Test
    public void goalSeparation() {
        List<Goal> today = List.of(
                new Goal(0, "1", GoalContext.HOME, 0, false),
                new Goal(1, "2", GoalContext.HOME, 1, false)
        );
        List<Goal> todayc = List.of(
                new Goal(0, "1c", GoalContext.HOME, 0, true),
                new Goal(1, "2c", GoalContext.HOME, 1, true)
        );
        List<Goal> tmrw = List.of(
                new Goal(0, "3", GoalContext.HOME, 0, false),
                new Goal(1, "4", GoalContext.HOME, 1, false)
        );
        List<Goal> tmrwc = List.of(
                new Goal(0, "3c", GoalContext.HOME, 0, true),
                new Goal(1, "4c", GoalContext.HOME, 1, true)
        );
        List<Goal> pending = List.of(
                new Goal(0, "5", GoalContext.HOME, 0, false),
                new Goal(1, "6", GoalContext.HOME, 1, false)
        );
        RecurrenceFactory factory = new RecurrenceFactory();
        Recurrence w = factory.createRecurrence(LocalDate.now(), RecurrenceFactory.RecurrenceEnum.WEEKLY);
        Recurrence m = factory.createRecurrence(LocalDate.now(), RecurrenceFactory.RecurrenceEnum.MONTHLY);
        List<RecurringGoal> recurring = List.of(
                new RecurringGoal(0, new Goal(null, "5", GoalContext.HOME, 0, false), w),
                new RecurringGoal(1, new Goal(null, "6", GoalContext.HOME, 1, false), m)
        );

        for (Goal goal : today) model.todayAppend(goal);
        for (Goal goal : todayc) model.todayAppend(goal);
        for (Goal goal : tmrw) model.tmrwAppend(goal);
        for (Goal goal : tmrwc) model.tmrwAppend(goal);
        for (Goal goal : pending) model.pendingAppend(goal);
        for (RecurringGoal goal : recurring) model.recurringAppend(goal);

        today = List.of(
                new Goal(0, "1", GoalContext.HOME, 0, false),
                new Goal(1, "2", GoalContext.HOME, 1, false),
                new Goal(2, "5", GoalContext.HOME, 2, false),
                new Goal(3, "6", GoalContext.HOME, 3, false)
        );

        assertEquals(today, model.getTodayOngoingGoals().getValue());
        assertEquals(todayc, model.getTodayCompletedGoals().getValue());
        assertEquals(tmrw, model.getTmrwOngoingGoals().getValue());
        assertEquals(tmrwc, model.getTmrwCompletedGoals().getValue());
        assertEquals(pending, model.getPendingGoals().getValue());
        assertEquals(recurring, model.getRecurringGoals().getValue());
    }

    @Test
    public void oneTime() {
        Goal g = new Goal(0, "1", GoalContext.HOME, 0, false);
        model.todayAppend(g);
        model.todayCompleteGoal(g);
        model.nextDay();

        assertEquals(List.of(), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
    }

    @Test
    public void daily() {
        RecurrenceFactory factory = new RecurrenceFactory();
        Recurrence daily = factory.createRecurrence(model.getDate().getValue(), RecurrenceFactory.RecurrenceEnum.DAILY);
        Goal g = new Goal(0, "1", GoalContext.HOME, 0, false);
        RecurringGoal rg = new RecurringGoal(null, g, daily);
        model.recurringAppend(rg);
        model.todayCompleteGoal(g);
        model.nextDay();

        assertEquals(List.of(g), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(g.withId(1)), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());

        model.todayCompleteGoal(g);
        model.nextDay();

        assertEquals(List.of(g.withId(1)), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(g.withId(2)), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());

        model.todayCompleteGoal(g.withId(1));
        model.tmrwCompleteGoal(g.withId(2));
        model.nextDay();

        assertEquals(List.of(), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(g.withId(3)), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());

        model.nextDay();

        assertEquals(List.of(g.withId(3)), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(g.withId(4)), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
    }

    @Test
    public void infrequently() {
        RecurrenceFactory factory = new RecurrenceFactory();
        Recurrence weekly = factory.createRecurrence(model.getDate().getValue(), RecurrenceFactory.RecurrenceEnum.WEEKLY);
        Goal g = new Goal(0, "1", GoalContext.HOME, 0, false);
        RecurringGoal rg = new RecurringGoal(null, g, weekly);
        model.recurringAppend(rg);
        assertEquals(List.of(g), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());

        model.todayCompleteGoal(g);
        for (int i = 0; i < 5; i++) {
            model.nextDay();

            assertEquals(List.of(), model.getTodayOngoingGoals().getValue());
            assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
            assertEquals(List.of(), model.getTmrwOngoingGoals().getValue());
            assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        }
        model.nextDay();

        assertEquals(List.of(), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(g), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());

        model.nextDay();

        assertEquals(List.of(g), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
    }

    @Test
    public void collision() {
        RecurrenceFactory factory = new RecurrenceFactory();
        Recurrence daily = factory.createRecurrence(model.getDate().getValue(), RecurrenceFactory.RecurrenceEnum.DAILY);
        Goal g = new Goal(0, "1", GoalContext.HOME, 0, false);
        RecurringGoal rg = new RecurringGoal(null, g, daily);
        model.recurringAppend(rg);
        model.todayCompleteGoal(g);
        model.nextDay();

        assertEquals(List.of(g), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(g.withId(1)), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());

        model.nextDay();

        assertEquals(List.of(g), model.getTodayOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
        assertEquals(List.of(g.withId(2)), model.getTmrwOngoingGoals().getValue());
        assertEquals(List.of(), model.getTodayCompletedGoals().getValue());
    }

    @Test
    public void pendingTest1() {
        Goal p = new Goal(2, "5", GoalContext.HOME, 0, false);
        ArrayList<Goal> today = new ArrayList<>(Arrays.asList(
                new Goal(0, "1", GoalContext.HOME, 0, false),
                new Goal(1, "2", GoalContext.HOME, 1, false)
        ));
        List<Goal> todayc = List.of(
                new Goal(0, "1c", GoalContext.HOME, 0, true),
                new Goal(1, "2c", GoalContext.HOME, 1, true)
        );
        List<Goal> tmrw = List.of(
                new Goal(0, "3", GoalContext.HOME, 0, false),
                new Goal(1, "4", GoalContext.HOME, 1, false)
        );
        List<Goal> tmrwc = List.of(
                new Goal(0, "3c", GoalContext.HOME, 0, true),
                new Goal(1, "4c", GoalContext.HOME, 1, true)
        );
        List<Goal> pending = List.of(
                new Goal(1, "6", GoalContext.HOME, 1, false)
        );

        for (Goal goal : today) model.todayAppend(goal);
        for (Goal goal : todayc) model.todayAppend(goal);
        for (Goal goal : tmrw) model.tmrwAppend(goal);
        for (Goal goal : tmrwc) model.tmrwAppend(goal);

        model.pendingAppend(p);
        model.pendingAppend(pending.get(0));

        model.pendingToToday(p);
        today.add(p.withSortOrder(2));

        assertEquals(today, model.getTodayOngoingGoals().getValue());
        assertEquals(todayc, model.getTodayCompletedGoals().getValue());
        assertEquals(tmrw, model.getTmrwOngoingGoals().getValue());
        assertEquals(tmrwc, model.getTmrwCompletedGoals().getValue());
        assertEquals(pending, model.getPendingGoals().getValue());
    }

    @Test
    public void pendingTest2() {
        Goal p = new Goal(2, "5", GoalContext.HOME, 0, false);
        ArrayList<Goal> today = new ArrayList<>(Arrays.asList(
                new Goal(0, "1", GoalContext.HOME, 0, false),
                new Goal(1, "2", GoalContext.HOME, 1, false)
        ));
        List<Goal> todayc = List.of(
                new Goal(0, "1c", GoalContext.HOME, 0, true),
                new Goal(1, "2c", GoalContext.HOME, 1, true)
        );
        ArrayList<Goal> tmrw = new ArrayList<>(List.of(
                new Goal(0, "3", GoalContext.HOME, 0, false),
                new Goal(1, "4", GoalContext.HOME, 1, false)
        ));
        List<Goal> tmrwc = List.of(
                new Goal(0, "3c", GoalContext.HOME, 0, true),
                new Goal(1, "4c", GoalContext.HOME, 1, true)
        );
        List<Goal> pending = List.of(
                new Goal(1, "6", GoalContext.HOME, 1, false)
        );

        for (Goal goal : today) model.todayAppend(goal);
        for (Goal goal : todayc) model.todayAppend(goal);
        for (Goal goal : tmrw) model.tmrwAppend(goal);
        for (Goal goal : tmrwc) model.tmrwAppend(goal);

        model.pendingAppend(p);
        model.pendingAppend(pending.get(0));

        model.pendingToTmrw(p);
        tmrw.add(p.withSortOrder(2));

        assertEquals(today, model.getTodayOngoingGoals().getValue());
        assertEquals(todayc, model.getTodayCompletedGoals().getValue());
        assertEquals(tmrw, model.getTmrwOngoingGoals().getValue());
        assertEquals(tmrwc, model.getTmrwCompletedGoals().getValue());
        assertEquals(pending, model.getPendingGoals().getValue());
    }

    @Test
    public void pendingTest3() {
        Goal p = new Goal(2, "5", GoalContext.HOME, 0, false);
        ArrayList<Goal> today = new ArrayList<>(Arrays.asList(
                new Goal(0, "1", GoalContext.HOME, 0, false),
                new Goal(1, "2", GoalContext.HOME, 1, false)
        ));
        ArrayList<Goal> todayc = new ArrayList<>(List.of(
                new Goal(0, "1c", GoalContext.HOME, 0, true),
                new Goal(1, "2c", GoalContext.HOME, 1, true)
        ));
        List<Goal> tmrw = List.of(
                new Goal(0, "3", GoalContext.HOME, 0, false),
                new Goal(1, "4", GoalContext.HOME, 1, false)
        );
        List<Goal> tmrwc = List.of(
                new Goal(0, "3c", GoalContext.HOME, 0, true),
                new Goal(1, "4c", GoalContext.HOME, 1, true)
        );
        List<Goal> pending = List.of(
                new Goal(1, "6", GoalContext.HOME, 1, false)
        );

        for (Goal goal : today) model.todayAppend(goal);
        for (Goal goal : todayc) model.todayAppend(goal);
        for (Goal goal : tmrw) model.tmrwAppend(goal);
        for (Goal goal : tmrwc) model.tmrwAppend(goal);

        model.pendingAppend(p);
        model.pendingAppend(pending.get(0));

        model.pendingCompleteGoal(p);
        todayc.add(0, p.withSortOrder(2).withIsCompleted(true));

        assertEquals(today, model.getTodayOngoingGoals().getValue());
        assertEquals(todayc, model.getTodayCompletedGoals().getValue());
        assertEquals(tmrw, model.getTmrwOngoingGoals().getValue());
        assertEquals(tmrwc, model.getTmrwCompletedGoals().getValue());
        assertEquals(pending, model.getPendingGoals().getValue());
    }

    @Test
    public void pendingTest4() {
        Goal p = new Goal(2, "5", GoalContext.HOME, 0, false);
        List<Goal> pending = List.of(
                new Goal(1, "6", GoalContext.HOME, 1, false)
        );

        model.pendingAppend(p);
        model.pendingAppend(pending.get(0));

        model.pendingDeleteGoal(p);

        assertEquals(pending, model.getPendingGoals().getValue());
    }
  
    // test for adding, completing, & uncompleting tomorrow goals
    @Test
    public void tomorrowGoalsTest() {
        List<Goal> expectedTomorrow = new ArrayList<>();
        expectedTomorrow.add(new Goal(0, "testing", GoalContext.HOME, 0, false));
        model.tmrwAppend(expectedTomorrow.get(0));

        assertEquals(expectedTomorrow, model.getTmrwOngoingGoals().getValue());
        assertEquals(new ArrayList<>(), model.getTmrwCompletedGoals().getValue());

        model.tmrwCompleteGoal(expectedTomorrow.get(0));
        Goal updatedGoal = expectedTomorrow.get(0).withIsCompleted(true);
        expectedTomorrow.set(0, updatedGoal);

        assertEquals(new ArrayList<>(), model.getTmrwOngoingGoals().getValue());
        assertEquals(expectedTomorrow, model.getTmrwCompletedGoals().getValue());

        model.tmrwUncompleteGoal(expectedTomorrow.get(0));
        updatedGoal = expectedTomorrow.get(0).withIsCompleted(false);
        expectedTomorrow.set(0, updatedGoal);

        assertEquals(expectedTomorrow, model.getTmrwOngoingGoals().getValue());
        assertEquals(new ArrayList<>(), model.getTmrwCompletedGoals().getValue());
    }
}