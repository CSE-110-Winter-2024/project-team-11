package edu.ucsd.cse110.successorator.ui.goals;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

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

    // Hold mock ongoing goals
    //public GoalList ongoingGoals = new GoalList(Collections.emptyList());
    public GoalList ongoingGoals = new GoalList(Arrays.asList(
            new Goal(1, "Go To Target", 1),
            new Goal(2, "Do Yoga", 2),
            new Goal(3, "Do laundry", 3)
    ));
    // Hold mock completed goals
    public GoalList completedGoals = new GoalList(Arrays.asList(
            new Goal(4, "Get haircut", 1),
            new Goal(5, "Do taxes", 2),
            new Goal(6, "Pay bills", 3)
    ));

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
        if (getArguments() != null) {
            this.ongoingGoals = (GoalList) getArguments().getSerializable("ongoingGoalList");
            this.completedGoals = (GoalList) getArguments().getSerializable("completedGoalList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        ListView ongoingListView = view.findViewById(R.id.ongoing_goals_list);
        ListView completedListView = view.findViewById(R.id.completed_goals_list);

        TextView noOngoingGoalText = view.findViewById(R.id.no_ongoing_goals_text);

        // Enable noOngoingGoalText
        if(ongoingGoals.getGoals().size() == 0)
        {
            noOngoingGoalText.setText("No goals for the Day.  Click the + at the upper right to enter your Most Important Thing.");
        }
        else
        {
            noOngoingGoalText.setText("");
        }

        if (ongoingGoals != null) {
            GoalsAdapter adapter = new GoalsAdapter(requireContext(), ongoingGoals.getGoals(), true);
            ongoingListView.setAdapter(adapter);
        }
        if (completedGoals != null) {
            GoalsAdapter adapter = new GoalsAdapter(requireContext(), completedGoals.getGoals(), false);
            completedListView.setAdapter(adapter);
        }

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