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
        if (theWidth <= 0 || theHeight <= 0) {
            throw new IllegalArgumentException("Dimensions must be greater than 0.");
        }

        myWidth = theWidth;
        myHeight = theHeight;
        myRooms = new Room[myHeight][myWidth];
        generateEmptyRooms();
    }

    /**
     * Initialize the maze, creating new empty rooms to be changed manually.
     */
    private void generateEmptyRooms() {
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                myRooms[i][j] = new Room(j, i);
            }
        }
    }

    /**
     * Fills the generated rooms with random doors.
     */
    public void fillRoomsRandom() {
        // TODO: Fill with random rooms
    }

    /**
     * Fills the generated rooms with random doors from the given category.
     * @param theCategory String name of the category
     * @thorws IllegalArgumentException if the passed category is not in the database
     */
    public void fillRoomsRandom(String theCategory) {
        if (!DatabaseManager.getCategories().contains(theCategory)) {
            throw new IllegalArgumentException("Category is not in the database");
        } else {
            // TODO: Fill with random rooms of the category
        }
    }

    /**
     * Get the room with the given position.
     * @param theX int X coordinate of the room
     * @param theY int Y coordinate of the room
     * @return Room object from the given coordinates
     * @throws IndexOutOfBoundsException if either of the given coordinates
     * are not contained within this maze
     */
    public Room getRoom (Position thePosition) {
        if (thePosition.getX() > myWidth || thePosition.getY() > myHeight
            || thePosition.getX() < 0 || thePosition.getY() < 0) {
            throw new IndexOutOfBoundsException("Given coordinates are out of bounds");
        }
        return myRooms[thePosition.getX()][thePosition.getY()];
    }

    /**
     * @return int width of this maze.
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * @return int height of this maze.
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Check whether a path to the exit is possible from the current player position.
     * @return true if a path to the exit is possible, false otherwise
     */
    public boolean isPathAvailable() {
        // implement recursively, sending a call down every path until all paths are covered or we find a good path.

        return false;
    }

    /**
     * @return Position of the entrance to this maze
     */
    public Position getEntrance() {
        return myEntrance;
    }

    /**
     * @return Position of the exit from this maze
     */
    public Position getExit() {
        return myExit;
    }

    /**
     * Check whether this maze is completed or not.
     * @return true if this maze is completed, false otherwise
     */
    public boolean isCompleted() {
        return myCompleted;
    }
}
