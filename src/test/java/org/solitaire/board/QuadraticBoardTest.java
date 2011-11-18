package org.solitaire.board;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class QuadraticBoardTest {

    private Board board;
    private Integer size = 4;

    @Before
    public void createBoard() {
        this.board = BoardFactory.getInstance().createBoard("quadratic",size);
    }

    @Test
    public void testQuadraticBoardAttributes() {
        assertNotNull(board);
        assertEquals(size, board.getColumns());
        assertEquals(size, board.getRows());
        assertEquals( (Integer)(size*size), board.getNumberOfHoles());

        //  check layout and startPosition
        Long layout = 0B1111_1111_1111_1111L;
        assertEquals(layout, board.getLayout());
        Long startPosition = 0B0111_1011_1101_1110L;
        assertEquals(startPosition, board.getStartPosition());
    }
}
