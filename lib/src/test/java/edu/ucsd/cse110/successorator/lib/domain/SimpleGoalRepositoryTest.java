package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import static edu.ucsd.cse110.successorator.lib.testUtils.Assertions.assertGoalListEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;

public class SimpleGoalRepositoryTest {

    InMemoryDataSource src;
    SimpleGoalRepository repo;
    List<Goal> goalList;

    @Before
    public void setup() {
        goalList = new ArrayList<>(List.of(
                new Goal(0, "shopping", 0, false),
                new Goal(1, "homework", 1, false),
                new Goal(2, "study", 2, false),
                new Goal(3, "laundry", 3, false),
                new Goal(4, "haircut", 4, false)
        ));

        src = new InMemoryDataSource();
        src.putGoals(List.copyOf(goalList));

        repo = new SimpleGoalRepository(src);
    }

    @Test
    public void append() {
        goalList.add(new Goal(5, "sleep", 5, false));
        repo.append(new Goal(5, "sleep", 10, false));
        assertGoalListEquals(goalList, Objects.requireNonNull(repo.findAll().getValue()));
    }

    // prepend not yet implemented
//    @Test
//    public void prepend() {
//        goalList = new ArrayList<>(List.of(
//                new Goal(0, "shopping", 1, false),
//                new Goal(1, "homework", 2, false),
//                new Goal(2, "study", 3, false),
//                new Goal(3, "laundry", 4, false),
//                new Goal(4, "haircut", 5, false),
//                new Goal(5, "sleep", 0, false)
//        ));
//        repo.prepend(new Goal(5, "sleep", 10, false));
//        assertGoalListEquals(goalList, Objects.requireNonNull(repo.findAll().getValue()));
//    }

    @Test
    public void clear() {
        goalList = new ArrayList<>();
        repo.clear();
        assertGoalListEquals(goalList, Objects.requireNonNull(repo.findAll().getValue()));
    }
}