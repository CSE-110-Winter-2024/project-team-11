package edu.ucsd.cse110.successorator.ui.goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

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
    public GoalList ongoingGoals = new GoalList(Arrays.asList(
            new Goal(1, "Go to Target", 1),
            new Goal(2, "Do Yoga", 2),
            new Goal(3, "Read my book", 3)
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

        if (ongoingGoals != null) {
            GoalsAdapter adapter = new GoalsAdapter(requireContext(), ongoingGoals.getGoals(), true);
            ongoingListView.setAdapter(adapter);
        }
        if (completedGoals != null) {
            GoalsAdapter adapter = new GoalsAdapter(requireContext(), completedGoals.getGoals(), false);
            completedListView.setAdapter(adapter);
        }

        return view;
    }
}