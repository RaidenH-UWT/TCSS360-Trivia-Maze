package src.model;

/**
 * Manages the lock state and question for this direction of a room.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */
public class Door implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // could replace these two with one int either 0, 1, or 2
    /**
     * Stores whether this door is permanantly locked or not.
     */
    private boolean myLocked;

    /**
     * Stores whether this door is open or not.
     */
    private boolean myOpened;

    /**
     * Stores the Question object associated with this door.
     */
    private Question myQuestion;

    /**
     * Constructs a new Door with the given Question.
     *
     * @param theQuestion the Question object associated with this Door
     */
    public Door(final Question theQuestion) {
        super();

        myQuestion = theQuestion;
        myLocked = false;
        myOpened = false;
    }

    /**
     * Get the locked state of this door.
     *
     * @return true if this door is locked, false otherwise
     */
    public boolean isLocked() {
        return myLocked;
    }

    /**
     * Get the opened state of this door.
     *
     * @return true if this door is open, false otherwise
     */
    public boolean isOpen() {
        return myOpened;
    }

    /**
     * Locks this door permanantly
     */
    public void lock() {
        myLocked = true;
        myOpened = false;
    }

    /**
     * Opens this door permanantly
     */
    public void open() {
        myOpened = true;
        myLocked = false;
    }

    /**
     * Get the Question associated with this Door.
     *
     * @return Question object associated with this door
     */
    public Question getQuestion() {
        return myQuestion;
    }

    // Should this behaviour be in the Question class instead?
    /**
     * Attempts to answer the question associated with this Door.
     *
     * @param theAnswer the answer to attempt
     * @return true if the answer was correct, false otherwise
     */
    public boolean answerQuestion(final String theAnswer) {
        return myQuestion != null && myQuestion.checkAnswer(theAnswer);
    }

    @Override
    public boolean equals(final Object theDoor) {
        // Make sure our casting works
        if (!this.getClass().equals(theDoor.getClass())) {
            return false;
        }

        boolean val
                = isOpen() == ((Door) theDoor).isOpen()
                && isLocked() == ((Door) theDoor).isLocked()
                && getQuestion().equals(((Door) theDoor).getQuestion());

        return val;
    }

    @Override
    public String toString() {
        String out = String.format("lock: %b, %s", isLocked(), getQuestion().toString());
        return out;
    }
}
