package src.model;

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
    }

    // could change to throw an exception instead of overwriting.
    // may want to make private and only used in the contructor (encapsulation!!)
    /**
     * Adds a new Door to the given direction.
     * WARNING: THIS METHOD WILL OVERWRITE IF THERE IS ALREADY A DOOR PRESENT
     * AT THE GIVEN DIRECTION.
     * @param theDir Direction to add the new Door to
     * @param theDoor Door object to add
     */
    public void addDoor(Direction theDir, Door theDoor) {

    }

    /**
     * Get the door in the given direction.
     * @param theDir Direction of the Door to retrieve
     * @return Door in the given direction
     */
    public Door getDoor(Direction theDir) {
        return null;
    }

    /**
     * Check whether a Door exists at the given direction.
     * @param theDir Direction to check
     * @return true if that direction has a Door, false otherwise
     */
    public boolean hasDoor(Direction theDir) {
        return false;
    }

    /**
     * @return int X coordinate of this Room.
     */
    public int getX() {
        return 0;
    }

    /**
     * @return int Y coordinate of this Room.
     */
    public int getY() {
        return 0;
    }

    /**
     * @return true if the player has visited this Room, false otherwise.
     */
    public boolean isVisited() {
        return false;
    }

    /**
     * Set whether the player has visited this Room or not.
     * @param theVisited boolean if the player has visited or not
     */
    public void setVisited(boolean theVisited) {
        
    }
}
