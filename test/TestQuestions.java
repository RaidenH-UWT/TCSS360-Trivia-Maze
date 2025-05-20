package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.model.DatabaseManager;
import src.model.Question;
import src.model.QuestionFactory;

/**
 * Class for testing the classes inheriting from Question.
 * @author Raiden H
 * @version May 2, 2025
 */
public class TestQuestions {
    /**
     * Questions to test on.
     */
    Question trueQuestion;
    Question falseQuestion;
    Question shortA;
    Question multiQ;
    

    @BeforeEach
    void setup() {
        DatabaseManager.connect();

        /**
         * Questions to test on.
         */
        trueQuestion = QuestionFactory.buildQuestion(1);
        falseQuestion = QuestionFactory.buildQuestion(5);
        shortA = QuestionFactory.buildQuestion(0);
        multiQ = QuestionFactory.buildQuestion(2);
    }

    /**
     * Test true/false questions checkAnswer works correctly.
     */
    @Test
    void testTrueFalseAnswer() {
        // Have to pull checkAnswer down into the child class
        assertTrue(trueQuestion.checkAnswer("true"));
        assertFalse(trueQuestion.checkAnswer("false"));

        assertTrue(falseQuestion.checkAnswer("false"));
        assertFalse(falseQuestion.checkAnswer("true"));
    }

    /**
     * Test short answer questions checkAnswer works correctly.
     */
    @Test
    void testShortAnswer() {
        assertTrue(shortA.checkAnswer("testShortA"));
        assertFalse(shortA.checkAnswer("nonsense"));
    }

    /**
     * Test multiple choice questions checkAnswer works correctly.
     */
    @Test
    void testMultiAnswer() {
        assertTrue(multiQ.checkAnswer("testMultiA"));
        assertFalse(multiQ.checkAnswer("garbage"));
    }

    /**
     * Test equals() method
     */
    @Test
    void testEquals() {
        assertEquals(trueQuestion, trueQuestion);
        assertEquals(falseQuestion, falseQuestion);
        assertEquals(shortA, shortA);
        assertEquals(multiQ, multiQ);

        assertNotEquals(trueQuestion, falseQuestion);
        assertNotEquals(trueQuestion, shortA);
        assertNotEquals(shortA, trueQuestion);
        assertNotEquals(multiQ, falseQuestion);
        assertNotEquals(shortA, multiQ);
    }
}
