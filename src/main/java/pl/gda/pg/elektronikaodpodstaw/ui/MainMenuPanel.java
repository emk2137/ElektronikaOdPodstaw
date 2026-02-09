package pl.gda.pg.elektronikaodpodstaw.ui;

import pl.gda.pg.elektronikaodpodstaw.levels.LevelManager;
import pl.gda.pg.elektronikaodpodstaw.levels.Levels;
import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the main menu panel of the game.
 * It provides navigation options, such as starting a new game, continuing, settings, or exiting the game.
 */
public class MainMenuPanel extends JPanel {

    /** The background image displayed in the main menu panel. */
    private Image backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(MainFrame.currentBackground))).getImage();

    /** The label displaying the title of the game. */
    private final JLabel titleLabel;

    /** The list of hover buttons displayed on the main menu. */
    private final java.util.List<HoverButton> buttons = new ArrayList<>();

    /**
     * Constructs the main menu panel and initializes its components.
     *
     * @param frame the main application frame.
     */
    public MainMenuPanel(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        UIManager.put("OptionPane.background", MainFrame.getBackgroundTheme());
        UIManager.put("Panel.background", MainFrame.getBackgroundTheme());
        UIManager.put("OptionPane.messageForeground", MainFrame.getTextTheme());

        titleLabel = new JLabel("Elektronika od podstaw");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        titleLabel.setForeground(MainFrame.getTextTheme());
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 60, 0));
        add(titleLabel);

        String[] options = {"Nowa gra", "Kontynuuj", "Wolna symulacja", "Ustawienia", "Wyjdź z gry"};
        for (String option : options) {
            HoverButton button = getHoverButton(frame, option);

            buttons.add(button);
            add(button);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }
    }

    /**
     * Creates and configures a hover button for the main menu based on the specified option.
     *
     * @param frame the main application frame.
     * @param option the button label.
     * @return a configured HoverButton instance.
     */
    private static HoverButton getHoverButton(MainFrame frame, String option) {
        HoverButton button = new HoverButton(option);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 60));
        button.setContentAreaFilled(false);
        button.setForeground(MainFrame.getTextTheme());

        button.addActionListener(_ -> {
            switch (option) {
                case "Nowa gra" -> {
                    try {
                        Levels.startMessage(frame);
                    } catch (BadLocationException e) {
                        throw new RuntimeException(e);
                    }
                    LevelManager.NewGame(frame);
                }
                case "Kontynuuj" -> {
                    CardLayout cl1 = (CardLayout) frame.getContentPane().getLayout();
                    cl1.show(frame.getContentPane(), "LevelMenu");
                }
                case "Wolna symulacja" -> {
                    MainFrame.currentLevel = 11;
                    JPanel currentStagePanel = new SimulationStagePanel(frame, "" , "" ,  "");
                    frame.getContentPane().add(currentStagePanel, "CurrentLevel");
                    ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "CurrentLevel");
                    frame.revalidate();
                    frame.repaint();
                }
                case "Ustawienia" -> {
                    CardLayout cl2 = (CardLayout) frame.getContentPane().getLayout();
                    cl2.show(frame.getContentPane(), "Settings");
                }
                case "Wyjdź z gry" -> System.exit(0);
            }
        });
        return button;
    }

    /**
     * Updates the background image and the text colors of the components
     * based on the current theme of the application.
     */
    private void updateBackgroundAndText() {
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(MainFrame.currentBackground))).getImage();
        Color textColor = MainFrame.getTextTheme();
        titleLabel.setForeground(textColor);
        for (HoverButton button : buttons) button.setForeground(textColor);
        repaint();
    }

    /**
     * Paints the main menu panel, including its background image and button states.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void paintComponent(Graphics g) {
        if (MainFrame.availableLevel > 1) {
            for (HoverButton b : buttons)
                if (b.getText().equals("Kontynuuj"))
                    b.enableContinueButton();
        }
        else {
            for (HoverButton b : buttons)
                if (b.getText().equals("Kontynuuj"))
                    b.disableContinueButton();
        }
        if (MainFrame.isCompleted.equals("yes")) {
            for (HoverButton b : buttons)
                if (b.getText().equals("Wolna symulacja"))
                    b.enableFreeSimButton();
        }
        else {
            for (HoverButton b : buttons)
                if (b.getText().equals("Wolna symulacja"))
                    b.disableFreeSimButton();
        }

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
