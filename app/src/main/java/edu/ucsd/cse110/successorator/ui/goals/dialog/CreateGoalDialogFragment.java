package edu.ucsd.cse110.successorator.ui.goals.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalContext;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;

public class CreateGoalDialogFragment extends DialogFragment {
    private FragmentDialogCreateGoalBinding view;
    private MainViewModel activityModel;

    private GoalContext selectedContext = null;

    CreateGoalDialogFragment() {

    }

    public static CreateGoalDialogFragment newInstance() {
        var fragment = new CreateGoalDialogFragment();
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
    private void setSelectedContext(GoalContext goalContext) {
        this.selectedContext = goalContext;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout and get the binding instance
        view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());

        // Define your TextViews
        TextView homeTextView = view.button1;
        TextView workTextView = view.button2;
        TextView schoolTextView = view.button3;
        TextView errandTextView = view.button4;

        // Set click listeners for each TextView
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

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the new goal text.")
                .setView(view.getRoot())
                .setPositiveButton("✔", this::onPositiveButtonClick)
                .setNegativeButton("X", this::onNegativeButtonClick)
                .create();
    }

    private void toggleTextViewBackground(TextView textView) {
        // Remove black border from all TextViews
        view.button1.setSelected(false);
        view.button1.setBackgroundResource(R.drawable.button_background_home);
        view.button2.setSelected(false);
        view.button2.setBackgroundResource(R.drawable.button_background_work);
        view.button3.setSelected(false);
        view.button3.setBackgroundResource(R.drawable.button_background_school);
        view.button4.setSelected(false);
        view.button4.setBackgroundResource(R.drawable.button_background_errand);

        // Set selected TextView and apply custom background
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


    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        if (selectedContext == null) {
            return;
        }

        var goalText = view.goalEditText.getText().toString();
        if (TextUtils.isEmpty(goalText)) {
            // Handle empty goal text
            return;
        }
        var goal = new Goal(null, goalText, selectedContext, -1, false);
        // once persistence is added to this database, this should work
        activityModel.append(goal);
        dialog.dismiss();

    }

    

    // lets user exit out of the add goal screen by pressing "x" or clicking outside box
    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
