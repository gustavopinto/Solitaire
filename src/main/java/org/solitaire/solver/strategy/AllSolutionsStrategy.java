package org.solitaire.solver.strategy;

import org.solitaire.board.Board;
import org.solitaire.solver.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This strategy will assemble all winning positions (modulo symmetry) and calculate all
 * possible solutions. The solution returned will be one with the lowest number of moves.
 * User: Tobias
 * Date: 26.09.2011
 */
public class AllSolutionsStrategy implements Strategy {

    // the list of position sets: sets for positions with the same number of pegs
    // Using HashSet for storing the positions sets is much faster than ArrayList since we use _contains_ a lot
    private ArrayList<HashSet<Long>> reachablePositions;

    /**
     * initialize the list of position sets
     */
    private void init() {
        reachablePositions = new ArrayList<>();
    }

    @Override
    public Solution solve(Board board, Long startPosition) {

        // assemble and store all winning positions in the member reachablePositions
        getWinningPositions(board, startPosition);

        // return if no winning position is found
        if (reachablePositions.get(1).isEmpty()) {
            System.out.println("NO SOLUTION FOUND!");
            return null;
        }

        // count number of solutions
        Long start = System.currentTimeMillis();
        long numberOfSolutions = countSolutions(board);
        System.out.println("\n Number of solutions: " + numberOfSolutions + " (" + (System.currentTimeMillis() - start) + " ms )");

        // TODO: this does take too long for the english board
        // get the set of all possible solutions
        HashSet<Solution> solutions = getSolutions(board, startPosition);

        // traverse all solutions and find one with the least number of moves
        Solution leastMovesSolution = solutions.iterator().next();
        Integer leastNumberOfMoves = leastMovesSolution.countMoves(board);
        for (Solution solution : solutions) {
            int numberOfMoves = solution.countMoves(board);
            if (numberOfMoves < leastNumberOfMoves) {
                leastMovesSolution = solution;
                leastNumberOfMoves = numberOfMoves;
            }
        }
        return leastMovesSolution;
    }

    /**
     * Assemble all solutions by using a recursive depth first method
     *
     * @param board         the board, the solutions live on
     * @param startPosition assemble solutions for this start position
     * @return the set of all solutions
     */
    private HashSet<Solution> getSolutions(Board board, Long startPosition) {

        HashSet<Solution> solutions = new HashSet<>();

        // initialize a first solution with the start position and add it to the solution set
        Solution firstSolution = new Solution();
        firstSolution.add(startPosition);
        solutions.add(new Solution().add(startPosition));


        // assemble all solutions by recursion
        Long start = System.currentTimeMillis();
        solutions = getSolutions(startPosition, solutions, board.getNumberOfPegs(startPosition), board);
        System.out.println("Assembled " + solutions.size() + " solutions " + " (" + (System.currentTimeMillis() - start) + " ms )");
        return solutions;
    }

    /**
     * Assemble all winning positions for the start position on the board
     * A position is a winning position if it is part of a solution
     *
     * @param board         the board, the positions live on
     * @param startPosition the start position
     * @return the set of winning positions
     */
    private ArrayList<HashSet<Long>> getWinningPositions(Board board, Long startPosition) {

        init();

        // assemble all reachable positions and store them
        assembleReachablePositions(board, startPosition);

        // return if no winning position is found
        if (reachablePositions.get(1).isEmpty()) {
            return reachablePositions;
        }

        Long start = System.currentTimeMillis();

        // remove positions which are not part of a solution
        removeNonWinningPositions(board);
        System.out.println("\n\n TOTAl TIME winning positions: " + (System.currentTimeMillis() - start) + " ms");

        return reachablePositions;
    }

