package edu.ucsd.cse110.successorator.ui.date;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class CalendarManager {

    public static final int DAY_START_HOUR = 2;

    private int daysOffset;

    private MutableSubject<Calendar> calendar;

    private CalendarManager() { daysOffset = 0; }

    public static CalendarManager newInstance(Calendar calendar) {
        CalendarManager calendarManager = new CalendarManager();
        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
        calendarManager.calendar = new SimpleSubject<>();
        calendarManager.calendar.setValue(calendar);
        return calendarManager;
    }

    public Subject<Calendar> getCalendar() {
        Calendar calendar = Calendar.getInstance();
        return getCalendar(calendar);
    }

    public Subject<Calendar> getCalendar(Calendar calendar) {
        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
        calendar.add(Calendar.DATE, daysOffset);
        this.calendar.setValue(calendar);
        return this.calendar;
    }

    public void nextDay() {
        daysOffset++;
        Calendar calendar = getCalendar().getValue();
        this.calendar.setValue(calendar);
        getCalendar();
    }
}
