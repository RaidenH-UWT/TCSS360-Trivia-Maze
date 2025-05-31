package src.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all mutable state for the model package
 *
 * @author Raiden H
 * @version May 1, 2025
 */
public class GameState implements PropertyChangeEnabledGameState, java.io.Serializable {

    private static final long serialVersionUID = 1L;
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
    private Position myCurrentPosition;

    /**
     * Stores the current direction of the player. Used for specifying which
     * Door we're looking at.
     */
    private Direction myCurrentDirection;
    // Could calculate these instead of storing them just counting the rooms in the maze
    // TODO: rework with updated property change events
    /**
     * Stores the number of questions the player has answered.
     */
    private int myQuestionsFailed;

    /**
     * Stores the number of questions the player has answered correctly.
     */
    private int myQuestionsSucceeded;

    // maybe List<Room> instead, cause rooms already store their position and we could access the data faster?
    /**
     * Stores the rooms the player has visited.
     */
    private List<Position> myVisitedRooms;

    /**
     * Initialize the state with the given maze.
     *
     * @param theWidth int width of the new maze
     * @param theHeight int height of the new maze
     * @param theCategories String[] of categories to include
     */
    public GameState(final int theWidth, final int theHeight, final String[] theCategories) {
        super();

        // Maze constructor does parameter checking
        myMaze = new Maze(theWidth, theHeight, theCategories);
        myCurrentPosition = myMaze.getEntrance();
        myQuestionsFailed = 0;
        myQuestionsSucceeded = 0;
        myVisitedRooms = new ArrayList<Position>(theWidth * theHeight);

        myPCS = new PropertyChangeSupport(this);
    }

    /**
     * Creates a new maze of the given dimensions.
     *
     * @param theWidth int width of the new maze
     * @param theHeight int height of the new maze
     */
    public GameState(final int theWidth, final int theHeight) {
        this(theWidth, theHeight, null);
    }

    /**
     * Initialize the state with the given maze.
     *
     * @param theSideLength int size of the square maze
     */
    public GameState(final int theSideLength) {
        this(theSideLength, theSideLength);
    }

    /**
     * Initialize the state with the given maze and question categories.
     *
     * @param theSideLength int size of the square maze
     * @param theCategories String[] of categories to include
     */
    public GameState(final int theSideLength, final String[] theCategories) {
        this(theSideLength, theSideLength, theCategories);
    }

    /**
     * Get the current position of the player.
     *
     * @return Position the player is currently at
     */
    public Position getMyCurrentPosition() {
        return myCurrentPosition;
    }

    /**
     * Gets current direction of the player.
     *
     * @return Direction the play is at.
     */
    public Direction getMyCurrentDirection() {
        return myCurrentDirection;
    }

    /**
     * Sets the position of the player.
     *
     * @param thePosition Position for the player to be set to
     */
    public void setMyCurrentPosition(final Position thePosition) {
        // Make sure the passed position is within bounds
        if (isWithinBounds(thePosition)) {
            myCurrentPosition = thePosition;

            // fire property change event with the new position.
            myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_POSITION, null, thePosition);
        } else {
            throw new IllegalArgumentException("Position out of bounds.");
        }
    }

    /**
     * Get the maze object for this game.
     *
     * @return Maze object with the data for this game
     */
    public Maze getMaze() {
        return myMaze;
    }

    /**
     * Get how many questions the player has answered.
     *
     * @return int number of questions the player has answered
     */
    public int getMyQuestionsFailed() {
        return myQuestionsFailed;
    }

    /**
     * Get how many questions the player has gotten correct.
     *
     * @return int number of questions the player has answered correctly
     */
    public int getMyQuestionsSucceeded() {
        return myQuestionsSucceeded;
    }

    // TODO: Add a method for selecting the current door, for use in the minimap/question panel, and fire PROPERTY_DOOR_VISITED from there
    /**
     * Attempt to answer the door in the given direction.
     *
     * @param theDir Direction of the door in the current room
     * @param theAnswer String answer to check
     * @return true if theAnswer and the door's answer match, false otherwise
     */
    public boolean answerDoor(final Direction theDir, final String theAnswer) {
        boolean out = myMaze.getRoom(myCurrentPosition).getDoor(theDir).answerQuestion(theAnswer);

        if (out) {
            myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_DOOR_VISITED, 3, theDir);
        } else {
            myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_DOOR_VISITED, 4, theDir);
        }

        return out;
    }

    /**
     * Add a room the player has visited to the list.
     *
     * @param thePosition Position of the room to be added
     */
    public void addVisitedRoom(final Position thePosition) {
        // make sure the room is within bounds and not already visited
        if (isWithinBounds(thePosition) && !myVisitedRooms.contains(thePosition)) {
            myVisitedRooms.add(thePosition);

            // Fire a property change event passing the position of the room visited.
            myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_ROOM_VISITED, null, thePosition);
        } else {
            throw new IllegalArgumentException("Position out of bounds.");
        }
    }

    /**
     * Get which rooms the player has visited.
     *
     * @return List<Position> list of the positions of rooms the player has
     * visited
     */
    public List<Position> getmyVisitedRooms() {
        return myVisitedRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * Check whether the given position is within the bounds of the maze.
     *
     * @param thePosition position to check
     * @return true if thePosition is within bounds, false otherwise
     */
    private boolean isWithinBounds(final Position thePosition) {
        return thePosition.getX() <= getMaze().getWidth()
                && thePosition.getX() >= 0
                && thePosition.getY() <= getMaze().getHeight()
                && thePosition.getY() >= 0;
    }

    /**
     * Sets the current direction of player.
     *
     * @param direction direction to set
     */
    public void setMyCurrentDirection(Direction direction) {
        myCurrentDirection = direction;
    }

    /**
     * Updates this GameState's fields from another GameState instance. Useful
     * for loading a saved state into an existing GameState object. Fires
     * property change events for position, direction, and visited rooms.
     *
     * @param other the GameState to copy from
     */
    public void updateFrom(GameState other) {
        if (other == null) {
            return;
        }
        this.myMaze = other.myMaze;
        this.myCurrentPosition = other.myCurrentPosition;
        this.myCurrentDirection = other.myCurrentDirection;
        this.myQuestionsFailed = other.myQuestionsFailed;
        this.myQuestionsSucceeded = other.myQuestionsSucceeded;
        this.myVisitedRooms = new ArrayList<>(other.myVisitedRooms);
        // Fire property change events so listeners update
        myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_POSITION, null, myCurrentPosition);
        myPCS.firePropertyChange(PropertyChangeEnabledGameState.PROPERTY_ROOM_VISITED, null, myCurrentPosition);
        // You may want to fire more events depending on your UI needs
    }

    public boolean tryMove(Direction direction, String answer) {
        Position pos = getMyCurrentPosition();
        Room current = myMaze.getRoom(pos);
        Door door = current.getDoor(direction);

        if (door == null || door.isLocked()) {
            return false;
        }

        if (!door.isOpen()) {
            if (!door.answerQuestion(answer)) {
                door.lock();
                return false;
            }
            door.open();

            Position next = pos.translate(direction);
            Room nextRoom = myMaze.getRoom(next);
            Door reverse = nextRoom.getDoor(direction.getOpposite());
            if (reverse != null) {
                reverse.open();
            }
        }

        setMyCurrentPosition(pos.translate(direction));
        return true;
    }
}
