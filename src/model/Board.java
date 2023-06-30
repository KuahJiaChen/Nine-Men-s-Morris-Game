package model;

import controller.GameController;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Board class is an integral part of the game system as it creates and assigns indexes to all the nodes.
 * 0----------1---------2
 * |  8-------9------10 |
 * |  |  16--17--18  |  |
 * 7  15 23      19  11 3
 * |  |  22--21--20  |  |
 * |  14-----13------12 |
 * 6----------5---------4
 **/
public class Board {
    /**
     * an integer represents the number of node triplets on the board
     */
    private static final int NUM_OF_NODE_TRIPLETS = 16;

    /**
     * an integer represents the number of rings on the board
     */
    private static final int NUM_OF_RINGS = 3;

    /**
     * an integer represents the number of node triplets in a ring on the board
     */
    private static final int NUM_OF_NODE_TRIPLETS_IN_A_RING = 4;

    /**
     * an integer represents the number of node triplets across ring on the board
     */
    private static final int NUM_OF_NODE_TRIPLETS_ACROSS_RINGS = 4;

    /**
     * an integer represents the distance between nodes on the board
     */
    private static int distBetweenNodes = 3;

    /**
     * an integer represents the node triplet index
     */
    private static int nodeTripletIndex = 0;

    /**
     * an Array List to stores the Observer instances
     */
    private List<Observer> observers = new ArrayList<>();

    /**
     * a List to stores the NodeTriplet instances
     */
    private NodeTriplet[] nodeTriplets;

    /**
     * instance of the Node class to indicate the selected node
     */
    private Node selectedNode;

    /**
     * Constructor
     */
    public Board() {
        nodeTriplets = new NodeTriplet[NUM_OF_NODE_TRIPLETS];
        setupNodeTriplets();
    }

    /**
     * Attach the observer instance
     * @param observer to be attached
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notify the observer instance by calling the update method for each observer
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Set up the node triplets on the board
     */
    private void setupNodeTriplets() {
        // within ring
        for (int i = 0; i < NUM_OF_RINGS; i++) {

            // create top left node
            Node topLeft = new Node(i, i);

            // create temp node to hold last node in previous node triplet
            Node temp = null;

            for (int j = 0; j < NUM_OF_NODE_TRIPLETS_IN_A_RING; j++) {
                // create node triplet
                nodeTriplets[nodeTripletIndex] = new NodeTriplet();

                // create 3 nodes
                Node first, second, third;

                // handle first node
                // at top of the ring
                if (temp == null) {
                    first = topLeft;
                } else {
                    // other sides of the ring
                    first = temp;
                }

                // handle second node
                second = createNode(j, first);

                // handle third node
                // at left of the ring
                if (j == NUM_OF_NODE_TRIPLETS_IN_A_RING - 1) {
                    third = topLeft;
                } else {
                    third = createNode(j, second);
                }

                // insert 3 nodes into node triplet
                nodeTriplets[nodeTripletIndex]. insertNodes(first, second, third);
                addNeighbors(second, first);
                addNeighbors(second, third);

                // update temp and node triplet index
                temp = third;
                nodeTripletIndex++;
            }

            // decrement dist
            distBetweenNodes--;
        }

        // across rings
        int outerIndex = 0, middleIndex = 4, innerIndex = 8;
        for (int i = 0; i < NUM_OF_NODE_TRIPLETS_ACROSS_RINGS; i++) {

            // create node triplet
            nodeTriplets[nodeTripletIndex] = new NodeTriplet();

            // retrieve existing nodes
            Node first = nodeTriplets[outerIndex].getSecondNode();
            Node second = nodeTriplets[middleIndex].getSecondNode();
            Node third = nodeTriplets[innerIndex].getSecondNode();

            // insert 3 nodes into node triplet
            nodeTriplets[nodeTripletIndex].insertNodes(first, second, third);
            addNeighbors(second, first);
            addNeighbors(second, third);

            // update index
            nodeTripletIndex++;
            outerIndex++;
            middleIndex++;
            innerIndex++;
        }
    }

    /**
     * Create a node based on given direction
     * @param direction an integer to represents the direction
     * @param prevNode a Node instance to indicate the previous node
     * @return the new node created
     */
    private Node createNode(int direction, Node prevNode) {
        switch (direction) {
            // 0 - right
            // 1 - bottom
            // 2 - left
            // 3 - top

            case 0:
                return new Node(prevNode.getX() + distBetweenNodes, prevNode.getY());
            case 1:
                return new Node(prevNode.getX(), prevNode.getY() + distBetweenNodes);
            case 2:
                return new Node(prevNode.getX() - distBetweenNodes, prevNode.getY());
            case 3:
                return new Node(prevNode.getX(), prevNode.getY() - distBetweenNodes);
            default:
                return null;
        }
    }

