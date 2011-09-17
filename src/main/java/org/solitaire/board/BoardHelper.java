package org.solitaire.board;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class BoardHelper {
    public static String toString(Board board, Long position) {
        StringBuilder positionString = new StringBuilder();
        for(int i=0;i < board.getNumberOfFields(); i++) {
            if( !testBit(board.getLayout(), i) ) {
                positionString.append("  ");
            } else if(testBit(position, i)) {
                positionString.append("* ");
            } else {
                positionString.append("o ");
            }
            if((i > 0) && (((i+1) % board.getRows()) == 0)) {
                positionString.append("\n");
            }
        }
        return positionString.toString();
    }

    public static boolean testBit(long l, int index) {
        return (l & 1L << index) != 0L;
    }
}
