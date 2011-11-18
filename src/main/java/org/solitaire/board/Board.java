package org.solitaire.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract class to describe the common methods and fields of boards
 * Since the boards do have method implementations and fields in common, we use an abstract class and not an interface
 * A Board is defined by a layout, a grid with holes for pegs.
 * The Board has a list of possible moves, knows which positions are symmetric
 * and can calculate consecutive positions of a start position.
 * User: Tobias
 * Date: 17.09.2011
 */
public abstract class Board {

    // list of possible moves on this board
    protected List<Move> moves;

    // masks to identify connected moves
    protected Set<Long> connectedMoveMasks;


    /**
     * This method returns the number of columns times number of rows
     * This number might not be equal to the number of usable holes on the board
     * => use getNumberOfPegs(layout) for number of pegs (i.e. all pegs set)
     * This method is mainly used for displaying the board as string.
     *
     * @return number of columns times number of rows
     */
    public abstract Integer getNumberOfHoles();


    /**
     * get number of columns of the grid
     *
     * @return Integer number of columns
     */
    public abstract Integer getColumns();


    /**
     * number of rows of the grid
     *
     * @return Integer number of rows
     */
    public abstract Integer getRows();


    /**
     * The layout is internally represented as a long where each bit corresponds to a field.
     * description of the holes which can be used, i.e. where a peg can be put
     *
     * @return the layout as long.
     */
    public abstract Long getLayout();


    /**
     * Internal representation of the start position as long.
     *
     * @return the default start position for this board as Long
     */
    public abstract Long getStartPosition();

    /**
     * Return the end position for this board. The english board has a defined endposition.
     * Quadratic boards usually do not have a predefined end position, the game is solved, when there
     * remains only one piece.
     *
     * @return a position which indicates that the game is solved.
     */
    public abstract Long getEndPosition();

    /**
     * mapping of pegs to id's
     *
     * @param row    the peg's row
     * @param column the peg's column
     * @return int the id of the peg
     */
    abstract int getPegID(int row, int column);

    /**
     * get all positions which are symmetric to this position
     *
     * @param position find positions which are symmetric to this position
     * @return an array for all symmetric positions
     */
    public abstract long[] getSymmetricPositions(long position);

    /**
     * return a string representing the position on this board
     * positions not in the layout are not displayed
     * positions where a peg is set are usually displayed as "O"
     * positions with no peg are usually displayed as "*"
     *
     * @param position Long the position on the board
     * @return the position as string
     */
    public abstract String toString(Long position);

    /**
     * Apply all possible moves to the given position and return the resulting positions.
     *
     * @param position long the position to which the moves are applied.
     * @return return all consecutive positions,
     *         i.e. all positions which are the result of applying one move to the given position
     */
    public List<Long> getConsecutivePositions(long position) {
        List<Long> positions = new ArrayList<>();
        for (Move move : moves) {
            // apply mask to position
            long masked = position & move.getMask();

            // detect if move is possible (i.e.  masked equals two pegs which are adjacent to an empty slot)
            if (masked == move.getCheck()) {
                // apply move, i.e. the two pegs are xor'ed to zero and the empty slot is xor'ed to one
                positions.add(position ^ move.getMask());
            }
        }
        return positions;
    }


    /**
     * get the masks to identify a connected move, i.e. two consecutive moves where the same peg moves twice
     *
     * @return set of masks
     */
    public Set<Long> getConnectedMoveMasks() {
        return this.connectedMoveMasks;
    }

    /**
     * return the number of bits which are set to one in position
     *
     * @param position long
     * @return number of bits which are set
     */
    public int getNumberOfPegs(long position) {
        return Long.bitCount(position);
    }

    /**
     * Assemble all possible moves on this board.
     * inspect the board from top left to right bottom for three consecutive holes.
     * Assemble masks for connected moves.
     */
    protected void assembleMoves() {
        Long layout = this.getLayout();
        // find all possible moves for this board
        // order of traversal: top left to bottom right
        for (int row = getRows() - 1; row > -1; row--) {
            for (int column = getColumns() - 1; column > -1; column--) {
                // detect possible horizontal moves
                if ((row + 2 < getRows())
                        && testBit(layout, getPegID(row, column))
                        && testBit(layout, getPegID(row + 1, column))
                        && testBit(layout, getPegID(row + 2, column))) {
                    moves.add(new Move(getPegID(row, column), getPegID(row + 1, column), getPegID(row + 2, column)));
                    moves.add(new Move(getPegID(row + 2, column), getPegID(row + 1, column), getPegID(row, column)));
                }
                // detect possible vertical moves
                if ((column + 2 < getColumns())
                        && testBit(layout, getPegID(row, column))
                        && testBit(layout, getPegID(row, column + 1))
                        && testBit(layout, getPegID(row, column + 2))) {
                    moves.add(new Move(getPegID(row, column), getPegID(row, column + 1), getPegID(row, column + 2)));
                    moves.add(new Move(getPegID(row, column + 2), getPegID(row, column + 1), getPegID(row, column)));
                }
            }
        }

        // assemble the masks for detection of connected moves
        this.connectedMoveMasks = new HashSet<>();
        for (Move firstMove : moves) {
            for (Move secondMove : moves) {
                if (firstMove.getEnd() == secondMove.getStart() && ((firstMove.getCheck() & secondMove.getCheck()) == 0L)) {
                    connectedMoveMasks.add(firstMove.getMask() ^ secondMove.getMask());
                }
            }
        }
    }

    /**
     * test bit at index in number. Used to check if a peg is present at position index
     *
     * @param number long
     * @param index  position of bit
     * @return true if bit at index is set
     */
    public static boolean testBit(long number, int index) {
        return (number & 1L << index) != 0L;
    }
}
