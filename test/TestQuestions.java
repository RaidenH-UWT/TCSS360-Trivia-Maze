package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

import src.model.MultipleChoiceQuestion;
import src.model.Question;
import src.model.ShortAnswerQuestion;
import src.model.TrueFalseQuestion;

/**
 * Class for testing the classes inheriting from Question.
 * @author Raiden H
 * @version May 2, 2025
 */
public class TestQuestions {
    /**
     * Questions to test on.
     */
    Question trueQuestion = new TrueFalseQuestion(0001, "truerQuestion", true, "test", 3);
    Question falseQuestion = new TrueFalseQuestion(0002, "falserQuestion", false, "test", 4);
    Question shortA = new ShortAnswerQuestion(0003, "shortA", "shortAns", "test", 0);
    Question multiQ = new MultipleChoiceQuestion(0004, "multiQ", "ansA", "test", 0);

    /**
     * Make sure creating two questions with the same ID throws an exception.
     */
    @Test
    @SuppressWarnings("unused")
    void testDupeId() {
        Question tfQuestion = new TrueFalseQuestion(0000, "dupeA", true, "test", 0);
        Question shortQuestion;
        assertThrows(IllegalArgumentException.class, () -> createDupe());
    }

    /**
     * Private helper to create a question with a duplicate ID.
     */
    @SuppressWarnings("unused")
    private void createDupe() {
        Question dupeQuestion = new ShortAnswerQuestion(0000, "dupeB", "dupeB", "test", 0);
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
        assertTrue(shortA.checkAnswer("shortAns"));
        assertFalse(shortA.checkAnswer("nonsense"));
    }

    /**
     * Test multiple choice questions checkAnswer works correctly.
     */
    @Test
    void testMultiAnswer() {
        assertTrue(multiQ.checkAnswer("multiA"));
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
