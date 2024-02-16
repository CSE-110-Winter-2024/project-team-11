package edu.ucsd.cse110.successorator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;

public class MainViewModelTest {
    MainViewModel model;

    @Before
    public void setUp() throws Exception {
        SimpleGoalRepository ongoingRepo = new SimpleGoalRepository(new InMemoryDataSource());
        SimpleGoalRepository completedRepo = new SimpleGoalRepository(new InMemoryDataSource());
        model = new MainViewModel(ongoingRepo, completedRepo);
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
            model.append(g);

            assertEquals(ongoingList, model.getOngoingGoals().getValue());
            assertEquals(completedList, model.getCompletedGoals().getValue());
        }
    }

    @Test
    public void ListMovetest1() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal simple = new Goal(1, "Hello", ongoingList.size(), false);
        completedList.add(simple.withIsCompleted(true));
        model.append(simple);
        model.completeGoal(simple);
        assertEquals(ongoingList, model.getOngoingGoals().getValue());
        assertEquals(completedList, model.getCompletedGoals().getValue());

    }

    @Test
    public void ListMovetest2() {
        List<Goal> ongoingList = new ArrayList<>();
        List<Goal> completedList = new ArrayList<>();
        Goal simple = new Goal(1, "Hello", ongoingList.size(), false);
        Goal done = new Goal(2, "Wow", completedList.size(), true);
        completedList.add(simple.withIsCompleted(true));
        completedList.add(done);
        model.append(done);
        model.append(simple);
        model.completeGoal(simple);
        assertEquals(ongoingList, model.getOngoingGoals().getValue());
        assertEquals(completedList, model.getCompletedGoals().getValue());

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
        completedList.add(incomplete2.withIsCompleted(true));
        Goal complete2 = new Goal(4, "Name", completedList.size(), true);
        completedList.add(complete2);
        Goal incomplete3 = new Goal(5, "Is", ongoingList.size(), false);
        ongoingList.add(incomplete3);
        Goal complete3 = new Goal(6, "Not", completedList.size(), true);
        completedList.add(complete3);
        model.append(incomplete1);
        model.append(incomplete2);
        model.append(incomplete3);
        model.append(complete1);
        model.append(complete2);
        model.append(complete3);
        model.completeGoal(incomplete2);
        assertEquals(ongoingList, model.getOngoingGoals().getValue());
        assertEquals(completedList, model.getCompletedGoals().getValue());

    }
}