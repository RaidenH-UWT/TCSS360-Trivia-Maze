package src.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
     * @throws FileNotFoundException if the given save file is not found
     */
    public GameState getSave(final String theFilename) {
         try (
            FileInputStream fileIn = new FileInputStream(theFilename);
            ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            return (GameState) in.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Save file not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
        return null;
    }
}
