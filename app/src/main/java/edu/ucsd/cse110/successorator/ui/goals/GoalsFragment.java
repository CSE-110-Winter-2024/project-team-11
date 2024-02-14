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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.util.GoalsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalsFragment extends Fragment {
    private MainViewModel activityModel;
    private GoalList ongoingGoals = new GoalList(new ArrayList<>());
    private GoalList completedGoals = new GoalList(new ArrayList<>());

    private GoalsAdapter ongoingGoalsAdapter;
    private GoalsAdapter completedGoalsAdapter;


    private GoalsAdapter adapter;

    // No arg constructor for the goalsFragment
    public GoalsFragment()
    {

    }


    public static GoalsFragment newInstance(GoalList ongoingGoals, GoalList completedGoals)
    {
        GoalsFragment fragment = new GoalsFragment();
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


        activityModel.getOngoingList().observe(goals -> {
            ArrayList<Goal> newOngoingGoals = (ArrayList<Goal>) goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());
            if (ongoingGoalsAdapter != null) {
                ongoingGoalsAdapter.updateData(newOngoingGoals);
            }
        });
        activityModel.getCompletedList().observe(goals -> {
            ArrayList<Goal> newCompletedGoals = (ArrayList<Goal>) goals.stream()
                    .sorted(Comparator.comparingInt(Goal::sortOrder))
                    .collect(Collectors.toList());
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
        TextView noOngoingGoalText = view.findViewById(R.id.no_ongoing_goals_text);

        // Initialize adapters with empty lists
        ongoingGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), false);
        completedGoalsAdapter = new GoalsAdapter(requireContext(), new ArrayList<>(), true);

        ongoingListView.setAdapter(ongoingGoalsAdapter);
        completedListView.setAdapter(completedGoalsAdapter);

        // Get the parent layout (ConstraintLayout)
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) completedListView.getLayoutParams();

        // Set constraints dynamically
        if (ongoingGoals.getGoals().size() == 0) {
            params.topToBottom = noOngoingGoalText.getId();
        } else {
            params.topToBottom = ongoingListView.getId();
        }

        // Apply the updated layout params
        completedListView.setLayoutParams(params);

        return view;
    }
}