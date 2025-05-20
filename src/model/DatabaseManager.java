package src.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
     * @return Question object from the given ID
     */
    public static Question getQuestionById(int theId) {
        try {
            return sqlRowToQuestion(QUERY.executeQuery("SELECT * FROM QUESTIONS WHERE ID=" + theId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a random question from the database.
     * @return random Question object from the database
     */
    public static Question getRandomQuestion() {
        List<Question> questions = getAllQuestions();
        Random rand = new Random();

        return questions.get(rand.nextInt(questions.size()));
    }

    /**
     * Gets a random question by its associated QuestionType
     * @param theType a QuestionType for the desired question
     * @return random Question object with the given type
     */
    public static Question getRandomQuestionByType(QuestionType theType) {
        List<Question> questions = getAllQuestionsByType(theType);
        Random rand = new Random();

        return questions.get(rand.nextInt(questions.size()));
    }

    /**
     * Gets a random question by its associated QuestionType
     * @param theCategory a QuestionType for the desired question
     * @return random Question object with the given type
     */
    public static Question getRandomQuestionByCategory(String theCategory) {
        List<Question> questions = getAllQuestionsByCategory(theCategory);
        Random rand = new Random();

        return questions.get(rand.nextInt(questions.size()));
    }

    /**
     * Gets all questions stored in the database.
     * @return List<Question> of every question in the database
     */
    public static List<Question> getAllQuestions() {
        try {
            ResultSet results = QUERY.executeQuery("SELECT * FROM QUESTIONS");
            List<Question> resList = new LinkedList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }
            return resList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
     }

     /**
      * Gets all questions with the given type.
      * @param theType QuestionType enum to select for
      * @return List<Question> of all questions with the given type
      */
    public static List<Question> getAllQuestionsByType(QuestionType theType) {
        try {
            ResultSet results = QUERY.executeQuery("SELECT * FROM QUESTIONS WHERE 'Type'=" + theType);
            List<Question> resList = new LinkedList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }
            return resList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
     }

     /**
      * Gets all questions with the given category.
      * @param theCategory String category to select for
      * @return List<Question> of all questions with the given category
      */
    public static List<Question> getAllQuestionsByCategory(String theCategory) {
        try {
            ResultSet results = QUERY.executeQuery("SELECT * FROM QUESTIONS WHERE 'Category'=" + theCategory);
            List<Question> resList = new LinkedList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }
            return resList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
     }

    public static List<String> getCategories() {
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

    /**
     * Parses a String and converts it into a java LinkedList splitting the string on commas.
     * @param theSQL String to parse into a list
     * @return LinkedList<String> of the parsed data
     */
    private static LinkedList<String> sqlStringToList(String theSQL) {
        return new LinkedList<String>(Arrays.asList(theSQL.split(",")));
    }

    /**
     * Parses a SQL ResultSet and returns the next row as a Question object.
     * @param theResults ResultSet to parse the first row of
     * @return Question object parsed
     */
    private static Question sqlRowToQuestion(ResultSet theResults) {
        Question result = null;
        try {
            switch (theResults.getString("Type")) {
                    case "ShortAnswer":
                        result = new ShortAnswerQuestion(theResults.getInt("ID"), theResults.getString("Question"), 
                            theResults.getString("Answer"), theResults.getString("Category"), theResults.getInt("Difficulty"));
                        break;
                    case "TrueFalse":
                        result = new TrueFalseQuestion(theResults.getInt("ID"), theResults.getString("Question"), 
                            Boolean.getBoolean(theResults.getString("Answer")), theResults.getString("Category"), theResults.getInt("Difficulty"));
                        break;
                    case "MultipleChoice":
                        result = new MultipleChoiceQuestion(theResults.getInt("ID"), theResults.getString("Question"), 
                            theResults.getString("Answer"), sqlStringToList(theResults.getString("Options")), theResults.getString("Category"), theResults.getInt("Difficulty"));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown question type.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
