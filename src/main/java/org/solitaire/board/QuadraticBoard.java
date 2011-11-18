package org.solitaire.board;

import java.util.ArrayList;

/**
 * A quadratic board implementation of board.
 * The board has dimension size x size and a "full" layout, i.e. every hole is usable.
 * Quadratic boards have no predefined end position.
 * The start position consists of the layout minus the diagonal.
 * User: Tobias
 * Date: 17.09.2011
 */
public class QuadraticBoard extends Board {

    // size of the board (width == height)
    private Integer size;

    /**
     * no size => no quadratic board
     */
    private QuadraticBoard() {

    }

    /**
     * default constructor with size
     * assemble possible moves
     *
     * @param size the size of the board (width==height)
     */
    public QuadraticBoard(Integer size) {
        this.moves = new ArrayList<>();
        this.size = size;
        assembleMoves();
    }

    @Override
    public String toString(Long position) {
        StringBuilder positionString = new StringBuilder();
        for (int row = getRows() - 1; row > -1; row--) {
            for (int column = getColumns() - 1; column > -1; column--) {
                int i = getPegID(row, column);
                if (!testBit(this.getLayout(), i)) {
                    positionString.append("  ");
                } else if (testBit(position, i)) {
                    positionString.append("O ");
                } else {
                    positionString.append("* ");
                }
            }
            positionString.append("\n");
        }
        return positionString.toString();
    }

    /**
     * mapping of pegs to id's: linear
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
        // size^2
        return size * size;
    }

    @Override
    public Integer getColumns() {
        return size;
    }

    @Override
    public Integer getRows() {
        return size;
    }

    /**
     * @return layout where every peg is set
     */
    @Override
    public Long getLayout() {
        long layout = 0L;
        for (int n = 0; n < getNumberOfHoles(); n++) {
            layout |= 1L << n;
        }
        return layout;
    }

    /**
     * @return layout minus the diagonal
     */
    @Override
    public Long getStartPosition() {
        long startPosition = getLayout();
        for (int i = 0; i < getColumns(); i++) {
            long position = 1L << (i * getColumns() + i);
            startPosition = startPosition ^ position;
        }
        return startPosition;
    }

    /**
     * for a quadratic board there is usually no predefined end position
     *
     * @return null
     */
    @Override
    public Long getEndPosition() {
        return null;
    }

    /**
     * for quadratic boards, we do not use symmetry at the moment
     * TODO: symmetric positions may be implemented later on.
     *
     * @param position return the position itself only
     * @return the position itself only
     */
    @Override
    public long[] getSymmetricPositions(long position) {
        long[] symmetricPositions = new long[1];
        symmetricPositions[0] = position;
        return symmetricPositions;
    }
}
