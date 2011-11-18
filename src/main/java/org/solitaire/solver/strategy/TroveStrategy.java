package org.solitaire.solver.strategy;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.set.hash.TLongHashSet;
import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

import java.util.ArrayList;

/**
 * User: Tobias
 * Date: 26.09.2011
 */
public class TroveStrategy implements Strategy {
    private ArrayList<TLongHashSet> reachablePositions = new ArrayList<>();

    @Override
    public Solution solve(Board board, Long startPosition) {
        assembleReachablePositions(board, startPosition);
        removeDeadEnds(board);
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
            TLongIterator positionIterator = reachablePositions.get(pins).iterator();
            while (positionIterator.hasNext()) {
                long position = positionIterator.next();
                if (board.getConsecutivePositions(position).isEmpty()) {
                    positionIterator.remove();
                }
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
            reachablePositions.add(new TLongHashSet(1));
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
    private void assembleReachablePositions(Board board, TLongHashSet currentPositions,
                                            TLongHashSet followingPositions) {
        TLongIterator positionIterator = currentPositions.iterator();
        while (positionIterator.hasNext()) {
            long position = positionIterator.next();
            for (long consecutivePosition : board.getConsecutivePositions(position)) {
                if (followingPositions.contains(consecutivePosition)) {
                    continue;
                }
                //check if a symmetric position is already in the set
                boolean inSet = false;
                for (long symPos : board.getSymmetricPositions(consecutivePosition)) {
                    if (followingPositions.contains(symPos)) {
                        inSet = true;
                        break;
                    }
                }
                //add position if no equivalent position is already in set and the position is no dead end
                if (!inSet) {
                    followingPositions.add(consecutivePosition);
                }
            }
        }
    }
}
