package edu.ucsd.cse110.successorator.ui.goals;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.util.GoalsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {
    private MainViewModel activityModel;
    private GoalsAdapter ongoingGoalsAdapter;
    private GoalsAdapter completedGoalsAdapter;

    // No arg constructor for the goalsFragment
    public GoalsFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);


        activityModel.getOngoingGoals().observe(goals -> {
            ArrayList<Goal> newOngoingGoals = (ArrayList<Goal>) (goals != null ? goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList()) : null);
            if (ongoingGoalsAdapter != null) {
                ongoingGoalsAdapter.updateData(newOngoingGoals);
                updateOngoingGoalsText(newOngoingGoals.isEmpty());
                updateCompletedGoalsParams(newOngoingGoals.isEmpty());
            }
        });
        activityModel.getCompletedGoals().observe(goals -> {
            ArrayList<Goal> newCompletedGoals = (ArrayList<Goal>) (goals != null ? goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList()) : null);
            if (completedGoalsAdapter != null) {
                completedGoalsAdapter.updateData(newCompletedGoals);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        ListView ongoingListView = view.findViewById(R.id.ongoing_goals_list);
        ListView completedListView = view.findViewById(R.id.completed_goals_list);
        // Initialize adapters with empty lists
        ongoingGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), false);
        completedGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), true);

        ongoingGoalsAdapter.setOnGoalCompleteListener(goal -> {
            // Set complete goal listener
            activityModel.completeGoal(goal);
        });

        completedGoalsAdapter.setOnGoalUnCompleteListener(goal -> {
            // Set uncomplete goal listener
            activityModel.unCompleteGoal(goal);
        });

        ongoingListView.setAdapter(ongoingGoalsAdapter);
        completedListView.setAdapter(completedGoalsAdapter);

        return view;
    }

    private void updateOngoingGoalsText(boolean isEmpty) {
        if(getView() == null) {return;}
        TextView noOngoingGoalText = getView().findViewById(R.id.no_ongoing_goals_text);
        if(isEmpty) {
            noOngoingGoalText.setText("No goals for the Day.  Click the + at the upper right to enter your Most Important Thing.");
        }
        else {
            noOngoingGoalText.setText("");
        }
    }

    private void updateCompletedGoalsParams(boolean ongoingIsEmpty) {
        if(getView() == null) {return;}
        ListView ongoingListView = getView().findViewById(R.id.ongoing_goals_list);
        ListView completedListView = getView().findViewById(R.id.completed_goals_list);
        TextView noOngoingGoalText = getView().findViewById(R.id.no_ongoing_goals_text);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) completedListView.getLayoutParams();
        if(ongoingIsEmpty) {
            params.topToBottom = noOngoingGoalText.getId();
        }
        else {
            params.topToBottom = ongoingListView.getId();
        }
        completedListView.setLayoutParams(params);
    }
}