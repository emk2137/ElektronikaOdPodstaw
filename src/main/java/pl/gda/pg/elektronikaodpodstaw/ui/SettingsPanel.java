package pl.gda.pg.elektronikaodpodstaw.ui;

import pl.gda.pg.elektronikaodpodstaw.config.ConfigManager;
import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Represents the settings panel of the game.
 * It allows users to customize the game's appearance and preferences, such as background and theme.
 */
public class SettingsPanel extends JPanel {

    /** The background image displayed in the settings panel. */
    private Image backgroundImage;

    /** The button for navigating back to the main menu. */
    private final HoverButton backButton;

    /** A list of all labels used in the settings panel. */
    private final List<JLabel> allLabels;

    /** A list of all radio buttons used in the settings panel. */
    private final List<JRadioButton> allRadioButtons;

    /**
     * Constructs the SettingsPanel and initializes its components for customizing game settings.
     *
     * @param frame the main application frame.
     */
    public SettingsPanel(MainFrame frame) {
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(MainFrame.currentBackground))).getImage();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        Font labelFont = new Font("Arial", Font.PLAIN, 45);
        Font buttonFont = new Font("Arial", Font.PLAIN, 40);
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 20, 10);

        JPanel backgroundPanel = new JPanel();
        JLabel backgroundLabel = new JLabel("Tło:");
        JRadioButton background1 = new JRadioButton("Tło 1");
        JRadioButton background2 = new JRadioButton("Tło 2");
        JRadioButton background3 = new JRadioButton("Tło 3");
        Stream.of(backgroundLabel, background1, background2, background3).forEach(backgroundPanel::add);

        switch (MainFrame.currentBackground) {
            case "/backgrounds/background1.jpg" -> background1.setSelected(true);
            case "/backgrounds/background2.jpeg" -> background2.setSelected(true);
            case "/backgrounds/background3.jpg" -> background3.setSelected(true);
        }

        background1.addActionListener(_ -> updateBackground("/backgrounds/background1.jpg"));
        background2.addActionListener(_ -> updateBackground("/backgrounds/background2.jpg"));
        background3.addActionListener(_ -> updateBackground("/backgrounds/background3.jpg"));

        ButtonGroup backgroundGroup = new ButtonGroup();
        Stream.of(background1, background2, background3).forEach(backgroundGroup::add);

        gbc.gridy = 0;
        add(backgroundPanel, gbc);

        JPanel themePanel = new JPanel();
        JLabel themeLabel = new JLabel("Motyw:");
        JRadioButton lightTheme = new JRadioButton("Jasny");
        JRadioButton darkTheme = new JRadioButton("Ciemny");
        Stream.of(themeLabel, lightTheme, darkTheme).forEach(themePanel::add);

        if (MainFrame.currentTheme.equals("light")) lightTheme.setSelected(true);
        else darkTheme.setSelected(true);

        lightTheme.addActionListener(_ -> updateTheme("light", MainFrame.configManager));
        darkTheme.addActionListener(_ -> updateTheme("dark", MainFrame.configManager));

        ButtonGroup themeGroup = new ButtonGroup();
        Stream.of(lightTheme, darkTheme).forEach(themeGroup::add);

        gbc.gridy = 1;
        add(themePanel, gbc);

        JPanel resistorPanel = new JPanel();
        JLabel resistorLabel = new JLabel("Symbol rezystora:");
        JRadioButton iecSymbol = new JRadioButton("Europejski");
        JRadioButton ansiSymbol = new JRadioButton("Amerykański");
        Stream.of(resistorLabel, iecSymbol, ansiSymbol).forEach(resistorPanel::add);

        if (MainFrame.currentResistorSymbol.equals("IEC")) iecSymbol.setSelected(true);
        else ansiSymbol.setSelected(true);

        iecSymbol.addActionListener(_ -> updateResistorSymbol("IEC", MainFrame.configManager));
        ansiSymbol.addActionListener(_ -> updateResistorSymbol("ANSI", MainFrame.configManager));

        ButtonGroup resistorSymbolGroup = new ButtonGroup();
        Stream.of(iecSymbol, ansiSymbol).forEach(resistorSymbolGroup::add);

        gbc.gridy = 2;
        add(resistorPanel, gbc);

        backButton = new HoverButton("Powrót");
        backButton.setFont(labelFont);
        backButton.setOpaque(false);
        backButton.setForeground(MainFrame.getTextTheme());
        backButton.addActionListener(_ -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "MainMenu");
        });

        gbc.gridy = 3;
        add(backButton, gbc);

        List<JPanel> allPanels = List.of(backgroundPanel, themePanel, resistorPanel);
        allLabels = List.of(backgroundLabel, themeLabel, resistorLabel);
        allRadioButtons = List.of(background1, background2, background3, lightTheme, darkTheme, iecSymbol, ansiSymbol);

        for (JPanel panel : allPanels) {
            panel.setLayout(flowLayout);
            panel.setOpaque(false);
        }

        for (JLabel label : allLabels) {
            label.setFont(labelFont);
            label.setForeground(MainFrame.getTextTheme());
        }

        for (JRadioButton button : allRadioButtons) {
            button.setOpaque(false);
            button.setFont(buttonFont);
            button.setForeground(MainFrame.getTextTheme());
        }
    }

    /**
     * Updates the background of the settings panel to the specified image.
     *
     * @param backgroundPath the path to the new background image.
     */
    private void updateBackground(String backgroundPath) {
        MainFrame.configManager.setProperty("background", backgroundPath);
        MainFrame.currentBackground = MainFrame.configManager.getProperty("background");
        updateBackgroundAndText();
    }

    /**
     * Updates the background image and text colors based on the current theme.
     * Adjusts the appearance of all labels, radio buttons, and the back button
     * to match the updated theme.
     */
    private void updateBackgroundAndText() {
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(MainFrame.currentBackground))).getImage();
        Color textColor = MainFrame.getTextTheme();
        for (JLabel label : allLabels) label.setForeground(textColor);
        for (JRadioButton button : allRadioButtons) button.setForeground(textColor);
        backButton.setForeground(textColor);
        repaint();
    }

    /**
     * Updates the theme of the game and applies the changes to the settings panel.
     *
     * @param Theme the new theme to apply.
     * @param configManager the configuration manager for saving and retrieving settings.
     */
    private void updateTheme (String Theme, ConfigManager configManager) {
        configManager.setProperty("theme", Theme);
        MainFrame.currentTheme = configManager.getProperty("theme");
        UIManager.put("OptionPane.background", MainFrame.getBackgroundTheme());
        UIManager.put("Panel.background", MainFrame.getBackgroundTheme());
        UIManager.put("OptionPane.messageForeground", MainFrame.getTextTheme());
        updateBackgroundAndText();
    }

    /**
     * Updates the resistor symbol setting and applies the changes globally.
     *
     * @param ResistorSymbol the new resistor symbol format.
     * @param configManager the configuration manager for saving and retrieving settings.
     */
    private void updateResistorSymbol (String ResistorSymbol, ConfigManager configManager) {
        configManager.setProperty("resistorSymbol", ResistorSymbol);
        MainFrame.currentResistorSymbol = configManager.getProperty("resistorSymbol");
    }

    /**
     * Paints the settings panel, including its background and component layout.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void paintComponent(Graphics g) {
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
