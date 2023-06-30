package command;

import model.Player;

/**
 * The MoveCommand class implements the Command interface and its main responsibility
 * is to execute the move token logic provided by the Player class.
 */
public class MoveCommand implements Command {
    /**
     * Player instance to execute the command
     */
    private Player player;

    /**
     * Constructor
     */
    public MoveCommand(Player player) {
        this.player = player;
    }

    /**
     * Execute the move command for the current player
     * @return true if the command has been successfully executed, else false
     */
    @Override
    public boolean execute() {
        return player.move();
    }
}
