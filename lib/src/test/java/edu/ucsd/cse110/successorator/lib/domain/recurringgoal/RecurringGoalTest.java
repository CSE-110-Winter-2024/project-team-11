package edu.ucsd.cse110.successorator.lib.domain.recurringgoal;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory;

public class RecurringGoalTest {

    @Test
    public void creation() {
        Goal goal = new Goal(3, "Clean garbage", 0, true);
        Recurrence recur = new RecurrenceFactory().createRecurrence(LocalDateTime.now(), RecurrenceFactory.RecurrenceEnum.DAILY);
        RecurringGoal recurringGoal = new RecurringGoal(2, goal, recur);

        // We want the id to be null so a new one will be assigned whenever the goal occurs
        assertNull(recurringGoal.getGoal().id());

        // We want completed to be false whenever the goal occurs
        assertFalse(recurringGoal.getGoal().isCompleted());
    }

    @Test
    public void text() {
        Goal goal = new Goal(3, "Clean garbage", 0, true);
        Recurrence recur = new RecurrenceFactory().createRecurrence(LocalDateTime.now(),
                RecurrenceFactory.RecurrenceEnum.DAILY);
        RecurringGoal recurringGoal = new RecurringGoal(2, goal, recur);

        assertEquals("Clean garbage, daily", recurringGoal.text());

        recur = new RecurrenceFactory().createRecurrence(LocalDateTime.now(),
                RecurrenceFactory.RecurrenceEnum.WEEKLY);
        recurringGoal = new RecurringGoal(2, goal, recur);

        assertEquals("Clean garbage, weekly on " + LocalDateTime.now().getDayOfWeek().name().charAt(0) + ""
                        + LocalDateTime.now().getDayOfWeek().name().toLowerCase().charAt(1)
                , recurringGoal.text());
    }
}