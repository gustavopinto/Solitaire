package org.solitaire.board;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class BoardFactoryTest {

    @Test
    public void testUnknownBoard() {
        Board unknownBoard = null;
        try {
            unknownBoard = BoardFactory.createBoard("org.solitaire.board.unknownBoard");
            fail("creating an unknown board should throw an exception");
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            assertNull(unknownBoard);
        }

        Board englishBoard;
        try {
            englishBoard = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
            assertNotNull(englishBoard);
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            fail("creating a board for which a class does exist should not throw an exception");
        }
    }
}
