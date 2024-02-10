package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class Goal implements Serializable {
    private final @Nullable Integer id;
    private final @NonNull String text;

    private boolean isCompleted;

    private int sortOrder;

    public Goal(@Nullable Integer id, @NonNull String text, int sortOrder, boolean isCompleted) {
        this.id = id;
        this.text = text;
        this.sortOrder = sortOrder;
        this.isCompleted = isCompleted;
    }

    public @Nullable Integer id() {
        return id;
    }

    public @NonNull String text() {
        return text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public int sortOrder() {
        return sortOrder;
    }

    public Goal withId(int id) {
        return new Goal(id, this.text, this.sortOrder, this.isCompleted);
    }

    public Goal withSortOrder(int sortOrder) {
        return new Goal(this.id, this.text, sortOrder, this.isCompleted);
    }

    public Goal withIsCompleted(boolean isCompleted) {
        return new Goal(this.id, this.text, this.sortOrder, isCompleted);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return isCompleted == goal.isCompleted && sortOrder == goal.sortOrder && Objects.equals(id, goal.id) && Objects.equals(text, goal.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, sortOrder, isCompleted);
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isCompleted=" + isCompleted +
                ", sortOrder=" + sortOrder +
                '}';
    }
}

