package model;

public class ShortAnswerQuestion extends Question {
    public ShortAnswerQuestion(final int theId, final String theQuestion, final String theAnswer,
                            final String theCategory, final int theDifficulty) {
        super(theId, theQuestion, theAnswer, theCategory, theDifficulty);
    }

    public boolean checkAnswer(String theAnswer) {
        return false;
    }

    public QuestionType getQuestionType() {
        return null;
    }
}
