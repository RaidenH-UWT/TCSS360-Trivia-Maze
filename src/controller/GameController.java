package src.controller;

import src.model.DatabaseManager;
import src.model.Direction;
import src.model.Door;
import src.model.GameState;
import src.model.Maze;
import src.model.Position;
import src.model.Room;
import src.view.ConsoleView;
import src.view.GameView;
import src.view.GuiView;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

//TODO: IMPORTANT; Potentially merge controller into view, because of the PropertyChange API

/**
 * Acts as an interface between the model and the view, and updates both
 * according to user input.
 * @author Raiden H
 * @version May 1, 2025
 */
public class GameController {
    /**
     * Instance of a GameState object from the model for this game.
     */
    private GameState myState;

    /**
     * Instance of a GameView object from the view for this game.
     */
    private GameView myView;

    /**
     * Instance of a DatabaseManager object from the model for modifying the database.
     */
    private DatabaseManager myDbManager;

    /**
     * GameController constructor for setting up the game.
     * @param theWidth int width of the maze
     * @param theHeight int height of the maze
     * @param theViewMode boolean selector for console or GUI view, true
     * for GUI false for console
     * @param theGenerationMode boolean selector for the room generation,
     * true for random questions false for manual questions
     * @param theDbPath String path to the SQLite database file (optional)
     */
    public GameController(int theWidth, int theHeight, boolean theViewMode, boolean theGenerationMode, String theDbPath) {
        super();

        myState = new GameState(theHeight, theWidth);

        if (theGenerationMode) {
            generateRandomRooms();
        }

        if (theViewMode) {
            myView = new GuiView();
        } else {
            myView = new ConsoleView();
        }

        myDbManager = new DatabaseManager(theDbPath);
    }

    public GameController(int theWidth, int theHeight, boolean theViewMode, boolean theGenerationMode) {
        this(theWidth, theHeight, theViewMode, theGenerationMode, "");
    }

    /**
     * Initialize the maze, creating new random rooms.
     */
    private void generateRandomRooms() {
        Maze mazeObj = myState.getMaze();
        for (int i = 0; i < mazeObj.getHeight(); i++) {
            for (int j = 0; j < mazeObj.getWidth(); j++) {
                for (Direction dir : Direction.values()) {
                    mazeObj.getRoom(j, i).addDoor(dir, 
                        new Door(myDbManager.getRandomQuestion()));
                }
            }
        }
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
        return myState.getMaze().getRoom(myState.getCurrentPosition().getX(), 
                                            myState.getCurrentPosition().getY());
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
        // use Maze.isPathAvailable()?
        return false;
    }

    /**
     * Returns whether the game in the model is won or not.
     * @return true if the game is won, false otherwise
     */
    public boolean isGameWon() {
        return myState.getMaze().isCompleted();
    }

    /**
     * Returns a map of the rooms adjacent to the player.
     * @return a Map<Direction, Room> of rooms adjacent to the player
     */
    public Map<Direction, Room> getAdjacentRooms() {
        Map<Direction, Room> adjRooms = new HashMap<Direction, Room>(4);
        Position currPosition = myState.getCurrentPosition();

        adjRooms.put(Direction.NORTH, 
            myState.getMaze().getRoom(currPosition.getX(), currPosition.getY() + 1));
        adjRooms.put(Direction.EAST, 
            myState.getMaze().getRoom(currPosition.getX() + 1, currPosition.getY()));
        adjRooms.put(Direction.SOUTH, 
            myState.getMaze().getRoom(currPosition.getX(), currPosition.getY() - 1));
        adjRooms.put(Direction.WEST, 
            myState.getMaze().getRoom(currPosition.getX() - 1, currPosition.getY()));

        return adjRooms;
    }

    /**
     * Attempts to answer the question for the current door with the given answer.
     * @param theDir Direction of the door being answered
     * @param theAnswer the String answer to attempt
     * @return true if the answer is correct, false otherwise
     */
    public boolean attemptAnswer(Direction theDir, String theAnswer) {
        Position currPosition = myState.getCurrentPosition();
        Room currRoom = myState.getMaze().getRoom(currPosition.getX(), currPosition.getY());
        
        return currRoom.getDoor(theDir).answerQuestion(theAnswer);
    }
}