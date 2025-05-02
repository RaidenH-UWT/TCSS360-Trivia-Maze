package model;

public abstract class Question {

    protected int myId;
    protected String myQuestionText;
    protected String myCorrectAnswer;
    protected String myCategory;
    protected int myDifficulty;

    public Question(int theId, String theQuestionText, String theCorrectAnswer,
            String theCategory, int theDifficulty) {
        if (theQuestionText == null || theCorrectAnswer == null || theCategory == null) {
            throw new IllegalArgumentException("Question parameters cannot be null");
        }
        if (theDifficulty < 1 || theDifficulty > 5) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 5");
        }

        myId = theId;
        myQuestionText = theQuestionText;
        myCorrectAnswer = theCorrectAnswer;
        myCategory = theCategory;
        myDifficulty = theDifficulty;
    }

    public int getId() {
        return myId;
    }

    public String getQuestionText() {
        return myQuestionText;
    }

    public String getCategory() {
        return myCategory;
    }

    public int getDifficulty() {
        return myDifficulty;
    }

    public abstract boolean checkAnswer(String theAnswer);

    public abstract QuestionType getQuestionType();
}
