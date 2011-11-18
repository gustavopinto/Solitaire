package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.solver.Solution;
import org.solitaire.solver.tasks.ParallelStrategyTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * This strategy will assemble all reachable positions (modulo symmetry) and calculate all
 * possible solutions. The solution returned will be one with the lowest number of moves.
 * Currently this strategy needs much more memory than the single-thread strategy to run efficiently
 * since the set of following positions needs to be divided/copied.
 * User: Tobias
 * Date: 26.09.2011
 */
public class ParallelStrategy implements Strategy {

    private ArrayList<Set<Long>> reachablePositions = new ArrayList<>();

    @Override
    public Solution solve(Board board, Long startPosition) {
        assembleReachablePositions(board, startPosition);
        Long start = System.currentTimeMillis();
        check(board);
        System.out.println("Time check: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        removeNonWinningPositions(board);
        System.out.println("Time non winning positions: " + (System.currentTimeMillis() - start) + "\n");
        //TODO: calculate all possible solutions
        return null;
    }

    /**
     * Due to concurrency there might be symmetric entries in the sets of reachable positions.
     * Remove symmetric entries from reachable positions.
     *
     * @param board Board where the positions live
     */
    private void check(Board board) {
        for (int pins = 0; pins < reachablePositions.size(); pins++) {
            HashSet<Long> redundantPositions = new HashSet<>();
            long start = System.currentTimeMillis();
            Set<Long> positions = reachablePositions.get(pins);
            for (Long position : positions) {
                for (long symmetricPosition : board.getSymmetricPositions(position)) {
                    if (symmetricPosition != position && positions.contains(symmetricPosition)) {
                        redundantPositions.add(position);
                        break;
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
     * remove all positions which are not part of a solution.
     *
     * @param board the board, the positions live on
     */
    private void removeNonWinningPositions(Board board) {
        for (int pins = 2; pins < reachablePositions.size(); pins++) {
            Set<Long> positions = reachablePositions.get(pins);
            Set<Long> followingPositions = reachablePositions.get(pins - 1);
            ArrayList<Long> deadEnds = new ArrayList<>();
            for (Long position : positions) {
                boolean isWinningPosition = false;
                for (Long consecutivePosition : board.getConsecutivePositions(position)) {
                    for (Long symmetricPosition : board.getSymmetricPositions(consecutivePosition)) {
                        if (followingPositions.contains(symmetricPosition)) {
                            isWinningPosition = true;
                            break;
                        }
                    }
                }
                if (!isWinningPosition) {
                    deadEnds.add(position);
                }
            }
            for (long deadEnd : deadEnds) {
                positions.remove(deadEnd);
            }
            System.out.println("Found " + deadEnds.size() + " non winning positions with " + pins + " pieces. Remaining " + positions.size());
        }
    }

    /**
     * Assemble all reachable positions starting with startPosition.
     * Use the join/fork framework: ParallelStrategyTask
     *
     * @param board         the board where the positions live
     * @param startPosition start finding positions with this start position
     */
    private void assembleReachablePositions(Board board, Long startPosition) {
        int numberOfStartPins = board.getNumberOfPegs(startPosition);

        for (int i = 0; i <= numberOfStartPins + 1; i++) {
            reachablePositions.add(new HashSet<Long>());
        }

        // add startPosition to reachablePositions
        reachablePositions.get(numberOfStartPins).add(startPosition);

        long totalTime = 0L;
        for (int numberOfRemainingPieces = numberOfStartPins - 1; (numberOfRemainingPieces > 0); numberOfRemainingPieces--) {
            long start = System.currentTimeMillis();
            System.out.println("search positions with " + numberOfRemainingPieces + " of " + numberOfStartPins + " pins");
            //even with a concurrent collection we do not get a clean set (i.e. the set contains symmetric positions)
            //ParallelStrategyTask.resultingPositions = Collections.newSetFromMap(new ConcurrentHashMap<Long,Boolean>());
            ParallelStrategyTask.resultingPositions = reachablePositions.get(numberOfRemainingPieces);
            ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            // we can not divide a set efficiently => copy set to list
            pool.invoke(new ParallelStrategyTask(board, new ArrayList<>(reachablePositions.get(numberOfRemainingPieces + 1))));

            totalTime += (System.currentTimeMillis() - start);
            System.out.println("found *" + reachablePositions.get(numberOfRemainingPieces).size() + "* positions in " +
                    (System.currentTimeMillis() - start) + " ms \n");
        }
        System.out.println("TOTAL TIME REACHABLE POSITIONS: " + totalTime + " ms");
    }
}
