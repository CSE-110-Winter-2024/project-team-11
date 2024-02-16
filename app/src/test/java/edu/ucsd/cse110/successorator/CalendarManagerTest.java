package edu.ucsd.cse110.successorator;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.ui.date.CalendarManager;
import edu.ucsd.cse110.successorator.ui.date.MockCalendarManager;

public class CalendarManagerTest {
    CalendarManager calendarManager;


    @Test
    public void nextDay() {
        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.HOUR_OF_DAY, 6);

        Calendar actual = Calendar.getInstance();
        actual.set(Calendar.HOUR_OF_DAY, 6);

        calendarManager = MockCalendarManager.newInstance(actual);
        for(int i = 0; i < 100; i++) {
            expected.add(Calendar.DATE, 1);
            calendarManager.nextDay();

            actual = calendarManager.getCalendar().getValue();
            assertEquals(expected.get(Calendar.DATE), actual.get(Calendar.DATE));
            assertEquals(expected.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
            assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        }
    }

    @Test
    public void dateChange() throws InterruptedException {
        // We expect the date to still be from yesterday right before 2:00AM
        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.HOUR_OF_DAY, 1);
        expected.set(Calendar.MINUTE, 59);
        expected.set(Calendar.SECOND, 57);
        expected.add(Calendar.DATE, -1);

        // The current date if it is right before 2:00AM
        Calendar input = Calendar.getInstance();
        input.set(Calendar.HOUR_OF_DAY, 1);
        input.set(Calendar.MINUTE, 59);
        input.set(Calendar.SECOND, 57);

        calendarManager = MockCalendarManager.newInstance((Calendar)input.clone());
        Calendar actual = Calendar.getInstance();

        // Check that the date is still from yesterday before 2:00AM
        actual.setTime(calendarManager.getCalendar((Calendar)input.clone()).getValue().getTime());
        assertEquals(expected.get(Calendar.DATE), actual.get(Calendar.DATE));
        assertEquals(expected.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));

        // Go past 2:00AM
        input.add(Calendar.SECOND, 5);

        // Check that the date is now from today since it is after 2:00AM
        expected.add(Calendar.DATE, 1);
        actual.setTime(calendarManager.getCalendar((Calendar)input.clone()).getValue().getTime());
        assertEquals(expected.get(Calendar.DATE), actual.get(Calendar.DATE));
        assertEquals(expected.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
    }
}
