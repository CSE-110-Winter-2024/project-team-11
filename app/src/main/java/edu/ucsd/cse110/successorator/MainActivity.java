package edu.ucsd.cse110.successorator;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
<<<<<<< HEAD

=======
        //view.placeholderText.setText(R.string.hello_world);
>>>>>>> 21db700d5887abb102c8c36e4e3a3c3458d0a7fb

        setContentView(view.getRoot());
    }
}
