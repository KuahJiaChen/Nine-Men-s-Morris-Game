package command;

import model.Player;

/**
 * The RemoveCommand class implements the Command interface and its main responsibility is to
 * execute the remove token logic provided by the Player class.
 */
public class RemoveCommand implements Command {
    /**
     * Player instance to execute the command
     */
    private Player player;

    /**
     * Constructor
     */
    public RemoveCommand(Player player) {
        this.player = player;
    }

    /**
     * Execute the remove command for the current player
     * @return true if the command has been successfully executed, else false
     */
    @Override
    public boolean execute() {
        return player.remove();
    }
}
