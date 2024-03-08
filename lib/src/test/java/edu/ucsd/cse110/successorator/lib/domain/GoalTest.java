package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoalTest {

    @Test
    public void getters() {
        var goal = new Goal(4, "shopping", "ERRAND",12, false);
        assertEquals(Integer.valueOf(4), goal.id());
        assertEquals("shopping", "ERRAND", goal.text());
        assertEquals(12, goal.sortOrder());
    }

    @Test
    public void withId() {
        var goal = new Goal(4, "shopping", "ERRAND",12, false);
        var expected = new Goal(23, "shopping", "ERRAND",12, false);
        var actual = goal.withId(23);
        assertEquals(expected, actual);
    }

    @Test
    public void withIsCompleted() {
        var goal = new Goal(4, "shopping", "ERRAND",12, false);
        var expected = new Goal(4, "shopping", "ERRAND", 12, true);
        var actual = goal.withIsCompleted(true);
        assertEquals(expected, actual);
    }

    @Test
    public void withSortOrder() {
        var goal = new Goal(4, "shopping", "ERRAND", 12, false);
        var expected = new Goal(4, "shopping", "ERRAND", 325, false);
        var actual = goal.withSortOrder(325);
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() {
        var goal1 = new Goal(4, "shopping", "ERRAND",12, false);
        var goal2 = new Goal(4, "shopping", "ERRAND", 12, false);
        var goal3 = new Goal(4, "shopping", "ERRAND", 12, true);

        assertEquals(goal1, goal2);
        assertNotEquals(goal1, goal3);
    }
}