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
        assertNotNull(board);
        assertEquals((Integer) 7, board.getColumns());
        assertEquals((Integer) 7, board.getRows());
        assertEquals((Integer) 49, board.getNumberOfHoles());

        //  check layout and startPosition
        Long layout = 0B0011100_0011100_1111111_1111111_1111111_0011100_0011100L;
        assertEquals(layout, board.getLayout());
        Long startPosition = 0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L;
        assertEquals(startPosition, board.getStartPosition());
    }

    @Test
    public void testCountMoves() {
        Solution solution = new Solution();

        // solution with 15 moves
        solution.add(0B0000000_0000000_0000000_0001000_0000000_0000000_0000000L);
        solution.add(0B0000000_0001000_0001000_0000000_0000000_0000000_0000000L);
        solution.add(0B0000000_0001000_0000110_0000000_0000000_0000000_0000000L);
        solution.add(0B0000000_0001000_0000100_0000010_0000010_0000000_0000000L);
        solution.add(0B0000000_0001000_0000100_0000010_0001100_0000000_0000000L);
        solution.add(0B0000000_0001000_0001100_0001010_0000100_0000000_0000000L);
        solution.add(0B0000000_0001000_0001100_0001010_0011000_0000000_0000000L);
        solution.add(0B0000000_0001000_0001100_0001010_1101000_0000000_0000000L);
        solution.add(0B0000000_0001000_1001100_1001010_0101000_0000000_0000000L);
        solution.add(0B0000000_0001000_0111100_1001010_0101000_0000000_0000000L);
        solution.add(0B0010000_0011000_0101100_1001010_0101000_0000000_0000000L);
        solution.add(0B0001100_0011000_0101100_1001010_0101000_0000000_0000000L);
        solution.add(0B0001100_0011000_0101011_1001010_0101000_0000000_0000000L);
        solution.add(0B0001100_0011000_0101010_1001011_0101001_0000000_0000000L);
        solution.add(0B0001100_0001000_0111010_1011011_0101001_0000000_0000000L);
        solution.add(0B0001100_0001000_0111010_1001011_0111001_0010000_0000000L);
        solution.add(0B0001100_0001000_0111010_1001011_0111001_0001100_0000000L);
        solution.add(0B0001100_0001000_0111010_1001111_0111101_0001000_0000000L);
        solution.add(0B0001100_0001100_0111110_1001011_0111101_0001000_0000000L);
        solution.add(0B0001100_0001100_0111110_1001011_0111001_0001100_0000100L);
        solution.add(0B0001100_0001100_0111110_1001011_0111001_0001100_0011000L);
        solution.add(0B0011100_0011100_0101110_1001011_0111001_0001100_0011000L);
        solution.add(0B0011100_0011100_0111110_1011011_0101001_0001100_0011000L);
        solution.add(0B0011100_0011100_0111110_1011111_0101101_0001000_0011000L);
        solution.add(0B0011100_0011100_0111110_1011111_0101001_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1011111_0100111_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1011111_0011111_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1011111_1101111_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1001111_1111111_0011100_0011100L);
        solution.add(0B0011100_0011100_0111110_1110111_1111111_0011100_0011100L);
        Integer numMoves = solution.countMoves(board);
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
