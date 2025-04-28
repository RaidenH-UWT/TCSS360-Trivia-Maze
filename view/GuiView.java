package view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import model.Maze;
import model.Position;
import model.Question;
import model.Room;

public class GuiView implements GameView {
    private JFrame mainFrame;
    private JPanel mazePanel;
    private JPanel roomPanel;
    private JPanel questionPanel;
    private JMenuBar menuBar;

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

    private JMenuBar createMenuBar() {
        return null;
    }
}
