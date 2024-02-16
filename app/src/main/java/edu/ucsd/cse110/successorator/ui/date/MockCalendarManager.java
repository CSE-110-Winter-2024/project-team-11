package edu.ucsd.cse110.successorator.ui.date;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class MockCalendarManager extends CalendarManager {

    public static MockCalendarManager newInstance(Calendar calendar) {
        MockCalendarManager calendarManager = new MockCalendarManager();
        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
        calendarManager.calendar = new SimpleSubject<>();
        calendarManager.calendar.setValue(calendar);
        return calendarManager;
    }
}
