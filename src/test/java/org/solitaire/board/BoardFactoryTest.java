package org.solitaire.board;

import org.junit.Test;

import static org.junit.Assert.assertNull;

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
        } catch( ClassNotFoundException e) {
            assertNull(unknownBoard);
        }
    }
}
