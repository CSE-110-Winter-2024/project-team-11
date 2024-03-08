package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.goals.RoomGoalRepository;
import edu.ucsd.cse110.successorator.data.db.goals.GoalDatabase;
import edu.ucsd.cse110.successorator.data.db.time.RoomTimeManager;
import edu.ucsd.cse110.successorator.data.db.time.TimeDatabase;
import edu.ucsd.cse110.successorator.lib.domain.goal.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.TimeManager;

public class SuccessoratorApplication extends Application {
    private GoalRepository ongoingHomeGoalRepository;
    private GoalRepository ongoingWorkGoalRepository;
    private GoalRepository ongoingSchoolGoalRepository;
    private GoalRepository ongoingErrandGoalRepository;
    private GoalRepository completedGoalRepository;

    private TimeManager timeManager;

    @Override
    public void onCreate() {
        super.onCreate();

        var ongoingHomeDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        GoalDatabase.class,
                        "successorator-ongoing-home-database"
                )
                .allowMainThreadQueries()
                .build();

        var ongoingWorkDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        GoalDatabase.class,
                        "successorator-ongoing-work-database"
                )
                .allowMainThreadQueries()
                .build();

        var ongoingSchoolDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        GoalDatabase.class,
                        "successorator-ongoing-school-database"
                )
                .allowMainThreadQueries()
                .build();

        var ongoingErrandDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        GoalDatabase.class,
                        "successorator-ongoing-errand-database"
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

        this.ongoingHomeGoalRepository = new RoomGoalRepository(ongoingHomeDatabase.goalDao());
        this.ongoingWorkGoalRepository = new RoomGoalRepository(ongoingWorkDatabase.goalDao());
        this.ongoingSchoolGoalRepository = new RoomGoalRepository(ongoingSchoolDatabase.goalDao());
        this.ongoingErrandGoalRepository = new RoomGoalRepository(ongoingErrandDatabase.goalDao());
        this.completedGoalRepository = new RoomGoalRepository(completedDatabase.goalDao());
        this.timeManager = new RoomTimeManager(timeDatabase.timeDao());
    }

    public GoalRepository getOngoingHomeGoalRepository() {
        return ongoingHomeGoalRepository;
    }

    public GoalRepository getOngoingWorkGoalRepository() {
        return ongoingWorkGoalRepository;
    }

    public GoalRepository getOngoingSchoolGoalRepository() {
        return ongoingSchoolGoalRepository;
    }

    public GoalRepository getOngoingErrandGoalRepository() {
        return ongoingErrandGoalRepository;
    }

    public GoalRepository getCompletedGoalRepository() {
        return completedGoalRepository;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }
}
