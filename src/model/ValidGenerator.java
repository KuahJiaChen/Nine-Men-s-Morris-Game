package model;

import controller.GameController;

import java.util.ArrayList;
import java.util.Random;

/**
 * This ValidGenerator class is used to generate valid legal moves based on the current situation on the Board
 */
public class ValidGenerator {

    /**
     * This method serves the purpose of identifying and retrieving all the unoccupied nodes or positions on the game
     * board. By utilizing this method, we can obtain a comprehensive list of available positions that players can
     * consider for placing their game tokens during the SET phase.
     * @return an array list of valid nodes for set
     */
    public static ArrayList<Node> getNodesForSet() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve empty nodes
        ArrayList<Node> emptyNodes = new ArrayList<>();
        for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
            for (Node node : nodeTriplet.getNodes()) {
                if (node.isEmpty()) {
                    emptyNodes.add(node);
                }
            }
        }

        return emptyNodes;
    }

    /**
     * This method is responsible for identifying and retrieving opponent tokens on the game board that can be removed,
     * excluding those that are part of a mill formation. By utilizing this method, players can access a comprehensive
     * list of positions containing opponent tokens eligible for removal.
     * @return an array list of valid nodes for remove
     */
    public static ArrayList<Node> getNodesForRemove() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve removable nodes
        ArrayList<Node> removableNodes = new ArrayList<>();
        for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
            for (Node node : nodeTriplet.getNodes()) {
                if (node.isOccupied() &&
                    node.getTokenColor() != gameController.getCurrentPlayer().getTokenColor() &&
                    !board.isMillExists(node, board.getPlayerFromTokenColor(node.getTokenColor()))) {
                    removableNodes.add(node);
                }
            }
        }

        return removableNodes;
    }

    /**
     * This method plays a pivotal role during the MOVE phase of the game. When the player has not yet selected a node
     * on the board, this method is utilized to identify all the player's tokens that can be moved if one of their
     * neighbouring nodes is unoccupied.
     * @return an array list of valid nodes for move
     */
    public static ArrayList<Node> getNodesForMove() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve source node
        Node src = board.getSelectedNode();

        // retrieve movable or neighbor nodes depending on criteria below
        ArrayList<Node> nodes = new ArrayList<>();

        // either node has not been selected or select the wrong color node
        if (src == null || src.getTokenColor() != gameController.getCurrentPlayer().getTokenColor()) {
            for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
                for (Node node : nodeTriplet.getNodes()) {
                    // if the token color of the node is equal to the color of the current player
                    if (node.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
                        // check if any of the neighbor is empty
                        for (Node neighbor : node.getNeighbors()) {
                            // if one of them is empty, it means this node can be moved
                            if (neighbor.isEmpty()) {
                                nodes.add(node);
                            }
                        }
                    }
                }
            }
        } else if (src.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
            // retrieve neighbor nodes
            for (Node neighbor : src.getNeighbors()) {
                if (neighbor.isEmpty()) {
                    nodes.add(neighbor);
                }
            }
        }

        return nodes;
    }

    /**
     * This method plays a vital role during the JUMP phase of the game. Initially, this method identifies all tokens
     * belonging to the player, as they are inherently jumpable to other nodes on the board. However, once the player
     * clicks on a jumpable token, the method retrieves all currently vacant nodes on the board, representing the
     * potential destinations to which the token can be jumped.
     * @return an array list of valid nodes for jump
     */
    public static ArrayList<Node> getNodesForJump() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve source node
        Node src = board.getSelectedNode();

        // retrieve jump or empty nodes depending on criteria below
        ArrayList<Node> nodes = new ArrayList<>();

        // either node has not been selected or select the wrong color node
        if (src == null || src.getTokenColor() != gameController.getCurrentPlayer().getTokenColor()) {
            for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
                for (Node node : nodeTriplet.getNodes()) {
                    // if the token color of the node is equal to the color of the current player
                    if (node.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
                        nodes.add(node);
                    }
                }
            }
        } else if (src.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
            // retrieve empty nodes
            for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
                for (Node node : nodeTriplet.getNodes()) {
                    if (node.isEmpty()) {
                        nodes.add(node);
                    }
                }
            }
        }

        return nodes;
    }
}
