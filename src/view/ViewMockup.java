package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import src.model.Direction;
import src.model.Door;
import src.model.GameSaver;
import src.model.GameState;
import src.model.Maze;
import src.model.Position;
import src.model.PropertyChangeEnabledGameState;
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
     * Stores selected sprite.
     */
    private ImageIcon mySelectedSprite = new ImageIcon("src/sprites/kirby1.png");

    /**
     * Current position of the player.
     */
    private Position myPlayerPosition;

    private final GameState myGameState;

    /**
     * Stores the index of the current room in the myRooms array
     */
    private int myCurrentRoom;

    /**
     * Map panel in the main panel.
     */
    private MapPanel myMapPanel;

    /**
     * Minimap panel in the main panel.
     */
    private MinimapPanel myMinimap;

    /**
     * Stats panel in the main panel.
     */
    private StatsPanel myStatsPanel;

    /**
     * Question panel in the main panel.
     */
    private QuestionPanel myQuestionPanel;

    /**
     * Control panel in the main panel.
     */
    private JPanel myControlPanel;

    /**
     * Info panel in the main panel.
     */
    private JPanel myInfoPanel;

    private MusicPlayer myMusicPlayer;

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

        myMinimap = new MinimapPanel(myPlayerPosition, new int[]{0, 0, 0, 0}, BACKGROUND_COLOR);

        // Initializing the frame
        myFrame = new JFrame("Trivia Maze");

        // Setting behaviour
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setPreferredSize(WINDOW_SIZE);
    }

    public void initialize() {

        myMusicPlayer = new MusicPlayer();

        // Adding GUI components
        myFrame.setJMenuBar(createMenuBar());
        myFrame.setContentPane(createContentPane());

        // Displaying the window
        myFrame.pack();
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
        myFrame.setVisible(true);

        // start music
        myMusicPlayer = new MusicPlayer();
        myMusicPlayer.playMusic("src/music/SundayPicnic.wav");
    }

    /**
     * Update the position of the player.
     */
    private void updatePosition(Position newPos) {
        // called when the player position changes
        myMapPanel.getRoomPanels()[myCurrentRoom].setIsPlayerPosition(false);
        myMapPanel.getRoomPanels()[myCurrentRoom].repaint();

        myCurrentRoom = newPos.getY() * myMazeSize.width + newPos.getX();

        myMinimap.setDoorStates(myMapPanel.getRoomPanels()[myCurrentRoom].getDoorState());

        myMapPanel.getRoomPanels()[myCurrentRoom].setIsPlayerPosition(true);
        myMapPanel.getRoomPanels()[myCurrentRoom].repaint();
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
     * Update displayed rooms.
     */
    private void updateRooms(final Position thePosition) {
        // called when a new room is visited.
    }

    private void updateDoor(final Direction theDir, final int theDoorState) {
        //updateStats(theDoorState == 4);
        //calling this here updated stats panel incorrectly.
        myMapPanel.getRoomPanels()[myCurrentRoom].setDoorState(theDir, theDoorState);
        myMinimap.setDoorStates(myMapPanel.getRoomPanels()[myCurrentRoom].getDoorState());

        if (theDoorState != 2) {
            Door oppDoor;
            switch (theDir) {
                case Direction.NORTH:
                    myMapPanel.getRoomPanels()[myCurrentRoom - myMazeSize.width].setDoorState(Direction.SOUTH, theDoorState);
                    oppDoor = myGameState.getMaze().getRoom(
                            new Position(myPlayerPosition.getX(), myPlayerPosition.getY() - 1)).getDoor(Direction.SOUTH);
                    if (theDoorState == 4) {
                        oppDoor.open();
                    } else {
                        oppDoor.lock();
                    }
                    break;
                case Direction.SOUTH:
                    myMapPanel.getRoomPanels()[myCurrentRoom + myMazeSize.width].setDoorState(Direction.NORTH, theDoorState);
                    oppDoor = myGameState.getMaze().getRoom(
                            new Position(myPlayerPosition.getX(), myPlayerPosition.getY() + 1)).getDoor(Direction.NORTH);
                    if (theDoorState == 4) {
                        oppDoor.open();
                    } else {
                        oppDoor.lock();
                    }
                    break;
                case Direction.EAST:
                    myMapPanel.getRoomPanels()[myCurrentRoom + 1].setDoorState(Direction.WEST, theDoorState);
                    oppDoor = myGameState.getMaze().getRoom(
                            new Position(myPlayerPosition.getX() + 1, myPlayerPosition.getY())).getDoor(Direction.WEST);
                    if (theDoorState == 4) {
                        oppDoor.open();
                    } else {
                        oppDoor.lock();
                    }
                    break;
                case Direction.WEST:
                    myMapPanel.getRoomPanels()[myCurrentRoom - 1].setDoorState(Direction.EAST, theDoorState);
                    oppDoor = myGameState.getMaze().getRoom(
                            new Position(myPlayerPosition.getX() - 1, myPlayerPosition.getY())).getDoor(Direction.EAST);
                    if (theDoorState == 4) {
                        oppDoor.open();
                    } else {
                        oppDoor.lock();
                    }
                    break;
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
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
        int confirm = JOptionPane.showConfirmDialog(
                myFrame,
                "Are you sure you want to start a new game?",
                "Confirm New Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        int width = myGameState.getMaze().getWidth();
        int height = myGameState.getMaze().getHeight();
        GameState freshState = new GameState(width, height);
        updateGameState(freshState);
        myStatsPanel.clear();
        myQuestionPanel.clear();

        JOptionPane.showMessageDialog(
                myFrame,
                "Starting a new game!",
                "New Game",
                JOptionPane.INFORMATION_MESSAGE
        );

    }

    private void settingsEvent(final ActionEvent theEvent) {
        SettingsPage settings = new SettingsPage(myFrame, myMusicPlayer);
        settings.setVisible(true);
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
        updateMinimap();
    }

    private void syncAllRoomPanelDoorStates() {
        Maze maze = myGameState.getMaze();
        for (int row = 0; row < myMazeSize.height; row++) {
            for (int col = 0; col < myMazeSize.width; col++) {
                Room modelRoom = maze.getRoom(new Position(col, row));
                RoomPanel panelRoom = myMapPanel.getRoomPanels()[row * myMazeSize.width + col];
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
        Use the arrow keys or controls (in the bottom right) to navigate the maze and rooms.

        Answer questions on the right in multiple choice or short answer format.

        Reach the exit at the yellow square to win.

        If you're locked out and can't make it to the exit, you lose.

        Save, load, and restart the game from the menu bar.
        """;
        JOptionPane.showMessageDialog(myFrame, howtoMsg);
    }

    // Debug menu events
    private void skipQuestion(final ActionEvent theEvent) {
        myQuestionPanel.skipQuestion();
    }

    // Misc events
    /**
     * Called when the game is over.
     *
     * @param theWin true when the game ended in a win, false for a loss
     */
    private void gameOverEvent(final boolean theWin) {
        if (theWin) {
            myMusicPlayer.playSoundEffect("src/music/win.wav");
        } else {
            myMusicPlayer.playSoundEffect("src/music/loss.wav");
        }

        // Game over message
        String gameOverMsg;
        if (theWin) {
            gameOverMsg = """
                Congratulations, you won!
            """;
        } else {
            gameOverMsg = """
                GAME OVER
            """;
        }

        int selection = JOptionPane.showOptionDialog(
                myFrame,
                gameOverMsg,
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Restart", "Quit"},
                null
        );

        if (selection == 0) {
            newGameEvent(null);
        } else {
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        }
    }


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

        final JMenuItem skipQuestionItem = new JMenuItem("Skip Question");
        skipQuestionItem.addActionListener(this::skipQuestion);

        menu.add(skipQuestionItem);

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
        myMapPanel = new MapPanel(myMazeSize.width, myMazeSize.height, BACKGROUND_COLOR, myGameState.getMaze().getExit());
        GridBagConstraints mapConstraint = new GridBagConstraints(0, 0, 3, 3, 0.7, 0.7,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        updatePosition(new Position(0, 0));
        myMapPanel.updateSprite(mySelectedSprite);

        myMinimap = new MinimapPanel(myPlayerPosition, myMapPanel.getRoomPanels()[myCurrentRoom].getDoorState(), BACKGROUND_COLOR);
        GridBagConstraints minimapConstraint = new GridBagConstraints(3, 0, 1, 1, 0.3, 0.3,
                GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(25, 85, 25, 85), 0, 0);

        myStatsPanel = new StatsPanel();
        GridBagConstraints statsConstraint = new GridBagConstraints(3, 1, 1, 1, 0.3, 0.3,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        myQuestionPanel = new QuestionPanel(this::processAnswer, myMusicPlayer);
        GridBagConstraints questionConstraint = new GridBagConstraints(3, 2, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        myControlPanel = createControlPanel();
        GridBagConstraints controlConstraint = new GridBagConstraints(3, 3, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 0, 80, 0), 0, 0);

        myInfoPanel = createInfoPanel();
        GridBagConstraints infoConstraint = new GridBagConstraints(0, 3, 3, 1, 0.3, 0.3,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        mainPanel.add(myMapPanel, mapConstraint);
        mainPanel.add(myMinimap, minimapConstraint);
        mainPanel.add(myStatsPanel, statsConstraint);
        mainPanel.add(myQuestionPanel, questionConstraint);
        mainPanel.add(myControlPanel, controlConstraint);
        mainPanel.add(myInfoPanel, infoConstraint);
        bindWASDKeys(mainPanel);

        return mainPanel;
    }

    private void updateMinimap() {
        myMinimap.setDoorStates(myMapPanel.getRoomPanels()[myCurrentRoom].getDoorState());
        myMinimap.setIsExit(myPlayerPosition.equals(myGameState.getMaze().getExit()));
        myMinimap.repaint();
    }

    private void processAnswer(final String theAnswer) {
        if (theAnswer == null) {
            JOptionPane.showMessageDialog(myFrame, "No door there!", "No door", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Direction dir = myGameState.getMyCurrentDirection();
        boolean wasCorrect = myGameState.tryMove(dir, theAnswer);
        updateStats(wasCorrect);

        if (wasCorrect) {
            myMusicPlayer.playSoundEffect("src/music/correct.wav");
        } else {
            myMusicPlayer.playSoundEffect("src/music/incorrect.wav");
            JOptionPane.showMessageDialog(myFrame, "Incorrect! That door is now locked.", "Wrong Answer", JOptionPane.ERROR_MESSAGE);
        }

        if (!myGameState.getMaze().isPathAvailable(myPlayerPosition)) {
            gameOverEvent(false);
        }
    }

    private JPanel createControlPanel() {

        final int panelSize = 200;
        JPanel dPadPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon dpadImage = new ImageIcon("src/sprites/dpadTrimmed.png");
                Image img = dpadImage.getImage();
                g.drawImage(img, 83, 10, panelSize, panelSize, this);
            }
        };

        ImageIcon upImage = new ImageIcon("src/sprites/upArrow.png");
        Image upImg = upImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon rightImage = new ImageIcon("src/sprites/rightArrow.png");
        Image rightImg = rightImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon leftImage = new ImageIcon("src/sprites/leftArrow.png");
        Image leftImg = leftImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon downImage = new ImageIcon("src/sprites/downArrow.png");
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
        Room currentRoom = myGameState.getMaze().getRoom(myPlayerPosition);
        Door door = currentRoom.getDoor(direction);
        if (myMapPanel.getRoomPanels()[myCurrentRoom].getDoorState()[direction.ordinal()] == DOOR_NOT_VISITED) {
            updateDoor(direction, DOOR_VISITED);
        }

        if (door == null) {
            myQuestionPanel.updateQuestion(null);
            return;
        }

        if (door.isOpen()) {
            movePlayer(direction);
            myQuestionPanel.updateQuestion(null);
        } else if (door.isLocked()) {
            JOptionPane.showMessageDialog(myFrame, "That door is locked!", "Locked", JOptionPane.WARNING_MESSAGE);
        } else {
            myGameState.setMyCurrentDirection(direction);
            door = myGameState.getMaze().getRoom(myPlayerPosition).getDoor(direction);
            myQuestionPanel.updateQuestion(door.getQuestion());
        }

        if (myPlayerPosition.equals(myGameState.getMaze().getExit())) {
            gameOverEvent(true);
        }
    }

    private void bindWASDKeys(JPanel panel) {
        panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moveUp");
        panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        panel.getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDPadPress(Direction.NORTH);
            }
        });
        panel.getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDPadPress(Direction.SOUTH);
            }
        });
        panel.getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDPadPress(Direction.WEST);
            }
        });
        panel.getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDPadPress(Direction.EAST);
            }
        });
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
            "src/sprites/kirby1.png",
            "src/sprites/kirby2.png",
            "src/sprites/kirby3.png",
            "src/sprites/kirby4.png"
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

                        for (RoomPanel room : myMapPanel.getRoomPanels()) {
                            if (room != null) {
                                room.setSelectedSprite(mySelectedSprite);
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
}
