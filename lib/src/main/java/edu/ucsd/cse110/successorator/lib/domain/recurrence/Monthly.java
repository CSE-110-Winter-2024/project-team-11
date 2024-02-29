package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class Monthly implements Recurrence {
    LocalDateTime startDate;

    public Monthly(@NonNull LocalDateTime startDate) {
        this.startDate = startDate.toLocalDate().atStartOfDay();
    }

    @Override
    public RecurrenceFactory.RecurrenceEnum getType() {
        return RecurrenceFactory.RecurrenceEnum.MONTHLY;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Counts the number of days in the interval [start of sinceMonth,  date] that are the same day
     * of week as date.
     *
     * @param date The date whose day of week to count
     * @param sinceMonth The month whose start is the start of the counting interval
     * @return The number of days that are the same day of week as date in the given interval
     */
    public int countSameDayOfWeek(LocalDateTime date, int sinceMonth) {
        int count = 0;
        for (LocalDateTime day = date.withMonth(sinceMonth).withDayOfMonth(1);
             !day.isAfter(date);
             day = day.plusDays(1)) {
            if (day.getDayOfWeek() == date.getDayOfWeek()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean occursOnDay(LocalDateTime date) {
        if (date.isBefore(startDate) || date.getDayOfWeek() != startDate.getDayOfWeek()) {
            return false;
        }

        boolean normalMatch = countSameDayOfWeek(startDate, startDate.getMonthValue())
                == countSameDayOfWeek(date, date.getMonthValue());

        boolean overflowMatch = countSameDayOfWeek(startDate, startDate.getMonthValue())
                == countSameDayOfWeek(date, date.getMonthValue() - 1);

        return normalMatch || overflowMatch;
    }

    @Override
    public boolean occursDuringInterval(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(this.startDate)
                || (startDate.getDayOfYear() == endDate.getDayOfYear()
                && startDate.getYear() == endDate.getYear())) {
            return false;
        }

        // Check whether or not any day in the interval matches the recurrence relation
        for (LocalDateTime date = startDate.plusDays(1); date.isBefore(endDate); date = date.plusDays(1)) {
            if (occursOnDay(date)) {
                return true;
            }
        }
        return occursOnDay(endDate);
    }

    public static final Map<Integer, String> ORDINAL_SUFFIX = new HashMap<>() {{
        put(1, "st");
        put(2, "nd");
        put(3, "rd");
        put(4, "th");
        put(5, "th");
    }};

    @Override
    public String recurrenceText() {
        int nthDayOfWeek = countSameDayOfWeek(startDate, startDate.getMonthValue());
        return "monthly " + nthDayOfWeek + ORDINAL_SUFFIX.get(nthDayOfWeek)
                + " " + startDate.getDayOfWeek();
    }
}
