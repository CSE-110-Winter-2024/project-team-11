package edu.ucsd.cse110.successorator.data.db.time;

//import java.util.LocalDateTime;
import java.time.*;

import edu.ucsd.cse110.successorator.lib.domain.SimpleTimeManager;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class RoomTimeManager implements TimeManager {

    TimeDao timeDao;

    SimpleTimeManager timeManager;

    public RoomTimeManager(TimeDao timeDao) {
        this(timeDao, LocalDateTime.now());
    }

    public RoomTimeManager(TimeDao timeDao, LocalDateTime localDateTime) {
        this.timeDao = timeDao;

        this.timeManager = new SimpleTimeManager(localDateTime);
    }

    @Override
    public Subject<LocalDateTime> getLocalDateTime() {
        return this.timeManager.getLocalDateTime();
    }

    @Override
    public Subject<LocalDateTime> getLocalDateTime(LocalDateTime localDateTime) {
        return this.timeManager.getLocalDateTime(localDateTime);
    }

    @Override
    public LocalDateTime getLastCleared() {
        if (timeDao.get() == null) updateLastCleared(getLocalDateTime().getValue());
        return timeDao.get().toTime();
    }

    @Override
    public void updateLastCleared(LocalDateTime time) {
        this.timeDao.update(TimeEntity.fromTime(time));
    }

    @Override
    public void nextDay() {
        this.timeManager.nextDay();
    }
}
