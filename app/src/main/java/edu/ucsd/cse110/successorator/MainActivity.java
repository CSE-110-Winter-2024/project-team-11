package edu.ucsd.cse110.successorator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;
import edu.ucsd.cse110.successorator.ui.goals.GoalsFragment;
import edu.ucsd.cse110.successorator.ui.recurringgoals.RecurringGoalsFragment;
import edu.ucsd.cse110.successorator.ui.today.dialog.CreateTodayGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.today.dialog.FilterGoalsDialogFragment;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var modelOwner = this;
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        var activityModel = modelProvider.get(MainViewModel.class);

        activityModel.getCurrentView().observe(viewEnum -> {
            switch(viewEnum) {
                case TODAY: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.goalsFragmentContainer.getId(),
                                    new GoalsFragment())
                            .commit();

                    activityModel.setDateDisplayTextLogic(dateText ->  "Today, " + dateText);

                    // Set the click listener for the createGoalButton
                    binding.createGoalButton.setOnClickListener(v -> {
                        CreateTodayGoalDialogFragment dialogFragment = CreateTodayGoalDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "CreateTodayGoalDialogFragment");
                    });
                    binding.filterMenuButton.setOnClickListener(v -> {
                        FilterGoalsDialogFragment dialogFragment = FilterGoalsDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "FilterGoalsDialogFragment");
                    });
                    break;
                }
                case TMRW: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.goalsFragmentContainer.getId(),
                                    new GoalsFragment())
                            .commit();

                    activityModel.setDateDisplayTextLogic(dateText ->  "Tomorrow, " + dateText);

                    // Set the click listener for the createGoalButton
                    binding.createGoalButton.setOnClickListener(v -> {
                        CreateTodayGoalDialogFragment dialogFragment = CreateTodayGoalDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "CreateTodayGoalDialogFragment");
                    });
                    binding.filterMenuButton.setOnClickListener(v -> {
                        FilterGoalsDialogFragment dialogFragment = FilterGoalsDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "FilterGoalsDialogFragment");
                    });
                    break;
                }
                case PENDING: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.goalsFragmentContainer.getId(),
                                    new GoalsFragment())
                            .commit();

                    activityModel.setDateDisplayTextLogic(dateText ->  "Pending");

                    // Set the click listener for the createGoalButton
                    binding.createGoalButton.setOnClickListener(v -> {
                        CreateTodayGoalDialogFragment dialogFragment = CreateTodayGoalDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "CreateTodayGoalDialogFragment");
                    });
                    binding.filterMenuButton.setOnClickListener(v -> {
                        FilterGoalsDialogFragment dialogFragment = FilterGoalsDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "FilterGoalsDialogFragment");
                    });
                    break;
                }
                case RECURRING: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.goalsFragmentContainer.getId(),
                                    new RecurringGoalsFragment())
                            .commit();

                    activityModel.setDateDisplayTextLogic(dateText ->  "Recurring");

                    // Set the click listener for the createGoalButton
                    binding.createGoalButton.setOnClickListener(v -> {
                        // To be changed
                        CreateTodayGoalDialogFragment dialogFragment = CreateTodayGoalDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "CreateTodayGoalDialogFragment");
                    });
                    binding.filterMenuButton.setOnClickListener(v -> {
                        FilterGoalsDialogFragment dialogFragment = FilterGoalsDialogFragment.newInstance();
                        dialogFragment.show(getSupportFragmentManager(), "FilterGoalsDialogFragment");
                    });
                    break;
                }
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(binding.dateFragmentContainer.getId(),
                        new DateFragment())
                .commit();

        binding.getRoot().setOnClickListener(v -> {
            activityModel.getDate();
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        getSupportFragmentManager().beginTransaction()
                .replace(binding.dateFragmentContainer.getId(), new DateFragment())
                .commit();


        var modelOwner = this;
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        modelProvider.get(MainViewModel.class).getDate();
    }
}
