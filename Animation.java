import biuoop.DrawSurface;

/**
 * An animation interface.
 */
public interface Animation {

    /**
     * do one frame.
     * @param d : given surface.
     */
    void doOneFrame(DrawSurface d);

    /**
     * if the "loop" should stop.
     * @return true if should stop, false else.
     */
    boolean shouldStop();
}