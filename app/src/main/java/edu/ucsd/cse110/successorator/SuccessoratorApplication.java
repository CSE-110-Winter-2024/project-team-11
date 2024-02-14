package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

public class SuccessoratorApplication extends Application {
    private GoalRepository ongoingGoalRepository;
    private GoalRepository completedGoalRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        var ongoingDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        SuccessoratorDatabase.class,
                        "successorator-ongoing-database"
                )
                .allowMainThreadQueries()
                .build();

        var completedDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        SuccessoratorDatabase.class,
                        "successorator-completed-database"
                )
                .allowMainThreadQueries()
                .build();

        this.ongoingGoalRepository = new RoomGoalRepository(ongoingDatabase.goalDao());
        this.completedGoalRepository = new RoomGoalRepository(completedDatabase.goalDao());
    }

    public GoalRepository getOngoingGoalRepository() {
        return ongoingGoalRepository;
    }

    public GoalRepository getCompletedGoalRepository() {
        return completedGoalRepository;
    }
}
