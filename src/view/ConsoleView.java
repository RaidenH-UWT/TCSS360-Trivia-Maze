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
    @Override
    public void displayMaze(Maze theMaze, Position theCurrentPosition) {
        System.out.println("Maze View:");
        System.out.println("You are at position: (" + theCurrentPosition.getX() + ", " + theCurrentPosition.getY() + ")");
        System.out.println();
    }
    @Override
    public void displayRoom(Room theRoom) {
        System.out.println("You have entered a new room at (" + theRoom.getX() + ", " + theRoom.getY() + ").");
        System.out.println("Visited: " + (theRoom.isVisited() ? "Yes" : "No"));
        System.out.println();
    }
    @Override
    public void displayQuestion(Question theQuestion) {
        System.out.println("Question:");
        System.out.println(theQuestion.getQuestion());
        System.out.println();
    }

    // note in super
    @Override
    public void displayMessage(String theMessage) {
        System.out.println(theMessage);
        System.out.println();
    }
    @Override
    public void displayGameOver(boolean isWon) {
        if (isWon) {
            System.out.println("Congratulations! You have escaped the maze!");
        } else {
            System.out.println("Game Over! You are trapped in the maze.");
        }
        System.out.println();
    }

    // note in super
    @Override
    public String getAnswer() {
        System.out.println("Your answer: ");
        return null;
    }
}
