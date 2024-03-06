package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Weekly implements Recurrence {
    LocalDateTime startDate;

    public Weekly(@NonNull LocalDateTime startDate) {
        this.startDate = startDate.toLocalDate().atStartOfDay();
    }

    @Override
    public RecurrenceFactory.RecurrenceEnum getType() {
        return RecurrenceFactory.RecurrenceEnum.WEEKLY;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public boolean occursOnDay(LocalDateTime date) {
        return !date.isBefore(startDate)
                && date.getDayOfWeek() == startDate.getDayOfWeek();
    }

    @Override
    public boolean occursDuringInterval(LocalDateTime startDate, LocalDateTime endDate) {
        startDate = startDate.toLocalDate().atStartOfDay();
        endDate = endDate.toLocalDate().atStartOfDay();
        if (endDate.isBefore(this.startDate) || endDate.isBefore(startDate)) {
            return false;
        }

        // Check whether or not any day in the interval matches the recurrence relation
        for (LocalDateTime date = startDate.plusDays(1); !date.isAfter(endDate); date = date.plusDays(1)) {
            if (occursOnDay(date)) {
                return true;
            }
        }
        return false;
    }

    public static String dayOfWeekAbbreviation(DayOfWeek dayOfWeek) {
        return dayOfWeek.name().charAt(0) + ""
                + dayOfWeek.name().toLowerCase().charAt(1);
    }

    @Override
    public String recurrenceText() {
        return "weekly on " + dayOfWeekAbbreviation(startDate.getDayOfWeek());
    }
}