import biuoop.DrawSurface;

import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Block object, which has a shape(rectangular) and color.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private java.awt.Color color;
    private int number; //initialize.
    private List<HitListener> hitListeners;


    /**
     * Constructor.
     * <p>
     * the block may be normal block or death block  or be ball producer block.
     * to be death block : probability of 0.025.
     * to be ball producer block : probability of 0.025
     *
     * @param rec   : Block's shape.
     * @param color : Block's color.
     */
    public Block(Rectangle rec, java.awt.Color color) {
        this.shape = rec;
        this.color = color;
        this.hitListeners = new ArrayList<>();
        Random rand = new Random();
        int n = rand.nextInt(40) + 1;
        if (n == 30) { // probability of 0.02 (1/40)
            this.number = -2; //Death block;
        } else if (n == 10) { // probability of 0.02 ( 1/40)
            this.number = -3; //Live block
        } else { //normal
            this.number = -1;
        }

    }

    /**
     * @return Block's shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Notify the block that we collided with it at collisionPoint with a given velocity.
     * if collided with it from below or above , turn the vertical direction.
     * if collided with it from left or right , turn the horizontal direction.
     * if collided with it from the it's angles , turn the vertical and horizontal directions.
     *
     * @param collisionPoint  : where an object hit the block.
     * @param currentVelocity : an object velocity.
     * @param hitter          : the ball that hit the block.
     * @return new velocity depends on where the object hits the block.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line upperLine = this.shape.getUpperLine();
        Line lowerLine = this.shape.getLowerLine();
        Line leftLine = this.shape.getLeftLine();
        Line rightLine = this.shape.getRightLine();
        double newDy = 0, newDx = 0;
        //decrease the number of the block when the ball collided with it.
        if (this.number >= 1) {
            this.number = this.number - 1;
        }
        //block's angles.
        if (upperLine.onSegment(collisionPoint) && leftLine.onSegment(collisionPoint)
                || upperLine.onSegment(collisionPoint) && rightLine.onSegment(collisionPoint)
                || leftLine.onSegment(collisionPoint) && lowerLine.onSegment(collisionPoint)
                || rightLine.onSegment(collisionPoint) && lowerLine.onSegment(collisionPoint)) {
            newDy = -1 * currentVelocity.getDy();
            newDx = -1 * currentVelocity.getDx();

            // hit the block from above or below.
        } else if (upperLine.onSegment(collisionPoint) || lowerLine.onSegment(collisionPoint)) {
            newDy = -1 * currentVelocity.getDy();
            newDx = currentVelocity.getDx();
            // hit the block from left or right.
        } else if (leftLine.onSegment(collisionPoint) || rightLine.onSegment(collisionPoint)) {
            newDy = currentVelocity.getDy();
            newDx = -1 * currentVelocity.getDx();
        }

        //return the new velocity.
        Velocity newVelocity = new Velocity(newDx, newDy);
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * @param num : the number that will be draw on the middle of the block.
     */
    public void setNumber(int num) {
        this.number = num;
    }

    /**
     * draw the block on the given DrawSurface.
     *
     * @param surface : surface to draw the block on it.
     */
    public void drawOn(DrawSurface surface) {
        Point upperLeft = this.shape.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        Rectangle rec = this.shape;
        Point p1 = rec.getUpperLeft();

        //death block
        if (this.number == -2) {
            surface.setColor(Color.red);
            surface.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.black);
            surface.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.white);
            surface.drawText((int) p1.getX() + (int) (this.getWidth() / 7)
                    , (int) p1.getY() + (int) (this.getHeight() * 0.65), "DEATH", 11);
        } else if (this.number == -3) { //ball producer block
            surface.setColor(Color.green);
            surface.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.black);
            surface.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.white);
            surface.drawText((int) p1.getX() + (int) (this.getWidth() / 4.6)
                    , (int) p1.getY() + (int) (this.getHeight() * 0.65), "BALL", 13);

        } else { //normal block
            surface.setColor(this.color);
            surface.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
            surface.setColor(Color.black);
            surface.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());

        }
    }

    /**
     * notify the block that time has passed.
     */
    public void timePassed() {
    }

    /**
     * adding the block to specified game.
     *
     * @param g : the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * remove this block from the game.
     *
     * @param gameLevel : game .
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * add hit listener to the listeners.
     *
     * @param hl : hit listener will be added.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * remove hit listener from the listeners.
     *
     * @param hl : hit listener will be removed.
     */
    public void removeHitListener(HitListener hl) {
        try {
            this.hitListeners.remove(hl);
        } catch (Exception e) { //not found in the collection of the listeners.
            System.out.println("this listener is not in the block's hit listeners");
        }

    }

    /**
     * will be called whenever a hit() occurs.
     * and notifiers all of the registered HitListener objects by calling their hitEvent method
     * @param hitter : ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * @return block points.
     */
    public int getHitPoints() {
        return this.number;
    }

    /**
     *
     * @return block's width.
     */
    public double getWidth() {
        return this.shape.getWidth();
    }

    /**
     *
     * @return block's height.
     */
    public double getHeight() {
        return this.shape.getHeight();
    }

    /**
     *
     * @return block's Color.
     */
    public Color getColor() {
        return this.color;
    }
}

