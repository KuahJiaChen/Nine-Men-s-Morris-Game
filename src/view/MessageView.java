package view;

import javax.swing.*;
import java.awt.*;

/**
 * The MessageView class is a crucial component responsible for the front-end display of information using
 * Java Swing. As an extension of the JPanel class, it provides the ability to set the size and color of
 * the message panel. All essential messages, such as information regarding the current game, are conveniently
 * displayed on this panel to notify the user in real time.
 */
public class MessageView extends JPanel {
    /**
     * an integer represents the width of the message view
     */
    private static final int WIDTH = 500;

    /**
     * an integer represents the height of the message view
     */
    private static final int HEIGHT = 80;

    /**
     * a JLabel instance represents the label
     */
    private JLabel label;

    /**
     * Constructor
     */
    public MessageView() {
        // handle size
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        setSize(dimension);
        setPreferredSize(dimension);

        // handle background color
        setBackground(Color.BLACK);

        // handle layout
        setLayout(new GridBagLayout());

        // create label and set parameters
        label = new JLabel();
        label.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        add(label, SwingConstants.CENTER);
    }

    /**
     * Display the text on the message panel
     * @param text the text to be show
     */
    public void setText(String text) {
        label.setText(text);
    }

}

