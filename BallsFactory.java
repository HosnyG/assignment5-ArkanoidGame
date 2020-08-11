import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

/**
 * ball's producer.
 */
public class BallsFactory {
    private List<Point> ballsCenter;
    private int ballsRadius;
    private Color ballsColor;
    private int screenWidth;
    private int screenHeight;
    private List<Velocity> v;


    /**
     * Constructor.
     *
     * @param ballsCetner  : list of balls centers.
     * @param r            : balls radius.
     * @param color        : balls color.
     * @param screenWidth  : screen width.
     * @param screenHeight : screen height.
     * @param v            : balls velocity.
     */
    public BallsFactory(List<Point> ballsCetner, int r, Color color, int screenWidth, int screenHeight
            , List<Velocity> v) {
        this.ballsCenter = ballsCetner;
        this.ballsRadius = r;
        this.ballsColor = color;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.v = v;
    }

    /**
     * produce a certain number of balls.
     *
     * @param ballsNum : balls number.
     * @return list of balls.
     */
    public List<Ball> createBalls(int ballsNum) {
        List<Ball> balls = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < ballsNum; i++) { //initialize velocity.
            Velocity velocity;
            if (this.v == null) { //random velocity.
                double dx = (rand.nextInt(3) + 10);
                double dy = -(rand.nextInt(5) + 2);
                velocity = new Velocity(dx, dy);
            } else {
                velocity = this.v.get(i);
            }
            //initialize the balls.
            Ball newBall = new Ball(this.ballsCenter.get(i), this.ballsRadius, this.ballsColor);
            newBall.setVelocity(velocity);
            newBall.setBoundaries(0, this.screenHeight, this.screenWidth, 0);
            balls.add(newBall);
        }
        return balls; //list of balls.
    }

}
