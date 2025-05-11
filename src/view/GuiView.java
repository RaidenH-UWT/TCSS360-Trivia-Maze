package src.view;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import src.model.GameState;
import src.model.Maze;
import src.model.Position;
import src.model.PropertyChangeEnabledGameState;
import src.model.Question;
import src.model.Room;

/**
 * GUI implementation of the GameView interface.
 * @author Raiden H
 * @version May 1, 2025
 */
public class GuiView implements GameView {
    /**
     * Window frame
     */
    private JFrame mainFrame;

    /**
     * Panel for the maze
     */
    private JPanel mazePanel;

    /**
     * Panel for the room preview
     */
    private JPanel roomPanel;

    /**
     * Panel for the question preview
     */
    private JPanel questionPanel;

    /**
     * Menu bar for the window
     */
    private JMenuBar menuBar;

    /**
     * Map<String, Runnable> for triggering methods based on PropertyChange events.
     */
    private Map<String, Runnable> propertyEvents;

    /**
     * Create a new GuiView object.
     */
    public GuiView(GameState theState) {
        super();

        propertyEvents = new HashMap<String, Runnable>();
        
        // here we add all the possible property events to the map.
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_POSITION, () -> updatePosition());
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_QUESTION_ANSWERED, () -> updateStats());
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_QUESTION_CORRECT, () -> updateStats());
        propertyEvents.put(PropertyChangeEnabledGameState.PROPERTY_ROOM_VISITED, () -> updateRooms());

        theState.addPropertyChangeListener(this);
    }
    
    public void displayMaze(Maze theMaze, Position theCurrentPosition) {
        
    }

    public void displayRoom(Room theRoom) {
        
    }

    public void displayQuestion(Question theQuestion) {
        
    }

    public void displayMessage(String theMessage) {

    }

    public void displayGameOver(boolean isWon) {
        
    }

    public String getAnswer() {
        return null;
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

    /**
     * Create a new menu bar for the window.
     * @return JMenuBar with necessary elements
     */
    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Save Game"));
        fileMenu.add(new JMenuItem("Load Game"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Exit"));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("About"));
        helpMenu.add(new JMenuItem("Game Play Instructions"));

        bar.add(fileMenu);
        bar.add(helpMenu);

        return bar;
    }

    /**
     * Listening for property changes
     */
    public void propertyChange(PropertyChangeEvent theEvent) {
        // Run a lambda from our propertyEvent map.
        propertyEvents.get(theEvent.getPropertyName()).run();
    }
}
