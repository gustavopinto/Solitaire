package org.solitaire.board;

import org.junit.Before;
import org.junit.Test;
import org.solitaire.solver.Solution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class EnglishBoardTest {

    private Board board;

    @Before
    public void createBoard() {
        this.board = BoardFactory.getInstance().createBoard("english");
    }

    @Test
    public void testEnglishBoardAttributes() {
        Board englishBoard = BoardFactory.getInstance().createBoard("english");
        assertNotNull(englishBoard);
        assertEquals((Integer) 7, englishBoard.getColumns());
        assertEquals((Integer) 7, englishBoard.getRows());
        assertEquals((Integer) 49, englishBoard.getNumberOfHoles());

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

        // solution with 15 moves
        solution[0] = 0B0000000_0000000_0000000_0001000_0000000_0000000_0000000L;
        solution[1] = 0B0000000_0001000_0001000_0000000_0000000_0000000_0000000L;
        solution[2] = 0B0000000_0001000_0000110_0000000_0000000_0000000_0000000L;
        solution[3] = 0B0000000_0001000_0000100_0000010_0000010_0000000_0000000L;
        solution[4] = 0B0000000_0001000_0000100_0000010_0001100_0000000_0000000L;
        solution[5] = 0B0000000_0001000_0001100_0001010_0000100_0000000_0000000L;
        solution[6] = 0B0000000_0001000_0001100_0001010_0011000_0000000_0000000L;
        solution[7] = 0B0000000_0001000_0001100_0001010_1101000_0000000_0000000L;
        solution[8] = 0B0000000_0001000_1001100_1001010_0101000_0000000_0000000L;
        solution[9] = 0B0000000_0001000_0111100_1001010_0101000_0000000_0000000L;
        solution[10] = 0B0010000_0011000_0101100_1001010_0101000_0000000_0000000L;
        solution[11] = 0B0001100_0011000_0101100_1001010_0101000_0000000_0000000L;
        solution[12] = 0B0001100_0011000_0101011_1001010_0101000_0000000_0000000L;
        solution[13] = 0B0001100_0011000_0101010_1001011_0101001_0000000_0000000L;
        solution[14] = 0B0001100_0001000_0111010_1011011_0101001_0000000_0000000L;
        solution[15] = 0B0001100_0001000_0111010_1001011_0111001_0010000_0000000L;
        solution[16] = 0B0001100_0001000_0111010_1001011_0111001_0001100_0000000L;
        solution[17] = 0B0001100_0001000_0111010_1001111_0111101_0001000_0000000L;
        solution[18] = 0B0001100_0001100_0111110_1001011_0111101_0001000_0000000L;
        solution[19] = 0B0001100_0001100_0111110_1001011_0111001_0001100_0000100L;
        solution[20] = 0B0001100_0001100_0111110_1001011_0111001_0001100_0011000L;
        solution[21] = 0B0011100_0011100_0101110_1001011_0111001_0001100_0011000L;
        solution[22] = 0B0011100_0011100_0111110_1011011_0101001_0001100_0011000L;
        solution[23] = 0B0011100_0011100_0111110_1011111_0101101_0001000_0011000L;
        solution[24] = 0B0011100_0011100_0111110_1011111_0101001_0001100_0011100L;
        solution[25] = 0B0011100_0011100_0111110_1011111_0100111_0001100_0011100L;
        solution[26] = 0B0011100_0011100_0111110_1011111_0011111_0001100_0011100L;
        solution[27] = 0B0011100_0011100_0111110_1011111_1101111_0001100_0011100L;
        solution[28] = 0B0011100_0011100_0111110_1001111_1111111_0011100_0011100L;
        solution[29] = 0B0011100_0011100_0111110_1110111_1111111_0011100_0011100L;
        solutionObject.setSolution(solution);
        Board englishBoard = BoardFactory.getInstance().createBoard("english");
        Integer numMoves = solutionObject.countMoves(englishBoard);
        assertEquals((Integer) 15, numMoves);
    }

    @Test
    public void testPositionToString() {
        String positionString =
                "    O O O     \n" +
                        "    O O O     \n" +
                        "O O O O O O O \n" +
                        "O O O * O O O \n" +
                        "O O O O O O O \n" +
                        "    O O O     \n" +
                        "    O O O     \n";
        assertEquals(positionString, board.toString(board.getStartPosition()));
    }

    @Test
    public void testNumberOfPins() {
        assertEquals(32, this.board.getNumberOfPegs(this.board.getStartPosition()));
        assertEquals(0, this.board.getNumberOfPegs(0L));
    }

}
