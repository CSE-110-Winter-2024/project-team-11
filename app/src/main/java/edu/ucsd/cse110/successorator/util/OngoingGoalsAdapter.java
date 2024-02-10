package edu.ucsd.cse110.successorator.util;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import edu.ucsd.cse110.successorator.R;

import java.util.List;


public class OngoingGoalsAdapter extends ArrayAdapter<Goal>
{

    public OngoingGoalsAdapter(Context context, List<Goal> goals)
    {
        super(context, 0, goals);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        // Inflate the list item view
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ongoing_goal_card, parent, false);
        }

        // Get the data item for this position
        Goal goal = getItem(position);

        // Get reference to views in the list item layout
        TextView textViewGoalText = convertView.findViewById(R.id.ongoing_goal_text); // Use the appropriate ID here

        // Set data to views
        if (goal != null)
        {
            textViewGoalText.setText(goal.text()); // Set the text from the Goal object
        }

        return convertView;
    }
}

