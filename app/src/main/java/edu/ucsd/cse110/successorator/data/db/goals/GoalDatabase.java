package edu.ucsd.cse110.successorator.data.db.goals;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.ucsd.cse110.successorator.data.db.goals.GoalDao;
import edu.ucsd.cse110.successorator.data.db.goals.GoalEntity;

@Database(entities = {GoalEntity.class}, version = 1)
public abstract class GoalDatabase extends RoomDatabase {
    public abstract GoalDao goalDao();
}
