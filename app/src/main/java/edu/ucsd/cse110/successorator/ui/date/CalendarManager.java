package edu.ucsd.cse110.successorator.ui.date;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.Locale;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.MutableLiveDataSubjectAdapter;

// https://stackoverflow.com/questions/65532918/changing-value-of-a-livedata-calendar-do-not-trigger-observer
public class CalendarManager {
    public static final int DAY_START_HOUR = 2;

    public int daysOffset;

    public MutableSubject<Calendar> calendar;

//    public CalendarManager(Calendar calendar) {
//        MutableLiveData<Calendar> calendarLiveData = new MutableLiveData<>();
//        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
//        calendarLiveData.setValue(calendar);
//        this.calendar = new MutableLiveDataSubjectAdapter<>(calendarLiveData);
//    }

    public CalendarManager() { daysOffset = 0; }

    public static CalendarManager newInstance() {
        CalendarManager calendarManager = new CalendarManager();
        MutableLiveData<Calendar> calendarLiveData = new MutableLiveData<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -DAY_START_HOUR);
        calendarLiveData.setValue(calendar);
        calendarManager.calendar = new MutableLiveDataSubjectAdapter<>(calendarLiveData);
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
