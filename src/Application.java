import controller.GameController;
import view.InitialPageView;

import javax.swing.*;

/**
 * Application class acts as the main class which runs the program.
 * It will get an instance from the gameController and start the game.
 */
public class Application {

    /**
     * The main method to start the game
     * @param args -
     */
    public static void main(String[] args) {
        // start game
//        GameController gameController = GameController.getInstance();
//        gameController.startGame();
        // Create an instance of InitialPageView
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InitialPageView();
            }
        });
    }

}
