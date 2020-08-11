import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * level 2 class.
 */
public class Level2 implements LevelInformation {

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


    /**
     * Constructor.
     *
     * @param screenWidth  : screen width.
     * @param screenHeight : screen height.
     */
    public Level2(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.blocks = new ArrayList<>();
        this.bodies = new ArrayList<>();
        this.initialize();
    }

    /**
     * initialize the sprites and background.
     */
    private void initialize() {
        this.levelName = "Wide Easy";
        this.numberOfBalls = 10;
        int ballsRadius = 5;
        Color ballsColor = Color.white;
        this.paddleHeight = (screenHeight / 27);
        this.paddleWidth = (int) (screenWidth * 0.7);
        this.paddleSpeed = 3;
        this.paddleColor = new Color(1.0f, 0.699f, 0.000f);
        int surroundingBlocksWidth = 30;
        this.paddleUpperLeft = new Point(screenWidth / 10, screenHeight - surroundingBlocksWidth
                - this.paddleHeight);
        this.background = new Block(new Rectangle(new Point(0, 0), screenWidth + 1
                , screenHeight + 2), new Color(0.6f, 0.808f, 0.980f));
        this.background.setNumber(-1);

        int removableBlocks = 0;
        int blocksNum = 15;
        double blocksWidth = (screenWidth - (2 * surroundingBlocksWidth)) / (double) blocksNum;
        int blocksHeight = (screenHeight / 20);
        Color[] colors = {Color.red, Color.red, Color.orange, Color.orange, Color.yellow, Color.yellow
                , Color.green, Color.green, Color.green, Color.blue, Color.blue, Color.magenta, Color.magenta
                , Color.cyan, Color.cyan};
        //blocks initialize.
        for (int i = 0; i < blocksNum; i++) {
            Block block = new Block(new Rectangle(new Point(surroundingBlocksWidth + (i * blocksWidth)
                    , screenHeight * 0.45), blocksWidth, blocksHeight), colors[i]);
            if (block.getHitPoints() != -2 && block.getHitPoints() != -3) { // not death or live block
                block.setNumber(1);
                removableBlocks++;
            }
            this.blocks.add(block);
        }
        this.numberOfBlocksToRemove = removableBlocks; //must remove all the blocks.

        List<Point> ballsCenter = new ArrayList<>();

        for (int i = 0; i < this.numberOfBalls; i++) {
            ballsCenter.add(new Point(screenWidth / 2, screenHeight * 0.85));

        }
        List<Velocity> ballsVelocity = new ArrayList<>();
        for (int i = 0; i < this.numberOfBalls; i++) {
            if (i % 2 == 0) {
                ballsVelocity.add(Velocity.fromAngleAndSpeed(-4 * ((i + 1) * 2), 8));
            } else {
                ballsVelocity.add(Velocity.fromAngleAndSpeed(4 * ((i + 1) * 2), 8));
            }

        }
        this.ballsFactory = new BallsFactory(ballsCenter, ballsRadius, ballsColor,
                screenWidth, screenHeight, ballsVelocity);

        //bodies
        //RainBow
        Point rainBowCenter = new Point(screenWidth / 2, screenHeight / 1.2);
        Color[] rainBowColors = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue,
                new Color(0.098f, 0.098f, 0.439f), new Color(0.729f, 0.333f, 0.827f),
                background.getColor()};
        for (int i = 0; i < 8; i++) {
            Circle r = new Circle(rainBowCenter, (int) (screenHeight * (0.825 - (i * 0.01))), rainBowColors[i]
                    , "fill");
            this.bodies.add(r);
        }


        //beans
        for (int i = 0; i < 90; i++) {
            Streak s = new Streak(screenWidth / 7, (int) (screenHeight / 4.2), (surroundingBlocksWidth + 10 * i)
                    , (int) (screenHeight * 0.45), Color.yellow);
            this.bodies.add(s);
        }

        Circle c1 = new Circle(screenWidth / 7, screenHeight / 4.2, 60, new Color(1.0f, 1.0f, 0.00f)
                , "fill");
        Circle c2 = new Circle(screenWidth / 7, screenHeight / 4.2, 50, new Color(1.0f, 0.9f, 0.00f)
                , "fill");
        Circle c3 = new Circle(screenWidth / 7, screenHeight / 4.2, 40, new Color(1.0f, 0.8f, 0.00f)
                , "fill");
        this.bodies.add(c1);
        this.bodies.add(c2);
        this.bodies.add(c3);
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
