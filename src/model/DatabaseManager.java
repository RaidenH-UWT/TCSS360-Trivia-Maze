package src.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
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
    private static Statement QUERY;

    /**
     * Filepath to the SQLite database.
     */
    private static String DB_PATH = "jdbc:sqlite:src/model/mazedb.db";

    /**
     * Connect to the database.
     */
    public static void connect() {
        try {
            Connection CONNECT = DriverManager.getConnection(DB_PATH);
            QUERY = CONNECT.createStatement();
        } catch (SQLException except) {
            except.printStackTrace();
        }
    }

    /**
     * Disconnect from the database. Should only be used when the connection
     * doesn't automatically close.
     */
    public static void disconnect() {
        try {
            CONNECT.close();
        } catch (SQLException except) {
            except.printStackTrace();
        }
    }

    /**
     * Gets a question from the database by the questions ID.
     * @param theId int ID associated with the question
     * @return ResultSet object from the given ID
     */
    protected static ResultSet getQuestionById(int theId) {
        try {
            return QUERY.executeQuery("SELECT * FROM QUESTIONS WHERE ID=" + theId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    

    /**
     * Gets all questions stored in the database.
     * @return ResultSet of every question in the database
     */
    protected static ResultSet getAllQuestions() {
        try {
            ResultSet results = QUERY.executeQuery("SELECT * FROM QUESTIONS");
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all questions in the test category.
     * @return ResultSet of every question in the test category
     */
    protected static ResultSet getTestQuestions() {
        return getAllQuestionsByCategory("test");
    }
    
    /**
     * Gets all questions with the given type.
     * @param theType QuestionType enum to select for
     * @return ResultSet of all questions with the given type
     */
    protected static ResultSet getAllQuestionsByType(QuestionType theType) {
        try {
            ResultSet results = QUERY.executeQuery("SELECT * FROM QUESTIONS WHERE 'Type'=" + theType);

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
     }

    /**
     * Gets all questions with the given category.
     * @param theCategory String category to select for
     * @return ResultSet of all questions with the given category
     */
    protected static ResultSet getAllQuestionsByCategory(String theCategory) {
        try {
            ResultSet results = QUERY.executeQuery("SELECT * FROM QUESTIONS WHERE 'Category'=" + theCategory);
            
            return results;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a list of all the categories in the database.
     * @return List<String> of every category name in the database
     */
    protected static List<String> getCategories() {
        try {
            ResultSet results = QUERY.executeQuery("SELECT category FROM QUESTIONS");
            List<String> resList = new LinkedList<String>();

            while (results.next()) {
                resList.add(results.getString("category"));
            }

            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
