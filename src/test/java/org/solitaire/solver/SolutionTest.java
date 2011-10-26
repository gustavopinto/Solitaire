package org.solitaire.solver;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class SolutionTest {

    private Solution solution;

    @Before
    public void createSolution() {
        this.solution = new Solution(32);
    }

    @Test
    public void testSolutionInstantiation() {
        assertNotNull(solution);
        assertEquals(32, solution.getSolution().length);
        for (int i = 0; i < 32; i++) {
            assertEquals(0, solution.getSolution()[i]);
        }
    }

    @Test
    public void testSolutionAsList() {
        List<Long> solutionAsList = solution.getSolutionAsList();
        assertEquals(32, solutionAsList.size());
        for (Long position : solutionAsList) {
            assertEquals((Long) 0L, position);
        }
    }
}
