package org.solitaire.board;

import java.util.ArrayList;
import java.util.Set;

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

    ArrayList<Long> getConsecutivePositions(long position);

    Set<Long> getConnectedMoveMasks();
}
