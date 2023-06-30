package view;

import controller.GameController;
import model.*;
import observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The BoardView class is a critical component of the application's front-end, responsible for rendering
 * the game board using Java Swing. To achieve this, it extends the JPanel class to set the size and color
 * of the board. Additionally, it implements the Observer interface, allowing all UI components to register
 * as observers of the model. This enables the BoardView to receive automatic notifications whenever there
 * is a change to the tokens, ensuring that the board is always up-to-date in real time.
 */
public abstract class BoardView extends JPanel implements Observer {
    /**
     * an integer represents the width of the board
     */
    private static final int WIDTH = 500;

    /**
     * an integer represents the height of the board
     */
    private static final int HEIGHT = 500;

    /**
     * an integer represents the distance between nodes
     */
    private static final int SCALE = 70;

    /**
     * an integer represents the grid margin offset
     */
    private static final int OFFSET = 40;

    /**
     * an integer represents the diameter of the token
     */
    private static final int TOKEN_DIAMETER = 30;

    /**
     * an integer represents the distance between tokens
     */
    private static final int DISTANCE_BETWEEN_TOKENS = 22;

    /**
     * an integer represents the distance from border for x
     */
    private static final int DISTANCE_FROM_BORDER_FOR_X = 40;

    /**
     * an integer represents the distance from border for y
     */
    private static final int DISTANCE_FROM_BORDER_FOR_Y = 20;

    /**
     * a BufferedImage instance represents the image of the star
     */
    private BufferedImage imgStar;

    /**
     * a BufferedImage instance represents the image of the star
     */
    private BufferedImage imgMoon;

    /**
     * a BufferedImage instance represents the current image
     */
    private BufferedImage currentImg;

    /**
     * a BufferedImage instance represents the image of the background
     */
    private BufferedImage background;

    /**
     * a boolean variable storing information on whether the board is being clicked currently
     */
    private boolean click;

    /**
     * Constructor
     */
    public BoardView() {
        // handle size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // attach board view to board model
        GameController.getInstance().getBoard().attach(this);
    }

    /**
     * Render a token on a node
     * @param g the Graphics2D instance
     * @param node the node instance to check
     */
    private void renderTokenOnNode(Graphics2D g, Node node) {
        // there is a token in current node
        if (node.isOccupied()) {

            // set color
            if (node.getTokenColor() == TokenColor.WHITE) {
                currentImg = imgStar;

            } else {
                currentImg = imgMoon;
            }

            // change the opacity of the selected node
            if (GameController.getInstance().getBoard().getSelectedNode() == node &&
            GameController.getInstance().getCurrentPlayer().getTokenColor() == node.getTokenColor()){

                // Create a new AlphaComposite with the desired opacity value (0.0 - 1.0)
                float opacity = 0.5f; // 50% opacity
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);

                // Set the AlphaComposite on the graphics object
                g.setComposite(alphaComposite);

                g.drawImage(currentImg, node.getX() * SCALE + OFFSET - 45 / 2,
                        node.getY() * SCALE + OFFSET - 38 / 2,
                        TOKEN_DIAMETER + 10, TOKEN_DIAMETER + 10, this);

                // Reset the AlphaComposite to default
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            } else {

            g.drawImage(currentImg, node.getX() * SCALE + OFFSET - 45 / 2,
                    node.getY() * SCALE + OFFSET - 38 / 2,
                    TOKEN_DIAMETER + 10, TOKEN_DIAMETER + 10, this);

            }

        }

        Player firstPlayer = GameController.getInstance().getFirstPlayer();
        Player secondPlayer = GameController.getInstance().getSecondPlayer();

        for (NodeTriplet nodeTriplet : GameController.getInstance().getBoard().getNodeTriplets()) {
            if (nodeTriplet.getPlayerColorIfMill() == firstPlayer.getTokenColor() && nodeTriplet.containsNode(node)) {
                // Create a new AlphaComposite with the desired opacity value (0.0 - 1.0)

                g.drawImage(currentImg, node.getX() * SCALE + OFFSET - 45 / 2,
                        node.getY() * SCALE + OFFSET - 38 / 2,
                        TOKEN_DIAMETER + 20, TOKEN_DIAMETER + 20, this);

                float opacity = 0.5f; // 50% opacity
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);

                // Set the AlphaComposite on the graphics object
                g.setComposite(alphaComposite);

                g.drawImage(currentImg, node.getX() * SCALE + OFFSET - 45 / 2,
                        node.getY() * SCALE + OFFSET - 38 / 2,
                        TOKEN_DIAMETER - 10, TOKEN_DIAMETER - 10, this);

                // Reset the AlphaComposite to default
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        }

