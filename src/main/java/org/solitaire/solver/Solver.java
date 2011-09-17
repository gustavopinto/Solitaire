package org.solitaire.solver;

import org.solitaire.board.Board;
import org.solitaire.solver.strategy.Strategy;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class Solver {
    public Solver(Board board) {

    }

    public Solution solve(Long startPosition) {
        return new Solution();
    }

    public void setStrategy(Strategy strategy) {

    }
}
