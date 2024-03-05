package edu.ucsd.cse110.successorator.ui.goals.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class CreateGoalDialogFragment extends DialogFragment {
    private FragmentDialogCreateGoalBinding view;
    private MainViewModel activityModel;

    private String selectedContext;

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
    private void setSelectedContext(String context) {
        this.selectedContext = context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());

        view.button1.setOnClickListener(v -> setSelectedContext("HOME"));
        view.button2.setOnClickListener(v -> setSelectedContext("WORK"));
        view.button3.setOnClickListener(v -> setSelectedContext("SCHOOL"));
        view.button4.setOnClickListener(v -> setSelectedContext("ERRAND"));


        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please provide the new goal text.")
                .setView(view.getRoot())
                .setPositiveButton("✔", this::onPositiveButtonClick)
                .setNegativeButton("X", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var goalText = view.goalEditText.getText().toString();
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
