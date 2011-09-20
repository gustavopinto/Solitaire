package org.solitaire.board;

import org.junit.Test;
import org.solitaire.solver.Solution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class EnglishBoardTest {

    @Test
    public void testEnglishBoardAttributes() {
        Board englishBoard = null;
        try {
            englishBoard = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            fail("creating an instance of EnglishBoard via factory should not throw an exception");
        }
        assertNotNull(englishBoard);
        assertEquals((Integer) 7, englishBoard.getColumns());
        assertEquals((Integer) 7, englishBoard.getRows());
        assertEquals((Integer) 49, englishBoard.getNumberOfFields());

        //  check layout and startPosition
        Long layout = 0B0011100_0011100_1111111_1111111_1111111_0011100_0011100L;
        assertEquals(layout, englishBoard.getLayout());
        Long startPosition = 0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L;
        assertEquals(startPosition, englishBoard.getStartPosition());
    }

    @Test
    public void testCountMoves() {
        Solution solutionObject = new Solution(1);
        long[] solution = new long[30];
        solution[0] = 1L << 24;
        solution[1] = 1L << 31 | 1L << 38;
        solution[2] = 1L << 29 | 1L << 30 | 1L << 38;
        solution[3] = 1L << 15 |1L << 22 | 1L << 30 | 1L << 38;
        solution[4] = 1L << 16 | 1L << 17 |1L << 22 | 1L << 30 | 1L << 38;
        solution[5] = 1L << 16 | 1L << 24 |1L << 31 |1L << 22 | 1L << 30 | 1L << 38;
        solution[6] = 1L << 17 |1L << 18 | 1L << 24 |1L << 31 |1L << 22 | 1L << 30 | 1L << 38;
        solution[7] = 1L << 17 |1L << 19 | 1L << 20 |1L << 24 |1L << 31 |1L << 22 | 1L << 30 | 1L << 38;
        solution[8] = 1L << 17 |1L << 19 | 1L << 27 |1L << 34 |1L << 24 |1L << 31 |1L << 22 | 1L << 30 | 1L << 38;
        solution[9] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32 |1L << 24 |1L << 31 |1L << 22 | 1L << 30 | 1L << 38;
        solution[10] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39 |1L << 46|1L << 24 |1L << 31 |1L << 22 | 1L << 30 | 1L << 38;
        solution[11] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39 |1L << 45|1L << 44|1L << 24 |1L << 31 |1L << 22 | 1L << 30 | 1L << 38;
        solution[12] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39 |1L << 45|1L << 44|1L << 24 |1L << 31 |1L << 22 | 1L << 29| 1L << 28 | 1L << 38;
        solution[13] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39 |1L << 45|1L << 44|1L << 24 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[14] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32|1L << 25 |1L << 45|1L << 44|1L << 24 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[15] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 11 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[16] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 10|1L << 9 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[17] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 10|1L << 16|1L << 23 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[18] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 10|1L << 16|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[19] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 10|1L << 9|1L << 2|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[20] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 32|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 10|1L << 9|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[21] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 10|1L << 9|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[22] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 25|1L << 32|1L << 10|1L << 9|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[23] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 25|1L << 32|1L << 10|1L << 16|1L << 23|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[24] = 1L << 17 |1L << 19 | 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 25|1L << 32|1L << 10|1L << 9|1L << 2|1L << 23|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[25] = 1L << 15|1L << 16|1L << 19 | 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 25|1L << 32|1L << 10|1L << 9|1L << 2|1L << 23|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[26] = 1L << 15|1L << 16|1L << 17|1L << 18| 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 25|1L << 32|1L << 10|1L << 9|1L << 2|1L << 23|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[27] = 1L << 15|1L << 16|1L << 17|1L << 20|1L << 19| 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 25|1L << 32|1L << 10|1L << 9|1L << 2|1L << 23|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[28] = 1L << 15|1L << 16|1L << 17|1L << 20|1L << 19| 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 24 |1L << 45|1L << 44|1L << 18|1L << 11|1L << 32|1L << 10|1L << 9|1L << 2|1L << 23|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solution[29] = 1L << 15|1L << 16|1L << 17|1L << 20|1L << 19| 1L << 27 |1L << 33 |1L << 39|1L << 46|1L << 25|1L << 26|1L << 45|1L << 44|1L << 18|1L << 11|1L << 32|1L << 10|1L << 9|1L << 2|1L << 23|1L << 3|1L << 4|1L << 30|1L << 37 |1L << 31 |1L << 22 | 1L << 29| 1L << 21| 1L << 14 | 1L << 38;
        solutionObject.setSolution(solution);
        Board englishBoard = null;
        try {
            englishBoard = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            fail("creating an instance of EnglishBoard via factory should not throw an exception");
        }
        Integer numMoves = BoardHelper.countMoves(englishBoard, solutionObject);
        assertEquals((Integer)15, numMoves);
    }
}
