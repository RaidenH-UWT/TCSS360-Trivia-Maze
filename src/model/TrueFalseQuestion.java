package src.model;

/**
 * True/False variant of the Question class
 */
public class TrueFalseQuestion extends Question {
    /**
     * Creates a new Question object.
     * @param theId int ID for this question, to be used in the database
     * @param theQuestion String of the question text
     * @param theAnswer boolean of the correct answer
     * @param theCategory String of the question category
     * @param theDifficulty int difficulty of this question [range]
     */
    public TrueFalseQuestion(final int theId, final String theQuestion, final boolean theAnswer,
                            final String theCategory, final int theDifficulty) {
        super(theId, theQuestion, String.valueOf(theAnswer).toLowerCase(),
            theCategory, theDifficulty);

        if (!myAnswer.equals("true") && !myAnswer.equals("false")) {
            throw new IllegalArgumentException("Answer must be true or false");
        }
        // the answer should probably be pulled down to the child classes because it depends on the QType
    }

    /**
     * Check the given answer against the correct answer.
     * @param theAnswer String answer to check
     * @return true if the passed answer and stored answer match, false otherwise
     */
    @Override
    public boolean checkAnswer(final String theAnswer) {
        if (theAnswer == null) {
            return false;
        }
        return myAnswer.equalsIgnoreCase(theAnswer.trim());
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
        String out = 
            "(" +
            myId + ", " +
            myQuestion + ", " +
            myAnswer + ", " + 
            myCategory + ", " +
            getQuestionType() + ", " +
            myDifficulty + 
            ")";
        return out;
    }
}
