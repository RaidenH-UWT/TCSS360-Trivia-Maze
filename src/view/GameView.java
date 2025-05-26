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
     * List of states for a door to be in
     * 0: Wall
     * 1: Not visited (never been)
     * 2: Visited (been, but hasn't answered yet)
     * 3: Failed
     * 4: Succeeded
     */
    public static final int DOOR_WALL = 0;
    public static final int DOOR_NOT_VISITED = 1;
    public static final int DOOR_VISITED = 2;
    public static final int DOOR_FAILED = 3;
    public static final int DOOR_SUCCEEDED = 4;

    /**
     * Display the maze and player in their current state.
     * @param theMaze Maze object to display
     * @param theCurrentPosition Position the player is curerntly at
     */
    public void displayMaze(final Maze theMaze, final Position theCurrentPosition);

    /**
     * Display a view of the given room.
     * @param theRoom Room object to display
     */
    public void displayRoom(final Room theRoom);

    /**
     * Display the given question.
     * @param theQuestion Question object to be displayed
     */
    public void displayQuestion(final Question theQuestion);

    /**
     * Display when the game ends.
     * @param theIsWon boolean whether the game ended in a win or not
     */
    public void displayGameOver(final boolean theIsWon);
}