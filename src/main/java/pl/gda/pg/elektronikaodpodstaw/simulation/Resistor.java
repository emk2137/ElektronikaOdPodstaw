package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;

/**
 * Represents a resistor in electrical circuit simulation.
 * It extends the Element class and provides specific functionality for resistors
 * including managing resistance values and rendering its visual representation.
 */
public class Resistor extends Element {

    /** The resistance value of the resistor in ohms. */
    private float resistance;

    /**
     * Constructs a Resistor object with the specified start and end points and default resistance R = 1 kΩ.
     *
     * @param start the starting point of the resistor on the simulation plane.
     * @param end the ending point of the resistor on the simulation plane.
     */
    public Resistor(Point start, Point end) {
        super(start, end);
        this.resistance = 1000f;
        incrementCounter(Resistor.class);
        elementName = "R" + getCounter(Resistor.class);
    }

    /**
     * Returns the name of the resistor element.
     *
     * @return the name of the resistor.
     */
    @Override
    public String getElementName() {
        return elementName;
    }

    /**
     * Returns the resistance value of the resistor.
     *
     * @return the resistance value in ohms.
     */
    @Override
    public float getElementValue() {
        return resistance;
    }

    /**
     * Sets the resistance value of the resistor.
     *
     * @param value the new resistance value in ohms.
     */
    @Override
    public void setElementValue(float value) {
        this.resistance = value;
    }

    /**
     * Draws the resistor on the provided graphics context.
     * The appearance depends on the orientation and positioning of the resistor in the simulation plane.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(MainFrame.getTextTheme());
        g2d.setStroke(new BasicStroke(3));

        if (MainFrame.currentResistorSymbol.equals("IEC")) {
            if (isVertical && end.y - start.y > 0) {
                g2d.drawLine(start.x, start.y, start.x, midY - 20);
                g2d.drawRect(start.x - 10, midY - 20, 20, 40);
                g2d.drawLine(start.x, midY + 20, end.x, end.y);
            } else if (isVertical) {
                g2d.drawLine(end.x, end.y, end.x, midY - 20);
                g2d.drawRect(end.x - 10, midY - 20, 20, 40);
                g2d.drawLine(end.x, midY + 20, start.x, start.y);
            } else if (end.x - start.x > 0) {
                g2d.drawLine(start.x, start.y, midX - 20, start.y);
                g2d.drawRect(midX - 20, start.y - 10, 40, 20);
                g2d.drawLine(midX + 20, start.y, end.x, end.y);
            } else {
                g2d.drawLine(end.x, end.y, midX - 20, end.y);
                g2d.drawRect(midX - 20, end.y - 10, 40, 20);
                g2d.drawLine(midX + 20, end.y, start.x, start.y);
            }
        }
        else {
            if (isVertical && end.y - start.y > 0) {
                g2d.drawLine(start.x, start.y, start.x, midY - 40);
                g2d.drawLine(start.x, midY - 40, start.x + 15, midY - 33);
                g2d.drawLine(start.x + 15, midY - 33, start.x - 15, midY - 20);
                g2d.drawLine(start.x - 15, midY - 20, start.x + 15, midY - 7);
                g2d.drawLine(start.x + 15, midY - 7, start.x - 15, midY + 7);
                g2d.drawLine(start.x - 15, midY + 7, start.x + 15, midY + 20);
                g2d.drawLine(start.x + 15, midY + 20, start.x - 15, midY + 33);
                g2d.drawLine(start.x - 15, midY + 33, start.x, midY + 40);
                g2d.drawLine(start.x, midY + 40, end.x, end.y);
            } else if (isVertical) {
                g2d.drawLine(end.x, end.y, end.x, midY - 40);
                g2d.drawLine(end.x, midY - 40, end.x + 15, midY - 33);
                g2d.drawLine(end.x + 15, midY - 33, end.x - 15, midY - 20);
                g2d.drawLine(end.x - 15, midY - 20, end.x + 15, midY - 7);
                g2d.drawLine(end.x + 15, midY - 7, end.x - 15, midY + 7);
                g2d.drawLine(end.x - 15, midY + 7, end.x + 15, midY + 20);
                g2d.drawLine(end.x + 15, midY + 20, end.x - 15, midY + 33);
                g2d.drawLine(end.x - 15, midY + 33, end.x, midY + 40);
                g2d.drawLine(end.x, midY + 40, start.x, start.y);
            } else if (end.x - start.x > 0) {
                g2d.drawLine(start.x, start.y, midX - 40, start.y);
                g2d.drawLine(midX - 40, start.y, midX - 33, start.y - 15);
                g2d.drawLine(midX - 33, start.y - 15, midX - 20, start.y + 15);
                g2d.drawLine(midX - 20, start.y + 15, midX - 7, start.y - 15);
                g2d.drawLine(midX - 7, start.y - 15, midX + 7, start.y + 15);
                g2d.drawLine(midX + 7, start.y + 15, midX + 20, start.y - 15);
                g2d.drawLine(midX + 20, start.y - 15, midX + 33, start.y + 15);
                g2d.drawLine(midX + 33, start.y + 15, midX + 40, start.y);
                g2d.drawLine(midX + 40, start.y, end.x, end.y);
            } else {
                g2d.drawLine(end.x, end.y, midX - 40, end.y);
                g2d.drawLine(midX - 40, end.y, midX - 33, end.y - 15);
                g2d.drawLine(midX - 33, end.y - 15, midX - 20, end.y + 15);
                g2d.drawLine(midX - 20, end.y + 15, midX - 7, end.y - 15);
                g2d.drawLine(midX - 7, end.y - 15, midX + 7, end.y + 15);
                g2d.drawLine(midX + 7, end.y + 15, midX + 20, end.y - 15);
                g2d.drawLine(midX + 20, end.y - 15, midX + 33, end.y + 15);
                g2d.drawLine(midX + 33, end.y + 15, midX + 40, end.y);
                g2d.drawLine(midX + 40, end.y, start.x, start.y);
            }
        }
    }

}
