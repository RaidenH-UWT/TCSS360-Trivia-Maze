package src.model;

/**
 * Short answer variant of the Question class.
 * @author Raiden H
 * @version May 1, 2025
 */
public class ShortAnswerQuestion extends Question {
    /**
     * {@inheritDoc}
     */
    public ShortAnswerQuestion(final int theId, final String theQuestion, final String theAnswer,
                            final String theCategory, final int theDifficulty) {
        super(theId, theQuestion, theAnswer, theCategory, theDifficulty);
    }

    // Abstract or child class, pick one
    /**
     * {@inheritDoc}
     */
    public boolean checkAnswer(String theAnswer) {
        return false;
    }

    // Shouldn't this be in the abstract class?
    /**
     * {@inheritDoc}
     */
    public QuestionType getQuestionType() {
        return null;
    }
}
