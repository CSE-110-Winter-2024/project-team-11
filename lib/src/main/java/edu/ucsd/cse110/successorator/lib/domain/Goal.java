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

    public Goal(@Nullable Integer id, @NonNull String text, int sortOrder) {
        this.id = id;
        this.text = text;
        this.sortOrder = sortOrder;
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
        return new Goal(id, this.text, this.sortOrder);
    }

    public Goal withSortOrder(int sortOrder) {
        return new Goal(this.id, this.text, sortOrder);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal flashcard = (Goal) o;
        return sortOrder == flashcard.sortOrder && Objects.equals(id, flashcard.id) && Objects.equals(text, flashcard.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, sortOrder);
    }
}

