package edu.ucsd.cse110.successorator.ui.goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.util.OngoingGoalsAdapter;

import edu.ucsd.cse110.successorator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ongoing_Goals_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ongoing_Goals_Fragment extends Fragment {

    private GoalList goalList;

    public Ongoing_Goals_Fragment(GoalList goalList) {
        this.goalList = goalList;
    }

    public static Ongoing_Goals_Fragment newInstance(String param1, String param2)
    {
        Ongoing_Goals_Fragment fragment = new Ongoing_Goals_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing_goals, container, false);

        ListView listView = view.findViewById(R.id.ongoing_goal_list);

        // Create adapter and set it to the ListView
        OngoingGoalsAdapter adapter = new OngoingGoalsAdapter(requireContext(), goalList.getGoals());
        listView.setAdapter(adapter);

        return view;
    }
}