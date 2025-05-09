package src.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Manages the connection and I/O to the database for the game.
 * @author Raiden H
 * @version May 1, 2025
 */
public class DatabaseManager {
    /**
     * Connection to the SQLite database for the game.
     */
    private static Connection CONNECT;

    /**
     * Statement for sending write queries to the database.
     */
    private static Statement WQUERY;

    /**
     * Filepath to the SQLite database.
     */
    private static String DB_PATH = "./path/to/database.db";

    static {
        try (Connection CONNECT = DriverManager.getConnection(DB_PATH)) {
            WQUERY = CONNECT.createStatement();
        } catch (SQLException except) {
            except.printStackTrace();
        }
    }

    /**
     * Gets a question from the database by the questions ID.
     * @param theId int ID associated with the question
     * @return Question object from the given ID
     */
    public static Question getQuestionById(int theId) {
        return null;
    }

    /**
     * Gets a random question from the database.
     * @return random Question object from the database
     */
    public static Question getRandomQuestion() {
        return null;
    }

    /**
     * Gets a random question by its associated QuestionType
     * @param type a QuestionType for the desired question
     * @return random Question object with the given type
     */
    public static Question getRandomQuestionByType(QuestionType type) {
        return null;
    }

    // Potentially have a getRandomQuestionByCategory if we have multiple trivia categories

    /**
     * Gets all questions stored in the database.
     * @return List<Question> of every question in the database
     */
    public static List<Question> getAllQuestions() {
        return null;
    }
}
