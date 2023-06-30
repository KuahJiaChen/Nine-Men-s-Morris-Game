package observer;

/**
 * The observer class in Java defines an interface that serves as a blueprint for other classes
 * to implement a set of required methods.
 */
public interface Observer {
    /**
     * Invoked to notify the BoardView class of any changes in the user interface elements
     */
    void update();
}
