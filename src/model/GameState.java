package src.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all mutable state for the model package
 * @author Raiden H
 * @version May 1, 2025
 */
public class GameState implements PropertyChangeEnabledGameState {
    /**
     * PropertyChangeSupport object for implementing the PropertyChange API.
     */
    private final PropertyChangeSupport myPCS;
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

    // maybe List<Room> instead, cause rooms already store their position and we could access the data faster?
    /**
     * Stores the rooms the player has visited.
     */
    private List<Position> visitedRooms;

    /**
     * Initialize the state with the given maze.
     * @param theWidth int width of the new maze
     * @param theHeight int height of the new maze
     * @param theCategory String category to pull questions from
     */
    public GameState(int theWidth, int theHeight, String theCategory) {
        super();

        myMaze = new Maze(theWidth, theHeight, theCategory);
        currentPosition = myMaze.getEntrance();
        questionsAnswered = 0;
        questionsCorrect = 0;
        visitedRooms = new ArrayList<Position>(theWidth * theHeight);

        myPCS = new PropertyChangeSupport(this);
    }

    /**
     * Creates a new maze of the given dimensions.
     * @param theWidth int width of the new maze
     * @param theHeight int height of the new maze
     */
    public GameState(int theWidth, int theHeight) {
        this(theWidth, theHeight, null);
    }

    /**
     * Initialize the state with the given maze.
     * @param side int size of the square maze
     */
    public GameState(int side) {
        this(side, side);
    }

    /**
     * Get the current position of the player.
     * @return Position the player is currently at
     */
    public Position getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the position of the player.
     * @param thePosition Position for the player to be set to
     */
    public void setCurrentPosition(Position thePosition) {
        // Make sure the passed position is within bounds
        if (isWithinBounds(thePosition)) {
            currentPosition = thePosition;

            // fire property change event with the new position.
            myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_POSITION, null, thePosition);
        } else {
            throw new IllegalArgumentException("Position out of bounds.");
        }
    }

    /**
     * Get the maze object for this game.
     * @return Maze object with the data for this game
     */
    public Maze getMaze() {
        return myMaze;
    }

    /**
     * Get how many questions the player has answered.
     * @return int number of questions the player has answered
     */
    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    /**
     * Get how many questions the player has gotten correct.
     * @return int number of questions the player has answered correctly
     */
    public int getQuestionsCorrect() {
        return questionsCorrect;
    }

    /**
     * Bumps up the number of questions the player has answered by 1.
     */
    public void incrementQuestionsAnswered() {
        questionsAnswered++;

        // fire a property change event passing int 1
        myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_QUESTION_ANSWERED, null, 1);
    }

    /**
     * Bumps up the number of questions the player has gotten correct by 1.
     */
    public void incrementQuestionsCorrect() {
        questionsCorrect++;

        // fire a property change event passing int 1
        myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_QUESTION_CORRECT, null, 1);
    }

    /**
     * Add a room the player has visited to the list.
     * @param thePosition Position of the room to be added
     */
    public void addVisitedRoom(Position thePosition) {
        // make sure the room is within bounds and not already visited
        if (isWithinBounds(thePosition) && !visitedRooms.contains(thePosition)) {
            visitedRooms.add(thePosition);

            // Fire a property change event passing the position of the room visited.
            myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_ROOM_VISITED, null, thePosition);
        }
    }

    /**
     * Get which rooms the player has visited.
     * @return List<Position> list of the positions of rooms the player has visited
     */
    public List<Position> getVisitedRooms() {
        return visitedRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(String thePropertyName, PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * Check whether the given position is within the bounds of the maze.
     * @param thePosition position to check
     * @return true if thePosition is within bounds, false otherwise
     */
    private boolean isWithinBounds(Position thePosition) {
        return thePosition.getX() <= getMaze().getWidth() 
            && thePosition.getY() <= getMaze().getHeight();
    }
}
