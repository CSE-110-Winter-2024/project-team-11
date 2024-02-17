package edu.ucsd.cse110.successorator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class MainViewModelTest {
    MainViewModel model;

    @Before
    public void setUp() throws Exception {
        SimpleGoalRepository ongoingRepo = new SimpleGoalRepository(new InMemoryDataSource());
        SimpleGoalRepository completedRepo = new SimpleGoalRepository(new InMemoryDataSource());
        TimeManager timeManager = new TimeManager(LocalDateTime.now());
        model = new MainViewModel(ongoingRepo, completedRepo, timeManager);
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

}