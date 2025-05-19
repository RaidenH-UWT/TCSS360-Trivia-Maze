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
     * List of options for this question.
     */
    private List<String> myOptions;

    public MultipleChoiceQuestion(final int theId, final String theQuestionText, final String theCorrectAnswer,
            List<String> theOptions, final String theCategory, final int theDifficulty) {
        super(theId, theQuestionText, theCorrectAnswer, theCategory, theDifficulty);

        if (theOptions == null || theOptions.size() < 2) {
            throw new IllegalArgumentException("Multiple choice questions must have at leaset 2 options");
        }
        if (!theOptions.contains(theCorrectAnswer)) {
            throw new IllegalArgumentException("Options must contain the correct answer");
        }

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
        String out
                = "("
                + myId + ", "
                + myQuestion + ", "
                + myAnswer + ", "
                + myOptions + ", "
                + myCategory + ", "
                + getQuestionType() + ", "
                + myDifficulty
                + ")";
        return out;
    }
}
