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

    private Board englishBoard;

    @Before
    public void createBoard() {
        try {
            this.englishBoard = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            fail("creating a board for which a class does exist should not throw an exception");
        }
    }

    @Test
    public void testSolverInstantiation() {
        Solver solver = new Solver(this.englishBoard);
        assertNotNull(solver);
        assertEquals(this.englishBoard, solver.getBoard());
    }

    @Test
    public void testSolverStrategy() {
        Solver solver = new Solver(this.englishBoard);
        Strategy basicStrategy = new BasicStrategy();
        solver.setStrategy(basicStrategy);
        assertEquals(basicStrategy, solver.getStrategy());
    }

    @Test
    public void testSolutionIsNotNull() {
        Solver solver = new Solver(this.englishBoard);
        solver.setStrategy(new BasicStrategy());

        Solution solution = solver.solve(englishBoard.getStartPosition());
        assertNotNull(solution);
    }

    @Test
    public void testSolutionIsCorrect() {
        Solver solver = new Solver(this.englishBoard);
        solver.setStrategy(new BasicStrategy());
        Long startPosition = englishBoard.getStartPosition();
        Solution solution = solver.solve(startPosition);

        //assertEquals(BoardHelper.getNumberOfPins(startPosition), solution.getSolution().size());

    }
}
