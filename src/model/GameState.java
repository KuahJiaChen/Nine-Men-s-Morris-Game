package model;

/**
 * The GameState class is an enumeration that stores constants used to indicate the current
 * game state during each iteration.
 */
public enum GameState {

    /**
     * use this constant to indicate current game state in set
     */
    SET,

    /**
     * use this constant to indicate current game state in remove
     */
    REMOVE,

    /**
     * use this constant to indicate current game state in move
     */
    MOVE,

    /**
     * use this constant to indicate current game state in jump
     */
    JUMP
}
