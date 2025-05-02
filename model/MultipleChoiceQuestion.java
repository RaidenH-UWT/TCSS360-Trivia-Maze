package model;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestion extends Question {

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

    public List<String> getOptions() {
        return new ArrayList<>(myOptions);
    }

    @Override
    public boolean checkAnswer(String theAnswer) {
        return myCorrectAnswer.equalsIgnoreCase(theAnswer.trim());
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.MULTIPLE_CHOICE;
    }
}
