package org.solitaire.board;

import java.util.ArrayList;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class EnglishBoard implements Board {

    private ArrayList<ArrayList<Long>> moveMasks;

    public EnglishBoard() {
        moveMasks = new ArrayList<>();

        Long layout = this.getLayout();
        // initialize masks
        for (int row = getRows() - 1; row > -1; row--) {
            for (int column = getColumns() - 1; column > -1; column--) {
                // detect possible horizontal moves
                if ((row + 2 < getRows())
                        && BoardHelper.testBit(layout, getPegID(row,column))
                        && BoardHelper.testBit(layout, getPegID(row + 1,column))
                        && BoardHelper.testBit(layout, getPegID(row + 2,column))) {
                    addMove( getPegID(row, column), getPegID(row + 1, column), getPegID(row + 2, column));
                }
                // detect possible vertical moves
                if ((column + 2 < getColumns())
                    && BoardHelper.testBit(layout, getPegID(row,column))
                    && BoardHelper.testBit(layout, getPegID(row,column + 1))
                    && BoardHelper.testBit(layout, getPegID(row,column + 2))) {
                    addMove( getPegID(row, column), getPegID(row, column + 1), getPegID(row, column + 2));
                }
            }
        }
    }

    /**
     * add the move detection mask ( lineup of three pins in a row or column)
     * and the moving pins (two pins which are adjacent to an empty slot) to the list
     * @param pin1 index of first pin
     * @param pin2 index of second pin
     * @param pin3 index of third pin
     */
    private void addMove(int pin1, int pin2, int pin3) {
        ArrayList<Long> newMoveMask = new ArrayList<>();
        newMoveMask.add(((1L << pin1) | (1L << pin2) | (1L << pin3)));
        newMoveMask.add((1L << pin1) | (1L << pin2));
        newMoveMask.add((1L << pin2) | (1L << pin3));
        moveMasks.add(newMoveMask);
    }

    private int getPegID(int row, int column) {
    	return (row*getColumns() + column);
    }

    @Override
    public Integer getColumns() {
        return 7;
    }

    /**
     * This method returns the number of columns times number of rows
     * This number might not be equal to the number of usable fields on the board
     * => use BoardHelper.getNumberOfPins(layout) for number of pins (i.e. all pins set)
     * This method is mainly used for displaying the board as string.
     * @return number of columns times number of rows
     */
    @Override
    public Integer getNumberOfFields() {
        return 49;
    }

    @Override
    public Integer getRows() {
        return 7;
    }

    /**
     * The layout is internally represented as a long where each bit corresponds to a field.
     * @return the layout as long.
     */
    @Override
    public Long getLayout() {
        return 0B0011100_0011100_1111111_1111111_1111111_0011100_0011100L;
    }

    /**
     * Internal representation of the start position as long.
     * @return  the start position as Long
     */
    @Override
    public Long getStartPosition() {
        return 0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L;
    }

    /**
     * Apply all possible moves to the given position and return the resulting positions.
     * @param position long the position to which the moves are applied.
     * @return return all consecutive positions,
     * i.e. all positions which are the result of applying one move to the given position
     */
    @Override
    public ArrayList<Long> getConsecutivePositions(long position) {
        ArrayList<Long> positions = new ArrayList<>();
        for(ArrayList<Long> mask : moveMasks) {
            // apply mask to position
            long masked = position & mask.get(0);

            // detect if move is possible (i.e.  masked equals two pins which are adjacent to an empty slot)
            if (masked == mask.get(1) || masked == mask.get(2)) {
                // apply move, i.e. the two pins are xor'ed to zero and the empty slot is xor'ed to one
                positions.add(position ^ mask.get(0));
            }
        }
        return positions;
    }
}
