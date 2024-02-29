package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeManager;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class WeeklyTest {
    @Test
    public void occursOnDay() {
        LocalDateTime now = LocalDateTime.now();

        Weekly Weekly = new Weekly(now);

        // always occurs on start day
        assertTrue(Weekly.occursOnDay(now));

        // occurs every seven days, starting from now
        for (int i = 0; i < 1000; i++) {
            assertEquals(i % 7 == 0, Weekly.occursOnDay(now.plusDays(i)));
        }

        // doesn't occur for previous days
        for (int i = 0; i < 100; i++) {
            assertFalse(Weekly.occursOnDay(now.minusDays(i+1)));
        }
    }

    @Test
    public void occursDuringInterval() {
        LocalDateTime now = LocalDateTime.now();

        Weekly Weekly = new Weekly(now);

        // It shouldn't occur again today (the start of any interval should
        // always be accounted for by a previous occurrence check)
        assertFalse(Weekly.occursDuringInterval(now, now));

        // It shouldn't occur any time in the next 6 days
        for (int i = 1; i < 7; i++) {
            assertFalse(Weekly.occursDuringInterval(now, now.plusDays(i)));
        }

        // It should occur today if it wasn't checked since yesterday
        assertTrue(Weekly.occursDuringInterval(now.minusDays(1), now));

        // It shouldn't occur before it started
        assertFalse(Weekly.occursDuringInterval(now.minusDays(2000), now.minusDays(1)));

        // It should occur after it started
        assertTrue(Weekly.occursDuringInterval(now.plusDays(1), now.plusDays(2000)));
        assertTrue(Weekly.occursDuringInterval(now.plusDays(1), now.plusDays(7)));

        // It should occur after it started even if it starts on today
        assertTrue(Weekly.occursDuringInterval(now.plusDays(0), now.plusDays(7)));
    }

    @Test
    public void recurrenceText() {
        LocalDateTime now = LocalDateTime.now();
        String expected = ", weekly on " + now.getDayOfWeek().name();
        assertEquals(expected, new Weekly(now).recurrenceText());
    }
}