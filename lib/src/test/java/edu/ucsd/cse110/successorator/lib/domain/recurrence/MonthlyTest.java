package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeManager;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class MonthlyTest {
    @Test
    public void occursToday() {
        LocalDateTime now = LocalDateTime.now();

        Monthly Monthly = new Monthly(now);

        // always occurs on start day
        assertTrue(Monthly.occursOnDay(now));
    }

    @Test
    public void occursOnDay() {
        LocalDateTime Thursday5thFeb = LocalDate.of(2024, 2, 29).atStartOfDay();
        LocalDateTime Thursday5thMarOver = LocalDate.of(2024, 4, 4).atStartOfDay();
        LocalDateTime Thursday5thAprOver = LocalDate.of(2024, 5, 2).atStartOfDay();
        LocalDateTime Thursday5thMay = LocalDate.of(2024, 5, 30).atStartOfDay();

        HashSet<LocalDateTime> occurDates = new HashSet<>(List.of(
            Thursday5thFeb,
            Thursday5thMarOver,
            Thursday5thAprOver,
            Thursday5thMay
        ));

        LocalDateTime startDate = Thursday5thFeb;
        Monthly monthly = new Monthly(startDate);

        // occurs every 5th thursday days, starting from now
        for (int i = 0; i < 100; i++) {
            LocalDateTime day = startDate.plusDays(i);
            assertEquals(occurDates.contains(day), monthly.occursOnDay(day));
        }

        // doesn't occur for previous days
        for (int i = 0; i < 100; i++) {
            assertFalse(monthly.occursOnDay(startDate.minusDays(i+1)));
        }
    }

    @Test
    public void occursDuringInterval() {
        LocalDateTime now = LocalDateTime.now();

        Monthly monthly = new Monthly(now);

        // It shouldn't occur again today (the start of any interval should
        // always be accounted for by a previous occurrence check)
        assertFalse(monthly.occursDuringInterval(now, now));

        // It shouldn't occur any time in the next 27 days at least
        for (int i = 1; i < 27; i++) {
            assertFalse(monthly.occursDuringInterval(now, now.plusDays(i)));
        }

        // It should occur today if it wasn't checked since yesterday
        assertTrue(monthly.occursDuringInterval(now.minusDays(1), now));

        // It shouldn't occur before it started
        assertFalse(monthly.occursDuringInterval(now.minusDays(2000), now.minusDays(1)));

        // It should occur after it started
        assertTrue(monthly.occursDuringInterval(now.plusDays(1), now.plusDays(2000)));
        assertTrue(monthly.occursDuringInterval(now.plusDays(1), now.plusDays(35)));

        // It should occur after it started even if it starts on today
        assertTrue(monthly.occursDuringInterval(now.plusDays(0), now.plusDays(35)));
    }

    @Test
    public void recurrenceText() {
        LocalDateTime now = LocalDate.of(2024, 2, 29).atStartOfDay();
        String expected = "monthly 5th Th";
        assertEquals(expected, new Monthly(now).recurrenceText());

        now = LocalDate.of(2024, 2, 6).atStartOfDay();
        expected = "monthly 1st Tu";
        assertEquals(expected, new Monthly(now).recurrenceText());

        now = LocalDate.of(2024, 2, 14).atStartOfDay();
        expected = "monthly 2nd We";
        assertEquals(expected, new Monthly(now).recurrenceText());

        now = LocalDate.of(2024, 2, 16).atStartOfDay();
        expected = "monthly 3rd Fr";
        assertEquals(expected, new Monthly(now).recurrenceText());

        now = LocalDate.of(2024, 2, 24).atStartOfDay();
        expected = "monthly 4th Sa";
        assertEquals(expected, new Monthly(now).recurrenceText());
    }
}