package edu.ucsd.cse110.successorator.ui.today.dialog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.DialogFragment;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentFilterGoalsDialogBinding;
import android.app.Dialog;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;


public class FilterGoalsDialogFragment extends DialogFragment {

    // Define the listener interface
    public interface DialogCloseListener {
        void onDialogClosed();
    }

    private FragmentFilterGoalsDialogBinding view;
    private MainViewModel activityModel;
    private DialogCloseListener closeListener; // Listener instance

    FilterGoalsDialogFragment() {}

    // Factory method to create the dialog fragment
    public static FilterGoalsDialogFragment newInstance() {
        return new FilterGoalsDialogFragment();
    }

    // onCreate method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    // Method to set the close listener
    public void setDialogCloseListener(DialogCloseListener listener) {
        this.closeListener = listener;
    }

    // onCreateDialog method
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentFilterGoalsDialogBinding.inflate(getLayoutInflater());

        // Set the on click listeners for the buttons
        view.homeButton.setOnClickListener(v -> toggleFilter("HOME", view.homeButton));
        view.workButton.setOnClickListener(v -> toggleFilter("WORK", view.workButton));
        view.schoolButton.setOnClickListener(v -> toggleFilter("SCHOOL", view.schoolButton));
        view.errandButton.setOnClickListener(v -> toggleFilter("ERRAND", view.errandButton));

        // Set selected filters with black border
        for (String filter : activityModel.selectedFilters) {
            TextView textView = null;
            switch (filter) {
                case "home":
                    textView = view.homeButton;
                    break;
                case "work":
                    textView = view.workButton;
                    break;
                case "school":
                    textView = view.schoolButton;
                    break;
                case "errand":
                    textView = view.errandButton;
                    break;
            }
            if (textView != null) {
                textView.setSelected(true);
                textView.setBackgroundResource(getBackgroundResource(textView));
            }
        }

        // Add event listener to cancelButton
        view.cancelButton.setOnClickListener(v -> onCancelButtonClick());

        // Create the AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Filter goals by context")
                .setMessage("Please filter goal by context.")
                .setView(view.getRoot())
                .create();

        // Set dialog not cancelable
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    // Method to handle cancel button click
    private void onCancelButtonClick() {
        // Close the dialog
        dismiss();

        // Iterate through the filter list and log them out
        for (String filter : activityModel.selectedFilters) {
            Log.d("Filter", filter);
        }

        activityModel.applyFilters();
    }

    // Method to toggle filter
    private void toggleFilter(String filter, TextView textView) {
        if (activityModel.selectedFilters.contains(filter)) {
            activityModel.removeFilter(filter);
            textView.setSelected(false);
        } else {
            activityModel.addFilter(filter);
            textView.setSelected(true);
        }
        textView.setBackgroundResource(getBackgroundResource(textView));
    }

    // Method to get background resource
    private int getBackgroundResource(TextView textView) {
        int resourceId;
        if (textView.getId() == R.id.home_button) {
            resourceId = R.drawable.rectangle_background_home;
        } else if (textView.getId() == R.id.work_button) {
            resourceId = R.drawable.rectangle_background_work;
        } else if (textView.getId() == R.id.school_button) {
            resourceId = R.drawable.rectangle_background_school;
        } else if (textView.getId() == R.id.errand_button) {
            resourceId = R.drawable.rectangle_background_errand;
        } else {
            resourceId = R.drawable.rectangle_background_completed;
        }
        return resourceId;
    }
}