package edu.ucsd.cse110.successorator.ui.goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ucsd.cse110.successorator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Completed_Goals_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Completed_Goals_Fragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentCardListBinding view;
    private CardListAdapter adapter;


    public Completed_Goals_Fragment()
    {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Completed_Goals_Fragment.
     */
    public static Completed_Goals_Fragment newInstance(String param1, String param2) {
        Completed_Goals_Fragment fragment = new Completed_Goals_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

    }
}