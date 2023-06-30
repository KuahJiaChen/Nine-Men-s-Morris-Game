package command;

/**
 * The Command class is an interface that enables the use of the Command design pattern in our system.
 * This pattern brings various benefits to our design. Since we may need to introduce additional types
 * of commands in the future, we have implemented this interface to allow for extension.
 */
public interface Command {

    /**
     * Execute the command based on the current game state
     * @return true if the command has been successfully executed, else false
     */
    boolean execute();
}
