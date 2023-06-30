package model;

import controller.GameController;

import java.util.ArrayList;

/**
 * HumanPlayer will inherit the Player class. It will override all the relevant methods, the
 * implementation details are defined here where the user input, clickedNode will be used to navigate
 * the set, move, jump and remove.
 */
public class HumanPlayer extends Player {

    /**
     * Constructor
     * @param tokenColor the token color of the human player
     */
    public HumanPlayer(TokenColor tokenColor) {
        super(tokenColor);
    }

    /**
     * Perform the set logic for this human player
     * @return true if set command has been executed, else false
     */
    @Override
    public boolean set() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve node triplets from board
        Board board = gameController.getBoard();
        NodeTriplet[] nodeTriplets = board.getNodeTriplets();

        // retrieve clicked node
        Node clickedNode = gameController.getClickedNode();

        // get set index
        int setIndex = clickedNode.getIndexOnBoard();

        // search for node with targeted setIndex
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            for (Node node : nodeTriplet.getNodes()) {
                int boardIndex = node.getIndexOnBoard();
                boolean indexEmpty = node.isEmpty();
                if (boardIndex==setIndex && indexEmpty) {
                    TokenColor playerColor = getTokenColor();
                    node.setTokenColor(playerColor);
                    updateTokensCountOnSet();
                    board.notifyObservers();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Perform the move logic for this human player
     * @return true if move command has been executed, else false
     */
    @Override
    public boolean move() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve src and dest node
        Node src = board.getSelectedNode();

        Node dest = gameController.getClickedNode();

        boolean destIsEmpty = dest.isEmpty();
        ArrayList<Node> srcNeighbors = src.getNeighbors();
        TokenColor srcTokenColor = src.getTokenColor();
        Player currentPlayer = gameController.getCurrentPlayer();
        TokenColor currentPlayerTokenColor = currentPlayer.getTokenColor();

        // move token from src to dest
        if (destIsEmpty && srcNeighbors.contains(dest) && srcTokenColor == currentPlayerTokenColor) {
            dest.setTokenColor(srcTokenColor);
            src.setTokenColor(null);
            board.setSelectedNode(null);
            board.notifyObservers();
            return true;
        }

        return false;
    }

    /**
     * Perform the remove logic for this human player
     * @return true if remove command has been executed, else false
     */
    @Override
    public boolean remove() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve clicked node
        Node clickedNode = gameController.getClickedNode();

        // get remove index
        int removeIndex = clickedNode.getIndexOnBoard();

        // node to be removed
        Node nodeToBeRemoved = board.getNodeFromIndex(removeIndex);

        if (nodeToBeRemoved.isOccupied() &&
            nodeToBeRemoved.getTokenColor() != getTokenColor() &&
            !board.isMillExists(nodeToBeRemoved, board.getPlayerFromTokenColor(nodeToBeRemoved.getTokenColor()))) {
            board.getOpponentPlayer(this).decrementTokensOnBoard();
            return board.removeTokenFromIndex(removeIndex);
        }

        return false;
    }

    /**
     * Perform the jump logic for this human player
     * @return true if jump command has been executed, else false
     */
    @Override
    public boolean jump() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve src and dest node
        Node src = gameController.getBoard().getSelectedNode();
        Node dest = gameController.getClickedNode();

        // move token from src to dest
        if (dest.isEmpty() && src.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
            dest.setTokenColor(src.getTokenColor());
            src.setTokenColor(null);
            gameController.getBoard().setSelectedNode(null);
            gameController.getBoard().notifyObservers();
            return true;
        }

        return false;
    }

    /**
     * Determine if the current player is a computer player
     * @return true if it is a computer player, else false
     */
    @Override
    public boolean isComputer() {
        return false;
    }
}
