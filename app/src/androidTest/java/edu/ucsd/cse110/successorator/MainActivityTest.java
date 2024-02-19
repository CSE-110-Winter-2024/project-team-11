package edu.ucsd.cse110.successorator;

import static androidx.test.core.app.ActivityScenario.launch;

import static junit.framework.TestCase.assertEquals;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.util.Calendar;

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
                var rootView = activity.findViewById(R.id.root);
                var binding = ActivityMainBinding.bind(rootView);
                TextView dateTextView = activity.findViewById(R.id.dateTextView);

                Calendar calendar = Calendar.getInstance();
                var expected = DateFragment.DATE_FORMAT.format(calendar.getTime());
                var actual = dateTextView.getText();

                assertEquals(expected, actual);
            });

            // Simulate moving to the started state (above will then be called).
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}