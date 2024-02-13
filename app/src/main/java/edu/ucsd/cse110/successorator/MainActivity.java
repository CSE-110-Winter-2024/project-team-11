package edu.ucsd.cse110.successorator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.goals.GoalsFragment;

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
                .commit();

    }
}
