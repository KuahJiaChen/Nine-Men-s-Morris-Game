package command;

import model.Player;

/**
 * The JumpCommand class implements the Command interface and its main responsibility is to execute
 * the jump token logic provided by the Player class.
 */
public class JumpCommand implements Command {
    /**
     * Player instance to execute the command
     */
    private Player player;

    /**
     * Constructor
     */
    public JumpCommand(Player player) {
        this.player = player;
    }

    /**
     * Execute the jump command for the current player
     * @return true if the command has been successfully executed, else false
     */
    @Override
    public boolean execute() {
        return player.jump();
    }
}
