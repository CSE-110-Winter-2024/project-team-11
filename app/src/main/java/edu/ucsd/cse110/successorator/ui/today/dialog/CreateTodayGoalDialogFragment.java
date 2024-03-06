package edu.ucsd.cse110.successorator.ui.today.dialog;

import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.DAILY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.MONTHLY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.WEEKLY;
import static edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory.RecurrenceEnum.YEARLY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateTodayGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.Recurrence;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.RecurrenceFactory;
import edu.ucsd.cse110.successorator.lib.domain.recurrence.Weekly;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoal;

public class CreateTodayGoalDialogFragment extends DialogFragment {

    private FragmentCreateTodayGoalBinding view;
    private MainViewModel activityModel;
    CreateTodayGoalDialogFragment() {

    }

    public static CreateTodayGoalDialogFragment newInstance() {
        var fragment = new CreateTodayGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        RecurrenceFactory recurrenceFactory = new RecurrenceFactory();
        Recurrence daily = recurrenceFactory.createRecurrence(activityModel.getTime().getValue(), DAILY);
        Recurrence weekly = recurrenceFactory.createRecurrence(activityModel.getTime().getValue(), WEEKLY);
        Recurrence monthly = recurrenceFactory.createRecurrence(activityModel.getTime().getValue(), MONTHLY);
        Recurrence yearly = recurrenceFactory.createRecurrence(activityModel.getTime().getValue(), YEARLY);
        view.weeklyRadioButton.setText(weekly.recurrenceText());
        view.monthlyRadioButton.setText(monthly.recurrenceText());
        view.yearlyRadioButton.setText(yearly.recurrenceText());

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

    public void saveGoal(Recurrence daily, Recurrence weekly, Recurrence monthly, Recurrence yearly) {
//        if(getView() == null) {return;} IDK why getView() returns null but it causes the function to exit
        var goalText = view.enterGoalText.getText().toString();
        var goal = new Goal(null, goalText, -1, false);

        if(view.oneTimeRadioButton.isChecked()) {
            activityModel.append(goal);
        }
        else if(view.dailyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, daily);
            // append ... can't implement yet
        }
        else if(view.weeklyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, weekly);
            // append ... can't implement yet
        }
        else if(view.monthlyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, monthly);
            // append ... can't implement yet
        }
        else if(view.yearlyRadioButton.isChecked()) {
            var recurringGoal = new RecurringGoal(null, goal, yearly);
            // append ... can't implement yet
        }
        else {
            throw new IllegalStateException("No radio button selected");
        }
    }
}
