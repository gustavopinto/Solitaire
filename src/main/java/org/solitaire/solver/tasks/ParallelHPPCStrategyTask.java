package org.solitaire.solver.tasks;

import com.carrotsearch.hppc.LongOpenHashSet;
import com.carrotsearch.hppc.LongSet;
import org.solitaire.board.Board;

import java.util.concurrent.RecursiveAction;

/**
 * User: Tobias
 * Date: 15.10.11
 */
public class ParallelHPPCStrategyTask extends RecursiveAction {
    private Board board;
    private LongOpenHashSet currentPositions;
    private int from;
    private int to;

    private boolean deadEnds = false;

    // LongSet
    public static LongSet followingPositions;

    public ParallelHPPCStrategyTask(Board board, LongOpenHashSet currentPositions) {
        this.board = board;
        this.currentPositions = currentPositions;
    }

    private void assembleReachablePositions(Board board, LongOpenHashSet currentPositions) {
        for (int i = from; i <= to && i < currentPositions.allocated.length; i++) {
            if (currentPositions.allocated[i]) {
                for (long consecutivePosition : board.getConsecutivePositions(currentPositions.keys[i])) {
                    if (ParallelHPPCStrategyTask.followingPositions.contains(consecutivePosition)) {
                        continue;
                    }
                    boolean inSet = false;
                    for (long symPos : board.getSymmetricPositions(consecutivePosition)) {
                        //check if a symmetric position is already in the set
                        if (ParallelHPPCStrategyTask.followingPositions.contains(symPos)) {
                            inSet = true;
                            break;
                        }
                    }
                    //add position if no equivalent (symmetric) position is already in set
                    if (!inSet) {
                        if (deadEnds || !board.getConsecutivePositions(consecutivePosition).isEmpty()) {
                            synchronized (ParallelHPPCStrategyTask.followingPositions) {
                                ParallelHPPCStrategyTask.followingPositions.add(consecutivePosition);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void compute() {
        if (to - from < 500000) {
            //System.out.println("Using range: " + from + "-" + to + " from " + currentPositions.allocated.length);
            assembleReachablePositions(board, currentPositions);
        } else {
            int split = from + (to - from) / 2;
            ParallelHPPCStrategyTask left = new ParallelHPPCStrategyTask(board, currentPositions);
            left.setRange(from, split);
            ParallelHPPCStrategyTask right = new ParallelHPPCStrategyTask(board, currentPositions);
            right.setRange(split + 1, to);
            invokeAll(left, right);
        }
    }

    public void setRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public void setDeadEnds(boolean deadEnds) {
        this.deadEnds = deadEnds;
    }
}
