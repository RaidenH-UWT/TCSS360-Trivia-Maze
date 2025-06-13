/*
 * Copyright 2025 Kalen Cha, Evan Lei, Raiden H
 * 
 * All rights reserved. This software made available under the terms of
 * the GNU General Public License v3 or later
 */
package src.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Room class to be used inside Maze objects.
 *
 * @author Raiden H
 * @version May 1, 2025
 */
public class Room implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
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
    private boolean myIsVisited;

    /**
     * Creates a new Room with the given coordinates and creates new doors for
     * the room.
     */
    public Room(final int theX, final int theY) {
        super();
        myX = theX;
        myY = theY;
        myDoors = new EnumMap<>(Direction.class);
        myIsVisited = false;
    }

    /**
     * Adds a new Door to the given direction.
     *
     * @param theDir Direction to add the new Door to
     * @param theDoor Door object to add
     * @throws IllegalArgumentException if this Room already has a Door in the
     * given direction
     */
    protected void addDoor(final Direction theDir, final Door theDoor) {
        if (hasDoor(theDir)) {
            throw new IllegalArgumentException("Room already has a door to the " + theDir);
        } else {
            myDoors.put(theDir, theDoor);
        }
    }

    /**
     * Get the door in the given direction.
     *
     * @param theDir Direction of the Door to retrieve
     * @return Door in the given direction
     */
    public Door getDoor(final Direction theDir) {
        return myDoors.get(theDir);
    }

    /**
     * Check whether a Door exists at the given direction.
     *
     * @param theDir Direction to check
     * @return true if that direction has a Door, false otherwise
     */
    public boolean hasDoor(final Direction theDir) {
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
        return myIsVisited;
    }

    /**
     * Set whether the player has visited this Room or not.
     *
     * @param theVisited boolean if the player has visited or not
     */
    public void setVisited(final boolean theVisited) {
        myIsVisited = theVisited;
    }

    @Override
    public boolean equals(final Object theRoom) {
        // Make sure our casting works
        if (!this.getClass().equals(theRoom.getClass())) {
            return false;
        }

        // Check if the doors are identical
        boolean val = true;
        for (Direction dir : Direction.values()) {
            if (getDoor(dir) == null && ((Room) theRoom).getDoor(dir) == null) {
                val = true;
            } else {
                val = getDoor(dir).equals(((Room) theRoom).getDoor(dir));
            }

            if (!val) {
                return val;
            }
        }

        // Get our final answer
        val = (getX() == ((Room) theRoom).getX()) && (getY() == ((Room) theRoom).getY());

        return val;
    }

    @Override
    public String toString() {
        String[] doors = new String[4];
        int i = 0;
        for (Direction dir : Direction.values()) {
            if (hasDoor(dir)) {
                doors[i] = getDoor(dir).toString();
            } else {
                doors[i] = "Wall";
            }
            i++;
        }

        String out = String.format("(%d, %d, [NORTH: %s, SOUTH: %s, EAST: %s, WEST: %s])",
                myX, myY, doors[0], doors[1], doors[2], doors[3]);

        return out;
    }
}