    /**
     * remove all positions which are not part of a solution from the set of reachable positions
     * respects symmetry
     *
     * @param board the board the positions live on
     */
    private void removeNonWinningPositions(Board board) {

        // the positions in the set of reachable positions with one peg are all winning positions
        // we start with the positions with two pegs and remove those, which have no consecutive position
        // in the position set with one peg
        for (int pegs = 2; pegs < reachablePositions.size(); pegs++) {

            Long start = System.currentTimeMillis();
            HashSet<Long> winningPositions = new HashSet<>();

            for (Long position : reachablePositions.get(pegs)) {
                boolean hasConsecutivePosition = false;
                for (Long consecutivePosition : board.getConsecutivePositions(position)) {
                    for (Long symmetricPosition : board.getSymmetricPositions(consecutivePosition)) {

                        // if the consecutivePosition or one of it's symmetries is found in the set of winning positions,
                        // it is a winning position
                        if (reachablePositions.get(pegs - 1).contains(symmetricPosition)) {
                            winningPositions.add(position);
                            hasConsecutivePosition = true;
                            break;
                        }
                    }
                    if (hasConsecutivePosition) {
                        break;
                    }
                }
            }

            // replace set of reachable positions with set of winning positions
            reachablePositions.set(pegs, winningPositions);

            System.out.println("Found " + winningPositions.size() + " winning positions with " + pegs + " pegs. (" + (System.currentTimeMillis() - start) + "ms)");
        }
    }

    /**
     * Count the number of solutions
     * Breadth first algorithm through tree
     * For each position the number of solutions for this position is stored.
     * The number of solutions for a position ist just the sum of the solution-counts of its preceding positions
     *
     * @param board the board, the solutions live on
     * @return the number of solutions modulo symmetry
     */
    private Long countSolutions(Board board) {

        HashMap<Long, Long> currentPositionMap;
        HashMap<Long, Long> consecutivePositionMap = new HashMap<>();

        // initialize the start position (root) with count one, since the start position has no preceding position
        for (Long position : reachablePositions.get(reachablePositions.size() - 1)) {
            consecutivePositionMap.put(position, 1L);
        }


        for (int pegs = reachablePositions.size() - 1; pegs > 1; pegs--) {
            HashSet<Long> currentPositions = reachablePositions.get(pegs);
            HashSet<Long> consecutivePositions = reachablePositions.get(pegs - 1);

            // swap the maps
            currentPositionMap = consecutivePositionMap;

            // initialize solution counts for the set of consecutive positions
            consecutivePositionMap = new HashMap<>(consecutivePositions.size());
            for (Long position : consecutivePositions) {
                consecutivePositionMap.put(position, 0L);
            }

            // if the position has a consecutive position in the set of winning positions, sum up the solution counts
            for (Long position : currentPositions) {
                for (Long consecutivePosition : getConsecutivePositionsInSet(position, board, consecutivePositions)) {
                    consecutivePositionMap.put(consecutivePosition, currentPositionMap.get(position) + consecutivePositionMap.get(consecutivePosition));
                }
            }
        }

        // the number of solutions is the sum of all counts for all end positions
        long numberOfSolutions = 0L;
        for (Long count : consecutivePositionMap.values()) {
            numberOfSolutions += count;
        }
        return numberOfSolutions;
    }

    /**
     * Recursive depth first search method for assembling all solutions.
     *
     * @param startPosition        find solutions for this start position
     * @param solutionsForPosition the intermediate solution set
     * @param numberOfPegs         for recursion: which set of positions to use
     * @param board                board on which the positions live
     * @return the set of all solutions
     */
    private HashSet<Solution> getSolutions(Long startPosition, HashSet<Solution> solutionsForPosition, int numberOfPegs, Board board) {
        if (numberOfPegs == 1) {
            return solutionsForPosition;
        }
        HashSet<Solution> resultingSolutions = new HashSet<>();
        for (Long consecutivePosition : getConsecutivePositionsInSet(startPosition, board, reachablePositions.get(numberOfPegs - 1))) {
            // deep copy solution set
            HashSet<Solution> tempSolutions = copy(solutionsForPosition);
            // add consecutivePosition to each solution
            for (Solution solution : tempSolutions) {
                solution.add(consecutivePosition);
            }
            // call getSolutions recursive and add solutions to result
            for (Solution solution : getSolutions(consecutivePosition, tempSolutions, numberOfPegs - 1, board)) {
                resultingSolutions.add(solution);
            }
        }
        return resultingSolutions;
    }