    /**
     * Add each node to others neighbor list
     * @param first a Node instance
     * @param second a Node instance
     */
    private void addNeighbors(Node first, Node second) {
        first.addNeighbor(second);
        second.addNeighbor(first);
    }

    /**
     * Reset the board's node triplet. This method is called when the user decided to perform a rematch.
     */
    public void resetBoard() {
        distBetweenNodes = 3;
        nodeTripletIndex = 0;
        Node.resetIndexOnBoardCounter();
        setupNodeTriplets();
    }

    /**
     * Return the nodeTriplets of this Board instance
     * @return a list of NodeTriplet instance
     */
    public NodeTriplet[] getNodeTriplets() {
        return nodeTriplets;
    }

    /**
     * Return the selectedNode of this Board instance
     * @return a Node instance
     */
    public Node getSelectedNode() {
        return selectedNode;
    }

    /**
     * Setter of selectedNode
     * @param node the Node instance that represents the selected node
     */
    public void setSelectedNode(Node node) {
        this.selectedNode = node;
    }


    /**
     * Verify if mill exists
     * @param node current node to checked
     * @param player current player to perform the command
     * @return true if mill exists, else false
     */
    public boolean isMillExists(Node node, Player player) {
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            if (nodeTriplet.getPlayerColorIfMill() == player.getTokenColor() &&
                nodeTriplet.containsNode(node)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMillOnBoard(Player player){
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            if (nodeTriplet.getPlayerColorIfMill() == player.getTokenColor()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if a player can remove any tokens from the opponent, there might be a possibility
     * where all of them are in mill.
     * @param currentPlayer current player to perform the command
     * @return true if has removable token, else false
     */
    public boolean hasRemovableToken(Player currentPlayer) {
        // retrieve opponent player
        Player opponent = getOpponentPlayer(currentPlayer);

        // determine if there is any token removable from opponent
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            for (Node node : nodeTriplet.getNodes()) {
                if (node.getTokenColor() == opponent.getTokenColor()) {
                    // verify if opponent token is in mill
                    if (!isMillExists(node, opponent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Retrieve opponent player
     * @param currentPlayer current player to perform the command
     * @return the Player instance that represents the opponent player
     */
    public Player getOpponentPlayer(Player currentPlayer) {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        if (currentPlayer.getTokenColor() == TokenColor.WHITE) {
            return gameController.getSecondPlayer();
        } else {
            return gameController.getFirstPlayer();
        }
    }

    /**
     * Retrieve player based on token color
     * @param tokenColor token color of the player
     * @return the Player with the token color
     */
    public Player getPlayerFromTokenColor(TokenColor tokenColor) {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        if (tokenColor == TokenColor.WHITE) {
            return gameController.getFirstPlayer();
        } else {
            return gameController.getSecondPlayer();
        }
    }

    /**
     * Retrieve node by a given index
     * @param index an integer represents the index
     * @return the Node instance represents the node at a given index
     */
    public Node getNodeFromIndex(int index) {
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            if (nodeTriplet.getFirstNode().getIndexOnBoard() == index) {
                return nodeTriplet.getFirstNode();
            }
            if (nodeTriplet.getSecondNode().getIndexOnBoard() == index) {
                return nodeTriplet.getSecondNode();
            }
            if (nodeTriplet.getThirdNode().getIndexOnBoard() == index) {
                return nodeTriplet.getThirdNode();
            }
        }
        return null;
    }

    /**
     * Remove node by a given index
     * @param index an integer represents the index
     * @return true if the node is removed, else false
     */
    public boolean removeTokenFromIndex(int index) {
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            if (nodeTriplet.getFirstNode().getIndexOnBoard() == index) {
                nodeTriplet.getFirstNode().setTokenColor(null);
                notifyObservers();
                return true;
            }
            if (nodeTriplet.getSecondNode().getIndexOnBoard() == index) {
                nodeTriplet.getSecondNode().setTokenColor(null);
                notifyObservers();
                return true;
            }
            if (nodeTriplet.getThirdNode().getIndexOnBoard() == index) {
                nodeTriplet.getThirdNode().setTokenColor(null);
                notifyObservers();
                return true;
            }
        }
        return false;
    }

    /**
     * reset selected nodes from being highlighted
     */
    public void resetSelectedNodes() {
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            for (Node node : nodeTriplet.getNodes()) {
                if (node.isSelected()) {
                    node.setSelected(false);
                }
            }
        }
    }
}
