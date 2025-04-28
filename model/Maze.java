package model;

public class Maze {
    private int myWidth;
    private int myHeight;
    private Room[][] myRooms;
    private Position myEntrance;
    private Position myExit;
    private boolean isCompleted;

    public Maze(int theWidth, int theHeight) {
        super();
    }

    public void initialize() {

    }

    public Room getRoom (int theX, int theY) {
        return null;
    }

    public boolean isPathAvailable() {
        return false;
    }

    public Position getEntrance() {
        return null;
    }

    public Position getExit() {
        return null;
    }

    public boolean isCompleted() {
        return false;
    }
}
