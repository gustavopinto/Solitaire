package org.solitaire.solver;

import org.solitaire.board.Board;
import org.solitaire.solver.strategy.Strategy;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class Solver {
    private Board board;
    private Strategy strategy;

    public Solver(Board board) {
        this.board = board;
    }

    public Solution solve(Long startPosition) {
        return new Solution(this.board.getNumberOfFields());
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Board getBoard() {
        return this.board;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }
}
