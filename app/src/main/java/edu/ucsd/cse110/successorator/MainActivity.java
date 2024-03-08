package edu.ucsd.cse110.successorator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;
import edu.ucsd.cse110.successorator.ui.goals.GoalsFragment;
import edu.ucsd.cse110.successorator.ui.goals.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.today.dialog.CreateTodayGoalDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Load first fragment into first FragmentContainerView
        getSupportFragmentManager().beginTransaction()
                .replace(binding.goalsFragmentContainer.getId(), new GoalsFragment())
                .replace(binding.dateFragmentContainer.getId(), new DateFragment())
                .commit();

        // Set the click listener for the createGoalButton
        binding.createGoalButton.setOnClickListener(v -> {
            CreateTodayGoalDialogFragment dialogFragment = CreateTodayGoalDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "CreateTodayGoalDialogFragment");
        });

        binding.getRoot().setOnClickListener(v -> {
            var modelOwner = this;
            var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
            var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
            modelProvider.get(MainViewModel.class).getTime();
        });
    }
}
