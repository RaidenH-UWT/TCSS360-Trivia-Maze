package model;

public class ShortAnswerQuestion extends Question {

    public ShortAnswerQuestion(final int theId,
            final String theQuestionText,
            final String theCorrectAnswer,
            final String theCategory,
            final int theDifficulty) {
        super(theId, theQuestionText, theCorrectAnswer,
                theCategory, theDifficulty);
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
        return QuestionType.SHORT_ANSWER;
    }
}
