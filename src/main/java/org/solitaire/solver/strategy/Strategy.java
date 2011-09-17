package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public interface Strategy {
    Solution solve(Board board, Long startPosition);
}
