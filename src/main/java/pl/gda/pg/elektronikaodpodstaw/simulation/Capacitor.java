package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;

/**
 * Represents a capacitor in electrical circuit simulation.
 * It extends the Element class and provides specific functionality for capacitors
 * including managing capacitance values and rendering its visual representation.
 */
public class Capacitor extends Element {

    /** The capacitance value of the capacitor in farads. */
    private float capacitance;

    /**
     * Constructs a Capacitor object with the specified start and end points and default capacitance C = 10 uF.
     *
     * @param start the starting point of the capacitor on the simulation plane.
     * @param end the ending point of the capacitor on the simulation plane.
     */
    public Capacitor(Point start, Point end) {
        super(start, end);
        this.capacitance = 10e-6f;
        incrementCounter(Capacitor.class);
        elementName = "C" + getCounter(Capacitor.class);
    }

    /**
     * Returns the name of the capacitor element.
     *
     * @return the name of the capacitor.
     */
    @Override
    public String getElementName() {
        return elementName;
    }

    /**
     * Returns the capacitance value of the capacitor.
     *
     * @return the capacitance value in farads.
     */
    @Override
    public float getElementValue() {
        return capacitance;
    }

    /**
     * Sets the capacitance value of the capacitor.
     *
     * @param value the new capacitance value in farads.
     */
    @Override
    public void setElementValue(float value) {
        this.capacitance = value;
    }

    /**
     * Draws the capacitor on the provided graphics context.
     * The appearance depends on the orientation and positioning of the capacitor in the simulation plane.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(MainFrame.getTextTheme());
        g2d.setStroke(new BasicStroke(3));
        if (isVertical && end.y - start.y > 0) {
            g2d.drawLine(start.x, start.y, start.x, midY - 7);
            g2d.drawLine(start.x - 20, midY - 7, start.x + 20, midY - 7);
            g2d.drawLine(start.x - 20, midY + 7, start.x + 20, midY + 7);
            g2d.drawLine(start.x, midY + 7, end.x, end.y);
        } else if (isVertical) {
            g2d.drawLine(end.x, end.y, end.x, midY - 7);
            g2d.drawLine(end.x - 20, midY - 7, end.x + 20, midY - 7);
            g2d.drawLine(end.x - 20, midY + 7, end.x + 20, midY + 7);
            g2d.drawLine(end.x, midY + 7, start.x, start.y);
        } else if (end.x - start.x > 0) {
            g2d.drawLine(start.x, start.y, midX - 7, start.y);
            g2d.drawLine(midX - 7, start.y - 20, midX - 7, start.y + 20);
            g2d.drawLine(midX + 7, start.y - 20, midX + 7, start.y + 20);
            g2d.drawLine(midX + 7, start.y, end.x, end.y);
        } else {
            g2d.drawLine(end.x, end.y, midX - 7, end.y);
            g2d.drawLine(midX - 7, end.y - 20, midX - 7, end.y + 20);
            g2d.drawLine(midX + 7, end.y - 20, midX + 7, end.y + 20);
            g2d.drawLine(midX + 7, end.y, start.x, start.y);
        }
    }

}