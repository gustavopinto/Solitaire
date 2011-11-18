package org.solitaire.board;

import java.util.ArrayList;

/**
 * An english board implementation of the class Board, which is the most popular layout for the peg solitaire game.
 * The english board has a "cross"-like layout on a 7x7 grid.
 * The start position is the layout except the center peg is removed.
 * The end position is the inverse of the start position.
 * User: Tobias
 * Date: 17.09.2011
 */
public class EnglishBoard extends Board {

    /**
     * default constructor: assemble possible moves
     */
    public EnglishBoard() {
        moves = new ArrayList<>();
        assembleMoves();
    }

    @Override
    public String toString(Long position) {
        StringBuilder positionString = new StringBuilder();
        for (int i = 0; i < this.getNumberOfHoles(); i++) {
            if (!testBit(this.getLayout(), i)) {
                positionString.append("  ");
            } else if (testBit(position, i)) {
                positionString.append("O ");
            } else {
                positionString.append("* ");
            }
            if ((i > 0) && (((i + 1) % this.getRows()) == 0)) {
                positionString.append("\n");
            }
        }
        return positionString.toString();
    }

    /**
     * mapping of pegs to id's : linear
     *
     * @param row    the peg's row
     * @param column the peg's column
     * @return int the id of the peg
     */
    @Override
    int getPegID(int row, int column) {
        return (row * getColumns() + column);
    }

    @Override
    public Integer getNumberOfHoles() {
        // 7*7
        return 49;
    }

    @Override
    public Integer getColumns() {
        return 7;
    }

    @Override
    public Integer getRows() {
        return 7;
    }

    @Override
    public Long getLayout() {
        return 0B0011100_0011100_1111111_1111111_1111111_0011100_0011100L;
    }

    @Override
    public Long getStartPosition() {
        return 0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L;
    }

    @Override
    public Long getEndPosition() {
        //for the english board the end position is the inverse of the start position.
        return 0B0001000_0000000_0000000_0000000L;
    }

    /**
     * We have eight symmetric positions:
     * the position itself
     * rotated by 90 degree
     * rotated by 180 degree
     * rotated by 270 degree
     * vertical mirror
     * horizontal mirror
     * diagonal mirror on d1
     * diagonal mirror on d2
     * Since rotating by 180 degree is inexpensive, use it to calculate other symmetric positions
     */
    @Override
    public long[] getSymmetricPositions(long position) {
        long[] symmetricPositions = new long[8];
        symmetricPositions[0] = position;
        long rotatedBy180 = rotateBy180(position);
        symmetricPositions[1] = rotatedBy180;
        symmetricPositions[2] = mirrorVertically(rotatedBy180);
        symmetricPositions[3] = mirrorVertically(position);
        long rotatedBy90 = rotateBy90(position);
        symmetricPositions[4] = rotatedBy90;
        symmetricPositions[5] = rotateBy180(rotatedBy90);
        long mirroredDiagonally = mirrorVertically(rotatedBy90);
        symmetricPositions[6] = mirroredDiagonally;
        symmetricPositions[7] = rotateBy180(mirroredDiagonally);
        return symmetricPositions;
    }

    /**
     * rotate position by 180 degree. 64 - 49 = 15 => reverse and shift by 15
     *
     * @param position as long
     * @return rotated position
     */
    private static long rotateBy180(long position) {
        return Long.reverse(position) >>> 15;
    }

    /**
     * shift each peg to the rotated position
     *
     * @param position as long
     * @return position rotated by 90 degree
     */
    private static long rotateBy90(long position) {
        long rotatedPosition;
        rotatedPosition = ((position & (1L << 2)) << 18) | ((position & (1L << 3)) << 24) | ((position & (1L << 4)) << 30)
                | ((position & (1L << 9)) << 10) | ((position & (1L << 10)) << 16) | ((position & (1L << 11)) << 22)
                | ((position & (1L << 14)) >> 10) | ((position & (1L << 15)) >> 4) | ((position & (1L << 16)) << 2)
                | ((position & (1L << 17)) << 8) | ((position & (1L << 18)) << 14) | ((position & (1L << 19)) << 20)
                | ((position & (1L << 20)) << 26)
                | ((position & (1L << 21)) >> 18) | ((position & (1L << 22)) >> 12) | ((position & (1L << 23)) >> 6)
                | ((position & (1L << 24)))
                | ((position & (1L << 25)) << 6) | ((position & (1L << 26)) << 12) | ((position & (1L << 27)) << 18)
                | ((position & (1L << 28)) >> 26) | ((position & (1L << 29)) >> 20) | ((position & (1L << 30)) >> 14)
                | ((position & (1L << 31)) >> 8) | ((position & (1L << 32)) >> 2) | ((position & (1L << 33)) << 4)
                | ((position & (1L << 34)) << 10)
                | ((position & (1L << 37)) >> 22) | ((position & (1L << 38)) >> 16) | ((position & (1L << 39)) >> 10)
                | ((position & (1L << 44)) >> 30) | ((position & (1L << 45)) >> 24) | ((position & (1L << 46)) >> 18);
        return rotatedPosition;
    }

    /**
     * mirror each row separately
     *
     * @param position as long
     * @return position mirrored vertically
     */
    private static long mirrorVertically(long position) {
        long verticallyMirroredPosition;
        verticallyMirroredPosition = ((position & (1L << 2)) << 42) | ((position & (1L << 3)) << 42) | ((position & (1L << 4)) << 42);
        verticallyMirroredPosition |= ((position & (1L << 9)) << 28) | ((position & (1L << 10)) << 28) | ((position & (1L << 11)) << 28);
        verticallyMirroredPosition |= ((position & (1L << 14)) << 14) | ((position & (1L << 15)) << 14) | ((position & (1L << 16)) << 14)
                | ((position & (1L << 17)) << 14) | ((position & (1L << 18)) << 14) | ((position & (1L << 19)) << 14)
                | ((position & (1L << 20)) << 14);
        verticallyMirroredPosition |= ((position & (1L << 21))) | ((position & (1L << 22))) | ((position & (1L << 23)))
                | ((position & (1L << 24)))
                | ((position & (1L << 25))) | ((position & (1L << 26))) | ((position & (1L << 27)));
        verticallyMirroredPosition |= ((position & (1L << 28)) >> 14) | ((position & (1L << 29)) >> 14) | ((position & (1L << 30)) >> 14)
                | ((position & (1L << 31)) >> 14) | ((position & (1L << 32)) >> 14) | ((position & (1L << 33)) >> 14)
                | ((position & (1L << 34)) >> 14);
        verticallyMirroredPosition |= ((position & (1L << 37)) >> 28) | ((position & (1L << 38)) >> 28) | ((position & (1L << 39)) >> 28);
        verticallyMirroredPosition |= ((position & (1L << 44)) >> 42) | ((position & (1L << 45)) >> 42) | ((position & (1L << 46)) >> 42);
        return verticallyMirroredPosition;
    }
}
