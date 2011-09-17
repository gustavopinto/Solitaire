package org.solitaire.solver;

import org.junit.Test;
import org.solitaire.board.Board;
import org.solitaire.board.BoardFactory;
import org.solitaire.solver.strategy.BasicStrategy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class SolverTest {

    @Test
    public void testSolver() {
        Board englishBoard = null;
        try {
            englishBoard = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            fail("creating a board for which a class does exist should not throw an exception");
        }
        Solver solver = new Solver(englishBoard);
        assertNotNull(solver);
        solver.setStrategy(new BasicStrategy());
        Solution solution = solver.solve(englishBoard.getStartPosition());
        assertNotNull(solution);

    }
}
