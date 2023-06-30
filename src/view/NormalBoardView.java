package view;

import controller.GameController;
import model.GameState;
import model.Node;
import model.ValidGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Represents a view for the normal board, which extends the BoardView class
 */
public class NormalBoardView extends BoardView{

    /**
     * Constructs a new instance of the NormalBoardView class. Initializes the normal board view when start the game.
     */
    public NormalBoardView() {
        // call super constructor
        super();

        // add hint button
        JButton hint = new JButton("Hint");
        hint.setBounds(220,230,60,40);
        hint.setFocusable(false);
        add(hint);
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

                // disable hint button
                if (GameController.getInstance().isPlayWithComputer()) {
                    if (GameController.getInstance().getCurrentPlayer() == GameController.getInstance().getSecondPlayer()) {
                        hint.setEnabled(false);
                    }
                }

                // Create a timer with a delay of 5 seconds
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {


                        // This code will be executed after the delay
                        GameController.getInstance().processComputerCommand();

                        // enable back hint button
                        hint.setEnabled(true);
                    }
                });

                // Start the timer
                timer.setRepeats(false); // Execute only once
                timer.start();

                // render the latest state of board
                update();

                if (GameController.getInstance().getCurrentPlayer().isComputer() && GameController.getInstance().getCurrentState() == GameState.REMOVE){
                    // Create a timer with a delay of 5 seconds
                    Timer timer2 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {


                            // This code will be executed after the delay
                            GameController.getInstance().processComputerCommand();

                            // enable back hint button
                            hint.setEnabled(true);
                        }
                    });
                    // Start the timer
                    timer2.setRepeats(false); // Execute only once
                    timer2.start();

                    // render the latest state of board
                    update();
                }
            }
        });

        // attach listener to hint button
        hint.addActionListener(new ActionListener() {

            // initialize hint toggle
            boolean toggle = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                // retrieve game controller
                GameController gameController = GameController.getInstance();

                // reset hint toggle
                toggle = !toggle;
                if (getClick()) {
                    toggle = true;
                    setClick(false);
                }

                // reset previously highlighted nodes
                gameController.getBoard().resetSelectedNodes();

                // retrieve current game state
                GameState currentState = gameController.getCurrentState();

                // store nodes to be highlighted
                ArrayList<Node> nodesToBeHighlighted = new ArrayList<>();

                // respond according to current game state
                switch (currentState) {
                    case SET:
                        // highlight node for set
                        nodesToBeHighlighted = ValidGenerator.getNodesForSet();
                        break;
                    case REMOVE:
                        // highlight node for remove
                        nodesToBeHighlighted = ValidGenerator.getNodesForRemove();
                        break;
                    case MOVE:
                        // highlight node for move
                        nodesToBeHighlighted = ValidGenerator.getNodesForMove();
                        break;
                    case JUMP:
                        // highlight node for jump
                        nodesToBeHighlighted = ValidGenerator.getNodesForJump();
                        break;
                }

                // highlight nodes
                if (toggle) {
                    for (Node node : nodesToBeHighlighted) {
                        node.setSelected(true);
                    }
                }
                update();

            }
        });
    }
}
