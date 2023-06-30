package view;

import javax.swing.*;
import java.awt.*;

/**
 * The GameView class is responsible for creating the user interface using Java Swing. It combines the
 * BoardView and MessageView classes to provide a complete interface for users to play the game.
 */
public class GameView {
    /**
     * an integer represents the width of the game view
     */
    private static final int WIDTH = 500;

    /**
     * an integer represents the height of the game view
     */
    private static final int HEIGHT = 640;

    /**
     * a JFrame instance that represents the frame of the game
     */
    private JFrame frame;

    /**
     * a BoardView instance that represents the board view
     */
    private BoardView boardView;

    /**
     * a MessageView instance that represents the message view
     */
    private MessageView messageView;

    /**
     * Constructor
     */
    public GameView(boolean isTutorial) {
        // create board and message view
        if (isTutorial) {
            boardView = new TutorialBoardView();
        }
        else {
            boardView = new NormalBoardView();
        }
        messageView = new MessageView();

        // setup game view
        frame = new JFrame();

        // handle size
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        frame.setSize(dimension);
        frame.setPreferredSize(dimension);
        frame.setResizable(false);

        // handle layout
        frame.setLayout(new BorderLayout());

        // attach board and message view
        frame.getContentPane().add(boardView, BorderLayout.CENTER);
        frame.getContentPane().add(messageView, BorderLayout.PAGE_END);

        // other properties
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Nine Men Morris");

        // show frame
        frame.setVisible(true);
    }

    /**
     * Show the message on the message panel
     * @param text the message to be show
     */
    public void setMessage(String text) {
        messageView.setText(text);
    }

    public void close() {
        frame.dispose();
    }
}
