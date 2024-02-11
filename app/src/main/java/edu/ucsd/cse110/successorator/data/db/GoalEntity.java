package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.Goal;


@Entity(tableName = "goals")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    @ColumnInfo(name = "is_completed")
    public boolean isCompleted;

    GoalEntity(@NonNull String text, int sortOrder, boolean isCompleted) {
        this.text = text;
        this.sortOrder = sortOrder;
        this.isCompleted = isCompleted;
    }

    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var card = new GoalEntity(goal.text(), goal.sortOrder(), goal.isCompleted());
        card.id = goal.id();
        return card;
    }

    public @NonNull Goal toGoal() {
        return new Goal(id, text, sortOrder, isCompleted);
    }
}
