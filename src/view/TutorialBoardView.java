package view;

import controller.GameController;
import model.Tutorial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a view for the tutorial board, which extends the BoardView class
 */
public class TutorialBoardView extends BoardView{

    /**
     * an integer represents the id of the tutorial
     */
    private int tutorialID;

    /**
     * Constructs a new instance of the TutorialBoardView class. Initializes the tutorial ID and adds a hint button to
     * the view.
     */
    public TutorialBoardView() {
        // call super constructor
        super();
        Tutorial tutorial = new Tutorial();
        tutorialID = 0;

        // add hint button
        JButton next = new JButton("Next");
        next.setBounds(218,230,65,40);
        next.setFocusable(false);
        add(next);
        setLayout(null);

        // attach mouse click event handler
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // set click attribute as true
                setClick(true);

                // reset previously highlighted nodes
                GameController.getInstance().getBoard().resetSelectedNodes();

                // print coordinates of click
                System.out.println("Click Coordinates: (" + e.getX() + ", " + e.getY() + ")");

                // pass to game controller for handling click event
                GameController.getInstance().processClick(e);

                // Create a timer with a delay of 1 seconds
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        // This code will be executed after the delay
                        GameController.getInstance().processComputerCommand();

                    }
                });

                // Start the timer
                timer.setRepeats(false); // Execute only once
                timer.start();

                // render the latest state of board
                update();
            }
        });

        // attach listener to next button
        next.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // retrieve gameView
                GameView gameView = GameController.getInstance().getGameView();

                // loop though all tutorial
                if (tutorialID < Tutorial.getTutorialCount()) {
                    // print tutorial id
                    System.out.println("Tutorial " + tutorialID);

                    // run tutorial and retrieve messages from tutorial
                    String msg = tutorial.runTutorial(tutorialID);
                    gameView.setMessage(msg);
                }

                // increment tutorial id
                tutorialID++;
                update();
            }
        });
    }
}
