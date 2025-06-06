package src.model;

/**
 * True/False variant of the Question class
 *
 * @author Raiden H
 * @author Kalen Cha
 */
public class TrueFalseQuestion extends Question {

    private static final long serialVersionUID = 1L;
    /**
     * String answer of this question.
     */
    private String myAnswer;

    /**
     * Creates a new Question object.
     *
     * @param theId int ID for this question, to be used in the database
     * @param theQuestion String of the question text
     * @param theAnswer String of the correct answer accepted values are "true"
     * or "false"
     * @param theDifficulty int difficulty of this question [range]
     */
    protected TrueFalseQuestion(final int theId, final String theQuestion,
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
        return theAnswer.equals(Question.SKIP) || myAnswer.equalsIgnoreCase(theAnswer.trim());
    }

    /**
     * @return QuestionType.TRUE_FALSE
     */
    @Override
    public QuestionType getQuestionType() {
        return QuestionType.TRUE_FALSE;
    }

    @Override
    public String toString() {
        String out = "(" + getId() + ", " + getQuestion() + ", " + getQuestionType() + ")";
        return out;
    }
}
