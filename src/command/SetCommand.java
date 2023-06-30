package command;

import model.Player;

/**
 * The SetCommand class implements the Command interface and its main responsibility is to execute
 * the set token logic provided by the Player class.
 */
public class SetCommand implements Command {
    /**
     * Player instance to execute the command
     */
    private Player player;

    /**
     * Constructor
     */
    public SetCommand(Player player) {
        this.player = player;
    }

    /**
     * Execute the set command for the current player
     * @return true if the command has been successfully executed, else false
     */
    @Override
    public boolean execute() {
        return player.set();
    }
}
