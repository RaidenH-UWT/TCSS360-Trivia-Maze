package src.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stores data about the maze and the rooms within it.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */
public class Maze implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Flag to allow duplicate questions in the maze. Use during development
     * when we don't have enough unique questions.
     */
    private static final boolean ALLOW_DUPLICATE_QUESTIONS = true;
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
     * Creates a new maze of the given dimensions and uses questions of the
     * given category.
     *
     * @param theWidth int width of the new maze
     * @param theHeight int height of the new maze
     * @param theCategory String category to pull questions from
     */
    public Maze(final int theWidth, final int theHeight, final String[] theCategories) {
        super();
        if (theWidth <= 0 || theHeight <= 0) {
            throw new IllegalArgumentException("Dimensions must be greater than 0.");
        }

        myWidth = theWidth;
        myHeight = theHeight;
        myRooms = new Room[myHeight][myWidth];

        myEntrance = new Position(0, 0);
        myExit = new Position(theWidth, theHeight);

        generateEmptyRooms();

        // If a category was passed, generate rooms with the given category.
        if (theCategories == null) {
            fillRoomsRandom();
        } else {
            // Error checking if theCategories exists in the database.
            fillRoomsRandom(theCategories);
        }
    }

    /**
     * Creates a new maze of the given dimensions.
     *
     * @param theWidth int width of the new maze
     * @param theHeight int height of the new maze
     */
    public Maze(final int theWidth, final int theHeight) {
        this(theWidth, theHeight, null);
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
        ArrayList<Question> questions = new ArrayList<Question>(QuestionFactory.getAllRealQuestions());
        fillRooms(questions);
    }

    /**
     * Fills the generated rooms with random doors from the given category.
     *
     * @param theCategory String name of the category
     * @throws IllegalArgumentException if the passed category is not in the
     * database
     */
    public void fillRoomsRandom(final String[] theCategories) {
        ArrayList<Question> questions = new ArrayList<Question>();
        for (String category : theCategories) {
            if (!DatabaseManager.getCategories().contains(category)) {
                throw new IllegalArgumentException("Category is not in the database");
            } else {
                questions.addAll(new ArrayList<Question>(QuestionFactory.getAllQuestionsByCategory(category)));
            }
        }
        fillRooms(questions);
    }

    /**
     * Fills all rooms of the maze with the given list of questions.
     *
     * @param theQuestions ArrayList<Question> of questions to fill with
     */
    @SuppressWarnings("unused") // Used for debugging/development
    private void fillRooms(final ArrayList<Question> theQuestions) {
        if (!ALLOW_DUPLICATE_QUESTIONS && theQuestions.size() < (myWidth * myHeight) * 4 - (2 * myWidth) - (2 * myHeight)) {
            throw new IndexOutOfBoundsException("Not enough questions to fill the maze");
        }

        Collections.shuffle(theQuestions);

        int i = 0;
        for (Room[] roomArr : myRooms) {
            for (Room room : roomArr) {
                // Only add doors if they don't lead to a wall
                if (!(room.getX() == 0)) {
                    Door door = new Door(theQuestions.get(i % theQuestions.size()));
                    room.addDoor(Direction.WEST, door);
                    i++;
                }
                if (!(room.getX() == myWidth - 1)) {
                    Door door = new Door(theQuestions.get(i % theQuestions.size()));
                    room.addDoor(Direction.EAST, door);
                    i++;
                }
                if (!(room.getY() == 0)) {
                    Door door = new Door(theQuestions.get(i % theQuestions.size()));
                    room.addDoor(Direction.NORTH, door);
                    i++;
                }
                if (!(room.getY() == myHeight - 1)) {
                    Door door = new Door(theQuestions.get(i % theQuestions.size()));
                    room.addDoor(Direction.SOUTH, door);
                    i++;
                }
            }
        }
    }

    /**
     * Print every room in the maze to the console.
     */
    public void printRooms() {
        for (Room[] roomArr : myRooms) {
            for (Room room : roomArr) {
                System.out.println(room);
            }
        }
    }

    /**
     * Get the room with the given position.
     *
     * @param thePosition Position coordinates of the room
     * @return Room object from the given coordinates
     * @throws IndexOutOfBoundsException if the given Position is not within the
     * maze
     */
    public Room getRoom(final Position thePosition) {
        int x = thePosition.getX();
        int y = thePosition.getY();

        if (x < 0 || x >= myWidth || y < 0 || y >= myHeight) {
            throw new IndexOutOfBoundsException("Position is out of bounds: " + x + ", " + y);
        }

        return myRooms[y][x];
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
     * Check whether a path to the exit is possible from the current player
     * position.
     *
     * @return true if a path to the exit is possible, false otherwise
     */
    public boolean isPathAvailable() {
        // implement recursively, sending a call down every path until all paths are covered or we find a good path.
        // TODO: Implement isPathAvailable()
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
     *
     * @return true if this maze is completed, false otherwise
     */
    public boolean isCompleted() {
        return myCompleted;
    }

    public boolean isWithinBounds(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return x >= 0 && x < myWidth && y >= 0 && y < myHeight;
    }

}
