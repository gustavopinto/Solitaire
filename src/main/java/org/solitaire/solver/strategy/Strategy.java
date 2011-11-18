package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

/**
 * Interface for all strategies which can be used to solve the solitaire game.
 * User: Tobias
 * Date: 17.09.2011
 */
public interface Strategy {

    /**
     * Main Method of a strategy: solve the game for a certain start position on a board.
     * @param board  the board for the game
     * @param startPosition solve the game for this start position
     * @return  a solution if found, null if not
     */
    Solution solve(Board board, Long startPosition);

}
