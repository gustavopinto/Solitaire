package org.solitaire.board;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class BoardFactoryTest {

    @Test
    public void testUnknownBoard() {
        Board unknownBoard = BoardFactory.getInstance().createBoard("unknown");
        assertNull(unknownBoard);

        Board englishBoard = BoardFactory.getInstance().createBoard("english");
        assertNotNull(englishBoard);
    }
}
