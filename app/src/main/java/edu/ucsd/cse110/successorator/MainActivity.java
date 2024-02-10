package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.goals.Completed_Goals_Fragment;
import edu.ucsd.cse110.successorator.ui.goals.Ongoing_Goals_Fragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load first fragment into first FragmentContainerView
        getSupportFragmentManager().beginTransaction()
                .replace(binding.ongoingGoalsFragmentContainer.getId(), new Ongoing_Goals_Fragment())
                .commit();

        // Load second fragment into second FragmentContainerView
        getSupportFragmentManager().beginTransaction()
                .replace(binding.completedGoalsFragmentContainer.getId(), new Completed_Goals_Fragment())
                .commit();
    }
}
