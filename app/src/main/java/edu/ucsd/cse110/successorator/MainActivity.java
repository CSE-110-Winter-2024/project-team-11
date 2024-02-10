package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.goals.Completed_Goals_Fragment;
import edu.ucsd.cse110.successorator.ui.goals.Ongoing_Goals_Fragment;

public class MainActivity extends AppCompatActivity
{

    private GoalList onGoingGoals = new GoalList(Arrays.asList(
            new Goal(1, "Go to Target", 1),
            new Goal(2, "Do Yoga", 2),
            new Goal(3, "Read my book", 3)
    ));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GoalList onGoingGoals = new GoalList(Arrays.asList(
                new Goal(1, "Go to Target", 1),
                new Goal(2, "Do Yoga", 2),
                new Goal(3, "Read my book", 3)
        ));
        // Load first fragment into first FragmentContainerView
        getSupportFragmentManager().beginTransaction()
                .replace(binding.ongoingGoalsFragmentContainer.getId(), new Ongoing_Goals_Fragment())
                .commit();

        // Load second fragment into second FragmentContainerView
        //getSupportFragmentManager().beginTransaction()
          //      .replace(binding.completedGoalsFragmentContainer.getId(), new Completed_Goals_Fragment())
           //     .commit();
    }
}