        for (NodeTriplet nodeTriplet : GameController.getInstance().getBoard().getNodeTriplets()) {
            if (nodeTriplet.getPlayerColorIfMill() == secondPlayer.getTokenColor() && nodeTriplet.containsNode(node)) {
                g.drawImage(currentImg, node.getX() * SCALE + OFFSET - 40 / 2,
                        node.getY() * SCALE + OFFSET - 35 / 2,
                        TOKEN_DIAMETER + 15, TOKEN_DIAMETER + 15, this);

                float opacity = 0.5f; // 50% opacity
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);

                // Set the AlphaComposite on the graphics object
                g.setComposite(alphaComposite);

                g.drawImage(currentImg, node.getX() * SCALE + OFFSET - 50 / 2,
                        node.getY() * SCALE + OFFSET - 40 / 2,
                        TOKEN_DIAMETER - 10, TOKEN_DIAMETER - 10, this);

                // Reset the AlphaComposite to default
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        }

        // highlight nodes
        if (node.isSelected()) {
            // Create a new AlphaComposite with the desired opacity value (0.0 - 1.0)
            float opacity = 0.5f; // 50% opacity
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);

            // Set the AlphaComposite on the graphics object
            g.setComposite(alphaComposite);

            g.setColor(Color.PINK);
            g.fillOval(node.getX() * SCALE + OFFSET - 30 / 2,
                    node.getY() * SCALE + OFFSET - 30 / 2,
                    TOKEN_DIAMETER + 5,
                    TOKEN_DIAMETER + 5);

            // Reset the AlphaComposite to default
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    /**
     * Render the latest state of board
     * @param g the Graphics2D instance
     */
    private void renderBoardWithTokens(Graphics2D g) {
        // retrieve node triplets from board
        NodeTriplet[] nodeTriplets = GameController.getInstance().getBoard().getNodeTriplets();

        // draw board
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            Node first = nodeTriplet.getFirstNode();
            Node second = nodeTriplet.getSecondNode();
            Node third = nodeTriplet.getThirdNode();

            // draw grid lines
            g.setStroke(new BasicStroke(10));
            g.setColor(Color.WHITE);
            g.drawLine(first.getX() * SCALE + OFFSET,
                    first.getY() * SCALE + OFFSET,
                    third.getX() * SCALE + OFFSET,
                    third.getY() * SCALE + OFFSET);

            // render token on node
            renderTokenOnNode(g, first);
            renderTokenOnNode(g, second);
            renderTokenOnNode(g, third);
        }
    }

    /**
     * Render tokens below the grid
     * @param g the Graphics2D instance
     */
    private void renderTokensBelowGrid(Graphics2D g){

        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve the image of star
        try {
            imgStar = ImageIO.read(getClass().getResource("/images/star.png"));
        } catch (IOException e) {
        }

        // retrieve the image of moon
        try {
            imgMoon = ImageIO.read(getClass().getResource("/images/moon.png"));
        } catch (IOException e) {
        }

        // the starting coordinate to draw the tokens
        int x = 0;
        int y = 460;

        // draw the 9-star tokens
        for (int i = 0; i < gameController.getFirstPlayer().getTokensToSet(); i++) {
            // draw pieces
            g.setColor(Color.WHITE);
            g.drawImage(imgStar, x + DISTANCE_FROM_BORDER_FOR_X, y + DISTANCE_FROM_BORDER_FOR_Y, 25, 25, this);
            x += DISTANCE_BETWEEN_TOKENS - 2;
        }

        // draw the 9-moon tokens
        x = 18 * DISTANCE_BETWEEN_TOKENS;
        y = 460;
        for (int i = gameController.getSecondPlayer().getTokensToSet(); i > 0; i--) {
            g.setColor(Color.BLACK);
            g.drawImage(imgMoon, x + DISTANCE_FROM_BORDER_FOR_X, y + DISTANCE_FROM_BORDER_FOR_Y, 25, 25, this);
            x -= DISTANCE_BETWEEN_TOKENS;
        }




    }


    /**
     *  Paint board view
     * @param g the Graphics instance
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // clear board

        // retrieve the image of the background and set the background of the board
        try {
            background = ImageIO.read(getClass().getResource("/images/board_background.png"));
        } catch (IOException e) {
        }
        g.drawImage(background, 0, 0, null); // image full size

        // render game
        renderBoardWithTokens((Graphics2D) g);
        renderTokensBelowGrid((Graphics2D) g);
    }

    /**
     * Invoked when the state of board changes
     */
    @Override
    public void update() {
        repaint();
    }

    /**
     * Return the boolean variable storing information on whether the board is being clicked currently
     * @return a boolean variable
     */
    public boolean getClick() {
        return click;
    }

    /**
     * Set the boolean variable storing information on whether the board is being clicked currently
     * @param click a boolean variable
     */
    public void setClick(boolean click) {
        this.click = click;
    }
}
