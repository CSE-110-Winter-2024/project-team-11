package edu.ucsd.cse110.successorator.ui.date;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDateBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends Fragment {
    private MainViewModel activityModel;

    FragmentDateBinding view;

    public static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern(
                    "EEEE M/d");

    // No arg constructor for the goalsFragment
    public DateFragment()
    {

    }


    public static DateFragment newInstance()
    {
        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        activityModel.getDate().observe(time -> {
            updateDateText(time);
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        this.view = FragmentDateBinding.inflate(inflater, container, false);

        view.tmrwButton.setOnClickListener(v -> {
            activityModel.nextDay();
        });

        updateDateText(activityModel.getDate().getValue());
        return view.getRoot();
    }

    private void updateDateText(LocalDate date) {
        if (this.view == null) {return;}
        TextView dateTextView = this.view.dateTextView;
        String dateText = DATE_TIME_FORMATTER.format(date);
        dateTextView.setText(dateText);
    }
}