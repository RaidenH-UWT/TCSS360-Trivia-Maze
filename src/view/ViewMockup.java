package src.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import src.model.Direction;
import src.model.Door;
import src.model.GameSaver;
import src.model.GameState;
import src.model.Maze;
import src.model.MultipleChoiceQuestion;
import src.model.Position;
import src.model.PropertyChangeEnabledGameState;
import src.model.Question;
import src.model.Room;

/**
 * Mockup of the GuiView class for experimentation with the GUI. Documentation &
 * conventions not strict in here.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version Spring 2025
 */
public class ViewMockup implements GameView {

    /**
     * The Dimension of the screen.
     */
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final Dimension WINDOW_SIZE = new Dimension(1024, 1024);

    /**
     * Array of Colors for door state, indexed according to state constants.
     */
    private static final Color[] DOOR_COLORS = {Color.DARK_GRAY, Color.GRAY, Color.BLUE, Color.RED, Color.GREEN};

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
    private ImageIcon mySelectedSprite = new ImageIcon("src/Sprites/kirby1.png");

    /**
     * Current position of the player.
     */
    private Position myPlayerPosition;

    private final GameState myGameState;
    private JLabel myCurrentQuestionLabel;
    private JPanel myAnswerInputPanel;
    private Object myAnswerComponent;

    private static final int DOOR_WALL = 0;
    private static final int DOOR_NOT_VISITED = 1;
    private static final int DOOR_VISITED = 2;
    private static final int DOOR_FAILED = 3;
    private static final int DOOR_SUCCEEDED = 4;

    /**
     * Stores the index of the current room in the myRooms array
     */
    private int myCurrentRoom;

    /**
     * Minimap panel in the main panel.
     */
    private RoomPanel myMinimap;

    /**
     * Construct a new ViewMockup with the given GameState.
     */
    public ViewMockup(GameState theState) {
        super();

        myGameState = theState;

        theState.addPropertyChangeListener(this);

        myMazeSize = new Dimension(theState.getMaze().getWidth(), theState.getMaze().getHeight());

        myPlayerPosition = new Position(0, 0);

        myCurrentRoom = myPlayerPosition.getY() * myMazeSize.width + myPlayerPosition.getX();

        myRooms = new RoomPanel[myMazeSize.width * myMazeSize.height];

        myMinimap = new RoomPanel(myPlayerPosition, new int[]{0, 0, 0, 0});

        // Initializing the frame
        myFrame = new JFrame("Trivia Maze");

        // Setting behaviour
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setPreferredSize(WINDOW_SIZE);
    }

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

    @Override
    public void displayMaze(Maze theMaze, Position theCurrentPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayMaze'");
    }

    @Override
    public void displayRoom(Room theRoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayRoom'");
    }

    @Override
    public void displayQuestion(Question theQuestion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayQuestion'");
    }

    @Override
    public void displayGameOver(boolean isWon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayGameOver'");
    }

    /**
     * Update the position of the player.
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
        // called when questionsAnswered or questionsCorrect increments, maybe replace?

        // always increment answered questions
        if (theCorrect) {
            // increment correct questions if theCorrect is true
        }
    }

    /**
     * Update displayed rooms.
     */
    private void updateRooms(final Position thePosition) {
        // called when a new room is visited.
    }

