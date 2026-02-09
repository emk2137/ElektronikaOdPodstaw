package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;

/**
 * Represents a voltage source in electrical circuit simulation.
 * It extends the Element class and provides specific functionality for voltage source
 * including managing voltage values and rendering its visual representation.
 */
public class VoltageSource extends Element {

    /** The voltage value of the source in volts. */
    private float voltageValue;

    /**
     * Constructs an VoltageSource object with the specified start and end points and default voltage V = 5 V.
     *
     * @param start the starting point of the voltage source on the simulation plane.
     * @param end the ending point of the voltage source on the simulation plane.
     */
    public VoltageSource(Point start, Point end) {
        super(start, end);
        this.voltageValue = 5f;
        incrementCounter(VoltageSource.class);
        elementName = "V" + getCounter(VoltageSource.class);
    }

    /**
     * Returns the name of the voltage source element.
     *
     * @return the name of the voltage source.
     */
    @Override
    public String getElementName() {
        return elementName;
    }

    /**
     * Returns the voltage value of the source.
     *
     * @return the voltage value in volts.
     */
    @Override
    public float getElementValue() {
        return voltageValue;
    }

    /**
     * Sets the voltage value of the source.
     *
     * @param value the new voltage value in volts.
     */
    @Override
    public void setElementValue(float value) {
        this.voltageValue = value;
    }

    /**
     * Draws the voltage source on the provided graphics context.
     * The appearance depends on the orientation and positioning of the source in the simulation plane.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(MainFrame.getTextTheme());
        g2d.setStroke(new BasicStroke(3));
        if (isVertical && end.y - start.y > 0) {
            g2d.drawLine(start.x, start.y, start.x, midY - 5);
            g2d.drawLine(start.x - 15, midY - 5, start.x + 15, midY - 5);
            g2d.drawLine(start.x - 25, midY + 5, start.x + 25, midY + 5);
            g2d.drawLine(start.x, midY + 5, end.x, end.y);
        } else if (isVertical) {
            g2d.drawLine(end.x, end.y, end.x, midY - 5);
            g2d.drawLine(end.x - 25, midY - 5, end.x + 25, midY - 5);
            g2d.drawLine(end.x - 15, midY + 5, end.x + 15, midY + 5);
            g2d.drawLine(end.x, midY + 5, start.x, start.y);

        } else if (end.x - start.x > 0) {
            g2d.drawLine(start.x, start.y, midX - 5, start.y);
            g2d.drawLine(midX - 5, start.y - 15, midX - 5, start.y + 15);
            g2d.drawLine(midX + 5, start.y - 25, midX + 5, start.y + 25);
            g2d.drawLine(midX + 5, start.y, end.x, end.y);
        } else {
            g2d.drawLine(end.x, end.y, midX - 5, end.y);
            g2d.drawLine(midX - 5, end.y - 25, midX - 5, end.y + 25);
            g2d.drawLine(midX + 5, end.y - 15, midX + 5, end.y + 15);
            g2d.drawLine(midX + 5, end.y, start.x, start.y);
        }
    }

}

