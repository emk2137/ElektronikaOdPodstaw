package pl.gda.pg.elektronikaodpodstaw.main;

import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import pl.gda.pg.elektronikaodpodstaw.levels.LevelManager;
import pl.gda.pg.elektronikaodpodstaw.levels.Levels;
import pl.gda.pg.elektronikaodpodstaw.config.ConfigManager;
import pl.gda.pg.elektronikaodpodstaw.ui.MainMenuPanel;
import pl.gda.pg.elektronikaodpodstaw.ui.SetLevelPanel;
import pl.gda.pg.elektronikaodpodstaw.ui.SettingsPanel;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Entry point of the game.
 * It initializes the application's configuration, manages the main window, and handles
 * the navigation between different panels, such as the main menu, level selection,
 * and settings.
 *
 */
public class MainFrame extends JFrame {

    /** Configuration manager to handle application settings. */
    public static ConfigManager configManager;

    /** The highest level currently available to the user. */
    public static int availableLevel;

    /** The level currently selected by the user. */
    public static int currentLevel;

    /** Manager responsible for handling levels (loading next stages or levels and saving progress). */
    public static LevelManager levelManager;

    /** Path to the current background image. */
    public static String currentBackground;

    /** Name of the current visual theme. */
    public static String currentTheme;

    /** Current symbol for resistors. */
    public static String currentResistorSymbol;

    /** Status informing whether the game has been completed. */
    public static String isCompleted;

    /** Panel for level selection. */
    public static SetLevelPanel setLevel;

    /**
     * Constructs the main frame of the application, initializing the configuration,
     * panels, and settings for the user interface.
     */
    public MainFrame() {
        configManager = new ConfigManager();
        startSettings();
        Levels.initializeLevels();

        setTitle("Elektronika od podstaw");
        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new CardLayout());
        MainMenuPanel mainMenu = new MainMenuPanel(this);
        setLevel = new SetLevelPanel(this);
        SettingsPanel settings = new SettingsPanel(this);
        levelManager = new LevelManager();

        add(mainMenu, "MainMenu");
        add(setLevel, "LevelMenu");
        add(settings, "Settings");

        setVisible(true);
    }

    /**
     * The main method serves as the entry point of the application.
     * It initializes and displays the main application window.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    /**
     * Displays theoretical information for a given level.
     *
     * @param frame the main application frame.
     * @param theory the theory content to display.
     */
    public static void showTheory(MainFrame frame, String theory) {
        JOptionPane.showMessageDialog(frame, MainFrame.getScrollPlane(theory, false), "Teoria", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Displays question or task to do for a given level.
     *
     * @param frame the main application frame.
     * @param question the question content to display.
     */
    public static void showQuestion(MainFrame frame, String question) {
        JOptionPane.showMessageDialog(frame, MainFrame.getScrollPlane(question, true), "Zadanie", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Creates a scrollable pane for displaying content.
     *
     * @param content the content to display.
     * @param isQuestion information whether the content is a question or theory in order to create the appropriate size of ScrollPlane
     * @return a JScrollPane containing the formatted content.
     */
    public static JScrollPane getScrollPlane(String content, boolean isQuestion) {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);

        StyledDocument doc = textPane.getStyledDocument();

        SimpleAttributeSet textAttributes = new SimpleAttributeSet();
        StyleConstants.setFontFamily(textAttributes, "Arial");
        StyleConstants.setFontSize(textAttributes, 20);
        StyleConstants.setLineSpacing(textAttributes, 0.2f);
        StyleConstants.setAlignment(textAttributes, StyleConstants.ALIGN_JUSTIFIED);

        SimpleAttributeSet centerAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttributes, StyleConstants.ALIGN_CENTER);

        String[] parts = content.split("\\$\\$\\$");

        for (String part : parts) {
            try {
                if (part.contains("\\textcolor")) {

                    TeXFormula formula = new TeXFormula(part.trim());
                    TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, 25);

                    BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(new Color(255, 255, 255, 0));
                    icon.paintIcon(null, g2d, 0, 0);
                    g2d.dispose();

                    SimpleAttributeSet iconAttributes = new SimpleAttributeSet();
                    StyleConstants.setIcon(iconAttributes, new ImageIcon(image));

                    int start = doc.getLength();
                    doc.insertString(start, "\n", iconAttributes);
                    doc.setParagraphAttributes(start, doc.getLength() - start, centerAttributes, false);
                } else if (part.startsWith("image:") && part.length() > 6) {
                    String imagePath = part.substring(6).trim();
                    ImageIcon image = new ImageIcon(Objects.requireNonNull(MainFrame.class.getResource(imagePath)));

                    int originalWidth = image.getIconWidth();
                    int originalHeight = image.getIconHeight();
                    float scale = Math.min((float) 700 / originalWidth, (float) 400 / originalHeight);
                    int scaledWidth = Math.round(originalWidth * scale);
                    int scaledHeight = Math.round(originalHeight * scale);

                    Image scaledImage = image.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    SimpleAttributeSet imageAttributes = new SimpleAttributeSet();
                    StyleConstants.setIcon(imageAttributes, scaledIcon);

                    int start = doc.getLength();
                    doc.insertString(start, "\n", imageAttributes);
                    doc.setParagraphAttributes(start, doc.getLength() - start, centerAttributes, false);

                } else {
                    int start = doc.getLength();
                    doc.insertString(start, part + " ", textAttributes);
                    doc.setParagraphAttributes(start, doc.getLength() - start, textAttributes, false);
                }
            } catch (Exception e) {
                try {
                    doc.insertString(doc.getLength(), "[Błąd renderowania fragmentu: " + part + "] ", textAttributes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(textPane);
        if (isQuestion)
            scrollPane.setPreferredSize(new Dimension(800, 200));
        else
            scrollPane.setPreferredSize(new Dimension(900, 500));
        textPane.setCaretPosition(0);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        textPane.setBackground(MainFrame.getBackgroundTheme());
        textPane.setForeground(MainFrame.getTextTheme());
        return scrollPane;
    }

    /**
     * Returns the color in the form of a String which is needed for equations.
     *
     * @return a string representing color.
     */
    public static String getEquationTheme() {
        if (currentTheme.equals("light"))

            return "black";
        else
            return "white";
    }

    /**
     * Retrieves the text color based on the current theme.
     *
     * @return the text color.
     */
    public static Color getTextTheme() {
        if (currentTheme.equals("light"))
            return Color.BLACK;
        else
            return Color.WHITE;
    }

    /**
     * Retrieves the background color based on the current theme.
     *
     * @return the background color.
     */
    public static Color getBackgroundTheme() {
        if (currentTheme.equals("light"))
            return Color.WHITE;
        else
            return Color.BLACK;
    }

    /**
     * Initializes application settings.
     * If the configuration file is broken, default settings are applied.
     */
    private void startSettings() {
        try {
            loadConfig();
        } catch (Exception _) {
            System.out.println("Config file broken. Using default settings.");
            configManager.setDefaultConfig();
            loadConfig();
        }
    }

    /**
     * Loads the application configuration from the config manager.
     * Initializes settings like available level, current theme, current resistor symbol.
     */
    private void loadConfig() {
        availableLevel = Integer.parseInt(configManager.getProperty("level"));
        currentLevel = availableLevel;
        isCompleted = configManager.getProperty("isCompleted");
        currentBackground = configManager.getProperty("background");
        currentTheme = configManager.getProperty("theme");
        currentResistorSymbol = configManager.getProperty("resistorSymbol");
    }

}
