package model;

import java.util.ArrayList;

/**
 * Node class refers to the position on the board. It is also used to record the position of a token.
 */
public class Node {
    /**
     * an integer represents the x coordinate of the node
     */
    private int x;

    /**
     * an integer represents the y coordinate of the node
     */
    private int y;

    /**
     * an integer represents the index of the node on board
     */
    private int indexOnBoard;

    /**
     * instance of the TokenColor class to indicate the token color
     */
    private TokenColor tokenColor;

    /**
     * a boolean to indicate if the node is selected
     */
    private boolean selected = false;

    /**
     * an Array List to stores the Node instances that represents the neighbors of the node
     */
    private ArrayList<Node> neighbors = new ArrayList<>();

    /**
     * an integer that is used as the counter for the index
     */
    private static int indexOnBoardCounter = 0;

    /**
     * Constructor
     * @param x the x coordinate of the node
     * @param y the y coordinate of the node
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.indexOnBoard = indexOnBoardCounter;
        indexOnBoardCounter++;
    }

    /**
     * Retrieve the x coordinate of the node
     * @return the x coordinate of the node
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieve the y coordinate of the node
     * @return the y coordinate of the node
     */
    public int getY() {
        return y;
    }

    /**
     * Retrieve the index of the board
     * @return the index of the board
     */
    public int getIndexOnBoard() {
        return indexOnBoard;
    }

    /**
     * Retrieve the token color of the node
     * @return the token color of the node
     */
    public TokenColor getTokenColor() {
        return tokenColor;
    }

    /**
     * Determine if the node is selected
     * @return true if it is selected, else false
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Retrieve the neighbors of the node
     * @return the neighbors of the node
     */
    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    /**
     * Setter for tokenColor
     * @param tokenColor the token color of the node
     */
    public void setTokenColor(TokenColor tokenColor) {
        this.tokenColor = tokenColor;
    }

    /**
     * Setter for selected
     * @param selected indicate if the node is selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Add neighbor to the node
     * @param node neighbor to be added
     */
    public void addNeighbor(Node node) {
        neighbors.add(node);
    }

    /**
     * Determine if current node is empty
     * @return true if current node is empty, else false
     */
    public boolean isEmpty() {
        return tokenColor == null;
    }

    /**
     * Determine if current node is occupied
     * @return true if current node is not empty, else false
     */
    public boolean isOccupied() {
        return tokenColor != null;
    }

    /**
     * Reset the value for the indexOnBoardCounter
     */
    public static void resetIndexOnBoardCounter() {
        Node.indexOnBoardCounter = 0;
    }
}
