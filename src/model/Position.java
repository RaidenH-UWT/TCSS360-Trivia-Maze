package src.model;

/**
 * Stores immutable 2D position of objects in the maze.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @author May 1, 2025
 */
public class Position implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

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
     *
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

    public Position translate(final Direction theDirection) {
        return switch (theDirection) {
            case NORTH ->
                new Position(myX, myY - 1);
            case SOUTH ->
                new Position(myX, myY + 1);
            case EAST ->
                new Position(myX + 1, myY);
            case WEST ->
                new Position(myX - 1, myY);
        };
    }

    /**
     * Checks whether two Positions are equal.
     *
     * @param thePosition Position to check against
     * @return true if the two Positions have the same X and Y coordinates,
     * false otherwise
     */
    @Override
    public boolean equals(final Object thePosition) {
        if (thePosition == null) {
            return false;
        } else if (!this.getClass().equals(thePosition.getClass())) {
            return false;
        }
        return myX == ((Position) thePosition).getX() && myY == ((Position) thePosition).getY();
    }

    @Override
    public String toString() {
        return "(" + myX + ", " + myY + ")";
    }
}
