package edu.ucsd.cse110.successorator.lib.domain.recurrence;

import java.time.LocalDateTime;

public class RecurrenceFactory {
    public enum RecurrenceEnum { DAILY, WEEKLY }

    public Recurrence createRecurrence(LocalDateTime startDate, RecurrenceEnum recurrenceEnum) {
        switch(recurrenceEnum) {
            case DAILY:
                return new Daily(startDate);
            case WEEKLY:
                return new Weekly(startDate);
        }
        return null;
    }
}
