package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.recurringgoal.RecurringGoal;

public class RecurringGoalsAdapter extends ArrayAdapter<RecurringGoal>
{
    // Constructor for the goals adapter
    public RecurringGoalsAdapter(Context context, List<RecurringGoal> goals)
    {
        super(context, 0, new ArrayList<>(goals));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        // Inflate the list item view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goal_card, parent, false);
        }

        convertView.setOnLongClickListener(v -> {
            RecurringGoal goal = getItem(position);
            if (goal != null) {
                // To be implemented
            }
            return false;
        });


        // Get the data item for this position
        RecurringGoal goal = getItem(position);

        // Get reference to views in the list item layout
        TextView textViewGoalText = convertView.findViewById(R.id.goal_text);

        // Set data to views
        if (goal != null) {
            textViewGoalText.setText(goal.text()); // Set the text from the Goal object
        }

        return convertView;
    }
    public void updateData(List<RecurringGoal> newGoals) {
        clear(); // Clear the existing data
        addAll(newGoals); // Add the new data
        notifyDataSetChanged(); // Notify the adapter to refresh the views
    }
}
