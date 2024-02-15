package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

@Dao
public interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(GoalEntity goal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<GoalEntity> goals);

    @Query("SELECT * FROM goals WHERE id = :id")
    GoalEntity find(int id);

    @Query("SELECT * FROM goals ORDER BY sort_order")
    List<GoalEntity> findAll();

    @Query("SELECT * FROM goals WHERE is_completed = :isCompleted ORDER BY sort_order")
    List<GoalEntity> findByCompleteness(boolean isCompleted);

    @Query("SELECT * FROM goals WHERE id = :id")
    LiveData<GoalEntity> findAsLiveData(int id);

    @Query("SELECT * FROM goals ORDER BY sort_order")
    LiveData<List<GoalEntity>> findAllAsLiveData();

    @Query("SELECT * FROM goals WHERE is_completed = :isCompleted ORDER BY sort_order")
    LiveData<List<GoalEntity>> findByCompletenessAsLiveData(boolean isCompleted);

    @Query("SELECT MIN(sort_order) FROM goals WHERE is_completed = :isCompleted")
    int getMinSortOrder(boolean isCompleted);

    @Query("SELECT MAX(sort_order) FROM goals WHERE is_completed = :isCompleted")
    int getMaxSortOrder(boolean isCompleted);
    // will be useful for uncomplete a goal us
    @Query("UPDATE goals SET sort_order = sort_order + :by "
            + "WHERE sort_order >= :from AND sort_order <= :to")
    void shiftSortOrders(int from, int to, int by);

    // depending on goal completion status, add to the end of respective list
    @Transaction
    default int append(GoalEntity goal) {
        var maxSortOrder = getMaxSortOrder(goal.isCompleted);
        var newGoal = new GoalEntity(
                goal.text, maxSortOrder + 1, goal.isCompleted
        );
        return Math.toIntExact(insert(newGoal));
    }

    // unimplemented as of right now
    @Transaction
    default int prepend(GoalEntity goal) {
        return goal.id;
    }
    @Query("DELETE FROM goals WHERE id = :id")
    void delete(int id);
}
