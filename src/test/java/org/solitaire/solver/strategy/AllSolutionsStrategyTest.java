package org.solitaire.solver.strategy;

import org.junit.Before;
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
public class AllSolutionsStrategyTest {

    private Board board;
    private AllSolutionsStrategy allSolutionsStrategy;
    private Solver solver;

    @Before
    public void init() {
        this.board = BoardFactory.getInstance().createBoard("quadratic",4);
        this.allSolutionsStrategy = new AllSolutionsStrategy();
        this.solver = new Solver(board);
    }

    @Test
    public void testAllSolutionsStrategyRun() {
        Board englishBoard = BoardFactory.getInstance().createBoard("english");
        Strategy allSolutionsStrategy = new AllSolutionsStrategy();
        Solver solver = new Solver(englishBoard);
        solver.setStrategy(allSolutionsStrategy);
        solver.solve(englishBoard.getStartPosition());
        fail("not fully implemented yet");
    }

    @Test
    public void testAllSolutionsStrategyOnFourTimesFourBoardForStartPositionWithoutSolution() {
        solver.setStrategy(allSolutionsStrategy);
        Long startPosition = board.getStartPosition();
        System.out.println(board.toString(startPosition));
        Solution solution = solver.solve(startPosition);
        //there is no solution
        assertNull(solution);
    }


    @Test
    public void testAllSolutionsStrategyOnFourTimesFourBoardForStartPositionWithThreeMoveSolution() {
        Board board = BoardFactory.getInstance().createBoard("quadratic",4);
        Strategy allSolutionsStrategy = new AllSolutionsStrategy();
        Solver solver = new Solver(board);
        solver.setStrategy(allSolutionsStrategy);
        // a start position with four solutions and three moves
        Long startPosition = 0B0000_0000_1110_1101L;
        Solution solution = solver.solve(startPosition);
        assertEquals((Integer) 3, solution.countMoves(board));
    }

    @Test
    public void testAllSolutionsStrategyOnFourTimesFourBoardForStartPositionWithOneMoveSolution() {
        Board board = BoardFactory.getInstance().createBoard("quadratic",4);
        Strategy allSolutionsStrategy = new AllSolutionsStrategy();
        Solver solver = new Solver(board);
        solver.setStrategy(allSolutionsStrategy);
        // a start position with four solutions and one move
        Long startPosition = 0B0000_0010_0101_0011L;
        Solution solution = solver.solve(startPosition);
        assertEquals((Integer) 1, solution.countMoves(board));
    }

    @Test
    public void testAllSolutionsStrategyOnFourTimesFourBoardForStartPositionWithEightMoveSolution() {
        Board board = BoardFactory.getInstance().createBoard("quadratic",4);
        Strategy allSolutionsStrategy = new AllSolutionsStrategy();
        Solver solver = new Solver(board);
        solver.setStrategy(allSolutionsStrategy);

        // a start position with many solutions and eight moves
        Long startPosition = 0B1110_1011_1111_1111L;
        Solution solution = solver.solve(startPosition);
        assertEquals((Integer) 8, solution.countMoves(board));
    }


    @Test
    public void testAllSolutionsStrategyOnSixTimesSixBoard() {
        Board board = BoardFactory.getInstance().createBoard("quadratic",6);
        Strategy allSolutionsStrategy = new AllSolutionsStrategy();
        Solver solver = new Solver(board);
        solver.setStrategy(allSolutionsStrategy);
        Long startPosition = 0B1110_1011_1111_1111L;
        Solution solution = solver.solve(startPosition);
        assertEquals((Integer) 8, solution.countMoves(board));
    }

    @Test
    public void testAllSolutionsStrategyOnFiveTimesFiveBoard() {
        Board board = BoardFactory.getInstance().createBoard("quadratic",5);
        Strategy allSolutionsStrategy = new AllSolutionsStrategy();
        Solver solver = new Solver(board);
        solver.setStrategy(allSolutionsStrategy);
        Long startPosition = 0B10101_10111_11010_11111_11111L;
        Solution solution = solver.solve(startPosition);
        assertNotNull(solution);
        assertEquals((Integer) 8, solution.countMoves(board));

    }
}
