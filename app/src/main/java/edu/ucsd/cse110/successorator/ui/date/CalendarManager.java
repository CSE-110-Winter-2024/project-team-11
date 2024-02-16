package edu.ucsd.cse110.successorator.ui.date;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.MutableLiveDataSubjectAdapter;

// https://stackoverflow.com/questions/65532918/changing-value-of-a-livedata-calendar-do-not-trigger-observer
public class CalendarManager {
    public static final int DAY_START_HOUR = 2;
    MutableLiveData<Calendar> calendarLiveData;

    public CalendarManager(Calendar calendar) {
        this.calendarLiveData = new MutableLiveData<>();
        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
        calendarLiveData.setValue(calendar);
    }

    public Subject<Calendar> getCalendar() {
        return new MutableLiveDataSubjectAdapter<>(calendarLiveData);
    }

    public void nextDay() {
        Calendar calendar = calendarLiveData.getValue();
        calendar.add(Calendar.DATE, 1);
        calendarLiveData.setValue(calendar);
    }
}
