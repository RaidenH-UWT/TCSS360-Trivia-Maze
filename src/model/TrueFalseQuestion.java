package src.model;

public class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(final int theId, final String theQuestion, final boolean theAnswer,
                            final String theCategory, final int theDifficulty) {
        super(theId, theQuestion, "", theCategory, theDifficulty);
    }
}
