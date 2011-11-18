package org.solitaire.solver.strategy;

import org.junit.Test;
import org.solitaire.board.Board;
import org.solitaire.board.BoardFactory;
import org.solitaire.solver.Solution;
import org.solitaire.solver.Solver;

import static org.junit.Assert.*;

/**
 * User: Tobias
 * Date: 26.09.2011
 */
public class BasicStrategyTest {

    @Test
    public void testBasicStrategyForQuadraticBoard() {
        Board quadraticBoard = BoardFactory.getInstance().createBoard("quadratic", 4);
        Strategy basicStrategy = new BasicStrategy();
        Solver solver = new Solver(quadraticBoard);
        solver.setStrategy(basicStrategy);
        Long startPosition = 0B1110_1011_1111_1111L;
        Solution solution = solver.solve(startPosition);
        assertNotNull(solution);
        // correct number of positions
        assertEquals(quadraticBoard.getNumberOfPegs(startPosition), solution.getPositions().size());

        // last position is a valid end position
        assertTrue(solution.isSolutionOn(quadraticBoard));

        // check if the solution consists of consecutive positions
        Long predecessor = startPosition;
        for (Long position : solution.getPositions()) {
            if (!predecessor.equals(position)) {
                assertTrue(quadraticBoard.getConsecutivePositions(predecessor).contains(position));
            }
            predecessor = position;
        }
    }

    @Test
    public void testBasicStrategyForEnglishBoard() {
        Board board = BoardFactory.getInstance().createBoard("english");
        Strategy basicStrategy = new BasicStrategy();
        Solver solver = new Solver(board);
        solver.setStrategy(basicStrategy);
        Long startPosition = board.getStartPosition();
        Solution solution = solver.solve(startPosition);
        assertNotNull(solution);
        // correct number of positions
        assertEquals(board.getNumberOfPegs(startPosition), solution.getPositions().size());

        // last position is a valid end position
        assertTrue(solution.isSolutionOn(board));

        // check if the solution consists of consecutive positions
        Long predecessor = startPosition;
        for (Long position : solution.getPositions()) {
            if (!predecessor.equals(position)) {
                assertTrue(board.getConsecutivePositions(predecessor).contains(position));
            }
            predecessor = position;
        }
    }
}
