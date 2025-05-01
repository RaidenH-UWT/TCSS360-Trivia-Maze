package src.view;

import src.model.Maze;
import src.model.Position;
import src.model.Question;
import src.model.Room;

/**
 * Console implementation of the GameView interface.
 * @author Raiden H
 * @version May 1, 2025
 */
public class ConsoleView implements GameView {
    public void displayMaze(Maze theMaze, Position theCurrentPosition) {
        
    }

    public void displayRoom(Room theRoom) {
        
    }

    public void displayQuestion(Question theQuestion) {
        
    }

    // note in super
    public void displayMessage(String theMessage) {

    }

    public void displayGameOver(boolean isWon) {
        
    }

    // note in super
    public String getAnswer() {
        return null;
    }
}
