package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public static void assertGoalListEquals(List<Goal> expected, List<Goal> actual) {
        assertEquals(Set.copyOf(expected), Set.copyOf(actual));
    }

    @Test
    public void find() {
        for (int i = 0; i < 100; i++) {
            int index = (int)(Math.random()*goalList.size());
            var expected = goalList.get(index);
            var actual = repo.find(index).getValue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void findAll() {
        var expected = goalList;
        var actual = repo.findAll().getValue();
        assertNotSame(expected, actual);
        assertGoalListEquals(expected, actual);
    }

    @Test
    public void save() {
        goalList.add(new Goal(5, "sleep", 5, false));
        repo.save(new Goal(5, "sleep", 5, false));

        var expected = goalList;
        var actual = repo.findAll().getValue();
        assertGoalListEquals(expected, actual);
    }

    @Test
    public void saveMany() {
        var append = List.of(
                new Goal(5, "sleep", 5, false),
                new Goal(6, "drink water", 6, false)
        );
        goalList.addAll(append);
        repo.save(List.copyOf(append));

        var expected = goalList;
        var actual = repo.findAll().getValue();
        assertGoalListEquals(expected, actual);
    }

    @Test
    public void remove() {
        var expected = new ArrayList<>(List.of(
                new Goal(0, "shopping", 0, false),
                new Goal(2, "study", 1, false),
                new Goal(3, "laundry", 2, false),
                new Goal(4, "haircut", 3, false)
        ));
        repo.remove(1);
        var actual = repo.findAll().getValue();
        assertGoalListEquals(expected, actual);

        expected = new ArrayList<>(List.of(
                new Goal(2, "study", 0, false),
                new Goal(3, "laundry", 1, false),
                new Goal(4, "haircut", 2, false)
        ));
        repo.remove(0);
        actual = repo.findAll().getValue();
        assertGoalListEquals(expected, actual);

        expected = new ArrayList<>(List.of(
                new Goal(2, "study", 0, false),
                new Goal(3, "laundry", 1, false)
        ));
        repo.remove(4);
        actual = repo.findAll().getValue();
        assertGoalListEquals(expected, actual);
    }

    @Test
    public void append() {
        goalList.add(new Goal(5, "sleep", 5, false));
        repo.append(new Goal(5, "sleep", 10, false));
        assertGoalListEquals(goalList, Objects.requireNonNull(repo.findAll().getValue()));
    }

    @Test
    public void prepend() {
        goalList = new ArrayList<>(List.of(
                new Goal(0, "shopping", 1, false),
                new Goal(1, "homework", 2, false),
                new Goal(2, "study", 3, false),
                new Goal(3, "laundry", 4, false),
                new Goal(4, "haircut", 5, false),
                new Goal(5, "sleep", 0, false)
        ));
        repo.prepend(new Goal(5, "sleep", 10, false));
        assertGoalListEquals(goalList, Objects.requireNonNull(repo.findAll().getValue()));
    }
}