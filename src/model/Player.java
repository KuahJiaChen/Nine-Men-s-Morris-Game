package model;

/**
 * The player is defined as an abstract class to provide abstraction. The spectrum of what a player can
 * do are fixed, this includes set, move, jump and remove. Any class that inherits this class cannot add
 * other capabilities other than the fundamental capabilities of the player. However, they can extend and
 * override abstract methods to define how different types of players will perform each action.
 */
public abstract class Player {
    /**
     * an integer represents the initial number of token to set
     */
    private static final int INIT_NUM_OF_TOKENS_TO_SET = 9;

    /**
     * an integer represents the initial number of token on board
     */
    private static final int INIT_NUM_OF_TOKENS_ON_BOARD = 0;

    /**
     * an integer represents the number of token to set
     */
    private int tokensToSet;

    /**
     * an integer represents the number of token on board
     */
    private int tokensOnBoard;

    /**
     * a TokenColor instance that represents the tokenColor
     */
    private TokenColor tokenColor;


    /**
     * Constructor
     * @param tokenColor the token color of the constructor
     */
    public Player(TokenColor tokenColor) {
        this.tokenColor = tokenColor;
        this.tokensToSet = INIT_NUM_OF_TOKENS_TO_SET;
        this.tokensOnBoard = INIT_NUM_OF_TOKENS_ON_BOARD;
    }

    /**
     * Update number of tokens (SET)
     */
    public void updateTokensCountOnSet() {
        tokensToSet--;
        tokensOnBoard++;
    }

    /**
     * Decrement number of tokens (REMOVE)
     */
    public void decrementTokensOnBoard() {
        tokensOnBoard--;
    }

    /**
     * Reset player tokens
     */
    public void resetPlayerTokens(){
        this.tokensToSet = INIT_NUM_OF_TOKENS_TO_SET;
        this.tokensOnBoard = INIT_NUM_OF_TOKENS_ON_BOARD;
    }

    /**
     * Set tokens on board
     */
    public void setTokensOnBoard(int tokensOnBoard) {
        this.tokensOnBoard = tokensOnBoard;
    }

    /**
     * Set tokens left to set
     */
    public void setTokensToSet(int tokensToSet) {
        this.tokensToSet = tokensToSet;
    }

    /**
     * Retrieve the number of tokens to set
     * @return an integer represents the number of tokens to set
     */
    public int getTokensToSet() {
        return tokensToSet;
    }

    /**
     * Retrieve the number of tokens on board
     * @return an integer represents the number of tokens on board
     */
    public int getTokensOnBoard() {
        return tokensOnBoard;
    }

    /**
     * Retrieve the token color of the player
     * @return a TokenColor instance represents the token color of the player
     */
    public TokenColor getTokenColor() {
        return tokenColor;
    }

    /**
     * Retrieve the token type of the player
     * @return a String represents the token type of the player
     */
    public String getTokenType() {
        String type = null;
        if (getTokenColor() == tokenColor.WHITE) {
            type = "Star";
        }
        else {
            type = "Moon";
        }
        return type;
    }

    /**
     * Perform the set logic of the Player
     * @return true if the set command is executed, else false
     */
    public abstract boolean set();

    /**
     * Perform the move logic of the Player
     * @return true if the move command is executed, else false
     */
    public abstract boolean move();

    /**
     * Perform the remove logic of the Player
     * @return true if the remove command is executed, else false
     */
    public abstract boolean remove();

    /**
     * Perform the jump logic of the Player
     * @return true if the jump command is executed, else false
     */
    public abstract boolean jump();

    /**
     * Determine if the player is a computer player
     * @return true if the player is a computer player, else false
     */
    public abstract boolean isComputer();

}
