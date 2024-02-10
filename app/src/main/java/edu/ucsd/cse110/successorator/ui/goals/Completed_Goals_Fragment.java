package edu.ucsd.cse110.successorator.ui.goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.util.OngoingGoalsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Completed_Goals_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Completed_Goals_Fragment extends Fragment {

    public GoalList completedGoals = new GoalList(Arrays.asList(
            new Goal(4, "Get haircut", 1),
            new Goal(5, "Do taxes", 2),
            new Goal(6, "Pay bills", 3)
    ));
    public Completed_Goals_Fragment()
    {

    }


    public static Completed_Goals_Fragment newInstance(GoalList goalist)
    {
        Completed_Goals_Fragment fragment = new Completed_Goals_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.completedGoals = (GoalList) getArguments().getSerializable("goalList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed__goals_, container, false);

        ListView listView = view.findViewById(R.id.completed_list);
        Log.d("Completed_Goals_Fragment", "List view: " + listView);

        if (completedGoals != null) {
            Log.d("Completed_Goals_Fragment", "Number of goals: " + completedGoals.getGoals().size());
            OngoingGoalsAdapter adapter = new OngoingGoalsAdapter(requireContext(), completedGoals.getGoals());
            listView.setAdapter(adapter);
        } else {
            Log.e("Completed_Goals_Fragment", "completedGoals is null");
        }

        return view;
    }
}