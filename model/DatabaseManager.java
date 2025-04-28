package model;

import java.sql.Connection;
import java.util.List;

public class DatabaseManager {
    private Connection myConnection;
    private String dbPath;

    public DatabaseManager(String dbPath) {
        super();
    }

    public void connect() {

    }

    public void disconnect() {

    }

    public Question getQuestionById(int theId) {
        return null;
    }

    public Question getRandomQuestion() {
        return null;
    }

    public Question getRandomQuestionByType(QuestionType type) {
        return null;
    }

    // Potentially have a getRandomQuestionByCategory if we have multiple trivia categories

    public List<Question> getAllQuestions() {
        return null;
    }
}
