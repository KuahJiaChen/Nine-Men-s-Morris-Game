package memento;

import model.GameState;

/**
 * This class applies the memento design pattern to store a snapshot of the game state at a specific point
 * in time. It serves as a container for capturing and preserving the state of the game.
 */
public class GameStateMemento {
    /**
     * GameState instance to store the game state at a specific point in time
     */
    private GameState gameState;

    /**
     * Constructor
     */
    public GameStateMemento(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Return the gameState of the GameStateMemento instance
     * @return a GameState instance
     */
    public GameState getGameState() {
        return gameState;
    }
}
