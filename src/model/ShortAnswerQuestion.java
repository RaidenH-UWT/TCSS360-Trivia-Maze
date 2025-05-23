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
     * String answer of this question.
     */
    private String myAnswer;

    /**
     * {@inheritDoc}
     */
    protected ShortAnswerQuestion(final int theId, final String theQuestion,
            final String theAnswer, final int theDifficulty) {
        super(theId, theQuestion, theDifficulty);

        myAnswer = theAnswer;
    }

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

    /**
     * @return QuestionType.SHORT_ANSWER
     */
    @Override
    public QuestionType getQuestionType() {
        return QuestionType.SHORT_ANSWER;
    }

    @Override
    public String toString() {
        String out = "(" + getId() + ", " + getQuestion() + ", " + getQuestionType() + ")";
        return out;
    }
}
