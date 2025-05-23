package src.view;

import java.beans.PropertyChangeEvent;

import src.model.GameState;
import src.model.Maze;
import src.model.Position;
import src.model.Question;
import src.model.Room;

// TODO: Console view implementation.

/**
 * Console implementation of the GameView interface.
 * @author Raiden H
 * @version May 1, 2025
 */
public class ConsoleView implements GameView {
    public ConsoleView(final GameState theState) {
        super();

        theState.addPropertyChangeListener(this);
    }

    @Override
    public void displayMaze(final Maze theMaze, final Position theCurrentPosition) {
        System.out.println("Maze View:");
        System.out.println("You are at position: (" + theCurrentPosition.getX() + ", " + theCurrentPosition.getY() + ")");
        System.out.println();
    }
    @Override
    public void displayRoom(final Room theRoom) {
        System.out.println("You have entered a new room at (" + theRoom.getX() + ", " + theRoom.getY() + ").");
        System.out.println("Visited: " + (theRoom.isVisited() ? "Yes" : "No"));
        System.out.println();
    }
    @Override
    public void displayQuestion(final Question theQuestion) {
        System.out.println("Question:");
        System.out.println(theQuestion.getQuestion());
        System.out.println();
    }

    @Override
    public void displayGameOver(final boolean theIsWon) {
        if (theIsWon) {
            System.out.println("Congratulations! You have escaped the maze!");
        } else {
            System.out.println("Game Over! You are trapped in the maze.");
        }
        System.out.println();
    }

    /**
     * Listening for property changes
     */
    public void propertyChange(final PropertyChangeEvent theEvent) {

    }
}
