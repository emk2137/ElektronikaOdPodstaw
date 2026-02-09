package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a base class for all electrical elements in the simulation.
 * Provides shared properties and methods for handling graphical representation,
 * value manipulation, and node assignments of elements.
 */
public abstract class Element extends Component {

    /** The starting point of the element in the simulation area. */
    public final Point start;

    /** The ending point of the element in the simulation area. */
    public final Point end;

    /** The horizontal midpoint of the element. */
    public final int midX;

    /** The vertical midpoint of the element. */
    public final int midY;

    /** Indicates whether the element is oriented vertically. */
    public final boolean isVertical;

    /** The voltage across the element in volts. */
    public float voltage = 0;

    /** The current flowing through the element in amps. */
    public float current = 0;

    /** The node number at the positive end of the element. */
    public int nodePlus = 0;

    /** The node number at the negative end of the element. */
    public int nodeMinus = 0;

    /** The unique name of the element, assigned during instantiation. */
    public String elementName = null;

    /** Maintains counters for instances of each subclass of Element. */
    private static final Map<Class<? extends Element>, Integer> elementCounters = new HashMap<>();

    /**
     * Constructs an `Element` with specified start and end points.
     * Calculates the midpoints and determines orientation based on the points.
     *
     * @param start the starting point of the element.
     * @param end the ending point of the element.
     */
    public Element(Point start, Point end) {
        this.start = start;
        this.end = end;
        midX = (start.x + end.x) / 2;
        midY = (start.y + end.y) / 2;
        isVertical = start.x == end.x;
    }

    /**
     * Increments the counter for the specified subclass of `Element`.
     *
     * @param clazz the class for which the counter is incremented.
     */
    public static void incrementCounter(Class<? extends Element> clazz) {
        elementCounters.put(clazz, elementCounters.getOrDefault(clazz, 0) + 1);
    }

    /**
     * Resets counters for all subclasses of `Element`.
     */
    public static void resetAllCounters() {
        elementCounters.clear();
    }

    /**
     * Retrieves the counter value for the specified subclass of `Element`.
     *
     * @param clazz the class for which the counter is retrieved.
     * @return the current count of instances of the specified class.
     */
    public static int getCounter(Class<? extends Element> clazz) {
        return elementCounters.getOrDefault(clazz, 0);
    }

    /**
     * Draws the element on the provided graphics context.
     *
     * @param g the graphics context used for rendering.
     */
    public void draw(Graphics g) {

    }

    /**
     * Returns the name of the element.
     *
     * @return the name of the element.
     */
    public abstract String getElementName();

    /**
     * Retrieves the value associated with the element (e.g., resistance for a resistor).
     *
     * @return the value of the element.
     */
    public abstract float getElementValue();

    /**
     * Sets the value associated with the element.
     *
     * @param value the new value to assign to the element.
     */
    public abstract void setElementValue(float value);

    /**
     * Converts a numerical value into metric prefix but special for simulation.
     *
     * @param value the value to convert.
     * @return the formatted value in metric prefix specific for simulation.
     */
    private static String ValueToUnit(float value) {
        String[] units = {"e", "t", "g", "meg", "k", "", "m", "u", "n", "p", "f"};
        float[] multipliers = {1e15f, 1e12f, 1e9f, 1e6f, 1e3f, 1f, 1e-3f, 1e-6f, 1e-9f, 1e-12f, 1e-15f};

        for (int i = 0; i < units.length; i++) {
            if (Math.abs(value) >= multipliers[i]) {
                float formattedValue = value / multipliers[i];
                String result = String.format(Locale.US, "%.3f", formattedValue);
                result = result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") : result;
                return result + units[i];
            }
        }

        String result = String.format(Locale.US, "%.3f", value);
        return result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") : result;
    }

    /**
     * Centers and draws a value string on the specified graphics context.
     *
     * @param g the graphics context used for rendering.
     * @param value the value to draw.
     * @param centerX the horizontal center point.
     * @param centerY the vertical center point.
     */
    private void CenterValueTool(Graphics g, String value, int centerX, int centerY) {
        switch (value.length()) {
            case 1 -> g.setFont(new Font("Arial", Font.PLAIN, 30));
            case 2 -> g.setFont(new Font("Arial", Font.PLAIN, 28));
            case 3 -> g.setFont(new Font("Arial", Font.PLAIN, 26));
            case 4 -> g.setFont(new Font("Arial", Font.PLAIN, 24));
            case 5 -> g.setFont(new Font("Arial", Font.PLAIN, 22));
            case 6 -> g.setFont(new Font("Arial", Font.PLAIN, 20));
            case 7 -> g.setFont(new Font("Arial", Font.PLAIN, 18));
            case 8 -> g.setFont(new Font("Arial", Font.PLAIN, 16));
            case 9 -> g.setFont(new Font("Arial", Font.PLAIN, 14));
            case 10 -> g.setFont(new Font("Arial", Font.PLAIN, 12));
            default -> g.setFont(new Font("Arial", Font.PLAIN, 15));
        }

        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(value);
        int textHeight = metrics.getHeight();
        int textAscent = metrics.getAscent();

        int x = centerX - (textWidth / 2);
        int y = centerY + (textAscent - (textHeight / 2));

        g.drawString(value, x, y);
    }

    /**
     * Draws the value of the element at an appropriate position on the simulation area.
     *
     * @param g the graphics context used for rendering.
     */
    public void drawValue(Graphics g) {
        if (MainFrame.currentLevel > 1) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(MainFrame.getTextTheme());
            String valueText = ValueToUnit(getElementValue());
            if (isVertical && start.x == 40) {
                CenterValueTool(g, valueText, start.x + 60, midY);
            } else if (isVertical) {
                CenterValueTool(g, valueText, start.x - 60, midY);
            } else if (start.y == 50) {
                CenterValueTool(g, valueText, midX, start.y + 45);
            } else {
                CenterValueTool(g, valueText, midX, start.y - 45);
            }
        }
    }

    /**
     * Converts a special metric prefix to its float value representation.
     *
     * @param value the special metric prefix to convert (e.g., "10k").
     * @return the numeric value of the prefix.
     * @throws NumberFormatException if the string format is invalid.
     */
    public static float UnitToValue(String value) {
        Map<String, Float> unitMultipliers = new HashMap<>();
        unitMultipliers.put("f", 1e-15f);
        unitMultipliers.put("p", 1e-12f);
        unitMultipliers.put("n", 1e-9f);
        unitMultipliers.put("u", 1e-6f);
        unitMultipliers.put("m", 1e-3f);
        unitMultipliers.put("k", 1e3f);
        unitMultipliers.put("meg", 1e6f);
        unitMultipliers.put("g", 1e9f);
        unitMultipliers.put("t", 1e12f);
        unitMultipliers.put("e", 1e5f);

        value = value.trim().toLowerCase();

        String regex = "^([-+]?[0-9]*\\.?[0-9]+)\\s*([a-z]*)$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(value);

        if (matcher.matches()) {
            String valuePart = matcher.group(1);
            String unitPart = matcher.group(2);

            float baseValue = Float.parseFloat(valuePart);

            if (unitMultipliers.containsKey(unitPart))
                return baseValue * unitMultipliers.get(unitPart);
            else if (unitPart.isEmpty())
                return baseValue;
            else
                throw new NumberFormatException();
        }
        throw new NumberFormatException();
    }

}