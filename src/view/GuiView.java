package src.view;

// General imports
import java.beans.PropertyChangeEvent;

// AWT imports
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.Font;

import javax.swing.ImageIcon;
// Swing imports
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Local imports
import src.model.Direction;
import src.model.Door;
import src.model.GameState;
import src.model.Maze;
import src.model.Position;
import src.model.PropertyChangeEnabledGameState;
import src.model.Question;
import src.model.Room;
import src.model.GameSaver;

/**
 * GUI view class
 * 
 * @author Kalen Cha
 * @author Raiden H
 * @version Spring 2025
 */
public class GuiView implements GameView {

    /**
     * The Dimension of the screen.
     */
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * The Dimension of the window.
     */
    private static final Dimension WINDOW_SIZE = new Dimension(1024, 1024);

    /**
     * Constant background color.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    /**
     * Reference to the JFrame for the window
     */
    private final JFrame myFrame;

    /**
     * Dimensions of the maze (in rooms)
     */
    private final Dimension myMazeSize;

    /**
     * Array of rooms in the maze panel.
     */
    private RoomPanel[] myRooms;

    /**
     * Stores selected sprite.
     */
    private ImageIcon mySelectedSprite = new ImageIcon("src/sprites/kirby1.png");

    /**
     * Current position of the player.
     */
    private Position myPlayerPosition;

    /**
     * GameState state of the game
     */
    private final GameState myGameState;

    /**
     * Label of the current question
     */
    private JTextArea myCurrentQuestionLabel;

    /**
     * Panel for question answer inputs
     */
    private JPanel myAnswerInputPanel;

    /**
     * Answer object, either ButtonGroup or JTextField
     */
    private Object myAnswerComponent;

    /**
     * Stores the index of the current room in the myRooms array
     */
    private int myCurrentRoom;

    /**
     * Minimap panel in the main panel.
     */
    private MinimapPanel myMinimap;

    /**
     * Stats panel in the main panel.
     */
    private StatsPanel myStatsPanel;

    /**
     * Create a new GuiView watching the given state
     * @param theState GameState object to watch for property changes
     */
    public GuiView(final GameState theState) {
        super();

        myGameState = theState;

        myGameState.addPropertyChangeListener(this);

        myMazeSize = new Dimension(myGameState.getMaze().getWidth(), myGameState.getMaze().getWidth());

        myPlayerPosition = new Position(0, 0);

        myCurrentRoom = myPlayerPosition.getY() * myMazeSize.width + myPlayerPosition.getX();

        myRooms = new RoomPanel[myMazeSize.width * myMazeSize.height];

        myMinimap = new MinimapPanel(myPlayerPosition, new int[] {0, 0, 0, 0}, BACKGROUND_COLOR);

        // Initializing the frame
        myFrame = new JFrame("Trivia Maze");

        // Setting behaviour
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setPreferredSize(WINDOW_SIZE);
    }

    /**
     * Setup the GUI components and display the window.
     */
    public void initialize() {
        // Adding GUI components
        myFrame.setJMenuBar(createMenuBar());
        myFrame.setContentPane(createContentPane());

        // Displaying the window
        myFrame.pack();
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
        myFrame.setVisible(true);
    }

    /**
     * Update the position of the player.
     * @param theOldPos Position the player was at previously
     * @param theNewPos Position the player is at now
     */
    private void updatePosition(Position newPos) {
        // called when the player position changes
        myRooms[myCurrentRoom].setIsPlayerPosition(false);
        myRooms[myCurrentRoom].repaint();

        myCurrentRoom = newPos.getY() * myMazeSize.width + newPos.getX();

        myMinimap.setDoorStates(myRooms[myCurrentRoom].getDoorState());

        myRooms[myCurrentRoom].setIsPlayerPosition(true);
        myRooms[myCurrentRoom].repaint();
    }

    /**
     * Update question stats for the player.
     */
    private void updateStats(final boolean theCorrect) {
        myStatsPanel.incrementQuestionsAnswered();
        if (theCorrect) {
            myStatsPanel.incrementQuestionsCorrect();
        }
    }

    /**
     * Update the door in the given direction of the current room with the given state.
     * @param theDir Direction of the Door being updated.
     * @param theDoorState int new state of the door. Possibilities described in the GameView interface.
     */
    private void updateDoor(final Direction theDir, final int theDoorState) {
        updateStats(theDoorState == 4);
        myRooms[myCurrentRoom].setDoorState(theDir, theDoorState);
        myMinimap.setDoorStates(myRooms[myCurrentRoom].getDoorState());
        if (theDoorState != 2) {
            switch (theDir) {
            case Direction.NORTH:
                myRooms[myCurrentRoom - myMazeSize.width].setDoorState(Direction.SOUTH, theDoorState);
                break;
            case Direction.SOUTH:
                myRooms[myCurrentRoom + myMazeSize.width].setDoorState(Direction.NORTH, theDoorState);
                break;
            case Direction.EAST:
                myRooms[myCurrentRoom + 1].setDoorState(Direction.WEST, theDoorState);
                break;
            case Direction.WEST:
                myRooms[myCurrentRoom - 1].setDoorState(Direction.EAST, theDoorState);
                break;
            }
        }
    }

