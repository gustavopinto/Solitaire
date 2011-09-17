package org.solitaire.solver;

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
        for(int i = 0; i < this.solution.length; i++){
            this.solution[i] = 0L;
        }
    }

    long[] getSolution() {
        return this.solution;
    }

    /**
     * This method may be used for printing the solution.
     * The first position in the list will be the start position
     * and the last position in the list is the end position.
     * @return the reversed solution array as list
     */
    public List<Long> getSolutionAsList() {
        List<Long> solutionList = new ArrayList<>();
        for(long position :  this.solution){
            solutionList.add(position);
        }
        Collections.reverse(solutionList);
        return solutionList;
    }
}
