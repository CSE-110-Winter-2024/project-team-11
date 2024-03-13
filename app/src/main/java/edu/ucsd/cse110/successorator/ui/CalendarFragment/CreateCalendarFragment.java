package edu.ucsd.cse110.successorator.ui.CalendarFragment;

import androidx.fragment.app.Fragment;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateTodayGoalBinding;
import edu.ucsd.cse110.successorator.ui.today.dialog.CreateTodayGoalDialogFragment;

public class CreateCalendarFragment extends Fragment {

    private MainViewModel activityModel;

    public CreateCalendarFragment(){

    }
    public static CreateCalendarFragment newInstance() {
        return new CreateCalendarFragment();
    }
}
