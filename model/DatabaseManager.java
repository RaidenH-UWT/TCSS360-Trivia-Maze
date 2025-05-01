package model;

import java.sql.Connection;
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
    private Connection myConnection;

    /**
     * Filepath to the SQLite database.
     */
    private String dbPath;

    /**
     * Constructor for the DatabaseManager class to get a connection to the database.
     * @param dbPath Filepath to the SQLite database file
     */
    public DatabaseManager(String dbPath) {
        super();
    }

    /**
     * Connects to the database associated with this DatabaseManager object.
     */
    public void connect() {

    }

    /**
     * Disconnects from the database associated with this DatabaseManager.
     */
    public void disconnect() {

    }

    /**
     * Gets a question from the database by the questions ID.
     * @param theId int ID associated with the question
     * @returns Question object from the given ID
     */
    public Question getQuestionById(int theId) {
        return null;
    }

    /**
     * Gets a random question from the database.
     * @returns random Question object from the database
     */
    public Question getRandomQuestion() {
        return null;
    }

    /**
     * Gets a random question by its associated QuestionType
     * @param type a QuestionType for the desired question
     * @returns random Question object with the given type
     */
    public Question getRandomQuestionByType(QuestionType type) {
        return null;
    }

    // Potentially have a getRandomQuestionByCategory if we have multiple trivia categories

    /**
     * Gets all questions stored in the database.
     * @returns List<Question> of every question in the database
     */
    public List<Question> getAllQuestions() {
        return null;
    }
}
