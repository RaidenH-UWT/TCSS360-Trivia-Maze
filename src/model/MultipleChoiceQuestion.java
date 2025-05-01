package src.model;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    // may need to change type
    private List<String> myOptions;
    
    public MultipleChoiceQuestion(final int theId, final String theQuestion, final String theAnswer,
                            final String theCategory, final int theDifficulty) {
        super(theId, theQuestion, theAnswer, theCategory, theDifficulty);
    }

    public List<String> getOptions() {
        return null;
    }

    // may need to change parameter type?
    public boolean checkAnswer(String theAnswer) {
        return false;
    }

    // shouldn't this be in the abstract class?
    public QuestionType getQuestionType() {
        return null;
    }
}
