package edu.ucsd.cse110.successorator;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.ui.date.CalendarManager;

public class CalendarManagerTest {
    CalendarManager calendarManager;


    @Test
    public void afterReset() {
        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.HOUR_OF_DAY, 6);
        calendarManager = new CalendarManager(expected);
        for(int i = 0; i < 100; i++) {
            expected.add(Calendar.DATE, 1);
            calendarManager.nextDay();

            assertEquals(expected, calendarManager.getCalendar().getValue());
        }
    }

    @Test
    public void beforeReset() {
        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.HOUR_OF_DAY, 1);
        calendarManager = new CalendarManager(expected);
        for(int i = 0; i < 100; i++) {
            expected.add(Calendar.DATE, 1);
            calendarManager.nextDay();
            assertNotEquals(expected, calendarManager.getCalendar().getValue());
        }
    }
}
