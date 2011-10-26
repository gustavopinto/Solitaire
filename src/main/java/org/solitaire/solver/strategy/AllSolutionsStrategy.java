package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This strategy will assemble all reachable positions (modulo symmetry) and calculate all
 * possible solutions. The solution returned will be one with the lowest number of moves.
 * User: Tobias
 * Date: 26.09.2011
 */
public class AllSolutionsStrategy implements Strategy {

    // HashSet is much faster than ArrayList since we use _contains_ a lot
    private ArrayList<HashSet<Long>> reachablePositions = new ArrayList<>();

    @Override
    public Solution solve(Board board, Long startPosition) {
        assembleReachablePositions(board, startPosition);
        Long start = System.currentTimeMillis();
        removeDeadEnds(board);
        System.out.println("Time dead ends: " + (System.currentTimeMillis() - start));
        //TODO: calculate all possible solutions
        return null;
    }

    /**
     * Remove positions which have no consecutive positions from reachable positions (dead ends).
     *
     * @param board Board the board the positions live on
     */
    private void removeDeadEnds(Board board) {
        for (int pins = 2; pins < reachablePositions.size(); pins++) {
            Set<Long> positions = reachablePositions.get(pins);
            ArrayList<Long> deadEnds = new ArrayList<>();
            for (Long position : positions) {
                if (board.getConsecutivePositions(position).isEmpty()) {
                    deadEnds.add(position);
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
        int numberOfStartPins = board.getNumberOfPins(startPosition);

        for (int i = 0; i <= numberOfStartPins + 1; i++) {
            reachablePositions.add(new HashSet<Long>());
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

    /**
     * Assemble and store all positions which follow the current positions (modulo symmetry)
     *
     * @param board              the board, the positions live on
     * @param currentPositions   positions found for a certain number of pins
     * @param followingPositions all positions which can follow the current positions (modulo symmetry)
     */
    private void assembleReachablePositions(Board board, HashSet<Long> currentPositions, HashSet<Long> followingPositions) {
        for (Long currentPosition : currentPositions) {
            for (Long consecutivePosition : board.getConsecutivePositions(currentPosition)) {
                if (followingPositions.contains(consecutivePosition)) {
                    continue;
                }
                boolean inSet = false;
                for (Long symPos : board.getSymmetricPositions(consecutivePosition)) {
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
