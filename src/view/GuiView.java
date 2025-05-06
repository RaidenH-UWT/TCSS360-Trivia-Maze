package src.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import src.model.Maze;
import src.model.Position;
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
     * Create a new GuiView object.
     */
    public GuiView() {
        super();
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
}
