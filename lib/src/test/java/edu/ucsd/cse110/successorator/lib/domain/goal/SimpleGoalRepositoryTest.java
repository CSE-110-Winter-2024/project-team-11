package edu.ucsd.cse110.successorator.lib.domain.goal;

import static edu.ucsd.cse110.successorator.lib.testUtils.Assertions.assertGoalListEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.data.GoalInMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.goal.Goal;
import edu.ucsd.cse110.successorator.lib.domain.goal.SimpleGoalRepository;

public class SimpleGoalRepositoryTest {

    GoalInMemoryDataSource src;
    SimpleGoalRepository repo;
    List<Goal> goalList;

    @Before
    public void setup() {
        goalList = new ArrayList<>(List.of(
                new Goal(0, "shopping", "ERRAND",0, false),
                new Goal(1, "homework", "SCHOOL",1, false),
                new Goal(2, "study", "SCHOOL",2, false),
                new Goal(3, "laundry", "HOME",3, false),
                new Goal(4, "haircut", "ERRAND",4, false)
        ));

        src = new GoalInMemoryDataSource();
        src.putGoals(List.copyOf(goalList));

        repo = new SimpleGoalRepository(src);
    }

    @Test
    public void append() {
        goalList.add(new Goal(5, "sleep", "HOME",5, false));
        repo.append(new Goal(5, "sleep", "HOME",10, false));
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

    @Test
    public void clear() {
        goalList = new ArrayList<>();
        repo.clear();
        assertGoalListEquals(goalList, Objects.requireNonNull(repo.findAll().getValue()));
    }
}