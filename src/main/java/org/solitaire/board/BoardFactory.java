package org.solitaire.board;

/**
 * This factory is a convenience class used to create certain kinds of boards without knowledge
 * of the underlying board implementation. You can always create boards directly if you might want to use your own boards.
 * The class uses the singleton pattern, since we do not need more than on one instance.
 * The boards are defined by String boardID. Possible values for boardID are "english" and "quadratic" at the moment.
 * Quadratic boards have an additional constructor parameter "size" => two different createBoard()-methods.
 * This class violates the Open/Closed-Principle since for new board-types this class needs to be changed.
 * On the other hand it is easy to maintain and extend.
 * <p/>
 * Thoughts about Factory Patterns:
 * * AbstractFactory:  does not suit the problem here, since it is usually uses composition, which we do not need here
 * ( no combined products)
 * * Factory Method:  Usually violates the Open/Closed-Principle.
 * Additionally since there is no easy self-registration of products (except via reflection) the client
 * needs to have knowledge of the concrete product classes (or concrete factories).
 * Construction parameters do not seem to go very well with this pattern.
 * <p/>
 * TODO: it would be nice, if the factory could return a list of possible board types.
 * User: Tobias
 * Date: 17.09.2011
 */

public class BoardFactory {

    // the only instance of the singleton
    private static BoardFactory instance = null;

    /**
     * Singleton pattern: private default constructor -> no instantiation except in the class itself.
     */
    private BoardFactory() {

    }

    /**
     * Singleton pattern: static method which returns the only instance of this class
     * @return the only instance of this factory
     */
    public static BoardFactory getInstance() {
        if (instance == null) {
            instance = new BoardFactory();
        }
        return instance;
    }

    /**
     * Board creation of english boards. Use the boardID "english" to create a board.
     *
     * @param boardID "english"
     * @return an english board if the boardID matches, null otherwise
     */
    public Board createBoard(String boardID) {
        switch (boardID) {
            case "english":
                return new EnglishBoard();
        }
        return null;
    }

    /**
     * board creation of quadratic boards. Use the boardID "quadratic" and a size to create a quadratic board.
     *
     * @param boardID "quadratic"
     * @param size    the size of the quadratic board.
     * @return a quadratic board if the boardID matches, null otherwise
     */
    public Board createBoard(String boardID, Integer size) {
        if (size == null || size < 3) {
            return null;
        }
        switch (boardID) {
            case "quadratic":
                return new QuadraticBoard(size);
        }
        return null;
    }
}
