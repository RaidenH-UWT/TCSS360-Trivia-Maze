package src.model;

/**
 * Short answer variant of the Question class.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */
public class ShortAnswerQuestion extends Question {

    /**
     * {@inheritDoc}
     */
    public ShortAnswerQuestion(final int theId,
            final String theQuestionText,
            final String theCorrectAnswer,
            final String theCategory,
            final int theDifficulty) {
        super(theId, theQuestionText, theCorrectAnswer,
                theCategory, theDifficulty);
    }

    // Abstract or child class, pick one
    /**
     * Check the given answer against the correct answer.
     *
     * @param theAnswer String answer to check
     * @return true if the passed answer and stored answer match, false
     * otherwise
     */
    @Override
    public boolean checkAnswer(final String theAnswer) {
        if (theAnswer == null) {
            return false;
        }
        return myAnswer.equalsIgnoreCase(theAnswer.trim());
    }

    // Shouldn't this be in the abstract class?
    /**
     * @return QuestionType.SHORT_ANSWER
     */
    @Override
    public QuestionType getQuestionType() {
        return QuestionType.SHORT_ANSWER;
    }

    @Override
    public String toString() {
        String out
                = "("
                + myId + ", "
                + myQuestion + ", "
                + myAnswer + ", "
                + myCategory + ", "
                + getQuestionType() + ", "
                + myDifficulty
                + ")";
        return out;
    }
}
