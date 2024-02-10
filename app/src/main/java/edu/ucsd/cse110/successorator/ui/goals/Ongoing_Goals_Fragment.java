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

    private GoalList onGoingGoals;

    public Ongoing_Goals_Fragment() {
        // Required empty public constructor
    }

    public static Ongoing_Goals_Fragment newInstance(GoalList goalList) {
        Ongoing_Goals_Fragment fragment = new Ongoing_Goals_Fragment();
        Bundle args = new Bundle();
        args.putSerializable("goalList", (Serializable) goalList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            onGoingGoals = (GoalList) getArguments().getSerializable("goalList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing__goals_, container, false);

        ListView listView = view.findViewById(R.id.ongoing_list);
        Log.d("Ongoing_Goals_Fragment", "List view: " + listView);

        if (onGoingGoals != null) {
            Log.d("Ongoing_Goals_Fragment", "Number of goals: " + onGoingGoals.getGoals().size());
            OngoingGoalsAdapter adapter = new OngoingGoalsAdapter(requireContext(), onGoingGoals.getGoals());
            listView.setAdapter(adapter);
        } else {
            Log.e("Ongoing_Goals_Fragment", "onGoingGoals is null");
        }

        return view;
    }
}

