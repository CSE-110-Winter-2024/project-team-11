package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface TimeManager {
    int DAY_START_HOUR = 2;

    Subject<LocalDateTime> getLocalDateTime();

    Subject<LocalDateTime> getLocalDateTime(LocalDateTime localDateTime);

    LocalDateTime getLastCleared();

    void updateLastCleared(LocalDateTime time);

    void nextDay();
}
