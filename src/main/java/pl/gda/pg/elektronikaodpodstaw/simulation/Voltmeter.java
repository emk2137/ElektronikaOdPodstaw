package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;

/**
 * Represents a voltmeter in electrical circuit simulation.
 * It extends the Element class and provides specific functionality for voltmeter getting value of potential difference on element
 * and rendering its visual representation.
 */
public class Voltmeter extends Element {

    /**
     * Constructs a Voltmeter object with the specified start and end points and name.
     *
     * @param start the starting point of the voltmeter at simulation plane.
     * @param end the ending point of the voltmeter at simulation plane.
     */
    public Voltmeter(Point start, Point end) {
        super(start, end);
        incrementCounter(Voltmeter.class);
        elementName = "VM" + getCounter(Voltmeter.class);
    }

    /**
     * Returns the name of the voltmeter element.
     *
     * @return the name of the voltmeter.
     */
    @Override
    public String getElementName() {
        return elementName;
    }

    /**
     * Returns the voltage value of the voltmeter.
     *
     * @return the voltage measured by the voltmeter.
     */
    @Override
    public float getElementValue() {
        return voltage;
    }

    /**
     * Sets the value of the voltmeter. This method is intentionally left empty
     * as the voltmeter does not modify its value directly.
     *
     * @param value the value to set (unused).
     */
    @Override
    public void setElementValue(float value) {

    }

    /**
     * Draws the voltmeter on the provided graphics context.
     * The appearance depends on the orientation and positioning of the voltmeter at simulation plane.
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
            g2d.drawLine(start.x, midY + 15, start.x - 15, midY - 15);
            g2d.drawLine(start.x, midY + 15, start.x + 15, midY - 15);
            g2d.drawLine(start.x + 17, midY + 46, start.x + 17, midY + 60);
            g2d.drawLine(start.x + 24, midY + 53, start.x + 10, midY + 53);
            g2d.drawLine(start.x + 24, midY - 53, start.x + 10, midY - 53);
            g2d.drawLine(start.x, midY + 30, end.x, end.y);
        } else if (isVertical) {
            g2d.drawLine(end.x, end.y, end.x, midY - 30);
            g2d.drawOval(end.x - 30, midY - 30, 60, 60);
            g2d.drawLine(end.x, midY + 15, end.x - 15, midY - 15);
            g2d.drawLine(end.x, midY + 15, end.x + 15, midY - 15);
            g2d.drawLine(start.x + 17, midY - 46, start.x + 17, midY - 60);
            g2d.drawLine(start.x + 24, midY - 53, start.x + 10, midY - 53);
            g2d.drawLine(start.x + 24, midY + 53, start.x + 10, midY + 53);
            g2d.drawLine(end.x, midY + 30, start.x, start.y);
        } else if (end.x - start.x > 0) {
            g2d.drawLine(start.x, start.y, midX - 30, start.y);
            g2d.drawOval(midX - 30, start.y - 30, 60, 60);
            g2d.drawLine(midX, start.y + 15, midX - 15, start.y - 15);
            g2d.drawLine(midX, start.y + 15, midX + 15, start.y - 15);
            g2d.drawLine(midX + 46, start.y - 17, midX + 60, start.y - 17);
            g2d.drawLine(midX + 53, start.y - 24, midX + 53, start.y - 10);
            g2d.drawLine(midX - 46, start.y - 17, midX - 60, start.y - 17);
            g2d.drawLine(midX + 30, start.y, end.x, end.y);
        } else {
            g2d.drawLine(end.x, end.y, midX - 30, end.y);
            g2d.drawOval(midX - 30, end.y - 30, 60, 60);
            g2d.drawLine(midX, end.y + 15, midX - 15, end.y - 15);
            g2d.drawLine(midX, end.y + 15, midX + 15, end.y - 15);
            g2d.drawLine(midX - 46, start.y - 17, midX - 60, start.y - 17);
            g2d.drawLine(midX - 53, start.y - 24, midX - 53, start.y - 10);
            g2d.drawLine(midX + 46, start.y - 17, midX + 60, start.y - 17);
            g2d.drawLine(midX + 30, end.y, start.x, start.y);
        }
    }

}