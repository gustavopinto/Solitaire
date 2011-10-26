package org.solitaire.board;

import java.util.ArrayList;
import java.util.Set;

/**
 * Interface to describe the interface methods of a board
 * User: Tobias
 * Date: 17.09.2011
 */
public interface Board {

    /**
     * This method returns the number of columns times number of rows
     * This number might not be equal to the number of usable holes on the board
     * => use getNumberOfPins(layout) for number of pins (i.e. all pins set)
     * This method is mainly used for displaying the board as string.
     *
     * @return number of columns times number of rows
     */
    Integer getNumberOfFields();


    /**
     * get number of columns of the grid
     *
     * @return Integer number of columns
     */
    Integer getColumns();


    /**
     * number of rows of the grid
     *
     * @return Integer number of rows
     */
    Integer getRows();


    /**
     * The layout is internally represented as a long where each bit corresponds to a field.
     * description of the holes which can be used, i.e. where a pin can be put
     *
     * @return the layout as long.
     */
    Long getLayout();


    /**
     * Internal representation of the start position as long.
     *
     * @return the default start position for this board as Long
     */
    Long getStartPosition();


    /**
     * Apply all possible moves to the given position and return the resulting positions.
     *
     * @param position long the position to which the moves are applied.
     * @return return all consecutive positions,
     *         i.e. all positions which are the result of applying one move to the given position
     */
    ArrayList<Long> getConsecutivePositions(long position);


    /**
     * get the masks to identify a connected move, i.e. a move where the same pin moves twice
     *
     * @return set of masks
     */
    Set<Long> getConnectedMoveMasks();


    /**
     * get all positions which are symmetric to this position
     *
     * @param position find positions which are symmetric to this position
     * @return an array for all symmetric positions
     */
    long[] getSymmetricPositions(long position);

    /**
     * return a string representing the position on this board
     *
     * @param position Long the position on the board
     * @return the position as string
     */
    public String toString(Long position);

    /**
     * return the number of bits which are set to one in position
     *
     * @param position long
     * @return number of bits which are set
     */
    public int getNumberOfPins(long position);
}
