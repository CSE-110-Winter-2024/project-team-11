package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class Daily implements Recurrence {
    LocalDateTime startDate;

    public Daily(@NonNull LocalDateTime startDate) {
        this.startDate = startDate.toLocalDate().atStartOfDay();
    }

    @Override
    public RecurrenceFactory.RecurrenceEnum getType() {
        return RecurrenceFactory.RecurrenceEnum.DAILY;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public boolean occursOnDay(LocalDateTime date) {
        return !date.isBefore(startDate);
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

    @Override
    public String recurrenceText() {
        return "daily";
    }
}
