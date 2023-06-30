package model;

/**
 * NodeTriplet is a class that is defined to have 3 nodes that appear in the same row in a set.
 * It will be used to check the mill.
 */
public class NodeTriplet {
    /**
     * an integer represents the number of nodes in a node triplet
     */
    private static final int NUM_OF_NODES = 3;

    /**
     * an integer to indicate the node counter
     */
    private int nodeCounter = 0;

    /**
     * a List to stores the Node instances
     */
    private Node[] nodes;

    /**
     * Constructor
     */
    public NodeTriplet() {
        nodes = new Node[NUM_OF_NODES];
    }

    /**
     * Insert a node into node triplet
     * @param node the node to be inserted
     */
    public void insertNode(Node node) {
        nodes[nodeCounter] = node;
        nodeCounter++;
    }

    /**
     * Insert 3 nodes into node triplet
     * @param first the first node in this node triplet
     * @param second the second node in this node triplet
     * @param third the third node in this node triplet
     */
    public void insertNodes(Node first, Node second, Node third) {
        insertNode(first);
        insertNode(second);
        insertNode(third);
    }

    /**
     * Retrieve the nodes of the node triplet
     * @return the nodes of the node triplet
     */
    public Node[] getNodes() {
        return nodes;
    }

    /**
     * Retrieve the first node of the node triplet
     * @return the first node of the node triplet
     */
    public Node getFirstNode() {
        return nodes[0];
    }

    /**
     * Retrieve the second node of the node triplet
     * @return the second node of the node triplet
     */
    public Node getSecondNode() {
        return nodes[1];
    }

    /**
     * Retrieve the third node of the node triplet
     * @return the third node of the node triplet
     */
    public Node getThirdNode() {
        return nodes[2];
    }

    /**
     * Determine if node triplet contains a specific node
     * @param node the node to be checked
     * @return true if node triplet contains a specific node, else false
     */
    public boolean containsNode(Node node) {
        return nodes[0] == node || nodes[1] == node || nodes[2] == node;
    }

    /**
     * Determine if current node triplet contains a mill
     * @return the token color if current node triplet contains a mill, else null
     */
    public TokenColor getPlayerColorIfMill() {
        if (getFirstNode().getTokenColor() == TokenColor.WHITE &&
            getSecondNode().getTokenColor() == TokenColor.WHITE &&
            getThirdNode().getTokenColor() == TokenColor.WHITE) {
            return TokenColor.WHITE;
        }
        if (getFirstNode().getTokenColor() == TokenColor.BLACK &&
            getSecondNode().getTokenColor() == TokenColor.BLACK &&
            getThirdNode().getTokenColor() == TokenColor.BLACK) {
            return TokenColor.BLACK;
        }
        return null;
    }
}
