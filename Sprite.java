import biuoop.DrawSurface;

/**
 * A Sprite interface.
 * Sprites can be drawn on the screen, and can be notified that time has passed
 *
 * @author : Ganaiem Hosny
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d : given surface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}