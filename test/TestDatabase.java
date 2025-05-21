package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.model.DatabaseManager;
import src.model.QuestionFactory;

/**
 * Class for testing the SQLite database
 * @author Raiden H
 * @version May 1, 2025
 */
public class TestDatabase {
    @BeforeEach
    void setup() {
        DatabaseManager.connect();
    }

    @Test
    void testGetByID() {
        assertEquals(QuestionFactory.buildQuestion(0), QuestionFactory.buildQuestion(0));
        assertNotEquals(QuestionFactory.buildQuestion(0), QuestionFactory.buildQuestion(1));
    }
}
