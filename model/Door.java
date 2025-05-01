package model;

/**
 * Manages the lock state and question for this direction of a room.
 * @author Raiden H
 * @version May 1, 2025
 */
public class Door {
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
     * @param theQuestion the Question object associated with this Door
     */
    public Door(Question theQuestion) {
        super();
    }

    /**
     * Get the locked state of this door.
     * @returns true if this door is locked, false otherwise
     */
    public boolean isLocked() {
        return false;
    }

    /**
     * Get the opened state of this door.
     * @returns true if this door is open, false otherwise
     */
    public boolean isOpen() {
        return false;
    }

    /**
     * Locks this door permanantly
     */
    public void lock() {

    }

    /**
     * Opens this door permanantly
     */
    public void open() {

    }

    /**
     * Get the Question associated with this Door.
     * @returns Question object associated with this door
     */
    public Question getQuestion() {
        return null;
    }
    // Should this behaviour be in the Question class instead?
    /**
     * Attempts to answer the question associated with this Door.
     * @param theAnswer the answer to attempt
     * @returns true if the answer was correct, false otherwise
     */
    public boolean answerQuestion(String theAnswer) {
        return false;
    }
}
