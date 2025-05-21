import src.model.DatabaseManager;
import src.model.GameState;
import src.view.GameView;
import src.view.ViewMockup;

/**
 * Entry point for the Trivia Maze application.
 */
public class TriviaMazeApp {
    private static GameState myState;

    private static GameView myView;
    public static void main(String[] args) {
        DatabaseManager.connect();
        myState = new GameState(5);

        myView = new ViewMockup(myState);

        ((ViewMockup) myView).initialize();
    }
}
