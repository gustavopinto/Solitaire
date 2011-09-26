package org.solitaire.solver.strategy;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.set.hash.TLongHashSet;
import org.solitaire.board.Board;
import org.solitaire.board.BoardHelper;
import org.solitaire.solver.Solution;

import java.util.ArrayList;

/**
 * User: Tobias
 * Date: 26.09.2011
 */
public class AllSolutionsOptimizedStrategy implements Strategy {
    private ArrayList<TLongHashSet> reachablePositions = new ArrayList<>();

    @Override
    public Solution solve(Board board, Long startPosition) {
        assembleReachablePositions(board, startPosition);
        //TODO: remove dead ends, calculate all possible solutions
        return null;
    }

    /**
     * Assemble all reachable positions starting with startPosition.
     * Store positions in a local field
     * @param board the board where the positions live
     * @param startPosition  start finding positions with this start position
     */
    private void assembleReachablePositions(Board board, Long startPosition) {
        int numberOfStartPins = BoardHelper.getNumberOfPins(startPosition);

        for(int i=0;i <= numberOfStartPins+1;i++){
            reachablePositions.add(new TLongHashSet(1));
        }

        // add startPosition to reachablePositions
        reachablePositions.get(numberOfStartPins).add(startPosition);

        long totalTime = 0L;
        for(int numberOfRemainingPieces = numberOfStartPins - 1;
            // Since it does take too long, we do not run until the end
            (numberOfRemainingPieces > 0) && (numberOfRemainingPieces > 0);
            //(numberOfRemainingPieces > 0) && (numberOfRemainingPieces > 0);
            numberOfRemainingPieces--) {
            long start = System.currentTimeMillis();
            System.out.println("search positions with " + numberOfRemainingPieces + " of " + numberOfStartPins + " pins");
            TLongHashSet temp = reachablePositions.get(numberOfRemainingPieces + 1);
            TLongIterator it = temp.iterator();
            while(it.hasNext()) {
                long position = it.next();
                for(long consecutivePosition: board.getConsecutivePositions(position) ) {
                    boolean inSet = false;
                    for(long symPos: board.getSymmetricPositions(consecutivePosition) ) {
                        //check if a symmetric position is in the set already
                        if( reachablePositions.get(numberOfRemainingPieces).contains(symPos)) {
                            inSet = true;
                            break;
                        }
                    }
                    //add position if no equivalent positions is already in set
                    if(!inSet) {
                        reachablePositions.get(numberOfRemainingPieces).add(consecutivePosition);
                    }
                }
            }

            totalTime += (System.currentTimeMillis() - start);
            System.out.println("found *" + reachablePositions.get(numberOfRemainingPieces).size() + "* positions in " +
                    (System.currentTimeMillis() - start) + " ms \n");
        }
        System.out.println("TOTAL TIME REACHABLE POSITIONS: " + totalTime + " ms");

    }
}
