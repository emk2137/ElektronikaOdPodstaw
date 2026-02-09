package pl.gda.pg.elektronikaodpodstaw.simulation;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import java.awt.*;

/**
 * Represents a wire in electrical circuit simulation.
 * It extends the Element class and provides specific functionality for wires
 * including connections between elements and rendering its visual representation.
 */
public class Wire extends Element {

    /**
     * Constructs a wire object with the specified start and end points.
     *
     * @param start the starting point of the wire on the simulation plane.
     * @param end the ending point of the wire on the simulation plane.
     */
    public Wire(Point start, Point end) {
        super(start, end);
    }

    /**
     * Returns an empty string as wires do not have a name because it is not needed.
     *
     * @return an empty string as the name of the wire.
     */
    @Override
    public String getElementName() {
        return "";
    }

    /**
     * Returns a fixed value 0, as wires do not carry distinct
     * measurable values in this context.
     *
     * @return a fixed placeholder value of 0.
     */
    @Override
    public float getElementValue() {
        return 0f;
    }

    /**
     * This method does not modify the wire's value because they do not carry distinct measurable values.
     * It is intentionally left empty to ensure wires remain non-modifiable.
     *
     * @param value the value to set (unused).
     */
    @Override
    public void setElementValue(float value) {

    }

    /**
     * Draws the wire on the provided graphics context.
     * The wire is represented as a simple line between its start and end points.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(MainFrame.getTextTheme());
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }

}
