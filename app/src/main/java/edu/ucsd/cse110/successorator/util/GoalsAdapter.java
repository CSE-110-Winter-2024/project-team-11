package edu.ucsd.cse110.successorator.util;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalsAdapter extends ArrayAdapter<Goal>
{

    // Hold if the adapter is for the ongoing goals
    private boolean isOngoing;


    // Constructor for the goals adapter
    public GoalsAdapter(Context context, List<Goal> goals, boolean isOngoing)
    {
        super(context, 0, goals);
        this.isOngoing = isOngoing;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        // Inflate the list item view
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goal_card, parent, false);
        }

        // Get the data item for this position
        Goal goal = getItem(position);

        // Get reference to views in the list item layout
        TextView textViewGoalText = convertView.findViewById(R.id.goal_text);

        // Check if the text is for the completed goals
        if(!isOngoing)  textViewGoalText.setPaintFlags(textViewGoalText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        // Set data to views
        if (goal != null)
        {
            textViewGoalText.setText(goal.text()); // Set the text from the Goal object
        }

        return convertView;
    }
}