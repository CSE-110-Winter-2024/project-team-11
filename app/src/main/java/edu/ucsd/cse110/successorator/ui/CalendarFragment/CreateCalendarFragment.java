package edu.ucsd.cse110.successorator.ui.CalendarFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Calendar;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentCalendarBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateTodayGoalBinding;
import edu.ucsd.cse110.successorator.ui.today.dialog.CreateTodayGoalDialogFragment;
import edu.ucsd.cse110.successorator.util.PendingGoalsAdapter;

public class CreateCalendarFragment extends DialogFragment {

    private FragmentCalendarBinding view;
    private CalendarView calendarView;
    private MainViewModel activityModel;

    public interface OnDateSelectedListener{
        void onDateSelected(Calendar selectedDate);
    }

    private OnDateSelectedListener dateSelectedListener;
    public CreateCalendarFragment(){

    }
    public static CreateCalendarFragment newInstance() {
        return new CreateCalendarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = rootView.findViewById(R.id.CalendarView);

        calendarView.setMinDate(System.currentTimeMillis()-1000);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                if (dateSelectedListener != null) {
                    dateSelectedListener.onDateSelected(selectedDate);
                }

                Toast.makeText(getContext(), "Selected Date: " + selectedDate.getTime().toString(), Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });
        return rootView;
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.dateSelectedListener = listener;
    }

}
