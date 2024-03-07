package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import java.time.LocalDate;

public interface Recurrence {
    public RecurrenceFactory.RecurrenceEnum getType();

    public LocalDate getStartDate();

    public boolean occursOnDay(LocalDate date);

    /**
     * Whether or not this should occur between startDate and endDate,
     * assuming it already occurred on startDate if applicable
     *
     * @param startDate The start of the interval (should be previous logged time from TimeManager)
     * @param endDate The end of the interval (should always be now according to TimeManager)
     * @return Whether it should occur in this interval
     */
    public boolean occursDuringInterval(LocalDate startDate, LocalDate endDate);

    public String recurrenceText();
}
