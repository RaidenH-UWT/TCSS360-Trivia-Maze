package src.model;

/**
 * Stores data about the maze and the rooms within it.
 * @author Raiden H
 * @version May 1, 2025
 */
public class Maze {
    /**
     * Width of the maze.
     */
    private int myWidth;

    /**
     * Height of the maze.
     */
    private int myHeight;

    /**
     * 2D array of the Rooms of the maze.
     */
    private Room[][] myRooms;

    /**
     * Position of the entrance room.
     */
    private Position myEntrance;

    /**
     * Position of the exit room.
     */
    private Position myExit;

    /**
     * Tracks whether the maze is complete or not.
     */
    private boolean myCompleted;

    /**
     * Creates a new maze of the given dimensions.
     * @param theWidth int width of the new maze
     * @param theHeight int height of the new maze
     */
    public Maze(int theWidth, int theHeight) {
        super();
        // TODO: add option for random room generation?
    }

    /**
     * Initialize the maze, creating new rooms.
     */
    public void initialize() {

    }

    // TODO: consider replacing with a Position instead of X and Y?
    /**
     * Get the room with the given position.
     * @param theX int X coordinate of the room
     * @param theY int Y coordinate of the room
     * @return Room object from the given coordinates
     * @throws IndexOutOfBoundsException if either of the given coordinates
     * are not contained within this maze
     */
    public Room getRoom (int theX, int theY) {
        return null;
    }

    // TODO: Unsure what the intended purpose of this method is, please document.
    public boolean isPathAvailable() {
        return false;
    }

    /**
     * @return Position`of the entrance to this maze
     */
    public Position getEntrance() {
        return null;
    }

    /**
     * @return Position of the exit from this maze
     */
    public Position getExit() {
        return null;
    }

    /**
     * Check whether this maze is completed or not.
     * @return true if this maze is completed, false otherwise
     */
    public boolean isCompleted() {
        return false;
    }
}
