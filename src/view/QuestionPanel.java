package src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import src.model.MultipleChoiceQuestion;
import src.model.Question;

public class QuestionPanel extends JPanel {

    /**
     * Label of the current question
     */
    private JTextArea myCurrentQuestionLabel;

    /**
     * Panel for question answer inputs
     */
    private JPanel myAnswerInputPanel;

    /**
     * Answer object, either ButtonGroup or JTextField
     */
    private Object myAnswerComponent;

    /**
     * Method to call to process user answers
     */
    private final Consumer<String> myAnswerMethod;

    /**
     * Thread to prevent overlapping animations.
     */
    private volatile Thread animationThread;

    /**
     * Lock object to ensure only one animation runs at a time.
     */
    private final Object animationLock = new Object();

    public QuestionPanel(final Consumer<String> theAnswerMethod) {
        super();

        myAnswerMethod = theAnswerMethod;

        setBackground(new Color(30, 30, 30));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel questionLabel = new JLabel("QUESTION: ");
        questionLabel.setForeground(new Color(0, 191, 255));
        questionLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(questionLabel, gbc);

        myCurrentQuestionLabel = new JTextArea(3, 25);
        myCurrentQuestionLabel.setLineWrap(true);
        myCurrentQuestionLabel.setWrapStyleWord(true);
        myCurrentQuestionLabel.setEditable(false);
        myCurrentQuestionLabel.setOpaque(false);
        myCurrentQuestionLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        myCurrentQuestionLabel.setForeground(Color.WHITE);
        myCurrentQuestionLabel.setFocusable(false);

        JScrollPane questionScrollPane = new JScrollPane(myCurrentQuestionLabel);
        questionScrollPane.setPreferredSize(new Dimension(250, 60));
        questionScrollPane.setBorder(null);
        questionScrollPane.setOpaque(false);
        questionScrollPane.getViewport().setOpaque(false);

        myCurrentQuestionLabel.setWrapStyleWord(true);
        myCurrentQuestionLabel.setLineWrap(true);
        myCurrentQuestionLabel.setEditable(false);
        myCurrentQuestionLabel.setOpaque(false);
        myCurrentQuestionLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        myCurrentQuestionLabel.setForeground(Color.WHITE);
        myCurrentQuestionLabel.setFocusable(false);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(questionScrollPane, gbc);

        JLabel answerLabel = new JLabel("ANSWER >");
        answerLabel.setForeground(new Color(106, 90, 205));
        answerLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(answerLabel, gbc);

        myAnswerInputPanel = new JPanel();
        myAnswerInputPanel.setBackground(getBackground());
        myAnswerInputPanel.setPreferredSize(new Dimension(300, 100));
        myAnswerInputPanel.setLayout(new BoxLayout(myAnswerInputPanel, BoxLayout.Y_AXIS));

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(myAnswerInputPanel, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(submitButton, gbc);

        submitButton.addActionListener(e -> processAnswer());
        setPreferredSize(new Dimension(350, 200));
        setMaximumSize(new Dimension(350, 200));
        setMinimumSize(new Dimension(350, 200));

    }

    /**
     * Update this panel with the current question.
     *
     * @param theQuestion Question object to update from
     */
    public void updateQuestion(final Question theQuestion) {
        myAnswerInputPanel.removeAll();

        if (theQuestion == null) {
            animateQuestionText("No door that way");
        } else {
            animateQuestionText(theQuestion.getQuestion());

            switch (theQuestion.getQuestionType()) {
                case SHORT_ANSWER:
                    setShortAnswer();
                    break;
                case MULTIPLE_CHOICE:
                    setMultipleChoice(((MultipleChoiceQuestion) theQuestion).getOptions());
                    break;
                case TRUE_FALSE:
                    setTrueFalse();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown question type");
            }
        }

        myAnswerInputPanel.revalidate();
        myAnswerInputPanel.repaint();
    }

    /**
     * Displays the given text word by word with a delay.
     */
    private void animateQuestionText(String fullText) {
        synchronized (animationLock) {
            if (animationThread != null && animationThread.isAlive()) {
                animationThread.interrupt();
            }

            Thread newThread = new Thread(() -> {
                String[] words = fullText.split(" ");
                StringBuilder builder = new StringBuilder();

                for (String word : words) {
                    try {
                        synchronized (animationLock) {
                            if (Thread.currentThread() != animationThread) {
                                return;
                            }
                        }

                        builder.append(word).append(" ");
                        String currentText = builder.toString();

                        SwingUtilities.invokeLater(() -> myCurrentQuestionLabel.setText(currentText));
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            });

            animationThread = newThread;
            newThread.start();
        }
    }

    private void setShortAnswer() {
        JTextField tf = new JTextField(20);
        tf.setBackground(Color.BLACK);
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Monospaced", Font.PLAIN, 14));
        myAnswerComponent = tf;
        myAnswerInputPanel.add(tf);
        tf.addActionListener(e -> processAnswer());
    }

    private void setMultipleChoice(final List<String> theOptions) {
        ButtonGroup mcGroup = new ButtonGroup();
        JPanel mcPanel = new JPanel(new GridLayout(0, 1));
        mcPanel.setBackground(Color.BLACK);

        for (String optionText : theOptions) {
            JRadioButton option = new JRadioButton(optionText);
            option.setForeground(Color.WHITE);
            option.setBackground(Color.BLACK);
            mcGroup.add(option);
            mcPanel.add(option);
        }

        myAnswerComponent = mcGroup;
        myAnswerInputPanel.add(mcPanel);
    }

    private void setTrueFalse() {
        ButtonGroup tfGroup = new ButtonGroup();
        JRadioButton trueButton = new JRadioButton("True");
        JRadioButton falseButton = new JRadioButton("False");

        for (JRadioButton b : new JRadioButton[]{trueButton, falseButton}) {
            b.setForeground(Color.WHITE);
            b.setBackground(Color.BLACK);
            tfGroup.add(b);
        }

        JPanel trueFalsePanel = new JPanel(new GridLayout(1, 2));
        trueFalsePanel.setBackground(Color.BLACK);

        trueFalsePanel.add(trueButton);
        trueFalsePanel.add(falseButton);

        myAnswerComponent = tfGroup;
        myAnswerInputPanel.add(trueFalsePanel);
    }

    private void processAnswer() {
        myAnswerMethod.accept(getUserAnswer());
    }

    void skipQuestion() {
        myAnswerMethod.accept(Question.SKIP);
    }

    private String getUserAnswer() {
        if (myAnswerComponent instanceof JTextField) {
            return ((JTextField) myAnswerComponent).getText();
        } else if (myAnswerComponent instanceof ButtonGroup) {
            ButtonGroup group = (ButtonGroup) myAnswerComponent;
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    return button.getText();
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported answer component");
        }
        return null;
    }
}
