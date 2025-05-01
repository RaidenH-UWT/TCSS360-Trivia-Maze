package src.model;

import java.util.Map;

public class Room {
    // Could these be replaced with a Position?
    private int myX;
    private int myY;
    private Map<Direction, Door> myDoors;
    private boolean isVisited;

    public Room(int theX, int theY) {
        super();
    }

    public void addDoor(Direction theDir, Door theDoor) {

    }

    public Door getDoor(Direction theDir) {
        return null;
    }

    public boolean hasDoor(Direction theDir) {
        return false;
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public boolean isVisited() {
        return false;
    }

    public void setVisited(boolean theVisited) {
        
    }
}
