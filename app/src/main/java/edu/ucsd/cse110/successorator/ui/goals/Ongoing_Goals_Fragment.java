package edu.ucsd.cse110.successorator.ui.goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.Serializable;
import java.util.Arrays;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.util.OngoingGoalsAdapter;
import edu.ucsd.cse110.successorator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ongoing_Goals_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ongoing_Goals_Fragment extends Fragment {

    public GoalList ongoingGoals = new GoalList(Arrays.asList(
            new Goal(1, "Go to Target", 1),
            new Goal(2, "Do Yoga", 2),
            new Goal(3, "Read my book", 3)
        ));

    public Ongoing_Goals_Fragment()
    {

    }

    public static Ongoing_Goals_Fragment newInstance(GoalList goalList)
    {
        Ongoing_Goals_Fragment fragment = new Ongoing_Goals_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.ongoingGoals = (GoalList) getArguments().getSerializable("goalList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing__goals_, container, false);

        ListView listView = view.findViewById(R.id.ongoing_list);
        Log.d("Ongoing_Goals_Fragment", "List view: " + listView);

        if (ongoingGoals != null) {
            Log.d("Ongoing_Goals_Fragment", "Number of goals: " + ongoingGoals.getGoals().size());
            OngoingGoalsAdapter adapter = new OngoingGoalsAdapter(requireContext(), ongoingGoals.getGoals());
            listView.setAdapter(adapter);
        } else {
            Log.e("Ongoing_Goals_Fragment", "onGoingGoals is null");
        }

        return view;
    }
}

