package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;

/**
 * Represents a current source in electrical circuit simulation.
 * It extends the Element class and provides specific functionality for current source
 * including managing current values and rendering its visual representation.
 */
public class CurrentSource extends Element {

    /** The current value of the source in amps. */
    private float currentValue;

    /**
     * Constructs an CurrentSource object with the specified start and end points and default voltage I = 10 mA.
     *
     * @param start the starting point of the current source on the simulation plane.
     * @param end the ending point of the current source on the simulation plane.
     */
    public CurrentSource(Point start, Point end) {
        super(start, end);
        this.currentValue = 10e-3f;
        incrementCounter(CurrentSource.class);
        elementName = "I" + getCounter(CurrentSource.class);
    }

    /**
     * Returns the name of the current source element.
     *
     * @return the name of the current source.
     */
    @Override
    public String getElementName() {
        return elementName;
    }

    /**
     * Returns the current value of the source.
     *
     * @return the current value in amps.
     */
    @Override
    public float getElementValue() {
        return currentValue;
    }

    /**
     * Sets the current value of the source.
     *
     * @param value the new current value in amps.
     */
    @Override
    public void setElementValue(float value) {
        this.currentValue = value;
    }

    /**
     * Draws the current source on the provided graphics context.
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
            g2d.drawLine(start.x, start.y, start.x, midY - 30);
            g2d.drawOval(start.x - 30, midY - 30, 60, 60);
            g2d.drawLine(start.x, midY - 15, end.x, midY + 15);
            g2d.drawLine(start.x, midY - 15, end.x - 10, midY - 5);
            g2d.drawLine(start.x, midY - 15, end.x + 10, midY - 5);
            g2d.drawLine(start.x, midY + 30, end.x, end.y);
        } else if (isVertical) {
            g2d.drawLine(end.x, end.y, end.x, midY - 30);
            g2d.drawOval(end.x - 30, midY - 30, 60, 60);
            g2d.drawLine(end.x, midY - 15, start.x, midY + 15);
            g2d.drawLine(end.x, midY + 15, start.x - 10, midY + 5);
            g2d.drawLine(end.x, midY + 15, start.x + 10, midY + 5);
            g2d.drawLine(end.x, midY + 30, start.x, start.y);
        } else if (end.x - start.x > 0) {
            g2d.drawLine(start.x, start.y, midX - 30, end.y);
            g2d.drawOval(midX - 30, start.y - 30, 60, 60);
            g2d.drawLine(midX - 15, start.y, midX + 15, start.y);
            g2d.drawLine(midX - 15, start.y, midX - 5, start.y - 10);
            g2d.drawLine(midX - 15, start.y, midX - 5, start.y + 10);
            g2d.drawLine(midX + 30, start.y, end.x, end.y);
        } else {
            g2d.drawLine(end.x, end.y, midX - 30, start.y);
            g2d.drawOval(midX - 30, end.y - 30, 60, 60);
            g2d.drawLine(midX - 15, end.y, midX + 15, end.y);
            g2d.drawLine(midX + 15, end.y, midX + 5, end.y - 10);
            g2d.drawLine(midX + 15, end.y, midX + 5, end.y + 10);
            g2d.drawLine(midX + 30, end.y, start.x, start.y);
        }
    }

}