    /**
     * Call methods based on the name of the property event passed.
     * Should only be called by objects we're listening to.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PropertyChangeEnabledGameState.PROPERTY_POSITION:
                updatePosition((Position) theEvent.getNewValue());
                break;
            case PropertyChangeEnabledGameState.PROPERTY_DOOR_VISITED:
                updateDoor((Direction) theEvent.getNewValue(), (Integer) theEvent.getOldValue());
                break;
            default:
                throw new UnsupportedOperationException("Property change not supported");
        }
    }


    /* 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * GUI EVENT METHODS BELOW
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    // File menu events
    private void newGameEvent(final ActionEvent theEvent) {
        // TODO: Implement newGameEvent
        JOptionPane.showMessageDialog(myFrame, "Starting a new game! (reset state, choose game parameters, etc.)");
    }

    private void settingsEvent(final ActionEvent theEvent) {
        JOptionPane.showMessageDialog(myFrame, "Opening settings!");
    }

    private void exitEvent(final ActionEvent theEvent) {
        int selection = JOptionPane.showConfirmDialog(myFrame, "Exit without saving?", "Exit?", JOptionPane.YES_NO_OPTION);
        // Exit only if the user confirms.
        if (selection == 0) {
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    // Save menu events
    private void saveGameEvent(final ActionEvent theEvent) {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Save Game");

        int userSelection = fileChooser.showSaveDialog(myFrame);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            try {
                GameSaver.saveGame(myGameState, fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(myFrame, "Game saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(myFrame, "Failed to save the game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadGameEvent(final ActionEvent theEvent) {
        // Create a file chooser to select the save file
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Load Game");

        // Show the open dialog
        int userSelection = fileChooser.showOpenDialog(myFrame);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToLoad = fileChooser.getSelectedFile();

            try {
                GameState loadedState = GameSaver.getSave(fileToLoad.getAbsolutePath());

                // Assuming we have a method to update the current game state
                updateGameState(loadedState);
                JOptionPane.showMessageDialog(myFrame, "Game loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(myFrame, "Failed to load the game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Updates the current game state with the loaded state.
     *
     * @param theLoadState The loaded GameState object.
     */
    private void updateGameState(final GameState theLoadState) {
        if (theLoadState == null) {
            JOptionPane.showMessageDialog(myFrame, "Failed to load: Save file is empty or corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        myGameState.removePropertyChangeListener(this);
        myGameState.updateFrom(theLoadState);
        myGameState.addPropertyChangeListener(this);
        myPlayerPosition = myGameState.getMyCurrentPosition();
        syncAllRoomPanelDoorStates();
        updatePosition(myPlayerPosition);
        updateQuestionPanel();
        updateMinimap();
    }

    /**
     * Set all the myRoom door state according to the state of the maze.
     */
    private void syncAllRoomPanelDoorStates() {
        Maze maze = myGameState.getMaze();
        for (int row = 0; row < myMazeSize.height; row++) {
            for (int col = 0; col < myMazeSize.width; col++) {
                Room modelRoom = maze.getRoom(new Position(col, row));
                RoomPanel panelRoom = myRooms[row * myMazeSize.width + col];
                int[] doorStates = new int[4]; // N, S, E, W
                for (Direction dir : Direction.values()) {
                    if (modelRoom.hasDoor(dir)) {
                        Door door = modelRoom.getDoor(dir);
                        if (door.isOpen()) {
                            doorStates[dir.ordinal()] = DOOR_SUCCEEDED;
                        } else if (door.isLocked()) {
                            doorStates[dir.ordinal()] = DOOR_FAILED;
                        } else {
                            doorStates[dir.ordinal()] = DOOR_NOT_VISITED;
                        }
                    } else {
                        doorStates[dir.ordinal()] = DOOR_WALL;
                    }
                }
                panelRoom.setDoorStates(doorStates);
            }
        }
    }

    // Help menu events
    private void aboutEvent(final ActionEvent theEvent) {
        String aboutMsg = """
        Trivia Maze Game
        Made for TCSS360 Spring 2025 at UWT

        Made by:
        - Evan Lei
        - Kalen Cha
        - Raiden Hiland
        """;
        JOptionPane.showMessageDialog(myFrame, aboutMsg);
    }

    private void howToEvent(final ActionEvent theEvent) {
        String howtoMsg = """
        Answer trivia questions
        Document door colour key
        Document controls
        Document minimap door selection 
        """; // TODO: Write something here
        JOptionPane.showMessageDialog(myFrame, howtoMsg);
    }

    // Debug menu events


    /* 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * GUI COMPONENTS BELOW
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = buildFileMenu();

        JMenu saveMenu = buildSaveMenu();

        JMenu helpMenu = buildHelpMenu();

        JMenu debugMenu = buildDebugMenu();

        bar.add(fileMenu);
        bar.add(saveMenu);
        bar.add(helpMenu);
        bar.add(debugMenu);

        return bar;
    }

    private JMenu buildFileMenu() {
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);

        final JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(this::newGameEvent);

        final JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.addActionListener(this::settingsEvent);

        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this::exitEvent);

        menu.add(newGameItem);
        menu.add(settingsItem);
        menu.addSeparator();
        menu.add(exitItem);

        return menu;
    }

    private JMenu buildSaveMenu() {
        final JMenu menu = new JMenu("Save");
        menu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem saveItem = new JMenuItem("Save Game");
        saveItem.addActionListener(this::saveGameEvent);

        final JMenuItem loadItem = new JMenuItem("Load Game");
        loadItem.addActionListener(this::loadGameEvent);

        menu.add(saveItem);
        menu.add(loadItem);

        return menu;
    }

    private JMenu buildHelpMenu() {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this::aboutEvent);

        final JMenuItem howToItem = new JMenuItem("How to play");
        howToItem.addActionListener(this::howToEvent);

        menu.add(aboutItem);
        menu.add(howToItem);

        return menu;
    }

    private JMenu buildDebugMenu() {
        JMenu menu = new JMenu("Debug");
        menu.setMnemonic(KeyEvent.VK_D);

        return menu;
    }

    private JPanel createContentPane() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Ok this is kind of stupid and excessively complicated. but.
        // int gridx: x coordinate from top left    int gridy: y coordinate from top left
        // int gridwidth: x width    int gridheight: y height
        // double weightx: percent of parent width taken up   double weighty: percent of parent height taken up
        // GridBagConstraints. anchor constant for where this shit goes
        // GridBagConstraints. fill constant if it takes up full height/width?
        // Insets(int top, int left, int bottom, int right) external padding
        // int ipadx: internal x padding   int ipady: internal y padding
        final JPanel mapPanel = createMapPanel();
        GridBagConstraints mapConstraint = new GridBagConstraints(0, 0, 3, 3, 0.7, 0.7,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel minimapPanel = createDoorPanel();
        GridBagConstraints minimapConstraint = new GridBagConstraints(3, 0, 1, 1, 0.3, 0.3,
                GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel statsPanel = createStatsPanel(3, 5);
        GridBagConstraints statsConstraint = new GridBagConstraints(3, 1, 1, 1, 0.3, 0.3,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel questionPanel = createQuestionPanel();
        GridBagConstraints questionConstraint = new GridBagConstraints(3, 2, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel controlPanel = createControlPanel();
        GridBagConstraints controlConstraint = new GridBagConstraints(3, 3, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel infoPanel = createInfoPanel();
        GridBagConstraints infoConstraint = new GridBagConstraints(0, 3, 3, 1, 0.3, 0.3,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        mainPanel.add(mapPanel, mapConstraint);
        mainPanel.add(minimapPanel, minimapConstraint);
        mainPanel.add(statsPanel, statsConstraint);
        mainPanel.add(questionPanel, questionConstraint);
        mainPanel.add(controlPanel, controlConstraint);
        mainPanel.add(infoPanel, infoConstraint);

        return mainPanel;
    }

    private JPanel createMapPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(myMazeSize.width, myMazeSize.height, 5, 5));
        panel.setBackground(Color.BLACK);

        for (int row = 0; row < myMazeSize.width; row++) {
            for (int col = 0; col < myMazeSize.height; col++) {
                final JPanel roomPane = new RoomPanel(new Position(row, col));
                panel.add(roomPane, row,  col);
            }
        }

        myRooms = panel.getComponents();

        updatePosition(new Position(0,0), new Position(0, 0));

        return panel;
    }

    private JPanel createDoorPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setLayout(new GridBagLayout());

        JButton openDoorButton = new JButton("OPEN DOOR");
        openDoorButton.setOpaque(true);
        openDoorButton.setContentAreaFilled(true);
        openDoorButton.setBorderPainted(true);
        openDoorButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        openDoorButton.setBackground(new Color(255, 204, 0));
        openDoorButton.setForeground(Color.BLACK);
        openDoorButton.setFocusPainted(false);
        openDoorButton.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK, 2));
        openDoorButton.setPreferredSize(new Dimension(450, 100));

        panel.add(openDoorButton);
        return panel;
    }

    private JPanel createStatsPanel(final int theAnswered, final int theFailed) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setLayout(new GridBagLayout());

        Font labelFont = new Font("Monospaced", Font.BOLD, 14);

        JLabel answeredLabel = new JLabel("QUESTIONS ANSWERED: " + theAnswered);
        answeredLabel.setFont(labelFont);
        answeredLabel.setForeground(Color.GREEN);

        JLabel failedLabel = new JLabel("QUESTIONS FAILED:   " + theFailed);
        failedLabel.setFont(labelFont);
        failedLabel.setForeground(new Color(255, 85, 85));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(answeredLabel, gbc);

        gbc.gridy = 1;
        panel.add(failedLabel, gbc);

        return panel;
    }

    private JPanel createQuestionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel questionLabel = new JLabel("QUESTION: ");
        questionLabel.setForeground(new Color(0, 191, 255));
        questionLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(questionLabel, gbc);

        JLabel currentQuestion = new JLabel("2 + 2 = 5 (T OR F)");
        currentQuestion.setForeground(Color.WHITE);
        currentQuestion.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(currentQuestion, gbc);

        JLabel answerLabel = new JLabel("ANSWER >");
        answerLabel.setForeground(new Color(106, 90, 205));
        answerLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(answerLabel, gbc);

        JTextField answerField = new JTextField(20);
        answerField.setBackground(Color.BLACK);
        answerField.setForeground(Color.GREEN);
        answerField.setCaretColor(Color.GREEN);
        answerField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gbc.gridx = 1;

        return panel;
    }

    // TODO: This method should probably be broken up, especially that inline class.
    private JPanel createControlPanel() {
        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int width = getWidth();
                int height = getHeight();
                int centerX = width / 2;
                int centerY = height / 2;

                int crossSize = Math.min(width, height) / 2;
                int armWidth = crossSize / 3;
                int circleRadius = armWidth / 2;
                int arrowSize = armWidth / 2;

                circleRadius = (int) (circleRadius * 0.6);

                java.awt.GradientPaint gradientPaint = new java.awt.GradientPaint(
                        0, 0, new Color(60, 60, 60),
                        width, height, new Color(40, 40, 40));
                ((java.awt.Graphics2D) g).setPaint(gradientPaint);

                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                int vX = centerX - armWidth / 2;
                int vY = centerY - crossSize / 2;
                int hX = centerX - crossSize / 2;
                int hY = centerY - armWidth / 2;

                RoundRectangle2D verticalArm = new RoundRectangle2D.Float(vX, vY, armWidth, crossSize, 10, 10);
                RoundRectangle2D horizontalArm = new RoundRectangle2D.Float(hX, hY, crossSize, armWidth, 10, 10);

                g2d.fill(verticalArm);
                g2d.fill(horizontalArm);

                g2d.setColor(Color.BLACK);
                g2d.draw(verticalArm);
                g2d.draw(horizontalArm);

                g.setColor(new Color(26, 26, 26));
                g.fillOval(centerX - circleRadius, centerY - circleRadius,
                        circleRadius * 2, circleRadius * 2);

                g.setColor(new Color(26, 26, 26));

                drawDirectionalArrow(g2d, centerX, centerY - crossSize / 3, Direction.NORTH, arrowSize);
                drawDirectionalArrow(g2d, centerX, centerY + crossSize / 3, Direction.SOUTH, arrowSize);
                drawDirectionalArrow(g2d, centerX - crossSize / 3, centerY, Direction.WEST, arrowSize);
                drawDirectionalArrow(g2d, centerX + crossSize / 3, centerY, Direction.EAST, arrowSize);
            }

            private void drawDirectionalArrow(java.awt.Graphics2D g, int x, int y, Direction dir, int size) {
                int[] xPoints = new int[3];
                int[] yPoints = new int[3];

                switch (dir) {
                    case NORTH -> {
                        xPoints = new int[]{x, x - size / 2, x + size / 2};
                        yPoints = new int[]{y - size / 2, y + size / 2, y + size / 2};
                    }
                    case SOUTH -> {
                        xPoints = new int[]{x, x - size / 2, x + size / 2};
                        yPoints = new int[]{y + size / 2, y - size / 2, y - size / 2};
                    }
                    case EAST -> {
                        xPoints = new int[]{x + size / 2, x - size / 2, x - size / 2};
                        yPoints = new int[]{y, y - size / 2, y + size / 2};
                    }
                    case WEST -> {
                        xPoints = new int[]{x - size / 2, x + size / 2, x + size / 2};
                        yPoints = new int[]{y, y - size / 2, y + size / 2};
                    }
                }

                g.fillPolygon(xPoints, yPoints, 3);
            }
        };

        panel.setPreferredSize(new Dimension(200, 200));
        panel.setBackground(new Color(240, 240, 240));

        return panel;
    }

    private JPanel createInfoPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 255, 255));

        return panel;
    }
}
