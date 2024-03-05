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
    private GoalsAdapter ongoingHomeGoalsAdapter;

    private GoalsAdapter ongoingWorkGoalsAdapter;

    private GoalsAdapter ongoingSchoolGoalsAdapter;

    private GoalsAdapter ongoingErrandGoalsAdapter;
    private GoalsAdapter completedGoalsAdapter;

    private boolean emptyOngoingHomeGoals = true;
    private boolean emptyOngoingWorkGoals = true;
    private boolean emptyOngoingSchoolGoals = true;
    private boolean emptyOngoingErrandGoals = true;

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


        activityModel.getOngoingHomeGoals().observe(goals -> {
            ArrayList<Goal> newOngoingGoals = (ArrayList<Goal>) (goals != null ? goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList()) : null);
            if (ongoingHomeGoalsAdapter != null) {
                ongoingHomeGoalsAdapter.updateData(newOngoingGoals);
                emptyOngoingHomeGoals = newOngoingGoals.isEmpty();
                updateOngoingGoalsText();
                updateCompletedGoalsParams();
            }
        });
        activityModel.getOngoingWorkGoals().observe(goals -> {
            ArrayList<Goal> newOngoingGoals = (ArrayList<Goal>) (goals != null ? goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList()) : null);
            if (ongoingWorkGoalsAdapter != null) {
                ongoingWorkGoalsAdapter.updateData(newOngoingGoals);
                emptyOngoingWorkGoals = newOngoingGoals.isEmpty();
                updateOngoingGoalsText();
                updateCompletedGoalsParams();
            }
        });
        activityModel.getOngoingSchoolGoals().observe(goals -> {
            ArrayList<Goal> newOngoingGoals = (ArrayList<Goal>) (goals != null ? goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList()) : null);
            if (ongoingSchoolGoalsAdapter != null) {
                ongoingSchoolGoalsAdapter.updateData(newOngoingGoals);
                emptyOngoingSchoolGoals = newOngoingGoals.isEmpty();
                updateOngoingGoalsText();
                updateCompletedGoalsParams();
            }
        });
        activityModel.getOngoingErrandGoals().observe(goals -> {
            ArrayList<Goal> newOngoingGoals = (ArrayList<Goal>) (goals != null ? goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList()) : null);
            if (ongoingErrandGoalsAdapter != null) {
                ongoingErrandGoalsAdapter.updateData(newOngoingGoals);
                emptyOngoingErrandGoals = newOngoingGoals.isEmpty();
                updateOngoingGoalsText();
                updateCompletedGoalsParams();
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

        ListView ongoingHomeListView = view.findViewById(R.id.home_goals_list);
        ListView ongoingWorkListView = view.findViewById(R.id.work_goals_list);
        ListView ongoingSchoolListView = view.findViewById(R.id.school_goals_list);
        ListView ongoingErrandListView = view.findViewById(R.id.errand_goals_list);
        ListView completedListView = view.findViewById(R.id.completed_goals_list);
        // Initialize adapters with empty lists
        ongoingHomeGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), false);
        ongoingWorkGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), false);
        ongoingSchoolGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), false);
        ongoingErrandGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), false);
        completedGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), true);


        ongoingHomeGoalsAdapter.setOnGoalCompleteListener(goal -> {
            // Set complete goal listener
            activityModel.completeGoal(goal);
        });
        ongoingWorkGoalsAdapter.setOnGoalCompleteListener(goal -> {
            // Set complete goal listener
            activityModel.completeGoal(goal);
        });
        ongoingSchoolGoalsAdapter.setOnGoalCompleteListener(goal -> {
            // Set complete goal listener
            activityModel.completeGoal(goal);
        });
        ongoingErrandGoalsAdapter.setOnGoalCompleteListener(goal -> {
            // Set complete goal listener
            activityModel.completeGoal(goal);
        });

        completedGoalsAdapter.setOnGoalUnCompleteListener(goal -> {
            // Set uncomplete goal listener
            activityModel.unCompleteGoal(goal);
        });

        ongoingHomeListView.setAdapter(ongoingHomeGoalsAdapter);
        ongoingWorkListView.setAdapter(ongoingWorkGoalsAdapter);
        ongoingSchoolListView.setAdapter(ongoingSchoolGoalsAdapter);
        ongoingErrandListView.setAdapter(ongoingErrandGoalsAdapter);
        completedListView.setAdapter(completedGoalsAdapter);

        return view;
    }

    private void updateOngoingGoalsText() {
        if(getView() == null) {return;}
        TextView noOngoingGoalText = getView().findViewById(R.id.no_ongoing_goals_text);
        if(emptyOngoingSchoolGoals && emptyOngoingErrandGoals
        && emptyOngoingWorkGoals && emptyOngoingHomeGoals) {
            noOngoingGoalText.setText("No goals for the Day.  Click the + at the upper right to enter your Most Important Thing.");
        }
        else {
            noOngoingGoalText.setText("");
        }
    }

    private void updateCompletedGoalsParams() {
        if(getView() == null) {return;}
        ListView ongoingHomeListView = getView().findViewById(R.id.home_goals_list);
        ListView ongoingWorkListView = getView().findViewById(R.id.work_goals_list);
        ListView ongoingSchoolListView = getView().findViewById(R.id.school_goals_list);
        ListView ongoingErrandListView = getView().findViewById(R.id.errand_goals_list);
        ListView completedListView = getView().findViewById(R.id.completed_goals_list);
        TextView noOngoingGoalText = getView().findViewById(R.id.no_ongoing_goals_text);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) completedListView.getLayoutParams();
        if(emptyOngoingHomeGoals && emptyOngoingWorkGoals
        && emptyOngoingSchoolGoals && emptyOngoingErrandGoals) {
            params.topToBottom = noOngoingGoalText.getId();
        }
        else {
            params.topToBottom = ongoingHomeListView.getId();
            params.topToBottom = ongoingWorkListView.getId();
            params.topToBottom = ongoingSchoolListView.getId();
            params.topToBottom = ongoingErrandListView.getId();
        }
        completedListView.setLayoutParams(params);
    }
}