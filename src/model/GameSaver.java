package src.model;

// TODO: Decide whether we want to use Serialization (security flaws, not human readable)
// or read/write from file (takes time to implement I/O pattern and structure)

public class GameSaver {
    // Potentionally add a List<File> or List<String> of all saves?

    public void saveGame(GameState theState, String theFilename) {

    }
    public GameState getSave(String theFilename) {
        return null;
    }
}
