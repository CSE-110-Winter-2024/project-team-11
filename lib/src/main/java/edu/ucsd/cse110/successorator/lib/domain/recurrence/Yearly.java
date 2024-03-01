package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class Yearly implements Recurrence {
    LocalDateTime startDate;

    public Yearly(@NonNull LocalDateTime startDate) {
        this.startDate = startDate.toLocalDate().atStartOfDay();
    }

    @Override
    public RecurrenceFactory.RecurrenceEnum getType() {
        return RecurrenceFactory.RecurrenceEnum.YEARLY;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public boolean occursOnDay(LocalDateTime date) {
        if (date.isBefore(startDate)) {
            return false;
        }

        boolean normalMatch = startDate.getMonth() == date.getMonth()
                && startDate.getDayOfMonth() == date.getDayOfMonth();

        // leap year
        boolean overflowMatch =
                startDate.getMonth() == Month.FEBRUARY && startDate.getDayOfMonth() == 29
                && date.getMonth() == Month.MARCH && date.getDayOfMonth() == 1
                && date.minusDays(1).getDayOfMonth() == 28;

        return normalMatch || overflowMatch;
    }

    @Override
    public boolean occursDuringInterval(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(this.startDate) || endDate.isBefore(startDate)
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

    @Override
    public String recurrenceText() {
        return "yearly on " + startDate.getMonthValue() + "/" + startDate.getDayOfMonth();
    }
}
