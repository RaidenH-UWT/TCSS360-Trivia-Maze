package src.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Multiple choice variant of the Question class.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */
public class MultipleChoiceQuestion extends Question {
    /**
     * String answer of this question.
     */
    private String myAnswer;

    /**
     * List of options for this question.
     */
    private List<String> myOptions;

    /**
     * Constructor called only from QuestionFactory.
     */
    protected MultipleChoiceQuestion(final int theId, final String theQuestion,
        final String theAnswer, final List<String> theOptions, final int theDifficulty) {
        super(theId, theQuestion, theDifficulty);

        if (theOptions == null || theOptions.size() < 2) {
            throw new IllegalArgumentException("Multiple choice questions must have at least 2 options");
        }
        if (!theOptions.contains(theAnswer)) {
            throw new IllegalArgumentException("Options must contain the correct answer");
        }

        myAnswer = theAnswer;

        myOptions = new ArrayList<>(theOptions);
    }

    /**
     * @return ArrayList<String> of the options for this question.
     */
    public List<String> getOptions() {
        return new ArrayList<>(myOptions);
    }

    /**
     * Check the given answer against the correct answer.
     *
     * @param theAnswer String answer to check
     * @return true if the passed answer and stored answer match, false
     * otherwise
     */
    @Override
    public boolean checkAnswer(String theAnswer) {
        if (theAnswer == null) {
            return false;
        }
        return myAnswer.equalsIgnoreCase(theAnswer.trim());
    }

    /**
     * @return QuestionType.MULTIPLE_CHOICE
     */
    @Override
    public QuestionType getQuestionType() {
        return QuestionType.MULTIPLE_CHOICE;
    }

    @Override
    public String toString() {
        String out = "(" + getId() + ", " + getQuestion() + ", " + getQuestionType() + ")";
        return out;
    }
}
