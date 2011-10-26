package org.solitaire.board;

/**
 * User: Tobias
 * Date: 17.09.2011
 */

public class BoardFactory {
    public static Board createBoard(String boardName) {
        switch (boardName) {
            case "english":
                return new EnglishBoard();
        }
        return null;
    }
}
