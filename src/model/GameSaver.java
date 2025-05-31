package src.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Manages game saving and loading to and from file.
 *
 * @author Raiden H
 * @version May 1, 2025
 */
public class GameSaver {
    // Potentionally add a List<File> or List<String> of all saves?

    /**
     * Saves the given GameState to the given filename.
     *
     * @param theState a GameState object storing the state of the game
     * @param theFilename the file to save as
     */
    public void saveGame(final GameState theState, final String theFilename) {
        try (FileOutputStream fileOut = new FileOutputStream(theFilename); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(theState);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Grabs save data from a save file.
     *
     * @param theFilename the save file to read
     * @return GameState object containing the state of the game
     * @throws IOException if the file is not found or cannot be read
     * @throws ClassNotFoundException if the GameState class cannot be found
     */
    public GameState getSave(final String theFilename) throws IOException, ClassNotFoundException {
        try (
                FileInputStream fileIn = new FileInputStream(theFilename); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Object obj = in.readObject();
            if (obj == null) {
                throw new IOException("Save file is empty.");
            }
            if (!(obj instanceof GameState)) {
                throw new IOException("Save file does not contain a valid game state.");
            }
            return (GameState) obj;
        }
    }
}
