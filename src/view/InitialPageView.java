package view;

import controller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The initial page view of the Nine Man Morris game. This view displays the title label, buttons, and graphical user
 * interface for the initial page of the game. It allows the user to start a normal game, a game against the computer,
 * or access the tutorial mode.
 */
public class InitialPageView extends JFrame {

    /**
     * The title label for the game window.
     */
    private JLabel titleLabel;

    /**
     * The button for starting a normal game.
     */
    private JButton normalGameStart;

    /**
     * The button for starting a game against the computer.
     */
    private JButton vsComputerGameStart;

    /**
     * The button for starting the tutorial mode.
     */
    private JButton tutorialMode;

    /**
     * The width of the game window.
     */
    private static final int WIDTH = 500;

    /**
     * The height of the game window.
     */
    private static final int HEIGHT = 640;

    /**
     * The font size for the buttons.
     */
    private static final int BUTTON_FONT_SIZE = 25;

    /**
     * The font size for the title label.
     */
    private static final int TITLE_FONT_SIZE = 45;

    /**
     * The width of the buttons.
     */
    private static final int BUTTON_WIDTH = 240;

    /**
     * The height of the buttons.
     */
    private static final int BUTTON_HEIGHT = 70;

    /**
     * The foreground color for the game window.
     */
    private static final Color FOREGROUND_COLOR = Color.WHITE;

    /**
     * The background color for the game window.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    /**
     * The background image for the game window.
     */
    private BufferedImage background;

    /**
     * Constructs a new instance of the InitialPageView class. Initializes and sets up the graphical user interface for
     * the initial page of the game. This includes setting the window title, size, location, background image, title
     * label, and buttons. The constructed view is displayed on the screen.
     */
    public InitialPageView() {
        // Set up the JFrame
        setTitle("Nine Man Morris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the JFrame on the screen

        // Create a panel to hold the components
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // retrieve the image of the background and set the background of the board
                try {
                    background = ImageIO.read(getClass().getResource("/images/board_background.png"));
                } catch (IOException e) {
                }
                g.drawImage(background, 0, 0, null); // image full size
            }
        };
        panel.setLayout(new BorderLayout());

        // Create the title label
        titleLabel = new JLabel("Nine Man Morris");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(FOREGROUND_COLOR);
        panel.add(titleLabel, BorderLayout.CENTER);

        // Create the buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // retrieve the image of the background and set the background of the board
                try {
                    background = ImageIO.read(getClass().getResource("/images/board_background.png"));
                } catch (IOException e) {
                }
                g.drawImage(background, 0, 0, null); // image full size
            }
        };

        // Create constraints to control the button panel's alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 100;
        gbc.gridy = 100;
        gbc.insets = new Insets(0, 0, 50, 0);

        // Set text for the button
        normalGameStart = new JButton("Game Start!");
        vsComputerGameStart = new JButton("VS Computer!");
        tutorialMode = new JButton("Tutorial First!");
        normalGameStart.setFont(new Font("Comic Sans MS", Font.BOLD, BUTTON_FONT_SIZE));
        vsComputerGameStart.setFont(new Font("Comic Sans MS", Font.BOLD, BUTTON_FONT_SIZE));
        tutorialMode.setFont(new Font("Comic Sans MS", Font.BOLD, BUTTON_FONT_SIZE));

        // Set size of the button
        normalGameStart.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        vsComputerGameStart.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        tutorialMode.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        // Set appealing of the button
        normalGameStart.setForeground(FOREGROUND_COLOR);
        normalGameStart.setBackground(BACKGROUND_COLOR);
        vsComputerGameStart.setForeground(FOREGROUND_COLOR);
        vsComputerGameStart.setBackground(BACKGROUND_COLOR);
        tutorialMode.setForeground(FOREGROUND_COLOR);
        tutorialMode.setBackground(BACKGROUND_COLOR);


        // Add action listeners to the buttons
        normalGameStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGameView(false);
            }
        });

        tutorialMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().startTutorial();
            }
        });

        vsComputerGameStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGameView(true);
            }
        });

        // Add the buttons to the panel
        buttonPanel.add(normalGameStart, gbc);
        gbc.gridy++;
        buttonPanel.add(vsComputerGameStart, gbc);
        gbc.gridy++;
        buttonPanel.add(tutorialMode, gbc);

        // Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the main panel as the content pane of the JFrame
        setContentPane(panel);

        // Display the JFrame
        setVisible(true);
    }

    /**
     * Opens the game view and starts the game.
     * @param playWithComputer true if the game should be played against the computer, false otherwise
     */
    private void openGameView(boolean playWithComputer) {
        // start game
        GameController gameController = GameController.getInstance();
        gameController.startGame(playWithComputer);

        close();
    }

    /**
     * Closes the initial page view.
     */
    private void close() {
        dispose();
    }
}
