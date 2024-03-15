package edu.ucsd.cse110.successorator.ui.recurringgoals;

import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.DAILY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.MONTHLY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.WEEKLY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.YEARLY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateRecurringGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalContext;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoal;

public class CreateRecurringGoalDialogFragment extends DialogFragment {

    private FragmentCreateRecurringGoalBinding view;
    private MainViewModel activityModel;
    private GoalContext selectedContext = null;

    CreateRecurringGoalDialogFragment() {}

    public static CreateRecurringGoalDialogFragment newInstance() {
        return new CreateRecurringGoalDialogFragment();
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
        this.view = FragmentCreateRecurringGoalBinding.inflate(getLayoutInflater());

        LocalDate date = activityModel.getDate().getValue();

        RecurrenceFactory recurrenceFactory = new RecurrenceFactory();
        AtomicReference<Recurrence> daily = new AtomicReference<>(recurrenceFactory.createRecurrence(date, DAILY));
        AtomicReference<Recurrence> weekly = new AtomicReference<>(recurrenceFactory.createRecurrence(date, WEEKLY));
        AtomicReference<Recurrence> monthly = new AtomicReference<>(recurrenceFactory.createRecurrence(date, MONTHLY));
        AtomicReference<Recurrence> yearly = new AtomicReference<>(recurrenceFactory.createRecurrence(date, YEARLY));
        view.weeklyRadioButton.setText(weekly.get().recurrenceText());
        view.monthlyRadioButton.setText(monthly.get().recurrenceText());
        view.yearlyRadioButton.setText(yearly.get().recurrenceText());
        view.CalendarButton.setText(DATE_TIME_FORMATTER.format(date));

        setupContextSelection();

        view.CalendarButton.setOnClickListener(v -> {
            CreateCalendarFragment CalendarFrag = CreateCalendarFragment.newInstance();
            CalendarFrag.setOnDateSelectedListener(selectedDate -> {
                LocalDate selectedDateLocal = LocalDate.of(selectedDate.get(Calendar.YEAR),
                        selectedDate.get(Calendar.MONTH) + 1,
                        selectedDate.get(Calendar.DAY_OF_MONTH));
                view.CalendarButton.setText(DATE_TIME_FORMATTER.format(selectedDateLocal));

                daily.set(recurrenceFactory.createRecurrence(selectedDateLocal, DAILY));
                weekly.set(recurrenceFactory.createRecurrence(selectedDateLocal, WEEKLY));
                monthly.set(recurrenceFactory.createRecurrence(selectedDateLocal, MONTHLY));
                yearly.set(recurrenceFactory.createRecurrence(selectedDateLocal, YEARLY));
                view.weeklyRadioButton.setText(weekly.get().recurrenceText());
                view.monthlyRadioButton.setText(monthly.get().recurrenceText());
                view.yearlyRadioButton.setText(yearly.get().recurrenceText());
            });
            CalendarFrag.show(getChildFragmentManager(), "calendar_dialog");

        });

        view.saveButton.setOnClickListener(v -> {
            saveGoal(daily.get(), weekly.get(), monthly.get(), yearly.get());
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
    public static DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern(
            "EE M/d");

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
        if (TextUtils.isEmpty(goalText) || selectedContext == null) {
            // Handle empty goal text or null context
            return;
        }
        var goal = new Goal(null, goalText, selectedContext, -1, false);

        if (view.dailyRadioButton.isChecked()) {
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
