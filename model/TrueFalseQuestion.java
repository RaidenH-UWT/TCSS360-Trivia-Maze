package model;

public class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(final int theId,
            final String theQuestionText,
            final boolean theCorrectAnswer,
            final String theCategory,
            final int theDifficulty) {
        super(theId, theQuestionText,
                String.valueOf(theCorrectAnswer).toLowerCase(),
                theCategory, theDifficulty);

        if (!myCorrectAnswer.equals("true") && !myCorrectAnswer.equals("false")) {
            throw new IllegalArgumentException("Answer must be true or false");
        }
    }

    @Override
    public boolean checkAnswer(final String theAnswer) {
        if (theAnswer == null) {
            return false;
        }
        return myCorrectAnswer.equalsIgnoreCase(theAnswer.trim());
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.TRUE_FALSE;
    }
}
