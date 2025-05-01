package src.model;

// TODO: Decide whether we want to use Serialization (security flaws, not human readable)
// or read/write from file (takes time to implement I/O pattern and structure)
/**
 * Manages game saving and loading to and from file.
 * @author Raiden H
 * @version May 1, 2025
 */
public class GameSaver {
    // Potentionally add a List<File> or List<String> of all saves?

    /**
     * Saves the given GameState to the given filename.
     * @param theState a GameState object storing the state of the game
     * @param theFilename the file to save as
     */
    public void saveGame(GameState theState, String theFilename) {

    }

    /**
     * Grabs save data from a save file.
     * @param theFilename the save file to read
     * @return GameState object containing the state of the game
     * @throws FileNotFoundException if the given save file is not found
     */
    public GameState getSave(String theFilename) {
        return null;
    }
}