    private void updateDoor(final Direction theDir, final int theDoorState) {
        updateStats(theDoorState == 4);
        myRooms[myCurrentRoom].setDoorState(theDir, theDoorState);
        myMinimap.setDoorStates(myRooms[myCurrentRoom].getDoorState());
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PropertyChangeEnabledGameState.PROPERTY_POSITION:
                updatePosition((Position) theEvent.getNewValue());
                break;
            case PropertyChangeEnabledGameState.PROPERTY_ROOM_VISITED:
                updateRooms((Position) theEvent.getNewValue());
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
                GameSaver gameSaver = new GameSaver();
                gameSaver.saveGame(myGameState, fileToSave.getAbsolutePath());
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
                GameSaver gameSaver = new GameSaver();
                GameState loadedState = gameSaver.getSave(fileToLoad.getAbsolutePath());

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
     * @param loadedState The loaded GameState object.
     */
    private void updateGameState(GameState loadedState) {
        if (loadedState == null) {
            JOptionPane.showMessageDialog(myFrame, "Failed to load: Save file is empty or corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        myGameState.removePropertyChangeListener(this);
        myGameState.updateFrom(loadedState);
        myGameState.addPropertyChangeListener(this);
        myPlayerPosition = myGameState.getMyCurrentPosition();
        syncAllRoomPanelDoorStates();
        updatePosition(myPlayerPosition);
        updateQuestionPanel();
        updateMinimap();
    }

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

    private void manageSavesEvent(final ActionEvent theEvent) {
        JOptionPane.showMessageDialog(myFrame, "Managing Saves! (opened a file explorer!)");
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
        """;
        JOptionPane.showMessageDialog(myFrame, howtoMsg);
    }

    // Debug menu events
    private void movePlayerEvent(final ActionEvent theEvent) {
        updatePosition(new Position((myPlayerPosition.getX() + 1) % myMazeSize.width,
                (myPlayerPosition.getY() + 1) % myMazeSize.height));
    }

    /* 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * GUI COMPONENTS BELOW
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    /**
     * Create a new menu bar for the window.
     *
     * @return JMenuBar with necessary elements
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

        final JMenuItem manageItem = new JMenuItem("Manage Saves");
        manageItem.addActionListener(this::manageSavesEvent);

        menu.add(saveItem);
        menu.add(loadItem);
        menu.add(manageItem);

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

        final JMenuItem movePlayerItem = new JMenuItem("Move Player");
        movePlayerItem.addActionListener(this::movePlayerEvent);

        menu.add(movePlayerItem);

        return menu;
    }

    // TODO: Break off each panel into it's own class (including RoomPanel probably)
    private JPanel createContentPane() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

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

        final JPanel minimapPanel = createMinimapPanel();
        GridBagConstraints minimapConstraint = new GridBagConstraints(3, 0, 1, 1, 0.3, 0.3,
                GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(25, 85, 25, 85), 0, 0);

        final JPanel statsPanel = createStatsPanel(3, 5);
        GridBagConstraints statsConstraint = new GridBagConstraints(3, 1, 1, 1, 0.3, 0.3,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel questionPanel = createQuestionPanel();
        GridBagConstraints questionConstraint = new GridBagConstraints(3, 2, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel controlPanel = createControlPanel();
        GridBagConstraints controlConstraint = new GridBagConstraints(3, 3, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 0, 80, 0), 0, 0);

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

    // Reworked createMapPanel() to work with any dimensions and use seperate RoomPanels 
    // instead of coloured boxes.
    // Fix the createMapPanel method - coordinate system consistency
    private JPanel createMapPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(myMazeSize.height, myMazeSize.width, 5, 5));
        panel.setBackground(Color.BLACK);

        for (int row = 0; row < myMazeSize.height; row++) {
            for (int col = 0; col < myMazeSize.width; col++) {
                // Start every door off at DOOR_NOT_VISITED
                int[] doorStates = {DOOR_NOT_VISITED, DOOR_NOT_VISITED, DOOR_NOT_VISITED, DOOR_NOT_VISITED}; // N, S, E, W

                // If this room is on an edge, set the corresponding door to DOOR_WALL
                if (col == 0) {
                    doorStates[3] = DOOR_WALL;
                }
                if (col == myMazeSize.height - 1) {
                    doorStates[2] = DOOR_WALL;
                }
                if (row == 0) {
                    doorStates[0] = DOOR_WALL;
                }
                if (row == myMazeSize.width - 1) {
                    doorStates[1] = DOOR_WALL;
                }

                final RoomPanel roomPane = new RoomPanel(new Position(col, row), doorStates);

                myRooms[row * myMazeSize.width + col] = roomPane;
                panel.add(roomPane);
            }
        }

        updatePosition(new Position(0, 0));

        return panel;
    }

    @Deprecated // in favour of createMinimapPanel()
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
        openDoorButton.setForeground(BACKGROUND_COLOR);
        openDoorButton.setFocusPainted(false);
        openDoorButton.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 2));
        openDoorButton.setPreferredSize(new Dimension(450, 100));

        panel.add(openDoorButton);
        return panel;
    }

    private JPanel createMinimapPanel() {
        myMinimap = new RoomPanel(myPlayerPosition,
                myRooms[myPlayerPosition.getY() * myMazeSize.width + myPlayerPosition.getX()].getDoorState());

        return myMinimap;
    }

    private void updateMinimap() {
        RoomPanel currentRoomPanel = myRooms[myPlayerPosition.getY() * myMazeSize.width + myPlayerPosition.getX()];
        myMinimap.setDoorStates(currentRoomPanel.getDoorState());
        myMinimap.repaint();
    }

    private JPanel createStatsPanel(int answered, int failed) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setLayout(new GridBagLayout());

        Font labelFont = new Font("Monospaced", Font.BOLD, 14);

        JLabel answeredLabel = new JLabel("QUESTIONS ANSWERED: " + answered);
        answeredLabel.setFont(labelFont);
        answeredLabel.setForeground(Color.GREEN);

        JLabel failedLabel = new JLabel("QUESTIONS FAILED:   " + failed);
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

        myCurrentQuestionLabel = new JLabel();
        myCurrentQuestionLabel.setForeground(Color.WHITE);
        myCurrentQuestionLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(myCurrentQuestionLabel, gbc);

        JLabel answerLabel = new JLabel("ANSWER >");
        answerLabel.setForeground(new Color(106, 90, 205));
        answerLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(answerLabel, gbc);

        myAnswerInputPanel = new JPanel();
        myAnswerInputPanel.setBackground(panel.getBackground());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(myAnswerInputPanel, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(e -> processAnswer());

        updateQuestionPanel();

        return panel;
    }

    public void updateQuestionPanel() {

        Room room = myGameState.getMaze().getRoom(myGameState.getMyCurrentPosition());
        Door door = room.getDoor(myGameState.getMyCurrentDirection());

        myAnswerInputPanel.removeAll();

        if (door == null) {
            myCurrentQuestionLabel.setText("No door that way");

        } else if (door.isOpen()) {
            myCurrentQuestionLabel.setText("Door is open");

        } else if (door.isLocked()) {
            myCurrentQuestionLabel.setText("Door is locked");

        } else {

            Question q = door.getQuestion();
            myCurrentQuestionLabel.setText(q.getQuestion());

            switch (q.getQuestionType()) {
                case SHORT_ANSWER:
                    JTextField tf = new JTextField(20);
                    tf.setBackground(Color.BLACK);
                    tf.setForeground(Color.WHITE);
                    tf.setCaretColor(Color.WHITE);
                    tf.setFont(new Font("Monospaced", Font.PLAIN, 14));
                    myAnswerComponent = tf;
                    myAnswerInputPanel.add(tf);
                    tf.addActionListener(e -> processAnswer());
                    break;
                case MULTIPLE_CHOICE:
                    ButtonGroup mcGroup = new ButtonGroup();
                    JPanel mcPanel = new JPanel(new GridLayout(0, 1));
                    mcPanel.setBackground(Color.BLACK);
                    for (String opt : ((MultipleChoiceQuestion) q).getOptions()) {
                        JRadioButton rb = new JRadioButton(opt);
                        rb.setForeground(Color.WHITE);
                        rb.setBackground(Color.BLACK);
                        mcGroup.add(rb);
                        mcPanel.add(rb);
                    }
                    myAnswerComponent = mcGroup;
                    myAnswerInputPanel.add(mcPanel);
                    break;
                case TRUE_FALSE:
                    ButtonGroup tfGroup = new ButtonGroup();
                    JRadioButton t = new JRadioButton("True");
                    JRadioButton f = new JRadioButton("False");
                    for (JRadioButton b : new JRadioButton[]{t, f}) {
                        b.setForeground(Color.WHITE);
                        b.setBackground(Color.BLACK);
                        tfGroup.add(b);
                    }
                    JPanel tfp = new JPanel(new GridLayout(1, 2));
                    tfp.setBackground(Color.BLACK);
                    tfp.add(t);
                    tfp.add(f);
                    myAnswerComponent = tfGroup;
                    myAnswerInputPanel.add(tfp);
                    break;
                default:
                    myAnswerComponent = null;
                    myAnswerInputPanel.add(new JLabel("Unknown question type."));
            }
        }

        myAnswerInputPanel.revalidate();
        myAnswerInputPanel.repaint();

    }

    private void processAnswer() {
        Direction dir = myGameState.getMyCurrentDirection();
        Room room = myGameState.getMaze().getRoom(myGameState.getMyCurrentPosition());
        Door door = room.getDoor(myGameState.getMyCurrentDirection());

        if (door == null || door.isOpen() || door.isLocked()) {

            updateQuestionPanel();
            return;
        }

        String userAnswer = "";
        if (myAnswerComponent instanceof JTextField) {
            userAnswer = ((JTextField) myAnswerComponent).getText();
        } else if (myAnswerComponent instanceof ButtonGroup) {
            ButtonGroup grp = (ButtonGroup) myAnswerComponent;
            for (Enumeration<AbstractButton> ez = grp.getElements(); ez.hasMoreElements();) {
                AbstractButton btn = ez.nextElement();
                if (btn.isSelected()) {
                    userAnswer = btn.getText();
                    break;
                }
            }
        }

        boolean correct = myGameState.answerDoor(dir, userAnswer);
        if (correct) {
            door.open();
            updateDoor(dir, DOOR_SUCCEEDED);

            Position next = myGameState.getMyCurrentPosition().translate(dir);
            Room nr = myGameState.getMaze().getRoom(next);
            Door rev = nr.getDoor(dir.getOpposite());
            if (rev != null) {
                rev.open();
            }
            movePlayer(dir);
        } else {
            door.lock();
            updateDoor(dir, DOOR_FAILED);
            JOptionPane.showMessageDialog(
                    myFrame,
                    "Incorrect! That door is now locked.",
                    "Wrong Answer",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        updateQuestionPanel();
    }

    private JPanel createControlPanel() {

        final int panelSize = 200;
        JPanel dPadPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon dpadImage = new ImageIcon("src/dpad/dpadTrimmed.png");
                Image img = dpadImage.getImage();
                g.drawImage(img, 83, 10, panelSize, panelSize, this);

            }
        };

        ImageIcon upImage = new ImageIcon("src/dpad/upArrow.png");
        Image upImg = upImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon rightImage = new ImageIcon("src/dpad/rightArrow.png");
        Image rightImg = rightImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon leftImage = new ImageIcon("src/dpad/leftArrow.png");
        Image leftImg = leftImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon downImage = new ImageIcon("src/dpad/downArrow.png");
        Image downImg = downImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon upIcon = new ImageIcon(upImg);
        ImageIcon rightIcon = new ImageIcon(rightImg);
        ImageIcon leftIcon = new ImageIcon(leftImg);
        ImageIcon downIcon = new ImageIcon(downImg);

        JButton upButton = new JButton(upIcon);
        JButton rightButton = new JButton(rightIcon);
        JButton leftButton = new JButton(leftIcon);
        JButton downButton = new JButton(downIcon);

        dPadPanel.setPreferredSize(new Dimension(panelSize, panelSize));
        dPadPanel.setLayout(null);

        upButton.setBounds(151, 23, 66, 66);
        upButton.setOpaque(false);
        upButton.setContentAreaFilled(false);
        upButton.setBorderPainted(false);

        rightButton.setBounds(200, 75, 66, 66);
        rightButton.setOpaque(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setBorderPainted(false);

        leftButton.setBounds(100, 75, 66, 66);
        leftButton.setOpaque(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setBorderPainted(false);

        downButton.setBounds(151, 128, 66, 66);
        downButton.setOpaque(false);
        downButton.setContentAreaFilled(false);
        downButton.setBorderPainted(false);

        upButton.addActionListener(e -> handleDPadPress(Direction.NORTH));
        rightButton.addActionListener(e -> handleDPadPress(Direction.EAST));
        leftButton.addActionListener(e -> handleDPadPress(Direction.WEST));
        downButton.addActionListener(e -> handleDPadPress(Direction.SOUTH));

        dPadPanel.add(upButton);
        dPadPanel.add(rightButton);
        dPadPanel.add(leftButton);
        dPadPanel.add(downButton);
        dPadPanel.setBackground(new Color(215, 215, 215));

        return dPadPanel;
    }

    private void handleDPadPress(Direction direction) {
        myGameState.setMyCurrentDirection(direction);
        Maze maze = myGameState.getMaze();
        Room currentRoom = maze.getRoom(myPlayerPosition);
        Door door = currentRoom.getDoor(direction);

        if (door == null) {
            return;
        }

        if (door.isOpen()) {
            movePlayer(direction);
            updateQuestionPanel();
            return;
        }

        if (door.isLocked()) {
            JOptionPane.showMessageDialog(myFrame, "That door is locked!", "Locked", JOptionPane.WARNING_MESSAGE);
            updateQuestionPanel();
            return;
        }

        Question question = door.getQuestion();
        String playerAnswer = JOptionPane.showInputDialog(myFrame, question.getQuestion());

        if (playerAnswer == null) {
            updateQuestionPanel();
            return;
        }

        if (door.answerQuestion(playerAnswer)) {
            door.open();
            updateDoor(direction, DOOR_SUCCEEDED);

            Position nextPosition = myPlayerPosition.translate(direction);
            Room nextRoom = maze.getRoom(nextPosition);
            Door reverseDoor = nextRoom.getDoor(direction.getOpposite());
            if (reverseDoor != null) {
                reverseDoor.open();
            }
            movePlayer(direction);
            updateQuestionPanel();

        } else {
            door.lock();
            updateDoor(direction, DOOR_FAILED);
            JOptionPane.showMessageDialog(myFrame, "Incorrect answer! The door is now locked.", "Wrong Answer", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void movePlayer(Direction direction) {
        Position newPosition = myPlayerPosition.translate(direction);
        myGameState.setMyCurrentPosition(newPosition);
        updatePosition(newPosition);
        myPlayerPosition = newPosition;
    }

    private JPanel createInfoPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        int[] widths = {56, 80, 60, 90};
        int[] heights = {56, 100, 60, 60};
        String[] imagePaths = {
            "src/Sprites/kirby1.png",
            "src/Sprites/kirby2.png",
            "src/Sprites/kirby3.png",
            "src/Sprites/kirby4.png"
        };

        JLabel[] kirbyLabels = new JLabel[imagePaths.length];

        final Border selectedBorder = BorderFactory.createLineBorder(Color.YELLOW, 3);
        final Border defaultBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);

        final JLabel[] selectedLabel = {null};

        for (int i = 0; i < imagePaths.length; i++) {
            ImageIcon originalIcon = new ImageIcon(imagePaths[i]);

            Image scaledImage = originalIcon.getImage().getScaledInstance(widths[i], heights[i], Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel label = new JLabel(scaledIcon);
            if (i == 0) {
                label.setBorder(selectedBorder);
                selectedLabel[0] = label;
            } else {
                label.setBorder(defaultBorder);
            }
            kirbyLabels[i] = label;
            final int index = i;
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int choice = JOptionPane.showConfirmDialog(
                            panel,
                            "Confirm selection of character" + (index + 1) + "?",
                            "Confirm Selection",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        if (selectedLabel[0] != null) {
                            selectedLabel[0].setBorder(defaultBorder);
                        }
                        label.setBorder(selectedBorder);
                        selectedLabel[0] = label;

                        mySelectedSprite = ((ImageIcon) label.getIcon());

                        for (RoomPanel room : myRooms) {
                            if (room != null) {
                                room.repaint();
                            }
                        }
                    } else {
                        mySelectedSprite = null;
                    }
                }
            });

            panel.add(label);
        }
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        mainPanel.add(panel, BorderLayout.CENTER);

        JLabel chooseLabel = new JLabel("CHOOSE YOUR CHARACTER");
        chooseLabel.setForeground(Color.YELLOW);
        chooseLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        mainPanel.add(chooseLabel, BorderLayout.NORTH);

        return mainPanel;

    }

    private ImageIcon getSelectedSprite() {
        return mySelectedSprite;
    }

    private class RoomPanel extends JPanel {

        private final Position myPos;

        private final Map<Direction, Polygon> DOORTRIANGLES = new HashMap<Direction, Polygon>(4);

        /**
         * Array of states for a door to be in 0: Wall 1: Not visited 2: Visited
         * 3: Failed 4: Succeeded north, south, east, west
         */
        private int[] myDoorState;

        private Color myColor;

        private boolean isPlayerPosition = false;

        public RoomPanel(final Position thePos, final int[] theRoomState) {
            super();

            myPos = thePos;
            myDoorState = theRoomState;
            myColor = BACKGROUND_COLOR;
            setBackground(myColor);
        }

        @Deprecated
        public void resetBackground() {
            myColor = new Color((myPos.getX() * 32) % 255, 0, (myPos.getY() * 32) % 255);
            setBackground(myColor);
            repaint();
        }

        public void setDoorState(final Direction theDir, final int theState) {
            if (theState >= 0 && theState <= 4) {
                // Assign to index based on the ordinal
                // {NORTH, SOUTH, EAST, WEST}
                myDoorState[theDir.ordinal()] = theState;
            }
            repaint();
        }

        public void setDoorStates(final int[] theStates) {
            for (int state : theStates) {
                if (state < 0 || state > 4) {
                    throw new IllegalArgumentException("Door state not defined");
                }
            }
            myDoorState = theStates;
            repaint();
        }

        public int[] getDoorState() {
            return myDoorState;
        }

        public void setIsPlayerPosition(boolean theIsPosition) {
            isPlayerPosition = theIsPosition;
        }

        @Override
        public void paintComponent(final Graphics theGraphics) {
            super.paintComponent(theGraphics);

            Graphics2D g2d = (Graphics2D) theGraphics;

            updateDoorTriangles();

            for (Direction dir : Direction.values()) {
                g2d.setColor(DOOR_COLORS[myDoorState[dir.ordinal()]]);
                g2d.fillPolygon(DOORTRIANGLES.get(dir));

                g2d.setColor(BACKGROUND_COLOR);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawPolygon(DOORTRIANGLES.get(dir));
            }
            if (isPlayerPosition && mySelectedSprite != null) {
                Image spriteImg = mySelectedSprite.getImage();
                int panelW = getWidth();
                int panelH = getHeight();
                int spriteW = Math.min(panelW, panelH) / 2;
                int spriteH = Math.min(panelW, panelH) / 2;
                int x = (panelW - spriteW) / 2;
                int y = (panelH - spriteH) / 2;
                g2d.drawImage(spriteImg, x, y, spriteW, spriteH, this);
            }

        }

        private void updateDoorTriangles() {
            int width = getWidth();
            int height = getHeight();

            if (width > 0 && height > 0) {
                Polygon northPoly = new Polygon(
                        new int[]{0, width / 2, width},
                        new int[]{0, height / 2, 0},
                        3
                );

                Polygon southPoly = new Polygon(
                        new int[]{0, width / 2, width},
                        new int[]{height, height / 2, height},
                        3
                );

                Polygon eastPoly = new Polygon(
                        new int[]{width, width / 2, width},
                        new int[]{0, height / 2, height},
                        3
                );

                Polygon westPoly = new Polygon(
                        new int[]{0, width / 2, 0},
                        new int[]{0, height / 2, height},
                        3
                );

                DOORTRIANGLES.put(Direction.NORTH, northPoly);
                DOORTRIANGLES.put(Direction.SOUTH, southPoly);
                DOORTRIANGLES.put(Direction.EAST, eastPoly);
                DOORTRIANGLES.put(Direction.WEST, westPoly);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        @Override
        public String toString() {
            return myPos.getX() + ", " + myPos.getY() + " with " + Arrays.toString(myDoorState);
        }
    }
}
