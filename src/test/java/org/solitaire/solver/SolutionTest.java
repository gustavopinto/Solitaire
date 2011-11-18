package org.solitaire.solver;

import org.junit.Before;
import org.junit.Test;

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
        assertEquals(32, solution.getPositions().size());
        for (Long position : solution.getPositions()) {
            assertEquals((Long) 0L, position);
        }
    }
}
