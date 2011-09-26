package org.solitaire.solver.strategy;

import org.junit.Test;
import org.solitaire.board.Board;
import org.solitaire.board.BoardFactory;
import org.solitaire.solver.Solver;

import static org.junit.Assert.fail;

/**
 * User: Tobias
 * Date: 26.09.2011
 */
public class AllSolutionsStrategyTest {

    @Test
    public void testAllSolutionsStrategyRun()
    {
        Board englishBoard = BoardFactory.createBoard("english");
        Strategy allSolutionsStrategy = new AllSolutionsStrategy();
        Solver solver = new Solver(englishBoard);
        solver.setStrategy(allSolutionsStrategy);
        solver.solve(englishBoard.getStartPosition());
        fail("not fully implemented yet");
    }
}
