
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * A streak class.
 * uses to draw line.
 */
public class Streak implements Sprite {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Color color;

    /**
     * Constructor.
     *
     * @param x1    : x-coordinate of line's start point.
     * @param y1    : y-coordinate of line's start point.
     * @param x2    : x=coordinate of line's end point.
     * @param y2    : y-coordinate of line's end point.
     * @param color : line color;
     */
    public Streak(double x1, double y1, double x2, double y2, Color color) {
        this.x1 = (int) x1;
        this.y1 = (int) y1;
        this.x2 = (int) x2;
        this.y2 = (int) y2;
        this.color = color;
    }

    /**
     * draw the line of given surface.
     *
     * @param d : given surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawLine(x1, y1, x2, y2);
    }

    /**
     * nothing to do.
     */
    public void timePassed() {

    }
}
