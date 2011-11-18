package org.solitaire.solver.strategy;

import com.carrotsearch.hppc.LongOpenHashSet;
import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

import java.util.ArrayList;

/**
 * This strategy will assemble all reachable positions (modulo symmetry) and calculate all
 * possible solutions. The solution returned will be one with the lowest number of moves.
 * User: Tobias
 * Date: 26.09.2011
 */
public class AllSolutionsHPPCStrategy implements Strategy {

    private ArrayList<LongOpenHashSet> reachablePositions = new ArrayList<>();

    @Override
    public Solution solve(Board board, Long startPosition) {
        assembleReachablePositions(board, startPosition);
        Long start = System.currentTimeMillis();
        removeDeadEnds(board);
        System.out.println("Time dead ends: " + (System.currentTimeMillis() - start));
        //TODO: calculate all possible solutions
        return null;
    }

    private void removeDeadEnds(Board board) {
        for (int pins = 2; pins < reachablePositions.size(); pins++) {
            LongOpenHashSet positions = reachablePositions.get(pins);
            ArrayList<Long> deadEnds = new ArrayList<>();
            for (int i = 0; i < positions.allocated.length; i++) {
                if (positions.allocated[i] && board.getConsecutivePositions(positions.keys[i]).isEmpty()) {
                    deadEnds.add(positions.keys[i]);
                }
            }
            for (long deadEnd : deadEnds) {
                positions.remove(deadEnd);
            }
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

            assembleReachablePositions(board, reachablePositions.get(numberOfRemainingPieces + 1),
                    reachablePositions.get(numberOfRemainingPieces));

            totalTime += (System.currentTimeMillis() - start);
            System.out.println("found *" + reachablePositions.get(numberOfRemainingPieces).size() + "* positions in " +
                    (System.currentTimeMillis() - start) + " ms \n");
        }
        System.out.println("TOTAL TIME REACHABLE POSITIONS: " + totalTime + " ms");

    }

    private void assembleReachablePositions(Board board, LongOpenHashSet currentPositions, LongOpenHashSet followingPositions) {
        for (int i = 0; i < currentPositions.allocated.length; i++) {
            if (currentPositions.allocated[i]) {
                for (long consecutivePosition : board.getConsecutivePositions(currentPositions.keys[i])) {
                    if (followingPositions.contains(consecutivePosition)) {
                        continue;
                    }
                    boolean inSet = false;
                    for (long symPos : board.getSymmetricPositions(consecutivePosition)) {
                        //check if a symmetric position is already in the set
                        if (followingPositions.contains(symPos)) {
                            inSet = true;
                            break;
                        }
                    }
                    //add position if no equivalent (symmetric) position is already in set
                    if (!inSet) {
                        followingPositions.add(consecutivePosition);
                    }
                }
            }
        }
    }
}
