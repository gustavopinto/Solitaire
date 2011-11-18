package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

/**
 * This strategy uses a recursive depth first search method for finding a solution.
 * Symmetry is not taken into account. The first solution found is returned.
 * User: Tobias
 * Date: 17.09.2011
 */
public class BasicStrategy implements Strategy {

    // the list of positions which is used by the solve method
    private Solution solution;

    // indicator for the recursive solve method to stop
    private boolean solved = false;

    // the board for which we solve the game
    private Board board;

    @Override
    public Solution solve(Board board, Long startPosition) {

        // initialize members
        this.solved = false;
        this.board = board;
        this.solution = new Solution(board.getNumberOfPegs(startPosition));

        // solve the game via recursion
        this.solveRecursive(startPosition);
        return solution;
    }

    /**
     * A depth first search method for finding a solution using recursion.
     *
     * @param startPosition solve the game for this start position
     */
    private void solveRecursive(long startPosition) {

        int numPieces = board.getNumberOfPegs(startPosition);
        // return if there is only one peg left
        if (numPieces <= 1) {
            return;
        }

        int numberOfMoves = this.solution.getPositions().size() - numPieces;

        // set current position in solution
        this.solution.getPositions().set(numberOfMoves, startPosition);

        // call this method for each consecutive position and return if a solution is found
        for (long position : this.board.getConsecutivePositions(startPosition)) {

            // set consecutive position
            this.solution.getPositions().set(numberOfMoves + 1, position);

            // return if we are finished
            if (solution.isSolutionOn(board)) {
                this.solved = true;
                return;
            } else {
                this.solveRecursive(position);
            }

            // return if we are finished after recursive call
            if (this.solved) {
                return;
            } else {
                // reset position, since no solution was found
                this.solution.getPositions().set(numberOfMoves + 1, 0L);
            }
        }
    }
}
