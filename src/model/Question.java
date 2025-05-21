package src.model;

/**
 * Abstraction of a question for inheriting from.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */

public abstract class Question {
    /**
     * ID of this question, for the database.
     */
    private int myId;

    /**
     * Text of this question.
     */
    private String myQuestion;

    /**
     * 1-5 int difficulty of this question.
     */
    private int myDifficulty;

    /**
     * Creates a new Question object.
     *
     * @param theId int ID for this question from the database
     */
    protected Question(int theId, String theQuestion, int theDifficulty) {
        super();

        myId = theId;
        myQuestion = theQuestion;
        myDifficulty = theDifficulty;
    }

    /**
     * @return int ID of this question
     */
    protected int getId() {
        return myId;
    }

    /**
     * @return String text of this question.
     */
    public String getQuestion() {
        return myQuestion;
    }

    /**
     * @return int difficult of this question.
     */
    public int getDifficulty() {
        return myDifficulty;
    }

    /**
     * Check the given answer against the correct answer.
     *
     * @param theAnswer String answer to check
     * @return true if the passed answer and correct answer match, false
     * otherwise
     */
    public abstract boolean checkAnswer(String theAnswer);

    /**
     * @return QuestionType type of this question.
     */
    public abstract QuestionType getQuestionType();

    @Override
    public boolean equals(Object question) {
        // Make sure our casting works
        if (!this.getClass().equals(question.getClass())) {
            return false;
        }

        // If we have unique IDs (we should) all we need to do is check the ID
        return myId == ((Question) question).getId();
    }
}
