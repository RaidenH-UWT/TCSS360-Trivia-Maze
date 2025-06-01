package src.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Stats panel for the GuiView class.
 * @author Raiden H
 * @version Spring 2025
 */
public class StatsPanel extends JPanel {
    /**
     * Counter for the number of questions the player has answered.
     */
    private int myQuestionsAnswered = 0;

    /**
     * Counter for the number of questions the player has gotten correct.
     */
    private int myQuestionsCorrect = 0;
    /**
     * Label for answered questions.
     */
    private final JLabel answeredLabel;
    /**
     * Label for failed questions.
     */
    private final JLabel failedLabel;
    
    /**
     * Create a new StatsPanel
     */
    public StatsPanel() {
        super();

        setBackground(new Color(18, 18, 18));
        setLayout(new GridBagLayout());

        Font labelFont = new Font("Monospaced", Font.BOLD, 14);

        answeredLabel = new JLabel();
        answeredLabel.setFont(labelFont);
        answeredLabel.setForeground(Color.GREEN);

        failedLabel = new JLabel();
        failedLabel.setFont(labelFont);
        failedLabel.setForeground(new Color(255, 85, 85));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(answeredLabel, gbc);

        gbc.gridy = 1;
        add(failedLabel, gbc);
        updateLabels();
    }

    /**
     * Create a new StatsPanel with the given initial counters.
     * @param theQuestionsAnswered int number of questions answered
     * @param theQuestionsCorrect int number of questions correct
     */
    public StatsPanel(final int theQuestionsAnswered, final int theQuestionsCorrect) {
        this();
        if (theQuestionsAnswered >= 0) {
            myQuestionsAnswered = theQuestionsAnswered;
        } else {
            throw new IllegalArgumentException("theQuestionsAnswered out of bounds");
        }

        if (theQuestionsCorrect >= 0) {
            myQuestionsCorrect = theQuestionsCorrect;
        } else {
            throw new IllegalArgumentException("theQuestionsCorrect out of bounds");
        }
    }

    /**
     * Increment the questions answered counter by 1.
     */
    public void incrementQuestionsAnswered() {
        myQuestionsAnswered++;
        updateLabels();
    }

    /**
     * Increment the questions correct counter by 1.
     */
    public void incrementQuestionsCorrect() {
        myQuestionsCorrect++;
        updateLabels();
    }

    /**
     * @return int number of questions answered.
     */
    public int getQuestionsAnswered() {
        return myQuestionsAnswered;
        
    }

    /**
     * @return int number of questions correct.
     */
    public int getQuestionsCorrect() {
        return myQuestionsCorrect;
    }

    /**
     * Reset the questions answered and questions correct counters to 0.
     */
    public void clear() {
        myQuestionsAnswered = 0;
        myQuestionsCorrect = 0;
        repaint();
    }
    /**
     * Updates both thhe Answered and Failed labels.
     */
    private void updateLabels() {
        answeredLabel.setText("QUESTIONS ANSWERED: " + myQuestionsAnswered);
        failedLabel.setText("QUESTIONS FAILED:   " + (myQuestionsAnswered - myQuestionsCorrect));
    }
}
