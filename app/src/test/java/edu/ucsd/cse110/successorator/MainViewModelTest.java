package edu.ucsd.cse110.successorator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.ui.date.CalendarManager;

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
    public void nextDay() {
        Calendar expected = model.getCalendar().getValue();
        for(int i = 0; i < 100; i++) {

            expected.add(Calendar.DATE, 1);
            model.nextDay();

            assertEquals(expected, model.getCalendar().getValue());
        }
    }

}