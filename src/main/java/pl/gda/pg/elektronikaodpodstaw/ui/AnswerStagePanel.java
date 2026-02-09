package pl.gda.pg.elektronikaodpodstaw.ui;

import pl.gda.pg.elektronikaodpodstaw.levels.Level;
import pl.gda.pg.elektronikaodpodstaw.levels.LevelManager;
import pl.gda.pg.elektronikaodpodstaw.levels.Levels;
import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the panel displayed during a question-answer stage in the game.
 * It includes components for displaying the question, capturing user input, and handling user interactions.
 */
public class AnswerStagePanel extends JPanel {

    /** The background image for the panel. */
    private final Image backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(MainFrame.currentBackground))).getImage();

    /** The text field for user input. */
    private final JTextField answerField;

    /** The label displaying the title of the current level. */
    private final JLabel levelTitleLabel;

    /** The text pane displaying the question. */
    private final JTextPane questionPane;

    /** A list of buttons displayed on the panel. */
    private final java.util.List<HoverButton> buttons = new ArrayList<>();

    /** The correct answer for the question. */
    private final String correctAnswer;

    /** The theory content associated with the current level. */
    private final String theory;

    /**
     * Constructs an AnswerStagePanel with the specified frame, question, correct answer, and theory.
     *
     * @param frame the main application frame.
     * @param question the question text to display.
     * @param correctAnswer the correct answer for the question.
     * @param theory the theory content associated with the current level.
     */
    public AnswerStagePanel(MainFrame frame, String question, String correctAnswer, String theory) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.correctAnswer = correctAnswer;
        this.theory = theory;

        Level currentLevel = Levels.levelsList.get(MainFrame.currentLevel - 1);
        String levelTitle = currentLevel.name();
        levelTitleLabel = new JLabel(levelTitle);
        levelTitleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        if (MainFrame.currentLevel == 3 || MainFrame.currentLevel == 5 || MainFrame.currentLevel == 7 || MainFrame.currentLevel == 11)
            levelTitleLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        else
            levelTitleLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        levelTitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(levelTitleLabel);

        questionPane = new JTextPane();
        questionPane.setText(question);
        questionPane.setEditable(false);
        questionPane.setFocusable(false);
        questionPane.setOpaque(false);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(attributes, "Arial");
        StyleConstants.setFontSize(attributes, 25);
        StyleConstants.setLineSpacing(attributes, 0.2f);
        StyledDocument doc = questionPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), attributes, false);
        add(questionPane);
        adjustQuestionPaneHeight();

        add(Box.createRigidArea(new Dimension(0, 20)));

        answerField = new JTextField();
        answerField.setMaximumSize(new Dimension(100, 30));
        answerField.setAlignmentX(CENTER_ALIGNMENT);
        add(answerField);

        add(Box.createRigidArea(new Dimension(0, 20)));

        String[] options = {"Zatwierdź", "Pomoc", "Powrót"};

        for (String option : options) {
            HoverButton button = getHoverButton(frame, option);
            buttons.add(button);
            add(button);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }

        SwingUtilities.invokeLater(() -> {
            if (LevelManager.getCurrentStageIndex() == 0) {
                JOptionPane.showMessageDialog(frame, levelTitle, "Poziom " + MainFrame.currentLevel, JOptionPane.INFORMATION_MESSAGE);
                MainFrame.showTheory(frame, theory);
            }
        });

        setupKeyBindings(frame);
    }

    /**
     * Creates and configures a HoverButton for the panel based on the provided option.
     *
     * @param frame the main application frame.
     * @param option the button label.
     * @return a configured HoverButton instance.
     */
    private HoverButton getHoverButton(MainFrame frame, String option) {
        HoverButton button = new HoverButton(option);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 45));
        button.setContentAreaFilled(false);
        button.setForeground(MainFrame.getTextTheme());
        button.addActionListener(_ -> {
            switch (option) {
                case "Zatwierdź" -> checkAnswer(frame);
                case "Pomoc" -> MainFrame.showTheory(frame, theory);
                case "Powrót" -> {
                    CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
                    cl.show(frame.getContentPane(), "MainMenu");
                    LevelManager.currentStageIndex = 0;
                }
            }
        });
        return button;
    }

    /**
     * Adjusts the height of the question pane to fit the content dynamically.
     */
    private void adjustQuestionPaneHeight() {
        questionPane.setSize(new Dimension(960, Integer.MAX_VALUE));
        int preferredHeight = questionPane.getPreferredSize().height;
        questionPane.setPreferredSize(new Dimension(960, preferredHeight));
        questionPane.setMaximumSize(new Dimension(960, preferredHeight));
        questionPane.revalidate();
    }

    /**
     * Validates the user's answer and advances to the next stage if correct.
     * Displays an error message if the answer is incorrect.
     *
     * @param frame the main application frame.
     */
    private void checkAnswer(MainFrame frame) {
        if (answerField.getText().trim().equals(correctAnswer))
            MainFrame.levelManager.nextStage(frame);
        else
            JOptionPane.showMessageDialog(frame, "Nieprawidłowa odpowiedź, spróbuj ponownie.", "Wynik", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sets up key bindings for the panel to handle user input shortcuts.
     *
     * @param frame the main application frame.
     */
    private void setupKeyBindings(MainFrame frame) {
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
        actionMap.put("back", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
                cl.show(frame.getContentPane(), "MainMenu");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "confirm");
        actionMap.put("confirm", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer(frame);
            }
        });
    }

    /**
     * Updates the background and text colors based on the current theme.
     */
    private void updateBackgroundAndText() {
        Color textColor = MainFrame.getTextTheme();
        levelTitleLabel.setForeground(textColor);
        questionPane.setForeground(textColor);
        for (HoverButton button : buttons) button.setForeground(textColor);
        repaint();
    }

    /**
     * Paints the background image of the panel.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void paintComponent(Graphics g) {
        updateBackgroundAndText();
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            if (MainFrame.currentTheme.equals("dark")) g2d.setColor(new Color(0, 0, 0, 150));
            else if (MainFrame.currentTheme.equals("light")) g2d.setColor(new Color(255, 255, 255, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

}