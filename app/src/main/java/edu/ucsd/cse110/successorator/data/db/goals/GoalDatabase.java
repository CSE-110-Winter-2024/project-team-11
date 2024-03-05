package edu.ucsd.cse110.successorator.data.db.goals;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.ucsd.cse110.successorator.data.db.goals.GoalDao;
import edu.ucsd.cse110.successorator.data.db.goals.GoalEntity;

@Database(entities = {GoalEntity.class}, version = 2)
public abstract class GoalDatabase extends RoomDatabase {

    public abstract GoalDao goalDao();

    private static volatile GoalDatabase INSTANCE;

    public static GoalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GoalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GoalDatabase.class, "goals")
                            .addMigrations(MIGRATION_1_2) // Add migration from version 1 to version 2
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Define your migration from version 1 to version 2
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Add the new column 'context' to the 'goals' table
            database.execSQL("ALTER TABLE goals ADD COLUMN context TEXT");

            // Perform any additional migration tasks if needed
        }
    };

}

