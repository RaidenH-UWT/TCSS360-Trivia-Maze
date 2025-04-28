package model;

public class Door {
    // could replace these two with one int either 0, 1, or 2
    private boolean myLocked;
    private boolean myOpened;
    private Question myQuestion;

    public Door(Question theQuestion) {
        super();
    }

    public boolean isLocked() {
        return false;
    }

    public boolean isOpen() {
        return false;
    }

    public void lock() {

    }

    public void open() {

    }

    public Question getQuestion() {
        return null;
    }
    // Should this behaviour be in the Question class instead?
    public boolean answerQuestion(String theAnswer) {
        return false;
    }
}
