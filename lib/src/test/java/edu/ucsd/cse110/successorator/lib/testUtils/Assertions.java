package edu.ucsd.cse110.successorator.lib.testUtils;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class Assertions {
    public static void assertGoalListEquals(List<Goal> expected, List<Goal> actual) {
        var sortedExpected = expected.stream()
                .sorted((a, b) -> a.hashCode() - b.hashCode())
                .collect(Collectors.toList());

        var sortedActual = actual.stream()
                .sorted((a, b) -> a.hashCode() - b.hashCode())
                .collect(Collectors.toList());

        assertEquals(sortedExpected, sortedActual);
    }
}
