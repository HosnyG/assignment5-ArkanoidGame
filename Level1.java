import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level1 class.
 */
public class Level1 implements LevelInformation {
    private int screenWidth;
    private int screenHeight;
    private int numberOfBalls;
    private int paddleSpeed;
    private Point paddleUpperLeft;
    private int paddleHeight;
    private int paddleWidth;
    private Color paddleColor;
    private String levelName;
    private Block background;
    private List<Block> blocks;
    private int numberOfBlocksToRemove;
    private BallsFactory ballsFactory;
    private List<Sprite> bodies;
    private Color ballsColor;
    private int ballsRadius;

    /**
     * Constructor.
     *
     * @param screenWidth  : screen width
     * @param screenHeight : screen height
     */
    public Level1(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.blocks = new ArrayList<>();
        this.bodies = new ArrayList<>();
        this.initialize();

    }

    /**
     * initialize and draw the bodies of the game.
     */
    private void initialize() {
        this.levelName = "Direct Hit";
        this.numberOfBalls = 1;
        this.ballsRadius = 5;
        this.ballsColor = Color.white;
        this.paddleHeight = (screenHeight / 27);
        this.paddleWidth = (screenWidth / 8);
        this.paddleSpeed = 6;
        this.paddleColor = new Color(1.0f, 0.699f, 0.000f);
        int surroundingBlocksWidth = 30;
        this.paddleUpperLeft = new Point((screenWidth / 2) - screenWidth / 12, screenHeight - surroundingBlocksWidth
                - this.paddleHeight);
        this.background = new Block(new Rectangle(new Point(0, 0), screenWidth + 1
                , screenHeight + 2), Color.black);
        this.background.setNumber(-1);
        Block block = new Block(new Rectangle(new Point((screenWidth / 2) - screenWidth / 22, screenHeight / 3.5)
                , screenWidth / 22, screenWidth / 22), Color.red);
        block.setNumber(1);
        this.blocks.add(block);
        this.numberOfBlocksToRemove = 1;

        List<Point> ballsCenter = new ArrayList<>();
        ballsCenter.add(new Point(block.getCollisionRectangle().getLowerLine().middle().getX()
                , paddleUpperLeft.getY() - 10));
        List<Velocity> ballsVelocity = new ArrayList<>();
        ballsVelocity.add(new Velocity(0, -7));

        this.ballsFactory = new BallsFactory(ballsCenter, this.ballsRadius, this.ballsColor,
                this.screenWidth, this.screenHeight, ballsVelocity);

        //bodies
        Rectangle blockShape = block.getCollisionRectangle();
        int linesLength = (int) (screenHeight / 5);
        Streak s1 = new Streak(blockShape.getLowerLine().middle().getX(), blockShape.getLowerLine().middle().getY() + 10
                , blockShape.getLowerLine().middle().getX(), blockShape.getLowerLine().middle().getY() + linesLength
                , Color.blue);

        Streak s2 = new Streak(blockShape.getUpperLine().middle().getX(), blockShape.getUpperLine().middle().getY() - 10
                , blockShape.getUpperLine().middle().getX(), blockShape.getUpperLine().middle().getY() - linesLength
                , Color.blue);

        Streak s3 = new Streak(blockShape.getRightLine().middle().getX() + 10, blockShape.getRightLine().middle().getY()
                , blockShape.getRightLine().middle().getX() + linesLength, blockShape.getRightLine().middle().getY()
                , Color.blue);

        Streak s4 = new Streak(blockShape.getLeftLine().middle().getX() - 10, blockShape.getLeftLine().middle().getY()
                , blockShape.getLeftLine().middle().getX() - linesLength, blockShape.getLeftLine().middle().getY()
                , Color.blue);

        //shooting circle
        Line blockDiagnoal = new Line(blockShape.getUpperLeft(), blockShape.getRightLine().end());
        Point blockCenter = blockDiagnoal.middle();
        Circle c1 = new Circle(blockCenter, (int) (this.screenHeight / 5), Color.blue, "not fill");
        Circle c2 = new Circle(blockCenter, (int) (this.screenHeight / 6.5), Color.blue, "not fill");
        Circle c3 = new Circle(blockCenter, (int) (this.screenHeight / 10), Color.BLUE, "not fill");
        this.bodies.add(c1);
        this.bodies.add(c2);
        this.bodies.add(c3);
        this.bodies.add(s1);
        this.bodies.add(s2);
        this.bodies.add(s3);
        this.bodies.add(s4);


    }

    /**
     * @return : balls number.
     */
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    /**
     * @return : balls initial velocity.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> l = new ArrayList<>();
        List<Ball> balls = this.ballsFactory.createBalls(this.numberOfBalls);
        for (int i = 0; i < balls.size(); i++) {
            l.add(balls.get(i).getVelocity());
        }
        return l;
    }


    /**
     * @return : paddle speed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return : paddle width.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * @return : level name.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * @return : the back grounf of the level.
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * @return : list of the blocks in the game.
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * @return : number of blocks to remove.
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }

    /**
     * @return : paddle upper left point.
     */
    public Point getPaddleUpperLeft() {
        return this.paddleUpperLeft;
    }

    /**
     * @return : paddle height.
     */
    public int getPaddleHeight() {
        return this.paddleHeight;
    }

    /**
     * @return : paddle color.
     */
    public Color getPaddleColor() {
        return this.paddleColor;
    }

    /**
     * @return : paddle factory of balls.
     */
    public BallsFactory getBallsFactory() {
        return this.ballsFactory;
    }

    /**
     * @return : bodies of the background.
     */
    public List<Sprite> getBodies() {
        return this.bodies;
    }
}
