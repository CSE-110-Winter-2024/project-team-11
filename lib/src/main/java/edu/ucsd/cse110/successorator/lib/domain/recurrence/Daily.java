package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Daily implements Recurrence {
    LocalDate startDate;

    public Daily(@NonNull LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public RecurrenceFactory.RecurrenceEnum getType() {
        return RecurrenceFactory.RecurrenceEnum.DAILY;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public boolean occursOnDay(LocalDate date) {
        return !date.isBefore(startDate);
    }

    @Override
    public boolean occursDuringInterval(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(this.startDate) || endDate.isBefore(startDate)) {
            return false;
        }

        // Check whether or not any day in the interval matches the recurrence relation
        for (LocalDate date = startDate.plusDays(1); !date.isAfter(endDate); date = date.plusDays(1)) {
            if (occursOnDay(date)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String recurrenceText() {
        return "daily";
    }
}
