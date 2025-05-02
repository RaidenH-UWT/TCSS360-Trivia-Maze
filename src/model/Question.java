package src.model;

/**
 * Abstraction of a question for inheriting from.
 * @author Raiden H
 * @version May 1, 2025
 */
public abstract class Question {
    // TODO: Consider replacing the passed ID with a generated ID, easy to
    // implement in SQLite and makes sure we have control over the IDs.
    /**
     * ID of this question, for the database.
     */
    protected int id;

    /**
     * Text of this question.
     */
    protected String questionText;

    // TODO: Consider moving down to child classes because this may be
    // type-specific, ex: multiple choice is more of an index thing while
    // short answer would be a string.
    /**
     * Correct answer for this question.
     */
    protected String correctAnswer;

    // should this be the QuestionType?
    /**
     * Category of this question.
     */
    protected String category;

    /**
     * Difficulty of this question
     */
    protected int difficulty;

    // correctAnswer may need to be moved down cause it depends on the QuestionType
    /**
     * Creates a new Question object.
     * @param theId int ID for this question, to be used in the database
     * @param theQuestion String of the question text
     * @param theAnswer String of the correct answer
     * @param theCategory String of the question category
     * @param theDifficulty int difficulty of this question [range]
     */
    public Question(int theId, String theQuestion, String theAnswer,
                    String theCategory, int theDifficulty) {
        super();
        // add to database here?
    }

    /**
     * @return int ID of this question.
     */
    public int getId() {
        return 0;
    }

    /**
     * @return String text of this question.
     */
    public String getQuestion() {
        return null;
    }

    /**
     * @return String category of this question.
     */
    public String getCategory() {
        return null;
    }

    /**
     * @return int difficult of this question.
     */
    public int getDifficulty() {
        return 0;
    }

    /**
     * Check the given answer against the correct answer.
     * @param theAnswer String answer to check
     * @return true if the passed answer and correct answer match, false otherwise
     */
    public boolean checkAnswer(String theAnswer) {
        return false;
    }

    /**
     * @return QuestionType type of this question.
     */
    public QuestionType getQuestionType() {
        return null;
    }
}
