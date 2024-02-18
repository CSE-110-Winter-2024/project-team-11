package edu.ucsd.cse110.successorator.lib.domain;

//import java.util.LocalDateTime;
import java.time.*;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class TimeManager {

    public static final int DAY_START_HOUR = 2;

    private int daysOffset;

    private MutableSubject<LocalDateTime> localDateTime;

    private MutableSubject<LocalDateTime> lastCleared;

    public TimeManager(LocalDateTime localDateTime) {
        daysOffset = 0;

        this.localDateTime = new SimpleSubject<>();
        this.localDateTime.setValue(localDateTime.minusHours(DAY_START_HOUR));
        this.lastCleared = new SimpleSubject<>();
        this.lastCleared.setValue(localDateTime.minusHours(DAY_START_HOUR));
    }

    public Subject<LocalDateTime> getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return getLocalDateTime(localDateTime);
    }

    public Subject<LocalDateTime> getLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime.setValue(
                localDateTime
                        .minusHours(DAY_START_HOUR)
                        .plusDays(daysOffset)
        );
        return this.localDateTime;
    }

    public Subject<LocalDateTime> getLastCleared() {
        return lastCleared;
    }

    public void updateLastCleared(LocalDateTime time) {
        lastCleared.setValue(time);
    }

    public void nextDay() {
        daysOffset++;
        getLocalDateTime();
    }
}