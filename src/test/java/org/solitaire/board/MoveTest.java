package org.solitaire.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Tobias
 * Date: 20.09.2011
 */
public class MoveTest {

    @Test
    public void testMoveAttributes() {
        Integer start = 6;
        Integer center = 5;
        Integer end = 4;
        Move move = new Move(start, center, end);
        Long mask = (1L << start) | (1L << center) | (1L << end);
        Long check = (1L << start) | (1L << center);
        assertEquals(mask, (Long)move.getMask());
        assertEquals(check, (Long)move.getCheck());
        assertEquals(1L << start, move.getStart());
        assertEquals(1L << end, move.getEnd());
        assertEquals(start, move.getStartID());
        assertEquals(end, move.getEndID());
    }
}
