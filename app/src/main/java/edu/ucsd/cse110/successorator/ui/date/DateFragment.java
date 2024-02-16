package edu.ucsd.cse110.successorator.ui.date;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDateBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.ui.goals.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.util.GoalsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends Fragment {
    private MainViewModel activityModel;

    FragmentDateBinding view;

    public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.FULL);

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

        activityModel.getCalendar().observe(calendar -> {
            updateDateText(calendar);
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

        updateDateText(activityModel.getCalendar().getValue());
        return view.getRoot();
    }

    private void updateDateText(Calendar calendar) {
        if (this.view == null) {return;}
        TextView dateTextView = this.view.dateTextView;
        String dateText = DATE_FORMAT.format(calendar.getTime());
        dateTextView.setText(dateText);
    }
}