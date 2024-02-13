package edu.ucsd.cse110.successorator.ui.mocklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentMockListBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.ui.mocklist.dialog.CreateGoalDialogFragment;

public class MockListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentMockListBinding view;
    private MockListAdapter adapter;

    public MockListFragment() {
        // Required empty public constructor
    }

    public static MockListFragment newInstance() {
        MockListFragment fragment = new MockListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        // Initialize the Adapter (with an empty list for now)
//        this.adapter = new MockListAdapter(requireContext(), List.of(), id -> {
//            var dialogFragment = ConfirmDeleteCardDialogFragment.newInstance(id);
//            dialogFragment.show(getParentFragmentManager(), "ConfirmDeleteCardDialogFragment");
//        });
//        activityModel.getOrderedCards().observe(cards -> {
//            if (cards == null) return;
//            adapter.clear();
//            adapter.addAll((Collection<? extends Goal>) new ArrayList<>(cards)); // remember the mutable copy here!
//            adapter.notifyDataSetChanged();
//        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentMockListBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.mockList.setAdapter(adapter);

        view.createGoalButton.setOnClickListener(v -> {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(getParentFragmentManager(), "CreateGoalDialogFragment");
        });

        return view.getRoot();
    }
}
