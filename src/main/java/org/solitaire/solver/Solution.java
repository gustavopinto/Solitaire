package org.solitaire.solver;

import org.solitaire.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * A Solution is just a list of positions with some additional convenience methods.
 * User: Tobias
 * Date: 17.09.2011
 */
public class Solution {
    private List<Long> positions;

    /**
     * Default constructor initializes the position list.
     */
    public Solution() {
        this.positions = new ArrayList<>();
    }

    /**
     * Initialize all positions with zero.
     *
     * @param size the size of the position list.
     */
    public Solution(int size) {
        this.positions = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            add(0L);
        }
    }

    /**
     * Copy constructor: copy the list of positions
     *
     * @param solution the solution which we want to copy
     */
    public Solution(Solution solution) {
        this();
        for (Long position : solution.getPositions()) {
            add(position);
        }
    }

    /**
     * a list method for adding a position.
     *
     * @param position the position to add
     * @return this object
     */
    public Solution add(Long position) {
        positions.add(position);
        return this;
    }

    /**
     * Getter for the position list
     *
     * @return the list of positions
     */
    public List<Long> getPositions() {
        return this.positions;
    }

    /**
     * count moves for this solution on board
     * TODO: move to board?
     *
     * @param board the board the positions live on
     * @return the number of moves for this solution
     */
    public Integer countMoves(Board board) {
        //first move is always a new move
        int numMoves = 1;
        for (int i = 0; i < positions.size() - 2; i++) {
            long pos1 = positions.get(i);
            long pos2 = positions.get(i + 1);
            long pos3 = positions.get(i + 2);

            long mask = pos1 ^ pos3;
            if ((((pos1 ^ pos2) | (pos2 ^ pos3)) & (pos1 & pos3)) != 0L || !board.getConnectedMoveMasks().contains(mask)) {
                numMoves++;
            }
        }
        return numMoves;
    }

    /**
     * Return true if this solution is a solution for the board board.
     * For boards with predefined end position check if the solutions end position equals
     * the boards end position.
     * For all other boards check if the last position has only one piece.
     *
     * @param board the board, the solution lives on
     * @return true   if this solution is a solution for the board board.
     */
    public boolean isSolutionOn(Board board) {
        long endPosition = positions.get(positions.size() - 1);
        if (board.getEndPosition() != null) {
            return (board.getEndPosition().equals(endPosition));
        } else if (endPosition != 0L) {
            return board.getNumberOfPegs(endPosition) == 1;
        }
        return false;
    }

    /**
     * Two solutions are equal if they are the same object or if the underlying lists are equal.
     * TODO: should we override hashcode too?
     *
     * @param solutionObject the object to compare to
     * @return true if the underlying lists are equal
     */
    @Override
    public boolean equals(Object solutionObject) {
        return (solutionObject == this) ||
                ((solutionObject instanceof Solution) && positions.equals(((Solution) solutionObject).getPositions()));
    }

    @Override
    public String toString() {
        StringBuilder solutionString = new StringBuilder();
        for (Long position : positions) {
            solutionString.append(position).append("\n");
        }
        return solutionString.toString();
    }

    /**
     * pretty print this solution using the board
     *
     * @param board the board, the positions live on
     * @return a string representing the solution in the board
     */
    public String toString(Board board) {
        StringBuilder solutionString = new StringBuilder();
        solutionString.append(" Solution: ").append("\n");
        for (Long position : positions) {
            solutionString.append(board.toString(position)).append("\n");
        }
        solutionString.append("\n");
        return solutionString.toString();
    }

}
