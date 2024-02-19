package edu.ucsd.cse110.successorator.lib.domain;

//import java.util.LocalDateTime;
import java.time.*;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleTimeManager implements TimeManager {

    private int daysOffset;

    private MutableSubject<LocalDateTime> localDateTime;

    private LocalDateTime lastCleared;

    public SimpleTimeManager() {
        this(LocalDateTime.now());
    }

    public SimpleTimeManager(LocalDateTime localDateTime) {
        daysOffset = 0;

        this.localDateTime = new SimpleSubject<>();
        this.localDateTime.setValue(localDateTime.minusHours(DAY_START_HOUR));
        this.lastCleared = localDateTime.minusHours(DAY_START_HOUR);
    }

    @Override
    public Subject<LocalDateTime> getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return getLocalDateTime(localDateTime);
    }

    @Override
    public Subject<LocalDateTime> getLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime.setValue(
                localDateTime
                        .minusHours(DAY_START_HOUR)
                        .plusDays(daysOffset)
        );
        return this.localDateTime;
    }

    @Override
    public LocalDateTime getLastCleared() {
        return this.lastCleared;
    }

    @Override
    public void updateLastCleared(LocalDateTime time) {
        this.lastCleared = time;
    }

    @Override
    public void nextDay() {
        daysOffset++;
        getLocalDateTime();
    }
}
