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
        Board board = BoardFactory.getInstance().createBoard("unknown");
        assertNull(board);
    }

    @Test
    public void testEnglishBoardCreation() {
        Board board = BoardFactory.getInstance().createBoard("english");
        assertNotNull(board);
    }

    @Test
    public void testQuadraticBoardCreation() {
        assertNull(BoardFactory.getInstance().createBoard("quadratic", 2));
        Board board = BoardFactory.getInstance().createBoard("quadratic", 6);
        assertNotNull(board);
    }
}
