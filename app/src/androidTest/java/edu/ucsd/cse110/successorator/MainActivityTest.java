package edu.ucsd.cse110.successorator;

import static androidx.test.core.app.ActivityScenario.launch;

import static junit.framework.TestCase.assertEquals;

import static edu.ucsd.cse110.successorator.MainViewModel.ViewEnum.*;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
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
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {

            // Observe the scenario's lifecycle to wait until the activity is created.
            scenario.onActivity(activity -> {
                var modelOwner = activity;
                var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
                var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
                var activityModel = modelProvider.get(MainViewModel.class);

                var rootView = activity.findViewById(R.id.root);
                var binding = ActivityMainBinding.bind(rootView);
                TextView dateTextView = activity.findViewById(R.id.dateTextView);
                LocalDate date = LocalDate.now();

                Map<MainViewModel.ViewEnum, String> expected = new HashMap<>() {{
                    put(TODAY, "Today, " + DateFragment.DATE_TIME_FORMATTER.format(date));
                    put(TMRW, "Tomorrow, " + DateFragment.DATE_TIME_FORMATTER.format(date));
                    put(PENDING, "Pending");
                    put(RECURRING, "Recurring");
                }};

                // Should start at today
                var actual = dateTextView.getText().toString();
                assertEquals(expected.get(TODAY), actual);

                Spinner selector = activity.findViewById(R.id.view_selector);
                // Tomorrow
                selector.getOnItemSelectedListener().onItemSelected(
                        selector, selector.getSelectedView(), TMRW.ordinal(), 0);
                actual = dateTextView.getText().toString();
                assertEquals(expected.get(TMRW), actual);

                // Pending
                selector.getOnItemSelectedListener().onItemSelected(
                        selector, selector.getSelectedView(), PENDING.ordinal(), 0);
                actual = dateTextView.getText().toString();
                assertEquals(expected.get(PENDING), actual);

                // Recurring
                selector.getOnItemSelectedListener().onItemSelected(
                        selector, selector.getSelectedView(), RECURRING.ordinal(), 0);
                actual = dateTextView.getText().toString();
                assertEquals(expected.get(RECURRING), actual);

                // Stress Test
                for (int i = 0; i < 100; i++) {
                    int idx = (int)(Math.random() * MainViewModel.ViewEnum.values().length);
                    MainViewModel.ViewEnum viewEnum = MainViewModel.ViewEnum.values()[idx];
                    selector.getOnItemSelectedListener().onItemSelected(
                            selector, selector.getSelectedView(), idx, 0);
                    actual = dateTextView.getText().toString();
                    assertEquals(expected.get(viewEnum), actual);
                }
            });

            // Simulate moving to the started state (above will then be called).
            scenario.moveToState(Lifecycle.State.STARTED);
            scenario.moveToState(Lifecycle.State.RESUMED);
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}