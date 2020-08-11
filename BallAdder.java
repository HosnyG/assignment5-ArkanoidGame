import java.util.List;

/**
 * ball adder class.
 */
public class BallAdder implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;
    private BallsFactory f;

    /**
     * Constructor.
     *
     * @param gameLevel      : game.
     * @param remainingBalls : remaining balls in the game.
     * @param f              : specific ball's factory.
     */
    public BallAdder(GameLevel gameLevel, Counter remainingBalls, BallsFactory f) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
        this.f = f;
    }

    /**
     * add ball to game if "ball" block being hit, according to specific balls factory.
     * @param beingHit : the object being hit.
     * @param hitter   : the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() != -3) { //not "ball" block
            return;
        }

        List<Ball> balls = f.createBalls(1);
        for (int i = 0; i < balls.size(); i++) { // add the ball to the game.
            balls.get(i).addToGame(this.gameLevel);
            balls.get(i).setEnvironment(this.gameLevel.getEnvironment());
        }
        this.remainingBalls.increase(1);
        this.gameLevel.setRemainedBlocks(this.remainingBalls);

    }

}
