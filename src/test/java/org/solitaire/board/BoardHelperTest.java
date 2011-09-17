package org.solitaire.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class BoardHelperTest {
    @Test
    public void testPositionToString() {
        Board englishBoard = null;
        try {
            englishBoard = BoardFactory.createBoard("org.solitaire.board.EnglishBoard");
        } catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


        String positionString =
                "    * * *     \n" +
                "    * * *     \n" +
                "* * * * * * * \n" +
                "* * * o * * * \n" +
                "* * * * * * * \n" +
                "    * * *     \n" +
                "    * * *     \n";
        assertEquals(positionString, BoardHelper.toString(englishBoard, englishBoard.getStartPosition()));
    }
}
