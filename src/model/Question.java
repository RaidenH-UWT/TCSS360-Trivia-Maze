package src.model;

public abstract class Question {
    protected int id;
    protected String questionText;
    protected String correctAnswer;
    // should this be the QuestionType?
    protected String category;
    protected int difficulty;

    // correctAnswer may need to be moved down cause it depends on the QuestionType
    public Question(int theId, String theQuestion, String theAnswer,
                    String theCategory, int theDifficulty) {
        super();
    }

    public int getId() {
        return 0;
    }

    public String getQuestion() {
        return null;
    }

    public String getCategory() {
        return null;
    }

    public int getDifficulty() {
        return 0;
    }

    public boolean checkAnswer(String theAnswer) {
        return false;
    }

    public QuestionType getQuestionType() {
        return null;
    }
}
