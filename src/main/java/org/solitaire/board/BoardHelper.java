package org.solitaire.board;

import org.solitaire.solver.Solution;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class BoardHelper {
    /**
     *
     * @param board  Board the board on which the position lives
     * @param position Long the position on the board
     * @return the position as string
     */
    public static String toString(Board board, Long position) {
        StringBuilder positionString = new StringBuilder();
        for(int i=0;i < board.getNumberOfFields(); i++) {
            if( !testBit(board.getLayout(), i) ) {
                positionString.append("  ");
            } else if(testBit(position, i)) {
                positionString.append("O ");
            } else {
                positionString.append("* ");
            }
            if((i > 0) && (((i+1) % board.getRows()) == 0)) {
                positionString.append("\n");
            }
        }
        return positionString.toString();
    }

    /**
     *
     * @param l long
     * @param index position of bit
     * @return true if bit at index is set
     */
    public static boolean testBit(long l, int index) {
        return (l & 1L << index) != 0L;
    }

    /**
     *
     * @param position long
     * @return number of bits which are set
     */
    public static int getNumberOfPins(long position) {
        return Long.bitCount(position);
    }

    public static Integer countMoves(Board board, Solution solution) {
        //first move is always a new move
        int numMoves = 1;
        for(int i = solution.getSolution().length-1; i > 1; i--) {
            long pos1 = solution.getSolution()[i];
            long pos2 = solution.getSolution()[i-1];
            long pos3 = solution.getSolution()[i-2];

            long mask = pos1 ^ pos3;
            if( ( ((pos1^pos2)| (pos2^pos3) ) & (pos1 & pos3 )) != 0L  || !board.getConnectedMoveMasks().contains(mask) ) {
                numMoves++;
            }
        }
        return numMoves;
    }
}
