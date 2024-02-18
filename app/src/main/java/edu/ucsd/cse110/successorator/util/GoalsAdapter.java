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

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalsAdapter extends ArrayAdapter<Goal>
{

    // Maybe need to move this to a new file?
    public interface OnGoalCompleteListener {
        void onGoalComplete(Goal goal);
    }

    // Listener for Goal completion
    private OnGoalCompleteListener onGoalCompleteListener;

    public interface OnGoalUnCompleteListener {
        void onGoalUnComplete(Goal goal);
    }

    // Listener for Goal completion
    private OnGoalUnCompleteListener onGoalUnCompleteListener;

    // Hold if the adapter is for the ongoing goals
    private boolean isCompleted;


    // Constructor for the goals adapter
    public GoalsAdapter(Context context, List<Goal> goals, boolean isCompleted)
    {
        super(context, 0, new ArrayList<>(goals));
        this.isCompleted = isCompleted;
    }

    // Set listener for goal completion
    public void setOnGoalUnCompleteListener(OnGoalUnCompleteListener listener) {
        this.onGoalUnCompleteListener = listener;
    }

    // Set listener for goal uncompletion
    public void setOnGoalCompleteListener(OnGoalCompleteListener listener) {
        this.onGoalCompleteListener = listener;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        // Inflate the list item view
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goal_card, parent, false);
        }

        convertView.setOnClickListener(v -> {
            Goal goal = getItem(position);
            if (goal != null) {
                goal = goal.withIsCompleted(true); // Mark the goal as completed
                if (onGoalCompleteListener != null) {
                    onGoalCompleteListener.onGoalComplete(goal);
                }
            }
        });

        convertView.setOnClickListener(v -> {
            Goal goal = getItem(position);
            if (goal != null) {
                goal = goal.withIsCompleted(false); // Mark the goal as uncompleted
                if (onGoalUnCompleteListener != null) {
                    onGoalUnCompleteListener.onGoalUnComplete(goal);
                }
            }
        });


        // Get the data item for this position
        Goal goal = getItem(position);

        // Get reference to views in the list item layout
        TextView textViewGoalText = convertView.findViewById(R.id.goal_text);

        // Check if the text is for the completed goals
        if(isCompleted)  textViewGoalText.setPaintFlags(textViewGoalText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        // Set data to views
        if (goal != null)
        {
            textViewGoalText.setText(goal.text()); // Set the text from the Goal object
        }

        return convertView;
    }
    public void updateData(List<Goal> newGoals) {
        clear(); // Clear the existing data
        addAll(newGoals); // Add the new data
        notifyDataSetChanged(); // Notify the adapter to refresh the views
    }
}
