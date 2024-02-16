package edu.ucsd.cse110.successorator;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.ui.date.CalendarManager;

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
        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.HOUR_OF_DAY, 1);
        expected.set(Calendar.MINUTE, 59);
        expected.set(Calendar.SECOND, 57);
        expected.add(Calendar.DATE, -1);

        Calendar actual = Calendar.getInstance();
        actual.set(Calendar.HOUR_OF_DAY, 1);
        actual.set(Calendar.MINUTE, 59);
        actual.set(Calendar.SECOND, 57);

        calendarManager = MockCalendarManager.newInstance(actual);
        actual = calendarManager.getCalendar().getValue();
        assertEquals(expected.get(Calendar.DATE), actual.get(Calendar.DATE));
        assertEquals(expected.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));

        System.out.println(expected.getTimeInMillis());
        Thread.sleep(5000);
        System.out.println(expected.getTimeInMillis());

        expected.add(Calendar.DATE, 1);
        actual = calendarManager.getCalendar().getValue();
        assertEquals(expected.get(Calendar.DATE), actual.get(Calendar.DATE));
        assertEquals(expected.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
    }
}