    /**
     * Deep copy the given set of solutions
     *
     * @param solutions the set of solutions to copy
     * @return the copy of the solution set
     */
    private HashSet<Solution> copy(HashSet<Solution> solutions) {
        HashSet<Solution> copy = new HashSet<>();
        for (Solution solution : solutions) {
            Solution newSolution = new Solution(solution);
            copy.add(newSolution);
        }
        return copy;
    }

    /**
     * Get all followers of the start position which are in the position set positions
     *
     * @param startPosition we are looking for consecutive positions of this start position
     * @param board         the board, the positions live on
     * @param positions     the returned positions need to be in this position set
     * @return set of followers of start position which are in the position set positions
     */
    private Set<Long> getConsecutivePositionsInSet(Long startPosition, Board board, HashSet<Long> positions) {
        Set<Long> consecutivePositions = new HashSet<>();
        for (Long consecutivePosition : board.getConsecutivePositions(startPosition)) {
            for (Long symPos : board.getSymmetricPositions(consecutivePosition)) {
                if (positions.contains(symPos)) {
                    consecutivePositions.add(symPos);
                    break;
                }
            }
        }
        return consecutivePositions;
    }

    /**
     * Assemble all reachable positions starting with startPosition.
     * Store positions in a local field
     *
     * @param board         the board where the positions live
     * @param startPosition start finding positions with this start position
     */
    private void assembleReachablePositions(Board board, Long startPosition) {
        int numberOfStartPegs = board.getNumberOfPegs(startPosition);

        for (int i = 0; i <= numberOfStartPegs; i++) {
            reachablePositions.add(new HashSet<Long>());
        }

        // add startPosition to reachablePositions
        reachablePositions.get(numberOfStartPegs).add(startPosition);

        long totalTime = 0L;
        for (int numberOfRemainingPegs = numberOfStartPegs - 1; numberOfRemainingPegs > 0; numberOfRemainingPegs--) {
            long start = System.currentTimeMillis();
            System.out.print("search positions with " + numberOfRemainingPegs + " of " + numberOfStartPegs + " pegs: ");

            assembleReachablePositions(board, reachablePositions.get(numberOfRemainingPegs + 1),
                    reachablePositions.get(numberOfRemainingPegs));

            totalTime += (System.currentTimeMillis() - start);
            System.out.println("found *" + reachablePositions.get(numberOfRemainingPegs).size() + "* positions (" +
                    (System.currentTimeMillis() - start) + " ms)");
        }
        System.out.println("\n TOTAL TIME REACHABLE POSITIONS: " + totalTime + " ms");

    }

    /**
     * Assemble and store all positions which follow the current positions (modulo symmetry)
     *
     * @param board                the board, the positions live on
     * @param currentPositions     positions found for a certain number of pegs
     * @param consecutivePositions all positions which can follow the current positions (modulo symmetry)
     */
    private void assembleReachablePositions(Board board, HashSet<Long> currentPositions, HashSet<Long> consecutivePositions) {

        // for each position in the set of current positions find the consecutive positions
        // and add them to the set of consecutive positions (modulo symmetry)
        for (Long currentPosition : currentPositions) {
            for (Long consecutivePosition : board.getConsecutivePositions(currentPosition)) {

                // position is already in set
                if (consecutivePositions.contains(consecutivePosition)) {
                    continue;
                }

                // check if a symmetric position is already in set
                boolean inSet = false;
                for (Long symPos : board.getSymmetricPositions(consecutivePosition)) {
                    if (consecutivePositions.contains(symPos)) {
                        inSet = true;
                        break;
                    }
                }

                //add position if no equivalent (symmetric) position is already in set
                if (!inSet) {
                    consecutivePositions.add(consecutivePosition);
                }
            }
        }
    }
}
