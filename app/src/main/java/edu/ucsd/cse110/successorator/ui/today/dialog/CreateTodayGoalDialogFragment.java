package edu.ucsd.cse110.successorator.ui.today.dialog;

import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.DAILY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.MONTHLY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.WEEKLY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.YEARLY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateTodayGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalContext;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoal;

public class CreateTodayGoalDialogFragment extends DialogFragment {

    private FragmentCreateTodayGoalBinding view;
    private MainViewModel activityModel;
    private GoalContext selectedContext = null;

    CreateTodayGoalDialogFragment() {}

    public static CreateTodayGoalDialogFragment newInstance() {
        return new CreateTodayGoalDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentCreateTodayGoalBinding.inflate(getLayoutInflater());

        LocalDate date = activityModel.getDate().getValue();

        RecurrenceFactory recurrenceFactory = new RecurrenceFactory();
        Recurrence daily = recurrenceFactory.createRecurrence(date, DAILY);
        Recurrence weekly = recurrenceFactory.createRecurrence(date, WEEKLY);
        Recurrence monthly = recurrenceFactory.createRecurrence(date, MONTHLY);
        Recurrence yearly = recurrenceFactory.createRecurrence(date, YEARLY);
        view.weeklyRadioButton.setText(weekly.recurrenceText());
        view.monthlyRadioButton.setText(monthly.recurrenceText());
        view.yearlyRadioButton.setText(yearly.recurrenceText());

        setupContextSelection();

        view.saveButton.setOnClickListener(v -> {
            saveGoal(daily, weekly, monthly, yearly);
            dismiss();
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the new goal text.")
                .setView(view.getRoot())
                .create();
    }

    private void setupContextSelection() {
        TextView homeTextView = view.button1;
        TextView workTextView = view.button2;
        TextView schoolTextView = view.button3;
        TextView errandTextView = view.button4;

        homeTextView.setOnClickListener(v -> {
            setSelectedContext(GoalContext.HOME);
            toggleTextViewBackground(homeTextView);
        });
        workTextView.setOnClickListener(v -> {
            setSelectedContext(GoalContext.WORK);
            toggleTextViewBackground(workTextView);
        });
        schoolTextView.setOnClickListener(v -> {
            setSelectedContext(GoalContext.SCHOOL);
            toggleTextViewBackground(schoolTextView);
        });
        errandTextView.setOnClickListener(v -> {
            setSelectedContext(GoalContext.ERRAND);
            toggleTextViewBackground(errandTextView);
        });
    }

    private void setSelectedContext(GoalContext context) {
        this.selectedContext = context;
    }

    private void toggleTextViewBackground(TextView textView) {
        view.button1.setSelected(false);
        view.button1.setBackgroundResource(R.drawable.button_background_home);
        view.button2.setSelected(false);
        view.button2.setBackgroundResource(R.drawable.button_background_work);
        view.button3.setSelected(false);
        view.button3.setBackgroundResource(R.drawable.button_background_school);
        view.button4.setSelected(false);
        view.button4.setBackgroundResource(R.drawable.button_background_errand);

        textView.setSelected(true);
        textView.setBackgroundResource(getBackgroundResource(textView));
    }

    private int getBackgroundResource(TextView textView) {
        int resourceId;
        if (textView.getId() == R.id.button1) {
            resourceId = R.drawable.button_background_home;
        } else if (textView.getId() == R.id.button2) {
            resourceId = R.drawable.button_background_work;
        } else if (textView.getId() == R.id.button3) {
            resourceId = R.drawable.button_background_school;
        } else if (textView.getId() == R.id.button4) {
            resourceId = R.drawable.button_background_errand;
        } else {
            resourceId = R.drawable.button_background_home; // Default case
        }
        return resourceId;
    }

    private void saveGoal(Recurrence daily, Recurrence weekly, Recurrence monthly, Recurrence yearly) {
        var goalText = view.enterGoalText.getText().toString();
        if (TextUtils.isEmpty(goalText)) {
            // Handle empty goal text
            return;
        }
        var goal = new Goal(null, goalText, selectedContext, -1, false);

        if (view.oneTimeRadioButton.isChecked()) {
            activityModel.todayAppend(goal);
        } else if (view.dailyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, daily);
            activityModel.recurringAppend(recurringGoal);
        } else if (view.weeklyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, weekly);
            activityModel.recurringAppend(recurringGoal);
        }
        else if(view.monthlyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, monthly);
            activityModel.recurringAppend(recurringGoal);
        }
        else if(view.yearlyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, yearly);
            activityModel.recurringAppend(recurringGoal);
        }
        else {
            throw new IllegalStateException("No radio button selected");
        }
    }
}