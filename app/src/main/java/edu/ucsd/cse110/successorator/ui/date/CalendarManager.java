package edu.ucsd.cse110.successorator.ui.date;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.MutableLiveDataSubjectAdapter;

// https://stackoverflow.com/questions/65532918/changing-value-of-a-livedata-calendar-do-not-trigger-observer
public class CalendarManager {
    public static final int DAY_START_HOUR = 2;

    MutableSubject<Calendar> calendar;

    public CalendarManager(Calendar calendar) {
        MutableLiveData<Calendar> calendarLiveData = new MutableLiveData<>();
        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
        calendarLiveData.setValue(calendar);
        this.calendar = new MutableLiveDataSubjectAdapter<>(calendarLiveData);
    }

    public Subject<Calendar> getCalendar() {
        return calendar;
    }

    public void nextDay() {
        Calendar calendar = this.calendar.getValue();
        calendar.add(Calendar.DATE, 1);
        this.calendar.setValue(calendar);
    }
}
