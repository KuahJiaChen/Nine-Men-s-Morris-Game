package controller;

import command.*;
import memento.GameStateMemento;
import model.*;
import view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The Board and GameView of the game, as well as the processing of user interactions, are all managed by
 * the GameController class. The GameController class generates the game and executes the appropriate
 * actions in response to user clicks. The GameController also keeps a Command instance that serves as a
 * representation of the game's current state and aids in carrying out the appropriate logic. The
 * GameController keeps track of the node that is now being clicked in order to determine which node
 * is being clicked and execute the proper game logic.
 */
public class GameController {
    /**
     * A singleton gameController instance
     */
    private static GameController gameController;

    /**
     * instance of the GameView class
     */
    private GameView gameView;

    /**
     * instance of the Board class
     */
    private Board board;

    /**
     * instance of the GameState class to indicate the current game state
     */
    private GameState currentState;

    /**
     * instance of the Player class to indicate the current player to perform the command
     */
    private Player currentPlayer;

    /**
     * instance of the Command class to indicate the current command to execute
     */
    private Command command;

    /**
     * instance of the Node class to indicate the current clicked node
     */
    private Node clickedNode;

    /**
     * an integer represents the SCALE when detecting the clicked node
     */
    private static final int SCALE = 70;

    /**
     * an integer represents the OFFSET when detecting the clicked node
     */
    private static final int OFFSET = 40;

    /**
     * an integer represents the CLICK_RADIUS when detecting the clicked node
     */
    private static final int CLICK_RADIUS = 55;

    /**
     * an integer represents the NUM_OF_PLAYERS in the game
     */
    private static final int NUM_OF_PLAYERS = 2;

    /**
     * a List to stores the Player instances
     */
    private Player[] players = new Player[NUM_OF_PLAYERS];

    /**
     * an Array List to stores the GameStateMemento instances
     */
    private List<GameStateMemento> gameStateMementos = new ArrayList<>();

    /**
     * a boolean to indicate if now playing with computer or not
     */
    private boolean playWithComputer = false;


