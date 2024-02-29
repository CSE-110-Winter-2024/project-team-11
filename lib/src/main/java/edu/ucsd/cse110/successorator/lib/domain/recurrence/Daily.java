package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import java.time.LocalDateTime;

public class Daily implements Recurrence {
    LocalDateTime startDate;

    public Daily(LocalDateTime startDate) {
        this.startDate = startDate;
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

    @Override
    public String recurrenceText() {
        return ", daily";
    }
}
