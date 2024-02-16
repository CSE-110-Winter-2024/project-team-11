package edu.ucsd.cse110.successorator;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.ui.date.CalendarManager;
import edu.ucsd.cse110.successorator.util.MutableLiveDataSubjectAdapter;

public class MockCalendarManager extends CalendarManager {

//    MutableSubject<Calendar> calendar;

    public static CalendarManager newInstance(Calendar calendar) {
        MockCalendarManager calendarManager = new MockCalendarManager();
        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
        calendarManager.calendar = new SimpleSubject<>();
        calendarManager.calendar.setValue(calendar);
        return calendarManager;
    }
}
