package org.solitaire.board;

/**
 * User: Tobias
 * Date: 17.09.2011
 */

public class BoardFactory {
    public static Board createBoard(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class concreteBoardClass = Class.forName(className);

        // Reminder: this does work for classes with default constructor only
        return (Board)concreteBoardClass.newInstance();
    }
}
