package org.solitaire.board;

import org.junit.Test;

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
}
