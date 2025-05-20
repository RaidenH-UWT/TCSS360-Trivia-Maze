package src.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Room class to be used inside Maze objects.
 * @author Raiden H
 * @version May 1, 2025
 */
public class Room {
    // Could these be replaced with a Position?
    /**
     * X coordinate of this Room.
     */
    private int myX;

    /**
     * Y coordinate of this Room.
     */
    private int myY;

    /**
     * Map of the Doors of this Room.
     */
    private Map<Direction, Door> myDoors;

    /**
     * Stores whether the player has visited this room or not.
     */
    private boolean isVisited;

    /**
     * Creates a new Room with the given coordinates and creates new doors for the room.
     */
    public Room(int theX, int theY) {
        super();
        myX = theX;
        myY = theY;
        myDoors = new EnumMap<>(Direction.class);
        isVisited = false;
    }

    // could change to throw an exception instead of overwriting.
    // TODO: may want to make private and only used in the contructor (encapsulation!!)
    /**
     * Adds a new Door to the given direction.
     * WARNING: THIS METHOD WILL OVERWRITE IF THERE IS ALREADY A DOOR PRESENT
     * AT THE GIVEN DIRECTION.
     * @param theDir Direction to add the new Door to
     * @param theDoor Door object to add
     */
    public void addDoor(Direction theDir, Door theDoor) {
         myDoors.put(theDir, theDoor);
    }

    /**
     * Get the door in the given direction.
     * @param theDir Direction of the Door to retrieve
     * @return Door in the given direction
     */
    public Door getDoor(Direction theDir) {
         return myDoors.get(theDir);
    }

    /**
     * Check whether a Door exists at the given direction.
     * @param theDir Direction to check
     * @return true if that direction has a Door, false otherwise
     */
    public boolean hasDoor(Direction theDir) {
        return myDoors.containsKey(theDir);
    }

    /**
     * @return int X coordinate of this Room.
     */
    public int getX() {
        return myX;
    }

    /**
     * @return int Y coordinate of this Room.
     */
    public int getY() {
        return myY;
    }

    /**
     * @return true if the player has visited this Room, false otherwise.
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Set whether the player has visited this Room or not.
     * @param theVisited boolean if the player has visited or not
     */
    public void setVisited(boolean theVisited) {
        isVisited = theVisited;
    }

    @Override
    public boolean equals(Object room) {
        // Make sure our casting works
        if (!this.getClass().equals(room.getClass())) {
            return false;
        }

        boolean val = true;
        for (Direction dir : Direction.values()) {
            if (getDoor(dir) == null && ((Room) room).getDoor(dir) == null) {
                val = true;
            } else {
                val = getDoor(dir).equals(((Room) room).getDoor(dir));
            }
            
            if (!val) {
                return val;
            }
        }
        val = (getX() == ((Room) room).getX()) && (getY() == ((Room) room).getY());

        return val;
    }
}
