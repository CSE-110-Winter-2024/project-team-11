package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.goals.RoomGoalRepository;
import edu.ucsd.cse110.successorator.data.db.goals.GoalDatabase;
import edu.ucsd.cse110.successorator.data.db.time.RoomTimeManager;
import edu.ucsd.cse110.successorator.data.db.time.TimeDatabase;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class SuccessoratorApplication extends Application {
    private GoalRepository ongoingGoalRepository;
    private GoalRepository completedGoalRepository;

    private TimeManager timeManager;

    @Override
    public void onCreate() {
        super.onCreate();

        var ongoingDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        GoalDatabase.class,
                        "successorator-ongoing-database"
                )
                .allowMainThreadQueries()
                .build();

        var completedDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        GoalDatabase.class,
                        "successorator-completed-database"
                )
                .allowMainThreadQueries()
                .build();

        var timeDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TimeDatabase.class,
                        "successorator-time-database"
                )
                .allowMainThreadQueries()
                .build();

        this.ongoingGoalRepository = new RoomGoalRepository(ongoingDatabase.goalDao());
        this.completedGoalRepository = new RoomGoalRepository(completedDatabase.goalDao());
        this.timeManager = new RoomTimeManager(timeDatabase.timeDao());
    }

    public GoalRepository getOngoingGoalRepository() {
        return ongoingGoalRepository;
    }

    public GoalRepository getCompletedGoalRepository() {
        return completedGoalRepository;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }
}
