package src.view;

import java.beans.PropertyChangeListener;

import src.model.Maze;
import src.model.Position;
import src.model.Question;
import src.model.Room;

/**
 * Interface describing behaviour of GameView classes.
 * @author Raiden H
 * @version May 1, 2025
 */
public interface GameView extends PropertyChangeListener {
    /**
     * Display the maze and player in their current state.
     * @param theMaze Maze object to display
     * @param theCurrentPosition Position the player is curerntly at
     */
    public void displayMaze(Maze theMaze, Position theCurrentPosition);

    /**
     * Display a view of the given room.
     * @param theRoom Room object to display
     */
    public void displayRoom(Room theRoom);

    /**
     * Display the given question.
     * @param theQuestion Question object to be displayed
     */
    public void displayQuestion(Question theQuestion);

    // TODO: Unsure what the purpose of this is, please document.
    public void displayMessage(String theMessage);

    /**
     * Display when the game ends.
     * @param isWon boolean whether the game ended in a win or not
     */
    public void displayGameOver(boolean isWon);

    // TODO: Why is this here, what does it do.
    public String getAnswer();
}
