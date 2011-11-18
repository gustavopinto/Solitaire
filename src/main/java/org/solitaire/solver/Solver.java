package org.solitaire.solver;

import org.solitaire.board.Board;
import org.solitaire.solver.strategy.Strategy;

/**
 * This class solves solitaire games via delegating the problem to a strategy.
 * Defines a interface for the solution process => solve()-method
 * User: Tobias
 * Date: 17.09.2011
 */
public class Solver {

    // the board for the game
    private Board board;

    // the strategy which solves the game
    private Strategy strategy;

    /**
     * Constructor which needs a board
     *
     * @param board this solver lives on a board
     */
    public Solver(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Invalid board.");
        }
        this.board = board;
    }

    /**
     * Main method: delegate problem to strategy
     *
     * @param startPosition solve the game for a certain start position
     * @return a solution if found, null if non is found
     */
    public Solution solve(Long startPosition) {
        if (strategy == null) {
            throw new IllegalStateException("We need a strategy for solving the problem.");
        }
        if (startPosition == null) {
            throw new IllegalArgumentException("Invalid start position.");
        }
        return this.strategy.solve(this.board, startPosition);
    }

    /**
     * The strategy which solves the game. The solver may use different strategies for finding a solution.
     *
     * @param strategy strategy to use for solving the game
     */
    public void setStrategy(Strategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid strategy.");
        }
        this.strategy = strategy;
    }

    /**
     * Getter for the strategy.
     *
     * @return the strategy which is used to solve the game
     */
    public Strategy getStrategy() {
        return this.strategy;
    }

    /**
     * Getter for the board
     *
     * @return the game board
     */
    public Board getBoard() {
        return this.board;
    }
}
