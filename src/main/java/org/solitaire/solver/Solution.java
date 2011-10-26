package org.solitaire.solver;

import org.solitaire.board.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class Solution {
    private long[] solution;

    public Solution(int size) {
        this.solution = new long[size];
        for (int i = 0; i < this.solution.length; i++) {
            this.solution[i] = 0L;
        }
    }

    public long[] getSolution() {
        return this.solution;
    }

    /**
     * This method may be used for printing the solution.
     * The first position in the list will be the start position
     * and the last position in the list is the end position.
     *
     * @return the reversed solution array as list
     */
    public List<Long> getSolutionAsList() {
        List<Long> solutionList = new ArrayList<>();
        for (long position : this.solution) {
            solutionList.add(position);
        }
        Collections.reverse(solutionList);
        return solutionList;
    }

    public void setSolution(long[] solution) {
        this.solution = solution;
    }

    public Integer countMoves(Board board) {
        //first move is always a new move
        int numMoves = 1;
        for (int i = this.getSolution().length - 1; i > 1; i--) {
            long pos1 = this.getSolution()[i];
            long pos2 = this.getSolution()[i - 1];
            long pos3 = this.getSolution()[i - 2];

            long mask = pos1 ^ pos3;
            if ((((pos1 ^ pos2) | (pos2 ^ pos3)) & (pos1 & pos3)) != 0L || !board.getConnectedMoveMasks().contains(mask)) {
                numMoves++;
            }
        }
        return numMoves;
    }

}
