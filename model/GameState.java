package model;

import java.util.List;

public class GameState {
    private Maze myMaze;
    private Position currentPosition;
    // Could calculate these instead of storing them just counting the rooms in the maze
    private int questionsAnswered;
    private int questionsCorrect;
    private List<Position> visitedRooms;

    public GameState(Maze theMaze) {
        super();
    }

    public Position getCurrentPosition() {
        return null;
    }

    public void setCurrentPosition(Position thePosition) {

    }

    public Maze getMaze() {
        return null;
    }

    public int getQuestionsAnswered() {
        return 0;
    }

    public int getQuestionsCorrect() {
        return 0;
    }

    public void incrementQuestionsAnswered() {

    }

    public void incrementQuestionsCorrect() {

    }

    public void addVisitedRoom(Position thePosition) {

    }

    public List<Position> getVisitedRooms() {
        return null;
    }
}
