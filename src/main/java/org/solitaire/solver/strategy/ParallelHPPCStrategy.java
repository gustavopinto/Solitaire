package org.solitaire.solver.strategy;

import com.carrotsearch.hppc.LongOpenHashSet;
import org.solitaire.board.Board;
import org.solitaire.solver.Solution;
import org.solitaire.solver.tasks.ParallelHPPCStrategyTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

/**
 * This strategy will assemble all reachable positions (modulo symmetry) and calculate all
 * possible solutions. The solution returned will be one with the lowest number of moves.
 * User: Tobias
 * Date: 26.09.2011
 */
public class ParallelHPPCStrategy implements Strategy {

    private ArrayList<LongOpenHashSet> reachablePositions = new ArrayList<>();

    @Override
    public Solution solve(Board board, Long startPosition) {
        assembleReachablePositions(board, startPosition);
        Long start = System.currentTimeMillis();
        check(board);
        System.out.println("Time check: " + (System.currentTimeMillis() - start));
        //TODO: calculate all possible solutions
        ArrayList<ArrayList<Long>> solutions = new ArrayList<>();
        // start with start-position
        // for each following (symmetric) position create a new solution
        for (int pins = 1; pins < reachablePositions.size(); pins++) {
            LongOpenHashSet positions = reachablePositions.get(pins);
            for (int i = 0; i < positions.allocated.length; i++) {
                if (positions.allocated[i]) {

                }
            }
        }

        return null;
    }

    private void check(Board board) {
        for (int pins = 0; pins < reachablePositions.size(); pins++) {
            long start = System.currentTimeMillis();
            HashSet<Long> redundantPositions = new HashSet<>();
            LongOpenHashSet positions = reachablePositions.get(pins);
            for (int i = 0; i < positions.allocated.length; i++) {
                if (positions.allocated[i]) {
                    for (long symmetricPosition : board.getSymmetricPositions(positions.keys[i])) {
                        if (symmetricPosition != positions.keys[i] && positions.contains(symmetricPosition)) {
                            redundantPositions.add(positions.keys[i]);
                            break;
                        }
                    }
                }
            }
            for (Long redundantPosition : redundantPositions) {
                for (long symmetricPosition : board.getSymmetricPositions(redundantPosition)) {
                    if (symmetricPosition != redundantPosition && positions.contains(symmetricPosition)) {
                        positions.remove(redundantPosition);
                        break;
                    }
                }
            }
            System.out.println("Check " + pins + ": " + redundantPositions.size() + ", Time: " + (System.currentTimeMillis() - start));
        }
    }

    /**
     * Assemble all reachable positions starting with startPosition.
     * Store positions in a local field
     *
     * @param board         the board where the positions live
     * @param startPosition start finding positions with this start position
     */
    private void assembleReachablePositions(Board board, Long startPosition) {
        int numberOfStartPins = board.getNumberOfPegs(startPosition);

        for (int i = 0; i <= numberOfStartPins + 1; i++) {
            reachablePositions.add(new LongOpenHashSet());
        }

        // add startPosition to reachablePositions
        reachablePositions.get(numberOfStartPins).add(startPosition);

        long totalTime = 0L;
        for (int numberOfRemainingPieces = numberOfStartPins - 1; (numberOfRemainingPieces > 0); numberOfRemainingPieces--) {
            long start = System.currentTimeMillis();
            System.out.println("search positions with " + numberOfRemainingPieces + " of " + numberOfStartPins + " pins");

            ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            ParallelHPPCStrategyTask.followingPositions = reachablePositions.get(numberOfRemainingPieces);
            ParallelHPPCStrategyTask assembleTask = new ParallelHPPCStrategyTask(board, reachablePositions.get(numberOfRemainingPieces + 1));
            assembleTask.setRange(0, reachablePositions.get(numberOfRemainingPieces + 1).allocated.length - 1);
            if (numberOfRemainingPieces == 1) {
                assembleTask.setDeadEnds(true);
            }
            pool.invoke(assembleTask);

            totalTime += (System.currentTimeMillis() - start);
            System.out.println("found *" + reachablePositions.get(numberOfRemainingPieces).size() + "* positions in " +
                    (System.currentTimeMillis() - start) + " ms \n");
        }
        System.out.println("TOTAL TIME REACHABLE POSITIONS: " + totalTime + " ms");

    }
}
