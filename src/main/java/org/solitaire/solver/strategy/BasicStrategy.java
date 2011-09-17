package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.board.BoardHelper;
import org.solitaire.solver.Solution;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class BasicStrategy implements Strategy {
    private Solution solution;
    private boolean solved = false;
    private Board board;
    private Long endPosition = null;

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
        return (endPosition != 0L) && (this.endPosition == endPosition);
    }
}
