package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.model.DatabaseManager;
import src.model.Door;
import src.model.Question;
import src.model.QuestionFactory;

/**
 * Class for testing the Door object.
 * @author Raiden H
 * @version May 5, 2025
 */
public class TestDoor {
    /**
     * Door object for testing.
     */
    Door testDoor;

    /**
     * Question object for testing
     */
    Question testQuestion;

    /**
     * Setup a clean Door object for each test with a T/F question.
     */
    @BeforeEach
    void setup() {
        DatabaseManager.connect();
        testQuestion = QuestionFactory.buildQuestion(1);
        testDoor = new Door(testQuestion);
    }

    /**
     * Test the isLocked() and lock() methods.
     */
    @Test
    void testLocked() {
        assertFalse(testDoor.isLocked());

        testDoor.lock();

        assertTrue(testDoor.isLocked());
    }

    /**
     * Test the isOpen() and open() methods.
     */
    @Test
    void testOpen() {
        assertFalse(testDoor.isOpen());

        testDoor.open();

        assertTrue(testDoor.isOpen());
    }

    /**
     * Test the right question is being stored and retrieved.
     */
    @Test
    void testGetQuestion() {
        assertEquals(testQuestion, testDoor.getQuestion());
    }

    /**
     * Test answering the question associated with this Door.
     */
    @Test
    void testAnswerQuestion() {
        assertFalse(testDoor.answerQuestion("false"));

        assertTrue(testDoor.answerQuestion("true"));
    }
}
