package org.solitaire.solver;

import org.junit.Before;
import org.junit.Test;
import org.solitaire.board.Board;
import org.solitaire.board.BoardFactory;
import org.solitaire.solver.strategy.BasicStrategy;
import org.solitaire.solver.strategy.Strategy;

import static org.junit.Assert.*;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class SolverTest {

    private Board board;

    @Before
    public void createBoard() {
        try {
            this.board = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            fail("creating a board for which a class does exist should not throw an exception");
        }
    }

    @Test
    public void testSolverInstantiation() {
        Solver solver = new Solver(this.board);
        assertNotNull(solver);
        assertEquals(this.board, solver.getBoard());
    }

    @Test
    public void testSolverStrategy() {
        Solver solver = new Solver(this.board);
        Strategy basicStrategy = new BasicStrategy();
        solver.setStrategy(basicStrategy);
        assertEquals(basicStrategy, solver.getStrategy());
    }

    @Test
    public void testSolutionIsNotNull() {
        Solver solver = new Solver(this.board);
        solver.setStrategy(new BasicStrategy());

        Solution solution = null;
        try {
            solution = solver.solve(board.getStartPosition());
        } catch (NullPointerException e) {
            fail("solving should not throw a NullPointerException");
        }
        assertNotNull(solution);
    }

    @Test
    public void testSolutionIsCorrect() {
        Solver solver = new Solver(this.board);
        solver.setStrategy(new BasicStrategy());
        Long startPosition = board.getStartPosition();
        Solution solution = solver.solve(startPosition);
        assertEquals(32, solution.getSolutionAsList().size());
        for(Long position : solution.getSolutionAsList()) {
            assertNotSame(0L, position);
        }
        assertEquals(0b0000000_0000000_0000000_0001000_0000000_0000000_0000000L, solution.getSolution()[0]);
    }
}
