package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;

/**
 * Represents a inductor in electrical circuit simulation.
 * It extends the Element class and provides specific functionality for inductors
 * including managing inductance values and rendering its visual representation.
 */
public class Inductor extends Element {

    /** The inductance value of the inductance in henries. */
    private float inductance;

    /**
     * Constructs an Inductor object with the specified start and end points and default inductance L = 1 H.
     *
     * @param start the starting point of the inductor on the simulation plane.
     * @param end the ending point of the inductor on the simulation plane.
     */
    public Inductor(Point start, Point end) {
        super(start, end);
        this.inductance = 1f;
        incrementCounter(Inductor.class);
        elementName = "L" + getCounter(Inductor.class);
    }

    /**
     * Returns the name of the inductor element.
     *
     * @return the name of the inductor.
     */
    @Override
    public String getElementName() {
        return elementName;
    }

    /**
     * Returns the inductance value of the inductor.
     *
     * @return the inductance value in henries.
     */
    @Override
    public float getElementValue() {
        return inductance;
    }

    /**
     * Sets the inductance value of the inductor.
     *
     * @param value the new inductance value in henries.
     */
    @Override
    public void setElementValue(float value) {
        this.inductance = value;
    }

    /**
     * Draws the inductor on the provided graphics context.
     * The appearance depends on the orientation and positioning of the inductor in the simulation plane.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(MainFrame.getTextTheme());
        g2d.setStroke(new BasicStroke(3));
        if (isVertical && end.y - start.y > 0) {
            g2d.drawLine(start.x, start.y, start.x, midY - 45);
            g2d.drawArc(start.x - 15, midY - 45, 30, 30, -90, 180);
            g2d.drawArc(start.x - 15, midY - 15, 30, 30, -90, 180);
            g2d.drawArc(start.x - 15, midY + 15, 30, 30, -90, 180);
            g2d.drawLine(start.x, midY + 45, end.x, end.y);
        } else if (isVertical) {
            g2d.drawLine(end.x, end.y, end.x, midY - 45);
            g2d.drawArc(end.x - 15, midY - 45, 30, 30, 90, 180);
            g2d.drawArc(end.x - 15, midY - 15, 30, 30, 90, 180);
            g2d.drawArc(end.x - 15, midY + 15, 30, 30, 90, 180);
            g2d.drawLine(end.x, midY + 45, start.x, start.y);
        } else if (end.x - start.x > 0) {
            g2d.drawLine(start.x, start.y, midX - 45, start.y);
            g2d.drawArc(midX - 45, start.y - 15, 30, 30, 0, 180);
            g2d.drawArc(midX - 15, start.y - 15, 30, 30, 0, 180);
            g2d.drawArc(midX + 15, start.y - 15, 30, 30, 0, 180);
            g2d.drawLine(midX + 45, start.y, end.x, end.y);
        } else {
            g2d.drawLine(end.x, end.y, midX - 45, end.y);
            g2d.drawArc(midX - 45, end.y - 15, 30, 30, 180, 180);
            g2d.drawArc(midX - 15, end.y - 15, 30, 30, 180, 180);
            g2d.drawArc(midX + 15, end.y - 15, 30, 30, 180, 180);
            g2d.drawLine(midX + 45, end.y, start.x, start.y);
        }
    }

}