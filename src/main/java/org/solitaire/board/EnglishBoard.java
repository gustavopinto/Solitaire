package org.solitaire.board;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public class EnglishBoard implements Board {
    @Override
    public Integer getColumns() {
        return 7;
    }

    @Override
    public Integer getNumberOfFields() {
        return 49;
    }

    @Override
    public Integer getRows() {
        return 7;
    }

    @Override
    public Long getLayout() {
        return 0B0011100_0011100_1111111_1111111_1111111_0011100_0011100L;
    }

    @Override
    public Long getStartPosition() {
        return 0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L;
    }
}
