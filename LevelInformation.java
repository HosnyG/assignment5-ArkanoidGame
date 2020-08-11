import java.awt.Color;
import java.util.List;

/**
 * specifies the information required to fully describe a level.
 */
public interface LevelInformation {

    /**
     * @return number of balls.
     */
    int numberOfBalls();

    /**
     * @return list of initial balls velocity.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return paddle speed.
     */
    int paddleSpeed();

    /**
     * @return paddle width.
     */
    int paddleWidth();

    /**
     * @return level name.
     */
    String levelName();

    /**
     * @return level's background.
     */
    Sprite getBackground();

    /**
     * @return blocks in the level.
     */
    List<Block> blocks();

    /**
     * @return number of blocks must be destroyed yo pass the level.
     */
    int numberOfBlocksToRemove();

    /**
     * @return paddle upper left point.
     */
    Point getPaddleUpperLeft();

    /**
     * @return paddle height.
     */
    int getPaddleHeight();

    /**
     * @return paddle color.
     */
    Color getPaddleColor();

    /**
     * @return balls factory.
     */
    BallsFactory getBallsFactory();

    /**
     * @return bodies of the level.
     */
    List<Sprite> getBodies();

}