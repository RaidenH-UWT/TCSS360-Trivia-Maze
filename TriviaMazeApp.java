import src.model.DatabaseManager;
import src.model.GameState;
import src.view.ViewMockup;

/**
 * Entry point for the Trivia Maze application.
 */
public class TriviaMazeApp {
    public static void main(String[] args) {
        DatabaseManager.connect();
        GameState state = new GameState(5);

        ViewMockup view = new ViewMockup(state);

        view.initialize();
    }
}
