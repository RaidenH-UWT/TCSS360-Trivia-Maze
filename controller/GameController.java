package controller;

import model.GameState;
import model.Room;
import model.DatabaseManager;
import model.Direction;
import view.GameView;

import java.util.Map;

//TODO: IMPORTANT; Potentially merge controller into view, because of the PropertyChange API

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

    /**
     * Sets up the model, including the GameState object and Maze object.
     * @param theWidth integer width of the maze
     * @param theHeight integer height of the maze
     */
    public void initializeGame(int theWidth, int theHeight) {

    }

    /**
     * Update the model & view with the input move.
     * @param theDir a Direction object describing the input direction
     */
    public void processMove(Direction theDir) {
        
    }

    /**
     * Returns the room the player is currently in as a Room object
     * @return a Room object corresponding to the room the player is currently in
     */
    public Room getCurrentRoom() {
        return null;
    }

    /**
     * Saves the game to a file.
     * @param theFilename the filename to write the save to
     */
    public void saveGame(String theFilename) {

    }

    /**
     * Loads the game from a given file
     * @param theFilename the file to load from
     * @throws FileNotFoundException if there is no file by the given name found
     */
    public void loadGame(String theFilename) {

    }

    /**
     * Returns whether the game in the model is over or not.
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return false;
    }

    /**
     * Returns whether the game in the model is won or not.
     * @return true if the game is won, false otherwise
     */
    public boolean isGameWon() {
        return false;
    }

    /**
     * Returns a map of the rooms adjacent to the player.
     * @return a Map<Direction, Room> of rooms adjacent to the player
     */
    public Map<Direction, Room> getAdjacentRooms() {
        return null;
    }

    /**
     * Attempts to answer the question in the current room with the given answer.
     * @param theAnswer the String answer to attempt
     * @return true if the answer is correct, false otherwise
     */
    public boolean attemptAnswer(String theAnswer) {
        return false;
    }
}