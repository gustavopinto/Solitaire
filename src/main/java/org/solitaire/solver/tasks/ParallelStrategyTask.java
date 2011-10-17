package org.solitaire.solver.tasks;

import org.solitaire.board.Board;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

/**
 * User: Tobias
 * Date: 15.10.11
 */
public class ParallelStrategyTask extends RecursiveAction {
    private Board board;
    private List<Long> currentPositions;

    // use a static reference to the resulting positions => all threads can access this set
    public static Set<Long> resultingPositions = new HashSet<>();


    /**
     * Constructor which saves the board and the sub-list of current positions.
     *
     * @param board            Board where the positions live
     * @param currentPositions the sub-list of the current positions which are processed by this task
     */
    public ParallelStrategyTask(Board board, List<Long> currentPositions) {
        this.board = board;
        this.currentPositions = currentPositions;
    }

    /**
     * The main work is done here (parallel). Assemble the reachable positions for the sub-list of currentPositions.
     *
     * @param board Board where the positions live
     */
    private void assembleReachablePositions(Board board) {
        for (Long currentPosition : currentPositions) {
            for (long consecutivePosition : board.getConsecutivePositions(currentPosition)) {
                // synchronizing on the _contains_ operations is too slow
                if (ParallelStrategyTask.resultingPositions.contains(consecutivePosition)) {
                    continue;
                }
                boolean inSet = false;
                for (long symPos : board.getSymmetricPositions(consecutivePosition)) {
                    //check if a symmetric position is already in the set
                    if (ParallelStrategyTask.resultingPositions.contains(symPos)) {
                        inSet = true;
                        break;
                    }
                }
                //add position if no equivalent (symmetric) position is already in set
                if (!inSet) {
                    // synchronizing on inserts is a good idea
                    synchronized (ParallelStrategyTask.resultingPositions) {
                        ParallelStrategyTask.resultingPositions.add(consecutivePosition);
                    }
                }
            }
        }
    }

    @Override
    protected void compute() {
        // avoid concurrency overhead for small sets
        if (currentPositions.size() < 500000) {
            assembleReachablePositions(board);
        } else {
            // split the set of current positions in two and start a task for each subset. The result is stored in resultingPositions.
            int split = currentPositions.size() / 2;
            ParallelStrategyTask left = new ParallelStrategyTask(board, currentPositions.subList(0, split));
            ParallelStrategyTask right = new ParallelStrategyTask(board, currentPositions.subList(split, currentPositions.size()));
            invokeAll(left, right);
        }
    }
}
