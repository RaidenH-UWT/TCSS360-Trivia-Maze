package src.model;

/**
 * Stores 2D position of objects in the maze.
 * @author Raiden H
 * @author May 1, 2025
 */
public class Position {
    /**
     * X coordinate
     */
    private final int myX;

    /**
     * Y coordinate
     */
    private final int myY;

    /**
     * Creates a new Position object with the given coordinates.
     * @param theX int X coordinate of this Position
     * @param theY int Y coordinate of this Position
     */
    public Position(final int theX, final int theY) {
        super();
        myX = theX;
        myY = theY;
    }

    /**
     * @return int X coordinate of this position
     */
    public int getX() {
        return myX;
    }

    /**
     * @return int Y coordinate of this position
     */
    public int getY() {
        return myY;
    }

    /**
     * Checks whether two Positions are equal.
     * @param thePosition Position to check against
     * @return true if the two Positions have the same X and Y coordinates, false otherwise
     */
    public boolean equals(Position thePosition) {
        if (thePosition == null) {
            return false;
        }
        return myX == thePosition.getX() && myY == thePosition.getY();
    }
}
