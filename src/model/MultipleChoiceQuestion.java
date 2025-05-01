package src.model;

import java.util.List;

/**
 * Multiple choice variant of the Question class.
 * @author Raiden H
 * @version May 1, 2025
 */
public class MultipleChoiceQuestion extends Question {
    // may need to change type
    /**
     * List of options for this question.
     */
    private List<String> myOptions;

    /**
     * {@inheritDoc}
     */
    public MultipleChoiceQuestion(final int theId, final String theQuestion, final String theAnswer,
                            final String theCategory, final int theDifficulty) {
        super(theId, theQuestion, theAnswer, theCategory, theDifficulty);
    }

    /**
     * @return List<String> of the options for this question.
     */
    public List<String> getOptions() {
        return null;
    }

    // may need to change parameter type?
    /**
     * Check a given answer against the correct answer.
     * @param theAnswer String answer to check
     * @return true if the given answer matches the correct answer, false otherwise
     */
    public boolean checkAnswer(String theAnswer) {
        return false;
    }

    // shouldn't this be in the abstract class?
    /**
     * {@inheritDoc}
     */
    public QuestionType getQuestionType() {
        return null;
    }
}
