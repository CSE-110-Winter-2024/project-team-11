package edu.ucsd.cse110.successorator;

import static androidx.test.core.app.ActivityScenario.launch;

import static junit.framework.TestCase.assertEquals;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalContext;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
//    @Test
//    public void displaysHelloWorld() {
//        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
//
//            // Observe the scenario's lifecycle to wait until the activity is created.
//            scenario.onActivity(activity -> {
//                var rootView = activity.findViewById(R.id.root);
//                var binding = ActivityMainBinding.bind(rootView);
//
//                var expected = activity.getString(R.string.hello_world);
//                var actual = binding.placeholderText.getText();
//
//                assertEquals(expected, actual);
//            });
//
//            // Simulate moving to the started state (above will then be called).
//            scenario.moveToState(Lifecycle.State.STARTED);
//        }
//    }

    @Test
    public void dateText() {
        var scenario = ActivityScenario.launch(MainActivity.class);
        // Observe the scenario's lifecycle to wait until the activity is created.
        scenario.onActivity(activity -> {
            var rootView = activity.findViewById(R.id.root);
            var binding = ActivityMainBinding.bind(rootView);
            TextView dateTextView = activity.findViewById(R.id.dateTextView);

            LocalDateTime localDateTime = LocalDateTime.now();
            var expected = DateFragment.DATE_TIME_FORMATTER.format(localDateTime);
            var actual = dateTextView.getText();

            assertEquals(expected, actual);
        });

        // Simulate moving to the started state (above will then be called).
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.close();
    }

    @Test
    public void persistentGoals() {
        List<Goal> goalList = new ArrayList<>(List.of(
                new Goal(1, "shopping", GoalContext.HOME, 0, false),
                new Goal(2, "homework", GoalContext.HOME, 1, false),
                new Goal(3, "study", GoalContext.WORK, 2, false),
                new Goal(4, "laundry", GoalContext.SCHOOL, 3, false),
                new Goal(5, "haircut", GoalContext.ERRAND, 4, false)
        ));


        var scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            var modelOwner = activity;
            var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
            var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
            var activityModel = modelProvider.get(MainViewModel.class);

            for (Goal goal : goalList) {
                activityModel.append(goal);
            }
        });

        // Simulate moving to the started state (above will then be called).
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.close();

        var scenario2 = ActivityScenario.launch(MainActivity.class);
        scenario2.onActivity(activity -> {
            var modelOwner = activity;
            var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
            var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
            var activityModel = modelProvider.get(MainViewModel.class);

            activityModel.getOngoingGoals().observe(goals -> {
                assertEquals(goalList, goals);
            });
        });

        scenario2.moveToState(Lifecycle.State.STARTED);
    }
}