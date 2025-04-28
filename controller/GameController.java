package controller;

import model.GameState;
import model.Room;
import model.DatabaseManager;
import model.Direction;
import view.GameView;

import java.util.Map;

public class GameController {
    private GameState myState;
    private GameView myView;
    private DatabaseManager myDbManager;

    public GameController(GameView theView) {
        super();

        //TODO: call GameState contructor
        myState = null;
        myView = theView;
        //TODO: call DatabaseManager constructor
        myDbManager = null;
    }

    public void initializeGame(int theWidth, int theHeight) {

    }

    public void processMove(Direction theDir) {
        
    }

    public Room getCurrentRoom() {
        return null;
    }

    public void saveGame(String theFilename) {

    }

    public void loadGame(String theFilename) {

    }

    public boolean isGameOver() {
        return false;
    }

    public boolean isGameWon() {
        return false;
    }

    public Map<Direction, Room> getAdjacentRooms() {
        return null;
    }

    public boolean attemptAnswer(String theAnswer) {
        return false;
    }
}