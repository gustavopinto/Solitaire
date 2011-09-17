package org.solitaire.board;

/**
 * User: Tobias
 * Date: 17.09.2011
 */
public interface Board {
    Integer getColumns();

    Integer getNumberOfFields();

    Integer getRows();

    Long getLayout();

    Long getStartPosition();
}