    /**
     * Get the singleton instance of GameController
     * @return GameController singleton instance
     */
    public static GameController getInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }
        return gameController;
    }

    /**
     * Start the game by initializing all the necessary components and print out appropriate message
     */
    public void startGame(boolean playWithComputer) {
        // create board and game view
        board = new Board();
        gameView = new GameView(false);

        // create players
        players[0] = new HumanPlayer(TokenColor.WHITE);

        if (playWithComputer){
            players[1] = new ComputerPlayer(TokenColor.BLACK);
        }else{
            players[1] = new HumanPlayer(TokenColor.BLACK);
        }

        // set initial state and player
        currentPlayer = getFirstPlayer();
        setCurrentState(GameState.SET);

        // initial message displayed
        gameView.setMessage("<html>Game starts and goooood luck! " + currentPlayer.getTokenType() + " to set.<html>");

        // set playWithComputer attribute depending on value of input argument
        this.playWithComputer = playWithComputer;
    }

    /**
     * This method serves as the method to initiate a tutorial mode game. It will create a gameView and
     * boardView specifically for tutorials, which contains a “Next” button in the middle of the application.
     */
    public void startTutorial() {
        // create board and gameView one
        board = new Board();
        gameView = new GameView(true);

        players[0] = new HumanPlayer(TokenColor.WHITE);
        players[1] = new ComputerPlayer(TokenColor.BLACK);
        currentPlayer = getFirstPlayer();

        // Welcome message for tutorial
        gameView.setMessage("Welcome to tutorial. Click \"Next\" button to run through them.");
    }

    /**
     * Retrieve the node at click position
     * @param point represents the clicked position
     * @return the node within the click boundaries
     */
    private Node getNodeAtClickPos(Point point) {
        // find if there is any node within click boundaries
        for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
            for (Node node : nodeTriplet.getNodes()) {
                if (node.getX() * SCALE + OFFSET > point.getX() - CLICK_RADIUS
                        && node.getX() * SCALE + OFFSET < point.getX() + CLICK_RADIUS
                        && node.getY() * SCALE + OFFSET > point.getY() - CLICK_RADIUS
                        && node.getY() * SCALE + OFFSET < point.getY() + CLICK_RADIUS) {
                    return node;
                }
            }
        }
        // no such node
        return null;
    }

    /**
     * This method serves the purpose of determining the appropriate command to be executed based on the current
     * game state.
     */
    private void identifyCommandToExecute() {
        // respond according to current game state
        switch (currentState) {
            case SET:
                command = new SetCommand(currentPlayer);
                break;
            case REMOVE:
                command = new RemoveCommand(currentPlayer);
                break;
            case MOVE:
                command = null;
                // no prior token has been selected
                if (!currentPlayer.isComputer()) {
                    if (board.getSelectedNode() == null) {
                        if (clickedNode.isOccupied()) {
                            board.setSelectedNode(clickedNode);;
                            board.notifyObservers();
                        }
                    } else {
                        if (board.getSelectedNode() == clickedNode){
                            board.setSelectedNode(null);
                        }
                        else {
                            command = new MoveCommand(currentPlayer);
                        }
                }
                } else {
                    command = new MoveCommand(currentPlayer);
                }
                break;
            case JUMP:
                command = null;
                // no prior token has been selected
                if (!currentPlayer.isComputer()) {
                    if (board.getSelectedNode() == null) {
                        if (clickedNode.isOccupied()) {
                            board.setSelectedNode(clickedNode);;
                            board.notifyObservers();
                        }
                    } else {
                        if (board.getSelectedNode() == clickedNode){
                            board.setSelectedNode(null);
                        }
                        else {
                            command = new JumpCommand(currentPlayer);
                        }
                    }
                } else {
                    command = new JumpCommand(currentPlayer);
//                }
                }
                break;
        }
    }

    /**
     * This method is responsible for updating the game state based on the current situation of the game.
     * It executes the appropriate command and performs necessary verifications to determine the resulting
     * game state.
     */
    private void updateGameState() {
        // used to store command execution status
        boolean status = false;

        // used to store if mill exists
        boolean mill = false;

        // 1. execute command
        // 2. verify if mill exists after command execution
        if (command != null) {
            status = command.execute();
            if (status) {
                if (!currentPlayer.isComputer()){
                    mill = board.isMillExists(clickedNode, currentPlayer);
                } else{
                    ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
                    mill = board.isMillExists(computerPlayer.getExecutedNode(), currentPlayer);
                    computerPlayer.setExecutedNode(null);
                }
            } else {
                board.setSelectedNode(null);
            }
        }
        System.out.println("Current state" + currentState);
        System.out.println(getCurrentPlayer().getTokenColor());
        System.out.println(board.getSelectedNode());

        // 3. if mill exists and board has a removable token, switch to REMOVE state
        // 4. if mill does not exist but command executes successfully, switch player
        if (mill) {
            if (board.hasRemovableToken(currentPlayer)) {
                // save current game state to memento
                System.out.println("mill" + currentPlayer.getTokenColor());
                gameStateMementos.add(saveStateToMemento());
                currentState = GameState.REMOVE;
                gameView.setMessage(currentPlayer.getTokenType() + "'s turn to remove.");
            } else {
                switchPlayer();
                gameView.setMessage("All tokens are in mill, remove not available. " + currentPlayer.getTokenType() + "'s turn now.");
            }
        } else if (status) {
            // handle remove command execution
            // retrieve previous game state from memento
            if (gameStateMementos.size() > 0) {
                restoreStateFromMemento(gameStateMementos.remove(gameStateMementos.size() - 1));
            }
            switchPlayer();
            gameView.setMessage(currentPlayer.getTokenType() + "'s turn to set.");
        }

        if (currentState != GameState.REMOVE) {
            // verify if the game is ready to transition to MOVE
            if (isReadyToMove()) {
                currentState = GameState.MOVE;
                gameView.setMessage(currentPlayer.getTokenType() + "'s turn to move.");
            }
            // verify if the game is ready to transition to JUMP
            if (isReadyToJump()) {
                currentState = GameState.JUMP;
                gameView.setMessage(currentPlayer.getTokenType() + "'s turn to jump.");
            }
            // verify if the game is ready to end
            if (isReadyToEnd()) {
                switchPlayer();
                gameView.setMessage(currentPlayer.getTokenType() + " wins!");
                showRematchOption();
            }
        }
    }

    /**
     * Process the click event by the user. Corresponding command will be executed based on the user click
     * @param event provides information about the specific mouse event that occurred
     */
    public void processClick(MouseEvent event) {
        // get node at click position
        clickedNode = getNodeAtClickPos(event.getPoint());

        // if there is a node being clicked
        if (clickedNode != null) {
            identifyCommandToExecute();
            updateGameState();
            if (currentState == GameState.REMOVE){
                identifyCommandToExecute();
                updateGameState();
            }
        }
    }

    /**
     * This method is designed to handle the computer command based on the current game state and its
     * situation. Similar to the processClick method, it invokes the identifyCommandToExecute and
     * updateGameState methods to determine and execute the appropriate command.
     */
    public void processComputerCommand() {
        if (currentPlayer.isComputer()){
            identifyCommandToExecute();
            updateGameState();
            if (currentState == GameState.REMOVE){
                identifyCommandToExecute();
                updateGameState();
            }
        }
    }


    /**
     * Verify if the game should switch to MOVE. It achieves this by checking one conditions:
     * the all the tokens for both players have been placed on the board
     * @return true if the condition met, else false
     */
    private boolean isReadyToMove() {
        return players[0].getTokensToSet() == 0 && players[1].getTokensToSet() == 0;
    }

    /**
     * Verify if the game should switch to JUMP. It achieves this by checking two conditions:
     * the number of tokens on the board must be equal to three, and the player must not be in the
     * process of placing tokens.
     * @return true if the condition met, else false
     */
    private boolean isReadyToJump() {
        return currentState != GameState.SET && currentPlayer.getTokensOnBoard() == 3;
    }

    /**
     * Verify if the token still has any legal move for the current player.
     * @return true if there is at least one legal move else false
     */
    private boolean isThereLegalMove(){
        // for each node
        for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
            for (Node node : nodeTriplet.getNodes()) {
                // if the token color of the node is equal to the current player
                if(node.getTokenColor() == currentPlayer.getTokenColor()){
                    // check if any of the neighbor is empty
                    for (Node neighbour : node.getNeighbors()){
                        // if one of them is empty, it means there is at least one legal move
                        if (neighbour.isEmpty()){
                            return true;
                        }
                    }
                }
            }
        }
        // else no legal move at all
        return false;
    }

    /**
     * Verify if the game should end. It achieves this by checking two conditions:
     * the number of tokens on the board for any player must be less than three, the player
     * must not be in the process of placing tokens and the current player must not have
     * any legal move to perform.
     * @return true if the condition met, else false
     */
    private boolean isReadyToEnd() {
        if (currentState != GameState.SET && (players[0].getTokensOnBoard() < 3 || players[1].getTokensOnBoard() < 3 || !isThereLegalMove())) {
            System.out.println("p1"+players[0].getTokensOnBoard());
            System.out.println("p2"+players[1].getTokensOnBoard());
            System.out.println(isThereLegalMove());
            return true;
        }
        return false;
    }

    /**
     * Switch the player
     */
    private void switchPlayer() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
    }

    /**
     * Presenting a dialog box to the user when the game has ended. This dialog box displays a message
     * indicating that the game has ended and provides the option to initiate a rematch.
     */
    private void showRematchOption() {
        String[] options = {"Yes", "Play With Computer", "No"};
        if (players[1].isComputer()){
            options = new String[]{"Yes", "Play With Human", "No"};
        }
        int input = JOptionPane.showOptionDialog(null, "Do you want to play again?", "Rematch ?", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (input == 0) {
            restart();
        } else if (input == 1) {
            // Go back to the main menu
            // Add your logic here
            restart();
            if (players[1].isComputer()){
                rebuild(false);
            } else {
                rebuild(true);
            }
        } else {
            System.exit(0);
        }
    }

    /**
     * Facilitating a game restart by resetting important class attributes and notifying the user about
     * the restart process.
     */
    public void restart() {
        board.resetBoard();
        currentState = GameState.SET;
        currentPlayer = players[0];
        players[0].resetPlayerTokens();
        players[1].resetPlayerTokens();
        board.notifyObservers();
        gameView.setMessage("Game starts and goooood luck! " + currentPlayer.getTokenType() + " to set.");
    }

    /**
     * Rebuild a game with options to play against computer or human
     */
    public void rebuild(boolean vsComputer) {
        // Close the current GameView
        gameView.close();

        gameView = new GameView(false);
        if (vsComputer) {
            players[1] = new ComputerPlayer(TokenColor.BLACK);
        } else {
            players[1] = new HumanPlayer(TokenColor.BLACK);
        }
    }

    /**
     * Return the board of this GameController instance
     * @return a board instance
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return the clickedNode of this GameController instance
     * @return a Node instance
     */
    public Node getClickedNode() {
        return clickedNode;
    }

    /**
     * Return the firstPlayer of this GameController instance
     * @return a Player instance
     */
    public Player getFirstPlayer(){
        return players[0];
    }

    /**
     * Return the secondPlayer of this GameController instance
     * @return a Player instance
     */
    public Player getSecondPlayer(){
        return players[1];
    }

    /**
     * Return the currentPlayer of this GameController instance
     * @return a Player instance
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Return the gameView of this GameController instance
     * @return a GameView instance
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * Setter of currentState
     * @param currentState the GameState instance that represents the current state of the game
     */
    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    /**
     * Save current game state inside a memento
     * @return a GameStateMemento instance
     */
    private GameStateMemento saveStateToMemento() {
        return new GameStateMemento(currentState);
    }

    /**
     * Restore previous game state using a memento object
     * @param memento the GameStateMemento instance to restore previous game state
     */
    private void restoreStateFromMemento(GameStateMemento memento) {
        this.currentState = memento.getGameState();
    }

    /**
     * Return the currentState of this GameController instance
     * @return a GameState instance
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Return the playWithComputer of this GameController instance
     * @return a boolean instance
     */
    public boolean isPlayWithComputer() {
        return playWithComputer;
    }
}

