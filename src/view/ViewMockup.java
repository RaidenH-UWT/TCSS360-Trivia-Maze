package src.view;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import src.model.GameState;
import src.model.Maze;
import src.model.Position;
import src.model.PropertyChangeEnabledGameState;
import src.model.Question;
import src.model.Room;
import src.model.GameSaver;

public class ViewMockup implements GameView {

    /**
     * The Dimension of the screen.
     */
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final Dimension WINDOW_SIZE = new Dimension(1024, 1024);

    /**
     * Map<String, Runnable> for triggering methods based on PropertyChange
     * events.
     */
    private Map<String, Runnable> propertyEvents;

    private final JFrame myFrame;

    public ViewMockup(GameState theState) {
        super();

        theState.addPropertyChangeListener(this);

        propertyEvents = new HashMap<String, Runnable>();
        // here we add all the possible property events to the map.
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_POSITION, () -> updatePosition());
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_QUESTION_ANSWERED, () -> updateStats());
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_QUESTION_CORRECT, () -> updateStats());
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_ROOM_VISITED, () -> updateRooms());

        // Initializing the frame
        myFrame = new JFrame("Trivia Maze");

        // Setting behaviour
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(true);
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
    public void displayMessage(String theMessage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayMessage'");
    }

    @Override
    public void displayGameOver(boolean isWon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayGameOver'");
    }

    @Override
    public String getAnswer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnswer'");
    }

    /**
     * Update the position of the player.
     */
    private void updatePosition() {
        // called when the player position changes
    }

    /**
     * Update question stats for the player.
     */
    private void updateStats() {
        // called when questionsAnswered or questionsCorrect increments, maybe replace?
    }

    /**
     * Update displayed rooms.
     */
    private void updateRooms() {
        // called when a new room is visited.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'propertyChange'");
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
        // Create a file chooser to select the save location
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Save Game");

        // Show the save dialog
        int userSelection = fileChooser.showSaveDialog(myFrame);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            try {
                // Need to have access to the GameState instance
                // For now, let's create a placeholder or use a method to get the current state
                GameState currentState = null; // TODO: Replace with actual GameState retrieval logic
                GameSaver gameSaver = new GameSaver();
                gameSaver.saveGame(currentState, fileToSave.getAbsolutePath());
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
        // TODO: Implement logic to update the current game state and refresh the UI
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
        """;
        JOptionPane.showMessageDialog(myFrame, howtoMsg);
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

        bar.add(fileMenu);
        bar.add(saveMenu);
        bar.add(helpMenu);

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

        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this::aboutEvent);

        final JMenuItem howToItem = new JMenuItem("How to play");
        howToItem.addActionListener(this::howToEvent);

        menu.add(aboutItem);
        menu.add(howToItem);

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

        final JPanel minimapPanel = createMinimapPanel();
        GridBagConstraints minimapConstraint = new GridBagConstraints(3, 0, 1, 1, 0.3, 0.3,
                GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel statsPanel = createStatsPanel();
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
        panel.setBackground(new Color(255, 0, 0));

        return panel;
    }

    private JPanel createMinimapPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 255, 0));

        return panel;
    }

    private JPanel createStatsPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 255));

        return panel;
    }

    private JPanel createQuestionPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 0));

        return panel;
    }

    private JPanel createControlPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 0, 255));

        return panel;
    }

    private JPanel createInfoPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 255, 255));

        return panel;
    }
}
