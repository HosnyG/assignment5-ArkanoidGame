
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * A circle class.
 * uses to draw a circle.
 */
public class Circle implements Sprite {

    private int x;
    private int y;
    private int radius;
    private Color color;
    private String status;

    /**
     * Constructor.
     *
     * @param x      : x-coordinate of circle's center point.
     * @param y      : y-coordinate of circle's center point.
     * @param r      : radius.
     * @param color  : circle color.
     * @param status : fill or nor fill.
     */
    public Circle(double x, double y, int r, Color color, String status) {
        this.x = (int) x;
        this.y = (int) y;
        this.radius = r;
        this.color = color;
        this.status = status;
    }

    /**
     * Constructor.
     *
     * @param center : center point .
     * @param r      : radius.
     * @param color  : circle color.
     * @param status : fill or not fill.
     */
    public Circle(Point center, int r, Color color, String status) {
        this.x = (int) center.getX();
        this.y = (int) center.getY();
        this.radius = r;
        this.color = color;
        this.status = status;
    }

    /**
     * draw a circle in given surface.
     *
     * @param d : given surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        if (this.status.equals("fill") || this.status.equals("Fill")) { //fill circle
            d.fillCircle(this.x, this.y, this.radius);
        } else if (this.status.equals("not fill") || this.status.equals("notFill")) { //not fill circle
            d.drawCircle(this.x, this.y, this.radius);
        }
    }

    /**
     * nothing to do.
     */
    public void timePassed() {

    }
}
