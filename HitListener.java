/**
 * Hit Listener interface.
 * Objects that want to be notified of hit events, should implement this interface.
 */
public interface HitListener {
    // This method is called whenever the beingHit object is hit.
    // The hitter parameter is the Ball that's doing the hitting.

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit : the object being hit.
     * @param hitter   : the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}