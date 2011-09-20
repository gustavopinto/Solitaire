package org.solitaire.board;

/**
 * User: Tobias
 * Date: 20.09.2011
 */
public class Move {
    private long mask;
    private long check;
    private long start;
    private long end;
    private int startID;
    private int endID;

    public Move(int start, int center, int end) {
        this.mask = (1L << start) | (1L << center) | (1L << end);
        this.check = (1L << start) | (1L << center);
        this.start = 1L << start;
        this.end = 1L << end;
        this.startID = start;
        this.endID = end;
    }

    public long getMask() {
        return mask;
    }

    public void setMask(long mask) {
        this.mask = mask;
    }

    public long getCheck() {
        return check;
    }

    public void setCheck(long check) {
        this.check = check;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public Integer getStartID() {
        return startID;
    }

    public void setStartID(Integer startID) {
        this.startID = startID;
    }

    public Integer getEndID() {
        return endID;
    }

    public void setEndID(Integer endID) {
        this.endID = endID;
    }
}
