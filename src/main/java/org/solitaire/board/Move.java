package org.solitaire.board;

/**
 * This class represents a move on a solitaire board. The constructor sets up all fields which are available via getters then.
 * The masks can be used to detect if a a move is possible for a position and to apply this move to a position.
 * end and start are used to detect if moves are connected.
 *
 * User: Tobias
 * Date: 20.09.2011
 */
public class Move {

    // the mask contains all three pegs
    private long mask;

    // the check mask contains the first and the second peg
    private long check;

    // the moving peg's position before the move
    private long start;

    // the moving peg's position after the move
    private long end;

    // the id of the moving peg
    private int startID;

    // the id of the moving peg after the move
    private int endID;

    /**
     *
     * @param start index of the moving peg
     * @param center index of the peg which will be removed after the move
     * @param end index of the peg after the move
     */
    public Move(int start, int center, int end) {
        // the mask contains all three pegs
        this.mask = (1L << start) | (1L << center) | (1L << end);
        // the check mask contains the first and the second peg
        this.check = (1L << start) | (1L << center);
        this.start = 1L << start;
        this.end = 1L << end;
        this.startID = start;
        this.endID = end;
    }

    /**
     * Getter for the move mask
     * @return the move mask
     */
    public long getMask() {
        return mask;
    }

    /**
     * Getter for the check mask
     * @return the check mask
     */
    public long getCheck() {
        return check;
    }

    /**
     * Getter for the moving peg's position before the move
     * @return the moving peg's position before the move
     */
    public long getStart() {
        return start;
    }

    /**
     * Getter for the moving peg's position after the move
     * @return the moving peg's position after the move
     */
    public long getEnd() {
        return end;
    }

    /**
     * Getter for the start id
     * @return the id of the position of the moving peg
     */
    public int getStartID() {
        return startID;
    }

    /**
     * Getter for the end id
     * @return  the id of the moving peg after the move
     */
    public int getEndID() {
        return endID;
    }
}
