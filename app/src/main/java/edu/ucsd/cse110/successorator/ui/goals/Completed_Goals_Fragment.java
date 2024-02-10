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
    public Completed_Goals_Fragment()
    {

    }


    public static Completed_Goals_Fragment newInstance(String param1, String param2)
    {
        Completed_Goals_Fragment fragment = new Completed_Goals_Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return null;
    }
}