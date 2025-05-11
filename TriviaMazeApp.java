import src.model.DatabaseManager;

/**
 * Entry point for the Trivia Maze application.
 */
public class TriviaMazeApp {
    public static void main(String[] args) {
        DatabaseManager.connect();
        System.out.println(DatabaseManager.getAllQuestions());
        System.out.println(DatabaseManager.getRandomQuestion());
    }
}
