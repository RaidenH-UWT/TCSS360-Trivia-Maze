package src.model;

import java.util.List;

/**
 * Contains all mutable state for the model package
 * @author Raiden H
 * @version May 1, 2025
 */
public class GameState {
    /**
     * Stores the maze object.
     */
    private Maze myMaze;
    
    /**
     * Stores the current position of the player.
     */
    private Position currentPosition;
    // Could calculate these instead of storing them just counting the rooms in the maze
    /**
     * Stores the number of questions the player has answered.
     */
    private int questionsAnswered;

    /**
     * Stores the number of questions the player has answered correctly.
     */
    private int questionsCorrect;

    /**
     * Stores the rooms the player has visited.
     */
    private List<Position> visitedRooms;

    /**
     * Initialize the state with the given maze.
     * @param theMaze a Maze object for the game
     */
    public GameState(Maze theMaze) {
        super();
    }

    /**
     * Get the current position of the player.
     * @return Position the player is currently at
     */
    public Position getCurrentPosition() {
        return null;
    }

    /**
     * Sets the position of the player.
     * @param thePosition Position for the player to be set to
     */
    public void setCurrentPosition(Position thePosition) {

    }

    /**
     * Get the maze object for this game.
     * @return Maze object with the data for this game
     */
    public Maze getMaze() {
        return null;
    }

    /**
     * Get how many questions the player has answered.
     * @return int number of questions the player has answered
     */
    public int getQuestionsAnswered() {
        return 0;
    }

    /**
     * Get how many questions the player has gotten correct.
     * @return int number of questions the player has answered correctly
     */
    public int getQuestionsCorrect() {
        return 0;
    }

    /**
     * Bumps up the number of questions the player has answered by 1.
     */
    public void incrementQuestionsAnswered() {

    }

    /**
     * Bumps up the number of questions the player has gotten correct by 1.
     */
    public void incrementQuestionsCorrect() {

    }

    /**
     * Add a room the player has visited to the list.
     * @param thePosition Position of the room to be added
     */
    public void addVisitedRoom(Position thePosition) {

    }

    /**
     * Get which rooms the player has visited.
     * @return List<Position> list of the positions of rooms the player has visited
     */
    public List<Position> getVisitedRooms() {
        return null;
    }
}
