package org.solitaire.solver;

import org.junit.Before;
import org.junit.Test;
import org.solitaire.board.Board;
import org.solitaire.board.BoardFactory;
import org.solitaire.solver.strategy.BasicStrategy;
import org.solitaire.solver.strategy.LessThanMovesStrategy;
import org.solitaire.solver.strategy.Strategy;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class SolverTest {

    private Board board;

    @Before
    public void createBoard() {
        this.board = BoardFactory.createBoard("english");
    }

    @Test
    public void testSolverInstantiation() {
        Solver solver = new Solver(this.board);
        assertNotNull(solver);
        assertEquals(this.board, solver.getBoard());
    }

    @Test
    public void testBasicStrategy() {
        Solver solver = new Solver(this.board);
        Strategy basicStrategy = new BasicStrategy();
        solver.setStrategy(basicStrategy);
        assertEquals(basicStrategy, solver.getStrategy());
    }

    @Test
    public void testMinNumMovesStrategy() {
        Solver solver = new Solver(this.board);
        Strategy lessThanMovesStrategy = new LessThanMovesStrategy(28);
        solver.setStrategy(lessThanMovesStrategy);
        assertEquals(lessThanMovesStrategy, solver.getStrategy());
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
    public void testBasicStrategySolutionIsCorrect() {
        Solver solver = new Solver(this.board);
        solver.setStrategy(new BasicStrategy());
        Long startPosition = this.board.getStartPosition();
        Solution solution = solver.solve(startPosition);
        List<Long> solutionAsList = solution.getSolutionAsList();
        assertEquals(32, solutionAsList.size());
        assertEquals(startPosition, solutionAsList.get(0));
        for (int i = 0; i < 31; i++) {
            assertTrue(this.board.getConsecutivePositions(solutionAsList.get(i)).contains(solutionAsList.get(i + 1)));
        }
        assertEquals(0b0000000_0000000_0000000_0001000_0000000_0000000_0000000L, solution.getSolution()[0]);
    }

    @Test
    public void testLessThanMovesStrategySolutionIsCorrect() {
        Solver solver = new Solver(this.board);
        Strategy lessThanMovesStrategy = new LessThanMovesStrategy(26);
        solver.setStrategy(lessThanMovesStrategy);
        Long startPosition = this.board.getStartPosition();
        Solution solution = solver.solve(startPosition);
        List<Long> solutionAsList = solution.getSolutionAsList();
        assertEquals(32, solutionAsList.size());
        assertEquals(startPosition, solutionAsList.get(0));
        for (int i = 0; i < 31; i++) {
            assertTrue(this.board.getConsecutivePositions(solutionAsList.get(i)).contains(solutionAsList.get(i + 1)));
        }
        assertEquals(0b0000000_0000000_0000000_0001000_0000000_0000000_0000000L, solution.getSolution()[0]);
        assertEquals((Integer) 25, solution.countMoves(this.board));
    }
}
