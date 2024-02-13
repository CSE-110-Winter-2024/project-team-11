package edu.ucsd.cse110.successorator;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
//    private final goalRepository goalRepository;

    // UI state
//    private final MutableSubject<List<Goal>> orderedGoals;
//    private final MutableSubject<Goal> topGoal;
//    private final MutableSubject<Boolean> isShowingFront;
//    private final MutableSubject<String> displayedText;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel();
                    });
}
