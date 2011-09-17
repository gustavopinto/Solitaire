package org.solitaire.board;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class BoardHelperTest {

    private Board board;

    @Before
    public void createBoard() {
        try {
            this.board = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
        } catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            fail("creation of english board should not throw an exception");
        }
    }

    @Test
    public void testPositionToString() {
        String positionString =
                "    * * *     \n" +
                "    * * *     \n" +
                "* * * * * * * \n" +
                "* * * o * * * \n" +
                "* * * * * * * \n" +
                "    * * *     \n" +
                "    * * *     \n";
        assertEquals(positionString, BoardHelper.toString(board, board.getStartPosition()));
    }

    @Test
    public void testNumberOfPins() {
        assertEquals(32,BoardHelper.getNumberOfPins(this.board.getStartPosition()));
        assertEquals(0,BoardHelper.getNumberOfPins(0L));

    }
}
