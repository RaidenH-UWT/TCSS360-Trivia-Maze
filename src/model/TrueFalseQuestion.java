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
        super(theId, theQuestion, "", theCategory, theDifficulty);
        // the answer should probably be pulled down to the child classes because it depends on the QType
    }
}
