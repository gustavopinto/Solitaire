package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

/**
 * Like the basic strategy this strategy uses a recursive depth first search method for finding a solution with a
 * additional constraint on the number of moves => find a solution with less than lessThanMoves moves.
 * Symmetry is not taken into account. The first solution found is returned.
 * To find a solution with the lowest number of moves use a strategy which uses breadth first search, since this
 * strategy is not efficient enough.
 * User: Tobias
 * Date: 20.09.2011
 */
public class LessThanMovesStrategy implements Strategy {

    // the list of positions which is used by the solve method
    private Solution solution;

    // indicator for the recursive solve method to stop
    private boolean solved = false;

    // the board for which we solve the game
    private Board board;

    // constraint on the number of moves in the solution
    private Integer lessThanMoves;

    /**
     * Default constructor setting the move constraint
     *
     * @param lessThanMoves the constraint for the number of moves in the solution
     */
    public LessThanMovesStrategy(Integer lessThanMoves) {
        this.lessThanMoves = lessThanMoves;
    }

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
            if (isSolution(solution)) {
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

    /**
     * Check the additional constraint on the number of moves for the solution.
     *
     * @param solution the solution to check
     * @return true if the solution is a solution for the board and has less than lessThanMoves moves.
     */
    private boolean isSolution(Solution solution) {
        if (solution.isSolutionOn(board)) {
            System.out.println("found solution with " + solution.countMoves(this.board) + " moves.");
            if (solution.countMoves(this.board) < this.lessThanMoves) {
                return true;
            }
        }
        return false;
    }
}
