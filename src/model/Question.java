package src.model;

/**
 * Abstraction of a question for inheriting from.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */
// TODO: Refactor all Questions to work primarily off the SQLite database, rather than java state
// e.g.: constructor only has an ID that's a reference to the table item in the database. 
public abstract class Question {

    // TODO: Consider replacing the passed ID with a generated ID, easy to
    // implement in SQLite and makes sure we have control over the IDs.
    /**
     * ID of this question, for the database.
     */
    protected int myId;

    /**
     * Text of this question.
     */
    protected String myQuestion;

    // TODO: Consider moving down to child classes because this may be
    // type-specific, ex: multiple choice is more of an index thing while
    // short answer would be a string.
    /**
     * Correct answer for this question.
     */
    protected String myAnswer;

    // should this be the QuestionType?
    /**
     * Category of this question.
     */
    protected String myCategory;

    /**
     * Difficulty of this question
     */
    protected int myDifficulty;

    // mynswer may need to be moved down cause it depends on the QuestionType
    /**
     * Creates a new Question object.
     *
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
        if (theQuestion == null || theAnswer == null || theCategory == null) {
            throw new IllegalArgumentException("Question parameters cannot be null");
        }
        if (theDifficulty < 1 || theDifficulty > 5) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 5");
        }

        if (false) {
            // TODO: Check DB to see if question exists with the same ID
        }

        myId = theId;
        myQuestion = theQuestion;
        myAnswer = theAnswer;
        myCategory = theCategory;
        myDifficulty = theDifficulty;
    }

    /**
     * @return int ID of this question.
     */
    public int getId() {
        return myId;
    }

    /**
     * @return String text of this question.
     */
    public String getQuestion() {
        return myQuestion;
    }

    /**
     * @return String category of this question.
     */
    public String getCategory() {
        return myCategory;
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
        return getId() == ((Question) question).getId();
    }
}
