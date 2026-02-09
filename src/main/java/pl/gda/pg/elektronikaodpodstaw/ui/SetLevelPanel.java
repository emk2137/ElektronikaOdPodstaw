package pl.gda.pg.elektronikaodpodstaw.ui;

import pl.gda.pg.elektronikaodpodstaw.levels.LevelManager;
import pl.gda.pg.elektronikaodpodstaw.levels.Levels;
import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the panel for selecting levels in the game.
 * It displays buttons for available levels and back to main menu option.
 */
public class SetLevelPanel extends JPanel {

    /** The background image for the level selection panel. */
    private Image backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(MainFrame.currentBackground))).getImage();

    /** The button for navigating back to the main menu. */
    private final HoverButton backButton;

    /** The label displaying the title of the level selection panel. */
    private final JLabel titleLabel;

    /** A list of buttons representing the selectable levels. */
    private final java.util.List<HoverButton> buttons = new ArrayList<>();

    /**
     * Constructs the SetLevelPanel and initializes its components.
     *
     * @param frame the main application frame.
     */
    public SetLevelPanel(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("Wybierz poziom");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        titleLabel.setForeground(MainFrame.getTextTheme());
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titleLabel);

        backButton = new HoverButton("Powrót");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setFont(new Font("Arial", Font.PLAIN, 40));
        backButton.setOpaque(false);
        backButton.setForeground(MainFrame.getTextTheme());
        backButton.addActionListener(_ -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "MainMenu");
        });
        buttons.add(backButton);
        add(backButton);

        updateLevelButtons();
    }

    /**
     * Updates the buttons on the panel to reflect the currently available levels.
     * This method removes all existing buttons, recreates them based on the number of levels,
     * and applies the current theme to the components.
     */
    public void updateLevelButtons() {
        removeAll();
        buttons.clear();

        titleLabel.setForeground(MainFrame.getTextTheme());
        add(titleLabel);

        for (int i = 0; i < MainFrame.availableLevel; i++) {
            HoverButton button = getHoverButton(i);

            buttons.add(button);
            add(button);
        }

        buttons.add(backButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(backButton);

        revalidate();
        repaint();
    }

    /**
     * Creates and configures a hover button for a specific level.
     *
     * @param i the index of the level this button represents.
     * @return a configured HoverButton instance.
     */
    private HoverButton getHoverButton(int i) {
        String level = Levels.levelsList.get(i).name();
        HoverButton button = new HoverButton(level);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        button.setContentAreaFilled(false);
        button.setForeground(MainFrame.getTextTheme());

        button.addActionListener(_ -> {
            MainFrame.currentLevel = i + 1;
            LevelManager.loadLevelStage((MainFrame) SwingUtilities.getWindowAncestor(this));
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
     * Paints the background of the level selection panel.
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
