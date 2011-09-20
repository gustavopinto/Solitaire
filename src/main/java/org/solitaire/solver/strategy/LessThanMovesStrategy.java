package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.board.BoardHelper;
import org.solitaire.solver.Solution;

/**
 * User: Tobias
 * Date: 20.09.2011
 */
public class LessThanMovesStrategy implements Strategy {
    private Solution solution;
    private boolean solved = false;
    private Board board;
    private Long endPosition = null;
    private Integer lessThanMoves;

    public LessThanMovesStrategy(Integer lessThanMoves) {
        this.lessThanMoves = lessThanMoves;
    }

    @Override
    public Solution solve(Board board, Long startPosition) {
        this.solved = false;
        this.board = board;
        //center piece for uneven layout only
        if(board.getNumberOfFields() % 2 == 1) {
            this.endPosition = (1L << (board.getNumberOfFields() / 2));
        }

        this.solution = new Solution(BoardHelper.getNumberOfPins(startPosition));
        this.solveRecursive(startPosition);
        return solution;
    }

    private void solveRecursive(long startPosition) {
        int numPieces = BoardHelper.getNumberOfPins(startPosition);
        if( numPieces <= 1) {
            return;
        }
        this.solution.getSolution()[numPieces-1] = startPosition;
        for(long position: this.board.getConsecutivePositions(startPosition)) {
            this.solution.getSolution()[numPieces-2] = position;
            if( isSolution(solution) ) {
                this.solved = true;
                return;
            } else {
                this.solveRecursive(position);
            }
            if(this.solved) {
                return;
            } else {
                this.solution.getSolution()[numPieces-2] = 0L;
            }
        }
    }

    private boolean isSolution(Solution solution) {
        long endPosition = solution.getSolution()[0];
        if( (endPosition != 0L) && (this.endPosition == endPosition) ) {
            System.out.println("found solution with " + BoardHelper.countMoves(this.board, solution) + " moves.");
            if( BoardHelper.countMoves(this.board, solution) < this.lessThanMoves) {
                return true;
            }
        }
        return false;
    }
}
