package pl.gda.pg.elektronikaodpodstaw.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import org.knowm.jspice.JSpice;
import org.knowm.jspice.netlist.*;
import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPoint;
import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPointResult;
import pl.gda.pg.elektronikaodpodstaw.levels.Level;
import pl.gda.pg.elektronikaodpodstaw.levels.LevelManager;
import pl.gda.pg.elektronikaodpodstaw.levels.Levels;
import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import pl.gda.pg.elektronikaodpodstaw.simulation.*;

/**
 * Represents the simulation stage panel in the game.
 * The panel enables players to build electrical circuits according to the task requirements.
 * It supports interactive features, allowing players to add, edit, remove elements or validate circuit.
 */
public class SimulationStagePanel extends JPanel {

    /** The background image displayed in the simulation stage panel. */
    private final Image backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(MainFrame.currentBackground))).getImage();

    /** A stack for redo operations during element manipulation. */
    private final Stack<Component> redoStack;

    /** A stack for tracking user actions performed in the simulation. */
    private final Stack<Element> actions = new Stack<>();

    /** The correct parameters required for the simulation. */
    private final String correctParams;

    /** The theory content associated with the current level. */
    private final String theory;

    /** The task text for the simulation stage. */
    private final String question;

    /** A list of buttons available in the simulation toolbar. */
    private final List<HoverButton> buttons = new ArrayList<>();

    /** The toolbar component used in the simulation stage. */
    private final JPanel toolbar;

    /** Indicates whether the current simulation configuration is correct. */
    private boolean isCorrect = false;

    /** Indicates whether the delete mode is active. */
    private boolean isDeleteMode = false;

    /** The currently selected element in the simulation. */
    private String selectedElement = null;

    /** The starting point of element which is being created. */
    private Point startPoint = null;

    /** The ending point of element which is being created. */
    private Point endPoint = null;

    /** A list of all elements currently in the simulation area. */
    private final List<Element> elements = new ArrayList<>();

    /** A list of points used for snapping elements in the simulation area. */
    private final List<Point> snapPoints = new ArrayList<>();

    /**
     * Constructs the SimulationStagePanel with the specified parameters.
     *
     * @param frame the main application frame.
     * @param question the question text for the simulation stage.
     * @param correctParams the correct parameters for the simulation validation.
     * @param theory the theory content associated with the current level.
     */
    public SimulationStagePanel(MainFrame frame, String question, String correctParams, String theory) {
        elements.clear();
        this.correctParams = correctParams;
        this.theory = theory;
        this.question = question;
        setLayout(new BorderLayout());
        redoStack = new Stack<>();
        Element.resetAllCounters();
        initializeElements(MainFrame.currentLevel);
        toolbar = createToolbar(frame);
        add(toolbar, BorderLayout.NORTH);

        JPanel simulationArea = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPoints(g);

                for (Element element : elements) {
                    element.draw(g);
                    if (element instanceof Wire) {
                        continue;
                    }

                    if (MainFrame.currentLevel == 5 && LevelManager.currentStageIndex ==7) {
                        if (!(element instanceof Resistor) && !(element instanceof VoltageSource))
                            element.drawValue(g);
                    } else
                        element.drawValue(g);
                }
            }
        };
        setupKeyBindings(simulationArea, frame);
        simulationArea.setLayout(null);
        simulationArea.setOpaque(false);

        simulationArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isDeleteMode)
                    startPoint = getNearestSnapPoint(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isDeleteMode) {
                    endPoint = getNearestSnapPoint(e.getPoint());

                    if (isValidConnection(startPoint, endPoint, frame) && selectedElement != null) {
                        Element createElement = createElement(selectedElement, startPoint, endPoint);
                        addElement(createElement);
                        repaint();
                    }

                    startPoint = null;
                    endPoint = null;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (isDeleteMode) {
                    deleteElement(e.getPoint(), frame);
                }
                if (MainFrame.currentLevel > 1) {
                    changeValue(e.getPoint(), frame);
                }
            }
        });

        simulationArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                repaint();
            }
        });

        add(simulationArea, BorderLayout.CENTER);
        revalidate();

        SwingUtilities.invokeLater(() -> {
            if (LevelManager.getCurrentStageIndex() == 0 && !(question.isEmpty())) {
                Level currentLevel = Levels.levelsList.get(MainFrame.currentLevel - 1);
                String levelTitle = currentLevel.name();
                JOptionPane.showMessageDialog(frame, levelTitle, "Poziom " + MainFrame.currentLevel, JOptionPane.INFORMATION_MESSAGE);
                MainFrame.showTheory(frame, theory);
                MainFrame.showQuestion(frame, question);
            } else if (!(question.isEmpty())) {
                MainFrame.showQuestion(frame, question);
            }
        });
    }

    /**
     * Creates and configures the toolbar for the simulation stage.
     *
     * @param frame the main application frame.
     * @return the configured toolbar panel.
     */
    private JPanel createToolbar(MainFrame frame) {
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BorderLayout());
        toolbar.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);

        JLabel levelLabel = getjLabel();
        titlePanel.add(levelLabel);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 8, 10, 10));
        gridPanel.setOpaque(false);

        String[] controlOptions = {"back", "undo", "redo", "delete", "clear", "help", "quest", "simulate"};
        String[] controlIcons = {"/icons/back.png", "/icons/undo.png", "/icons/redo.png", "/icons/delete.png", "/icons/clear.png", "/icons/help.png", "/icons/quest.png", "/icons/simulate.png"};

        String[] elementOptions = {"V", "I", "voltmeter", "ammeter", "R", "C", "L", "wire"};
        String[] elementIcons = {"/icons/V.png", "/icons/I.png", "/icons/voltmeter.png", "/icons/ammeter.png", "/icons/R_IEC.png", "/icons/C.png", "/icons/L.png", "/icons/wire.png"};
        if (MainFrame.currentResistorSymbol.equals("ANSI"))
            elementIcons[4] = "icons/R_ANSI.png";

        for (int i = 0; i < controlOptions.length; i++) {
            HoverButton button = getHoverButton(frame, controlOptions[i], controlIcons[i]);
            buttons.add(button);
            gridPanel.add(button);
        }

        for (int i = 0; i < elementOptions.length; i++) {
            HoverButton button = getHoverButton(frame, elementOptions[i], elementIcons[i]);
            buttons.add(button);
            gridPanel.add(button);
        }

        toolbar.add(titlePanel, BorderLayout.NORTH);
        toolbar.add(gridPanel, BorderLayout.CENTER);

        return toolbar;
    }


    /**
     * Creates and configures a `JLabel` to display the current level's title or a default label for free simulation mode.
     * Adjusts font size and styling based on the level.
     *
     * @return a configured `JLabel` instance representing the current level title or "Free Simulation".
     */
    private JLabel getjLabel() {
        Level currentLevel = Levels.levelsList.get(MainFrame.currentLevel - 1);

        String levelTitle = currentLevel.name();
        if(question.isEmpty()) {
            levelTitle = "Wolna symulacja";
        }
        JLabel levelLabel = new JLabel(levelTitle);
        if (MainFrame.currentLevel == 3 || MainFrame.currentLevel == 5 || MainFrame.currentLevel == 7 || MainFrame.currentLevel == 11)
            levelLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        else
            levelLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        levelLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        levelLabel.setForeground(MainFrame.getTextTheme());
        return levelLabel;
    }

    /**
     * Creates and configures a `HoverButton` with the specified action, icon, and behavior.
     * The button is styled dynamically based on the provided parameters and is associated with
     * specific actions depending on its purpose.
     *
     * @param frame the main application frame, used for context and action handling.
     * @param option the action command associated with the button.
     * @param iconPath the file path to the icon image used for the button.
     * @return a fully configured `HoverButton` instance.
     */
    private HoverButton getHoverButton(MainFrame frame, String option, String iconPath) {
        HoverButton button = new HoverButton();
        button.setActionCommand(option);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
        icon = recolorIcon(icon,MainFrame.getTextTheme());
        Image scaledIcon;
        if(option.equals("R") ||option.equals("C") || option.equals("L") || option.equals("wire") || option.equals("V") || option.equals("I") || option.equals("voltmeter") || option.equals("ammeter")) {
            scaledIcon = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            button.addActionListener(_ -> setSelectedElement(option, button));
        } else if (option.equals("delete")) {
            button.addActionListener(_ -> setSelectedElement(option, button));
            scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        }
        else
            scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledIcon));

        button.addActionListener(_ -> {
            switch (option) {
                case "back" -> {
                    CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
                    cl.show(frame.getContentPane(), "MainMenu");
                    LevelManager.currentStageIndex = 0;
                }
                case "undo" -> undo();
                case "redo" -> redo();
                case "clear" -> clear(frame);
                case "help" -> MainFrame.showTheory(frame, theory);
                case "quest" -> MainFrame.showQuestion(frame, question);
                case "simulate" -> simulate(frame);
            }
        });
        return button;
    }

    /**
     * Recolors an existing `ImageIcon` by replacing all visible pixels with the specified color.
     * Preserves transparency in the icon.
     *
     * @param icon the original `ImageIcon` to be recolored.
     * @param newColor the new color to apply to the icon's visible pixels.
     * @return a new `ImageIcon` instance with the recolored image.
     */
    private static ImageIcon recolorIcon(ImageIcon icon, Color newColor) {
        Image image = icon.getImage();

        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgba = bufferedImage.getRGB(x, y);
                Color originalColor = new Color(rgba, true);

                if (originalColor.getAlpha() > 0)
                    bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return new ImageIcon(bufferedImage);
    }

    /**
     * Draws snap points on the simulation area and populates the list of available snap points.
     * Snap points help align elements in the simulation to predefined grid positions.
     *
     * @param g the Graphics context used for rendering the snap points.
     */
    private void drawPoints(Graphics g) {
        g.setColor(MainFrame.getTextTheme());

        int availableWidth = getWidth() - 80;
        int availableHeight = getHeight() - toolbar.getHeight() - 80;
        int rows = 4;
        int cols = 7;

        int xPadding = availableWidth / (cols - 1);
        int yPadding = availableHeight / (rows - 1);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = 40 + col * xPadding;
                int y = 50 + row * yPadding;
                int pointSize = 10;

                g.fillOval(x - pointSize / 2, y - pointSize / 2, pointSize, pointSize);
                snapPoints.add(new Point(x, y));
            }
        }
    }

    /**
     * Finds the nearest snap point to the specified point within a defined threshold distance.
     *
     * @param p the point to which the nearest snap point is to be determined.
     * @return the nearest snap point if within the threshold distance, or {@code null} if no snap point is close enough.
     */
    private Point getNearestSnapPoint(Point p) {
        Point nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Point snapPoint : snapPoints) {
            double distance = p.distance(snapPoint);
            if (distance < minDistance && distance < 50) {

                nearest = snapPoint;
                minDistance = distance;
            }
        }
        return nearest;
    }

    /**
     * Validates whether a connection between two points in the simulation is valid.
     * Ensures the connection adheres to rules such as orientation, distance, and element placement restrictions.
     *
     * @param start the starting point of the connection.
     * @param end the ending point of the connection.
     * @param frame the main application frame, used for displaying warning dialogs.
     * @return {@code true} if the connection is valid; {@code false} otherwise.
     */
    private boolean isValidConnection(Point start, Point end, MainFrame frame) {
        if (start == null || end == null || start.equals(end))
            return false;

        if (selectedElement == null) {
            JOptionPane.showMessageDialog(frame, "Nie wybrano elementu.", "Uwaga", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        boolean isHorizontal = start.y == end.y;
        boolean isVertical = start.x == end.x;

        if (!isHorizontal && !isVertical) {
            JOptionPane.showMessageDialog(frame, "Tylko prostopadłe połączenia.", "Uwaga", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int distance = isHorizontal ? Math.abs(start.x - end.x) : Math.abs(start.y - end.y);
        if (distance > 180) {
            JOptionPane.showMessageDialog(frame, "Połączenia tylko między najbliższymi punktami.", "Uwaga", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        for (Element element : elements) {
            if ( (element.start.equals(start) && element.end.equals(end)) || (element.start.equals(end) && element.end.equals(start)) ) {
                JOptionPane.showMessageDialog(frame, "Tu już znajduje się element.", "Uwaga", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (MainFrame.currentLevel > 1 && MainFrame.currentLevel != 9) {
                if (((element.start.x == 40 || element.end.x == 40) && (start.x == 194 || end.x == 194)
                        && (element.start.y == start.y || element.start.y == end.y) && (element.end.y == start.y || element.end.y == end.y) && element.isVertical) ||
                        (element.start.x == 194 || element.end.x == 194) && (start.x == 40 || end.x == 40)
                                && (element.start.y == start.y || element.start.y == end.y) && (element.end.y == start.y || element.end.y == end.y) && element.isVertical) {
                    JOptionPane.showMessageDialog(frame, "Nie można dodać elementu w tym miejscu, ze względu na możliwość wystąpienia słabej czytelności wartości elementu.", "Uwaga", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Configures keyboard shortcuts for the simulation area.
     * Binds key presses to specific actions, such as adding elements, performing undo/redo operations,
     * switching modes, and controlling the simulation flow.
     *
     * @param simulationArea the panel representing the simulation area where key bindings are applied.
     * @param frame the main application frame, used for navigating and displaying dialogs.
     */
    private void setupKeyBindings(JPanel simulationArea, MainFrame frame) {
        InputMap inputMap = simulationArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = simulationArea.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
        actionMap.put("back", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
                cl.show(frame.getContentPane(), "MainMenu");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control Z"), "undo");
        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control shift Z"), "redo");
        actionMap.put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("H"), "showTheory");
        actionMap.put("showTheory", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.showTheory(frame, theory);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("Q"), "showQuestion");
        actionMap.put("showQuestion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.showQuestion(frame, question);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("DELETE"), "clear");
        actionMap.put("clear", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(frame);
            }
        });
        inputMap.put(KeyStroke.getKeyStroke("D"), "deleteMode");
        actionMap.put("deleteMode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("delete");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "continue");
        actionMap.put("continue", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isCorrect)
                    MainFrame.levelManager.nextStage(frame);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("S"), "simulate");
        actionMap.put("simulate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulate(frame);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("R"), "resistor");
        actionMap.put("resistor", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("R");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("C"), "capacitor");
        actionMap.put("capacitor", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("C");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("L"), "inductor");
        actionMap.put("inductor", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("L");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("V"), "voltageSource");
        actionMap.put("voltageSource", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("V");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("I"), "currentSource");
        actionMap.put("currentSource", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("I");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("B"), "voltmeter");
        actionMap.put("voltmeter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("voltmeter");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("A"), "ammeter");
        actionMap.put("ammeter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("ammeter");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("W"), "wire");
        actionMap.put("wire", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedElementByAction("wire");
            }
        });
    }

    /**
     * Updates the selected element type and highlights the corresponding button in the toolbar.
     * Activates or deactivates delete mode based on the selected option.
     *
     * @param option the type of element selected.
     * @param selectedButton the button corresponding to the selected option.
     */
    private void setSelectedElement(String option, HoverButton selectedButton) {
        for (HoverButton button : buttons) {
            if (button.getActionListeners().length > 0)
                button.setSelected(false);
        }
        selectedButton.setSelected(true);
        selectedElement = option;
        isDeleteMode = selectedElement.equals("delete");
    }

    /**
     * Updates the selected element and highlights the corresponding button in the toolbar based on the specified action command.
     * If no matching button is found, clears the current selection.
     *
     * @param action the action command associated with the button to be selected.
     */
    private void setSelectedElementByAction(String action) {
        for (HoverButton button : buttons) {
            if (button.getActionCommand() != null && button.getActionCommand().equals(action)) {
                setSelectedElement(action, button);
                return;
            } else {
                button.setSelected(false);
                selectedElement = null;
            }
        }
    }

    /**
     * Deletes an element from the simulation plane based on the given click point.
     * If an element is within a 60-pixel radius of the click point, it is removed
     * from the plane. If no element is found, a warning dialog is displayed.
     *
     * @param clickPoint the point where the user clicked to delete an element.
     * @param frame the main application frame, used for displaying warning dialogs.
     */
    private void deleteElement(Point clickPoint, MainFrame frame) {
        Element toRemove = null;
        for (Element element : elements) {
            if (Math.abs(clickPoint.x - element.midX) <= 60 && Math.abs(clickPoint.y - element.midY) <= 60) {
                toRemove = element;
                break;
            }
        }
        if (toRemove != null) {
            elements.remove(toRemove);
            redoStack.clear();
            actions.push(toRemove);
            repaint();
        } else
            JOptionPane.showMessageDialog(frame, "Nie wybrano elementu do usunięcia.", "Uwaga", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Creates a new circuit element based on the provided type, start point and end point.
     * The type of element is determined by the `option` parameter, and the element
     * is defined by its start and end points.
     *
     * @param option the type of element to create.
     * @param start the starting point of the element in the simulation area.
     * @param end the ending point of the element in the simulation area.
     * @return the created circuit element, or {@code null} if the option does not match any type.
     */
    private Element createElement (String option, Point start, Point end) {
        Element element = null;

        switch (option) {
            case "R" -> element = new Resistor(start, end);
            case "C" -> element = new Capacitor(start, end);
            case "L" -> element = new Inductor(start, end);
            case "V" -> element = new VoltageSource(start, end);
            case "I" -> element = new CurrentSource(start, end);
            case "voltmeter" -> element = new Voltmeter(start, end);
            case "ammeter" -> element = new Ammeter(start, end);
            case "wire" -> element = new Wire(start, end);
        }
        return element;
    }

    /**
     * Adds a new element to the simulation area.
     *
     * @param element the element to add.
     */
    private void addElement(Element element) {
        elements.add(element);
        actions.push(element);
        redoStack.clear();
        repaint();
    }

    /**
     * Restores the last action related to adding an item.
     * Moves the reverted action to the redo stack.
     */
    private void undo() {
        if (!actions.isEmpty()) {
            Element lastAction = actions.pop();
            redoStack.push(lastAction);
            if (elements.contains(lastAction))
                elements.remove(lastAction);
            else
                elements.add(lastAction);
            repaint();
        }
    }

    /**
     * Re-applies the last undone action.
     * Moves the redone action back to the actions stack.
     */
    private void redo() {
        if (!redoStack.isEmpty()) {
            Element lastRedo = (Element) redoStack.pop();
            actions.push(lastRedo);
            if (elements.contains(lastRedo))
                elements.remove(lastRedo);
            else
                elements.add(lastRedo);
            repaint();
        }
    }

    /**
     * Changes the value of a circuit element selected by the user.
     * Prompts the user to input a new value for the element, validates the input,
     * and updates the element's value if valid.
     *
     * @param clickPoint the point where the user clicked to select an element to change its value.
     * @param frame the main application frame, used for displaying dialogs.
     */
    private void changeValue(Point clickPoint, MainFrame frame) {
        if (isDeleteMode || MainFrame.currentLevel == 6 || (MainFrame.currentLevel == 5 && LevelManager.currentStageIndex == 7))
            return;
        Element toChange = null;
        for (Element element : elements) {
            if (Math.abs(clickPoint.x - element.midX) <= 60 && Math.abs(clickPoint.y - element.midY) <= 60) {
                setSelectedElementByAction(null);
                toChange = element;
                break;
            }
        }
        if (!(toChange instanceof Voltmeter) && !(toChange instanceof Ammeter) && !(toChange instanceof Wire) && toChange != null) {
            String newValueStr = JOptionPane.showInputDialog(this, "Wprowadź nową wartość dla elementu:", "Zmień wartość", JOptionPane.PLAIN_MESSAGE);
            try {
                if (newValueStr != null) {
                    float newValue = Element.UnitToValue(newValueStr);
                    if (newValue < 0)
                        JOptionPane.showMessageDialog(frame, "Wartość nie może być ujemna.", "Błąd", JOptionPane.WARNING_MESSAGE);
                    else {
                        toChange.setElementValue(newValue);
                        repaint();
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format wartości.", "Błąd", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Changes the values of elements during the automatic circuit creation process required in certain levels.
     *
     * @param value the new value to assign to the element, required for the specified circuit.
     * @param clickPoint the point where the element to be set is located.
     */
    private void InitChangeValue(Point clickPoint, String value) {
        setSelectedElementByAction(null);
        Element toChange = null;
        for (Element element : elements) {
            if (Math.abs(clickPoint.x - element.midX) <= 60 && Math.abs(clickPoint.y - element.midY) <= 60) {
                toChange = element;
                break;
            }
        }
        float newValue = Element.UnitToValue(value);
        assert toChange != null;
        toChange.setElementValue(newValue);
        repaint();
    }

    /**
     * Clears all elements and actions in the simulation stage.
     * Resets counters, clears stacks, and refreshes the simulation area.
     * Displays a warning message if no elements are present to clear.
     *
     * @param frame the main application frame, used for displaying warning dialogs.
     */
    private void clear(MainFrame frame) {
        if (elements.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Brak elementów.", "Błąd", JOptionPane.WARNING_MESSAGE);
        } else {
            Element.resetAllCounters();
            elements.clear();
            actions.clear();
            redoStack.clear();
            repaint();
            setSelectedElementByAction(null);
        }
    }

    /**
     * Simulates the circuit by assigning node numbers and verifying the circuit's correctness.
     * Outputs debugging information and simulation status to the console.
     *
     * @param frame the main application frame, used for displaying dialogs and messages.
     */
    private void simulate(MainFrame frame) {
        for (int i = 0; i < 5 ; i++)
            System.out.println();
        System.out.println("--------SYMULACJA------------");
        assignNodes();
        checkCircuit(frame);
    }

    /**
     * Assigns unique node numbers to all points in the circuit based on their connectivity.
     * Ensures consistent node mapping for each element, leveraging wires to establish connections between elements,
     * thereby assigning appropriate node numbers to the components.
     */
    private void assignNodes() {
        Map<Point, Integer> nodeMap = new HashMap<>();
        int currentNode = 0;

        for (Element element : elements) {
            Point start = element.start;
            Point end = element.end;
            if (element instanceof Wire) {
                if (nodeMap.containsKey(start) && nodeMap.containsKey(end)) {
                    int targetNode = nodeMap.get(end);
                    int sourceNode = nodeMap.get(start);
                    if (sourceNode != targetNode)
                        propagateNode(nodeMap, sourceNode, targetNode);
                    element.nodePlus = targetNode;
                    element.nodeMinus = targetNode;
                }
                else if (nodeMap.containsKey(start)) {
                    int node = nodeMap.get(start);
                    nodeMap.put(end, node);
                    element.nodePlus = node;
                    element.nodeMinus = node;
                } else if (nodeMap.containsKey(end)) {
                    int node = nodeMap.get(end);
                    nodeMap.put(start, node);
                    element.nodePlus = node;
                    element.nodeMinus = node;
                }
                else {
                    nodeMap.put(start, currentNode);
                    nodeMap.put(end, currentNode);
                    element.nodePlus = currentNode;
                    element.nodeMinus = currentNode;
                    currentNode++;
                }
            } else {
                if (!nodeMap.containsKey(start))
                    nodeMap.put(start, currentNode++);
                if (!nodeMap.containsKey(end))
                    nodeMap.put(end, currentNode++);
                element.nodeMinus = nodeMap.get(start);
                element.nodePlus = nodeMap.get(end);
            }
        }
        System.out.println();
        System.out.println("---WĘZŁY------------");
        for (Element element : elements) {
            if (!(element instanceof Wire))
                System.out.println(element.getElementName() + " " + element.nodePlus + " " + element.nodeMinus);
        }
        System.out.println("--------------------");
    }

    /**
     * Propagates node changes throughout the circuit to ensure consistent node assignments.
     * Updates the node map and reassigns nodes for connected elements, minimizing node discrepancies.
     *
     * @param nodeMap a map associating points in the circuit with their corresponding node numbers.
     * @param sourceNode the node number to propagate.
     * @param targetNode the node number to replace with the source node.
     */
    private void propagateNode(Map<Point, Integer> nodeMap, int sourceNode, int targetNode) {
        int newTargetNode = Math.min(sourceNode, targetNode);
        int oldNode = Math.max(sourceNode, targetNode);
        
        Queue<Point> pointsToVisit = new LinkedList<>();
        
        for (Map.Entry<Point, Integer> entry : nodeMap.entrySet()) {
            if (entry.getValue() == oldNode) {
                pointsToVisit.add(entry.getKey());
                entry.setValue(newTargetNode);
            }
        }
        
        while (!pointsToVisit.isEmpty()) {
            Point currentPoint = pointsToVisit.poll();

            for (Element element : elements) {
                boolean isStartPoint = element.start.equals(currentPoint);
                boolean isEndPoint = element.end.equals(currentPoint);

                if (isStartPoint || isEndPoint) {
                    if (!element.start.equals(element.end)) {
                        if (isStartPoint && nodeMap.containsKey(element.start) && nodeMap.get(element.start) == oldNode) {
                            nodeMap.put(element.start, newTargetNode);
                            pointsToVisit.add(element.start);
                        }

                        if (isEndPoint && nodeMap.containsKey(element.end) && nodeMap.get(element.end) == oldNode) {
                            nodeMap.put(element.end, newTargetNode);
                            pointsToVisit.add(element.end);
                        }
                    }
                    
                    if (isStartPoint && element.nodeMinus == oldNode) {
                        element.nodeMinus = newTargetNode;
                    }
                    if (isEndPoint && element.nodePlus == oldNode) {
                        element.nodePlus = newTargetNode;
                    }
                }
            }
        }
    }

    /**
     * Verifies and simulates the circuit to check its correctness.
     * Validates the element counts and parameters against the requirements, simulates the circuit,
     * and updates the element values based on the simulation results.
     *
     * @param frame the main application frame, used for displaying dialogs and messages.
     */
    private void checkCircuit(MainFrame frame) {
        isCorrect = false;
        if (!(question.isEmpty())) {
            if (!verifyElementCounts(correctParams, frame))
                return;
        }

        NetlistBuilder builder = getNetlistBuilder();
        Netlist netlist = getSimulate(builder, frame);
        repaint();
        if (netlist != null)
            getValues(netlist);
        else
            return;

        System.out.println();
        for (Element element : elements) {
            if (!(element instanceof Wire))
                System.out.println(element.elementName + " = " + element.getElementValue()+ "   V = " + element.voltage + "   I = " + element.current);
        }

        if (!(question.isEmpty())) {
            if (!verifyElementParameters(correctParams, frame))
                return;
            else {
                isCorrect = true;
                JOptionPane.showMessageDialog(frame, "Obwód poprawny. Naciśnij Enter aby kontynuować.", "Zadanie ukończone", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else
            JOptionPane.showMessageDialog(frame, "Symulacja zakończona.", "Obwód poprawny", JOptionPane.INFORMATION_MESSAGE);
        repaint();
    }

    /**
     * Constructs a `NetlistBuilder` instance based on the elements present in the simulation.
     * Maps each type of element to its corresponding netlist representation.
     *
     * @return a configured `NetlistBuilder` containing all elements from the simulation.
     */
    private NetlistBuilder getNetlistBuilder() {
        NetlistBuilder builder = new NetlistBuilder();
        for (Element element : elements) {
            if (element instanceof VoltageSource)
                builder.addNetlistDCVoltage(element.elementName, element.getElementValue(), String.valueOf(element.nodePlus), String.valueOf(element.nodeMinus));
            if (element instanceof Resistor)
                builder.addNetlistResistor(element.elementName, element.getElementValue(), String.valueOf(element.nodePlus), String.valueOf(element.nodeMinus));
            if (element instanceof CurrentSource)
                builder.addNetlistDCCurrent(element.elementName, element.getElementValue(), String.valueOf(element.nodePlus), String.valueOf(element.nodeMinus));
            if (element instanceof Capacitor)
                builder.addNetlistCapacitor(element.elementName, element.getElementValue(), String.valueOf(element.nodePlus), String.valueOf(element.nodeMinus));
            if (element instanceof Inductor)
                builder.addNetlistResistor(element.elementName, 1e-11, String.valueOf(element.nodePlus), String.valueOf(element.nodeMinus));
            if (element instanceof Voltmeter)
                builder.addNetlistCapacitor(element.elementName, element.getElementValue(), String.valueOf(element.nodePlus), String.valueOf(element.nodeMinus));
            if (element instanceof Ammeter)
                builder.addNetlistResistor(element.elementName, 1e-11, String.valueOf(element.nodePlus), String.valueOf(element.nodeMinus));
        }
        return builder;
    }

    /**
     * Builds and simulates a netlist using the provided NetlistBuilder.
     * Handles various error scenarios that might occur during the netlist building
     * or simulation process and displays appropriate error messages.
     *
     * @param builder the NetlistBuilder used to construct the netlist.
     * @param frame the main application frame, used for displaying error dialogs.
     * @return the constructed and simulated netlist, or {@code null} if an error occurs.
     */
    private Netlist getSimulate(NetlistBuilder builder, MainFrame frame) {
        String message;
        Netlist netlist;
        try {
            netlist = builder.build();
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            if (message.contains("A component cannot be connected to the same node twice!"))
                JOptionPane.showMessageDialog(frame, "W obwodzie występuje zwarcie.", "Błąd symulacji", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            JSpice.simulate(netlist);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            if (message.contains("Must have at least 2 NetListParts!"))
                JOptionPane.showMessageDialog(frame, "Obwód musi zawierać co najmniej dwa elementy.", "Błąd symulacji", JOptionPane.ERROR_MESSAGE);
            else if (message.startsWith("Must have at least 2 Connections for node"))
                JOptionPane.showMessageDialog(frame, "W obwodzie występują niepodłączone elementy.", "Błąd symulacji", JOptionPane.ERROR_MESSAGE);
            else if (message.contains("Current sources cannot be in series!"))
                JOptionPane.showMessageDialog(frame, "Źródła prądowe nie mogą być połączone szeregowo!", "Błąd symulacji", JOptionPane.ERROR_MESSAGE);
            else if (message.contains("Voltage sources cannot be in parallel!"))
                JOptionPane.showMessageDialog(frame, "Źródła napięciowe nie mogą być połączone równolegle!", "Błąd symulacji", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, "Źródła prądowe nie mogą być połączone szeregowo!", "Błąd symulacji", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Wystąpił nieoczekiwany błąd.", "Błąd symulacji", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return netlist;
    }

    /**
     * Computes and assigns voltage and current values for elements in the circuit based on the provided netlist.
     * Extracts results from the DC operating point analysis and updates the properties of each element accordingly.
     * Fixes the computed values to account for the use of very low-resistance resistors as ammeters.
     *
     * @param netlist the netlist representing the electrical circuit configuration.
     */
    private void getValues(Netlist netlist) {
        DCOperatingPoint dcOperatingPoint = new DCOperatingPoint(netlist);
        DCOperatingPointResult result = dcOperatingPoint.run();

        Map<String, Double> nodeVoltages = new HashMap<>();
        Map<String, Double> sourceCurrents = new HashMap<>();
        Map<String, Double> elementCurrents = result.getDeviceLabels2Value();

        for (Map.Entry<String, Double> entry : result.getNodeLabels2Value().entrySet()) {
            if (entry.getKey().startsWith("V("))
                nodeVoltages.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Double> entry : result.getNodeLabels2Value().entrySet()) {
            if (entry.getKey().startsWith("I("))
                sourceCurrents.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Double> entry : result.getDeviceLabels2Value().entrySet()) {
            if (entry.getValue() == null)
                elementCurrents.put(entry.getKey(), (double) 0);
        }

        for (Element element : elements) {
            if (!(element instanceof Wire)) {
                double voltagePlus = nodeVoltages.getOrDefault("V(" + element.nodePlus + ")", 0.0);
                double voltageMinus = nodeVoltages.getOrDefault("V(" + element.nodeMinus + ")", 0.0);
                float value;

                if (element instanceof Resistor)
                    value = Math.abs((float) (voltagePlus - voltageMinus));
                else
                    value = (float) (voltagePlus - voltageMinus);
                element.voltage = Math.round(value * 100) / 100f;

                if (element instanceof VoltageSource) {
                    value = sourceCurrents.getOrDefault("I(" + element.elementName + ")", 0.0).floatValue();
                    element.current = Math.round(value * 1000) / 1000f;
                } else if (element instanceof Resistor || element instanceof Inductor) {
                    value = Math.abs(elementCurrents.getOrDefault("I(" + element.elementName + ")", 0.0).floatValue());
                    element.current = Math.round(value * 1000) / 1000f;
                } else if (element instanceof Voltmeter || element instanceof Ammeter || element instanceof CurrentSource) {
                    value = elementCurrents.getOrDefault("I(" + element.elementName + ")", 0.0).floatValue();
                    element.current = Math.round(value * 1000) / 1000f;
                }
            }
        }
    }

    /**
     * Parses the `correctParams` string into a structured map of element requirements.
     * The resulting map contains element types as keys and a list of their required properties or counts.
     *
     * @param correctParams a string representing the required elements and their properties or counts.
     *                      Format:
     *                      - For elements with properties: "Resistor: (V=5,I=0.01)(V=10,I=0.02)"
     *                      - For elements with counts: "Capacitor: 3"
     * @return a map where keys are element types and values are lists of required properties (or empty maps for count-based elements).
     * @throws IllegalArgumentException if the input format is invalid.
     */
    private Map<String, List<Map<String, Float>>> parseCorrectParams(String correctParams) {
        Map<String, List<Map<String, Float>>> requiredElements = new HashMap<>();
        Map<String, Integer> requiredCounts = new HashMap<>();

        String[] elementGroups = correctParams.split("\\s*\\n\\s*");
        for (String group : elementGroups) {
            if (group.trim().isEmpty())
                continue;

            String[] parts = group.split(":");
            if (parts.length != 2)
                throw new IllegalArgumentException("Nieprawidłowy format w correctParams: \"" + group + "\". Oczekiwano dwukropka (:).");

            String type = parts[0].trim();
            String details = parts[1].trim();

            if (type.equals("Resistor") || type.equals("Voltmeter") || type.equals("Ammeter")) {
                String[] instances = details.split("\\)\\(");
                instances[0] = instances[0].replace("(", "");
                instances[instances.length - 1] = instances[instances.length - 1].replace(")", "");

                List<Map<String, Float>> valuesList = getMaps(instances);

                valuesList.forEach(map -> {
                    Map<String, Float> cleanedMap = new HashMap<>();
                    map.forEach((key, value) -> {
                        String cleanedKey = key.replaceAll("\\d+", "");
                        cleanedMap.put(cleanedKey, value);
                    });
                    map.clear();
                    map.putAll(cleanedMap);
                });

                requiredElements.put(type, valuesList);
            } else {
                try {
                    int count = Integer.parseInt(details);
                    requiredCounts.put(type, count);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Nieprawidłowy format liczby elementów w correctParams: \"" + details + "\".");
                }
            }
        }

        for (Map.Entry<String, Integer> entry : requiredCounts.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            List<Map<String, Float>> placeholderList = new ArrayList<>();
            for (int i = 0; i < count; i++)
                placeholderList.add(new HashMap<>());
            requiredElements.put(type, placeholderList);
        }

        requiredElements.forEach((_, value) -> value.removeIf(Map::isEmpty));

        return requiredElements;
    }

    /**
     * Parses an array of instance definitions into a list of maps.
     * Each map represents the properties of an instance with keys as property names
     * and values as their corresponding float values.
     *
     * @param instances an array of strings, where each string represents an instance
     *                  with properties in the format "key1=value1,key2=value2".
     * @return a list of maps, where each map contains the properties of an instance.
     * @throws IllegalArgumentException if the input format for any instance is invalid.
     */
    private static List<Map<String, Float>> getMaps(String[] instances) {
        List<Map<String, Float>> valuesList = new ArrayList<>();
        for (String instance : instances) {
            if (instance.trim().isEmpty())
                continue;

            String[] properties = instance.split(",");
            Map<String, Float> values = new HashMap<>();
            for (String property : properties) {
                String[] keyValue = property.split("=");
                if (keyValue.length != 2)
                    throw new IllegalArgumentException("Nieprawidłowy format parametrów dla elementów w correctParams");
                values.put(keyValue[0].trim(), Float.parseFloat(keyValue[1].trim()));
            }
            valuesList.add(values);
        }
        return valuesList;
    }

    /**
     * Verifies whether the circuit contains the correct number and types of elements as specified in the requirements.
     * Displays an error dialog if the counts do not match the required configuration.
     *
     * @param correctParams a string representing the required element counts, formatted as "ElementType: Count".
     *                      Each element type and its required count should be on a new line.
     * @param frame the main application frame, used for displaying error dialogs.
     * @return {@code true} if the circuit satisfies the requirements; {@code false} otherwise.
     */
    private boolean verifyElementCounts(String correctParams, MainFrame frame) {
        Map<String, Integer> requiredCounts = new HashMap<>();
        
        String[] elementGroups = correctParams.split("\\s*\\n\\s*");
        for (String group : elementGroups) {
            String[] parts = group.split(":");
            String type = parts[0].trim();
            int count = Integer.parseInt(parts[1].split("\\(")[0].trim());
            requiredCounts.put(type, count);
        }
        
        Map<String, Integer> actualCounts = new HashMap<>();
        for (Element element : elements) {
            String type = element.getClass().getSimpleName();
            if (type.equals("Wire")) {
                continue;
            }
            actualCounts.put(type, actualCounts.getOrDefault(type, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : requiredCounts.entrySet()) {
            String type = entry.getKey();
            int requiredCount = entry.getValue();
            int actualCount = actualCounts.getOrDefault(type, 0);

            if (actualCount != requiredCount) {
                JOptionPane.showMessageDialog(frame, "Niewłaściwa liczba elementów wymaganych elementów.", "Błąd weryfikacji wymaganych elementów", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        for (String type : actualCounts.keySet()) {
            if (!requiredCounts.containsKey(type)) {
                JOptionPane.showMessageDialog(frame, "Obwód zawiera niewłaściwe elementy", "Błąd weryfikacji wymaganych elementów", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies whether the parameters of the elements in the circuit match the specified requirements.
     * Compares the actual properties of elements in the simulation with the required properties
     * defined in the `correctParams` string.
     * Displays an error dialog if any mismatch is found.
     *
     * @param correctParams a string representing the required properties of elements, formatted
     *                      with types and their parameters (e.g., "Resistor: V=5,I=0.01").
     * @param frame the main application frame, used for displaying error dialogs.
     * @return {@code true} if all element parameters match the requirements; {@code false} otherwise.
     */
    private boolean verifyElementParameters(String correctParams, MainFrame frame) {
        Map<String, List<Map<String, Float>>> requiredElements = parseCorrectParams(correctParams);
        Map<String, List<Element>> groupedElements = new HashMap<>();

        for (Element element : elements) {
            String type = element.getClass().getSimpleName();
            if (!type.equals("Wire")) {
                groupedElements.putIfAbsent(type, new ArrayList<>());
                groupedElements.get(type).add(element);
            }
        }

        for (Map.Entry<String, List<Map<String, Float>>> requiredEntry : requiredElements.entrySet()) {
            String type = requiredEntry.getKey();
            List<Map<String, Float>> requiredInstances = requiredEntry.getValue();
            List<Element> actualElements = groupedElements.getOrDefault(type, new ArrayList<>());

            for (Map<String, Float> requiredProperties : requiredInstances) {
                boolean matchFound = false;
                for (Element element : actualElements) {
                    if (type.equals("Resistor") || type.equals("Voltmeter") || type.equals("Ammeter")) {
                        float voltage = element.voltage;
                        float current = element.current;

                        if (requiredProperties.containsKey("V") && !requiredProperties.get("V").equals(voltage))
                            continue;
                        if (requiredProperties.containsKey("I") && !requiredProperties.get("I").equals(current))
                            continue;
                    }
                    matchFound = true;
                    actualElements.remove(element);
                    break;
                }

                if (!matchFound) {
                    JOptionPane.showMessageDialog(frame, "Otrzymane wyniki symulacji nie są zgodne wymaganiami obwodu.", "Błąd weryfikacji parametrów", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Initializes the elements in some levels.
     *
     * @param currentLevel the current level of the game.
     */
    private void initializeElements(int currentLevel) {
        switch (currentLevel) {
            case 5 -> {
                if (LevelManager.currentStageIndex == 7) {
                    addElement(createElement("V", new Point(194,362), new Point(194, 206)));
                    addElement(createElement("wire", new Point(194,206), new Point(348, 206)));
                    addElement(createElement("wire", new Point(194,362), new Point(348, 362)));
                    addElement(createElement("R", new Point(348,206), new Point(348, 362)));
                    InitChangeValue(new Point(194,300), "10");
                    InitChangeValue(new Point(348,300), "1250");
                }
            }
            case 6 -> {
                if (LevelManager.currentStageIndex == 4) {
                    addElement(createElement("wire", new Point(194,512), new Point(348, 512)));
                    addElement(createElement("wire", new Point(348,512), new Point(502, 512)));
                    addElement(createElement("wire", new Point(502,512), new Point(656, 512)));
                    addElement(createElement("wire", new Point(656,512), new Point(810, 512)));
                    addElement(createElement("wire", new Point(194,50), new Point(348, 50)));
                    addElement(createElement("wire", new Point(348,50), new Point(502, 50)));
                    addElement(createElement("wire", new Point(502,50), new Point(656, 50)));
                    addElement(createElement("wire", new Point(656,50), new Point(810, 50)));
                    addElement(createElement("wire", new Point(194,512), new Point(194, 358)));
                    addElement(createElement("wire", new Point(194,204), new Point(194, 50)));
                    addElement(createElement("wire", new Point(502,512), new Point(502, 358)));
                    addElement(createElement("wire", new Point(502,204), new Point(502, 50)));
                    addElement(createElement("wire", new Point(810,512), new Point(810, 358)));
                    addElement(createElement("wire", new Point(810,204), new Point(810, 50)));
                    addElement(createElement("V", new Point(194,358), new Point(194, 204)));
                    addElement(createElement("R", new Point(502,358), new Point(502, 204)));
                    addElement(createElement("R", new Point(810,358), new Point(810, 204)));
                    InitChangeValue(new Point(194,300), "12");
                    InitChangeValue(new Point(502,300), "500");
                    InitChangeValue(new Point(810,300), "3k");
                }
                if (LevelManager.currentStageIndex == 5) {
                    addElement(createElement("wire", new Point(194,512), new Point(348, 512)));
                    addElement(createElement("wire", new Point(348,512), new Point(502, 512)));
                    addElement(createElement("wire", new Point(502,512), new Point(656, 512)));
                    addElement(createElement("wire", new Point(656,512), new Point(810, 512)));
                    addElement(createElement("wire", new Point(194,204), new Point(348, 204)));
                    addElement(createElement("R", new Point(348,204), new Point(502, 204)));
                    addElement(createElement("wire", new Point(502,204), new Point(656, 204)));
                    addElement(createElement("R", new Point(656,204), new Point(810, 204)));
                    addElement(createElement("V", new Point(194,512), new Point(194, 358)));
                    addElement(createElement("wire", new Point(194,358), new Point(194, 204)));
                    addElement(createElement("R", new Point(502,512), new Point(502, 358)));
                    addElement(createElement("wire", new Point(502,358), new Point(502, 204)));
                    addElement(createElement("R", new Point(810,512), new Point(810, 358)));
                    addElement(createElement("wire", new Point(810,358), new Point(810, 204)));
                    InitChangeValue(new Point(194,400), "20");
                    InitChangeValue(new Point(502,400), "60");
                    InitChangeValue(new Point(810,400), "100");
                    InitChangeValue(new Point(400,204), "20");
                    InitChangeValue(new Point(700,204), "150");

                }
            }
            case 7 -> {
                if (LevelManager.currentStageIndex == 4) {
                    addElement(createElement("wire", new Point(40,362), new Point(194, 362)));
                    addElement(createElement("wire", new Point(194,362), new Point(348, 362)));
                    addElement(createElement("wire", new Point(348,362), new Point(502, 362)));
                    addElement(createElement("wire", new Point(502,362), new Point(656, 362)));
                    addElement(createElement("wire", new Point(656,362), new Point(810, 362)));

                    addElement(createElement("wire", new Point(40,206), new Point(194, 206)));
                    addElement(createElement("wire", new Point(194,206), new Point(348, 206)));
                    addElement(createElement("R", new Point(348,206), new Point(502, 206)));
                    addElement(createElement("wire", new Point(502,206), new Point(656, 206)));
                    addElement(createElement("R", new Point(656,206), new Point(810, 206)));

                    addElement(createElement("V", new Point(40,362), new Point(40, 206)));
                    addElement(createElement("R", new Point(810,362), new Point(810, 206)));
                    InitChangeValue(new Point(40, 300), "10");
                    InitChangeValue(new Point(400, 206), "10");
                    InitChangeValue(new Point(700, 206), "15");
                    InitChangeValue(new Point(810, 300), "25");
                }
                if (LevelManager.currentStageIndex == 5) {
                    addElement(createElement("wire", new Point(194,362), new Point(348, 362)));
                    addElement(createElement("wire", new Point(348,362), new Point(502, 362)));
                    addElement(createElement("wire", new Point(502,362), new Point(656, 362)));
                    addElement(createElement("wire", new Point(656,362), new Point(810, 362)));
                    addElement(createElement("wire", new Point(194,50), new Point(348, 50)));
                    addElement(createElement("wire", new Point(348,50), new Point(502, 50)));
                    addElement(createElement("wire", new Point(502,50), new Point(656, 50)));
                    addElement(createElement("wire", new Point(656,50), new Point(810, 50)));
                    addElement(createElement("V", new Point(194,362), new Point(194, 206)));
                    addElement(createElement("V", new Point(194,206), new Point(194, 50)));
                    addElement(createElement("R", new Point(502,362), new Point(502, 206)));
                    addElement(createElement("wire", new Point(502,206), new Point(502, 50)));
                    addElement(createElement("R", new Point(656,362), new Point(656, 206)));
                    addElement(createElement("wire", new Point(656,206), new Point(656, 50)));
                    addElement(createElement("R", new Point(810,362), new Point(810, 206)));
                    addElement(createElement("wire", new Point(810,206), new Point(810, 50)));
                    InitChangeValue(new Point(502, 300), "50");
                    InitChangeValue(new Point(656, 300), "10");
                    InitChangeValue(new Point(810, 300), "100");
                }
            }
            case 9 -> {
                addElement(createElement("wire", new Point(40,512), new Point(194, 512)));
                addElement(createElement("wire", new Point(194,512), new Point(348, 512)));
                addElement(createElement("wire", new Point(348,512), new Point(502, 512)));
                addElement(createElement("wire", new Point(502,512), new Point(656, 512)));
                addElement(createElement("wire", new Point(656,512), new Point(810, 512)));

                addElement(createElement("wire", new Point(40,50), new Point(194, 50)));
                addElement(createElement("R", new Point(194,50), new Point(348, 50)));
                addElement(createElement("wire", new Point(348,50), new Point(502, 50)));
                addElement(createElement("wire", new Point(502,50), new Point(656, 50)));
                addElement(createElement("wire", new Point(656,50), new Point(810, 50)));

                addElement(createElement("wire", new Point(40,512), new Point(40, 358)));
                addElement(createElement("V", new Point(40,358), new Point(40, 204)));
                addElement(createElement("wire", new Point(40,204), new Point(40, 50)));

                addElement(createElement("wire", new Point(502,512), new Point(502, 358)));
                addElement(createElement("R", new Point(502,358), new Point(502, 204)));
                addElement(createElement("wire", new Point(502,204), new Point(502, 50)));

                addElement(createElement("R", new Point(810,512), new Point(810, 358)));
                addElement(createElement("R", new Point(810,358), new Point(810, 204)));
                addElement(createElement("wire", new Point(810,204), new Point(810, 50)));


                InitChangeValue(new Point(40,300), "12");
                InitChangeValue(new Point(300,50), "50");
                InitChangeValue(new Point(502,300), "50");
                InitChangeValue(new Point(810,300), "30");
                InitChangeValue(new Point(810,400), "70");
            }
        }
        actions.clear();
    }

    /**
     * Paints the simulation area, including elements and snap points.
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(MainFrame.getTextTheme());
        g2d.setStroke(new BasicStroke(2));
        g.drawLine(0, toolbar.getHeight() + 10, getWidth(), toolbar.getHeight() + 10);
        g.drawLine(0, toolbar.getHeight() - 126, getWidth(), toolbar.getHeight() - 126);
    }

}
