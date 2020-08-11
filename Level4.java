import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * level 4 class.
 */
public class Level4 implements LevelInformation {

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
    public Level4(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.blocks = new ArrayList<>();
        this.bodies = new ArrayList<>();
        this.initialize();
    }

    /**
     * initialize sprites and bodies.
     */
    private void initialize() {
        this.levelName = "Final Four";
        this.numberOfBalls = 3;
        int ballsRadius = 5;
        Color ballsColor = Color.white;
        this.paddleHeight = (screenHeight / 27);
        this.paddleWidth = (screenWidth / 6);
        this.paddleSpeed = 10;
        this.paddleColor = new Color(1.0f, 0.699f, 0.000f);
        int surroundingBlocksWidth = 30;
        this.paddleUpperLeft = new Point(screenWidth / 2.5, screenHeight - surroundingBlocksWidth
                - this.paddleHeight);
        this.background = new Block(new Rectangle(new Point(0, 0), screenWidth + 1
                , screenHeight + 2), new Color(0.118f, 0.565f, 1.000f));
        this.background.setNumber(-1);

        Color[] colors = {Color.darkGray, Color.red, Color.yellow, Color.green, Color.white, Color.pink, Color.cyan};
        int blocksRowsNum = 7;
        int blocksInRow = 15;
        double blocksWidth = (screenWidth - (2 * surroundingBlocksWidth)) / (double) blocksInRow;
        int blocksHeight = (screenHeight / 20);
        int firstBlockYlocation = (int) (screenHeight / 5.5);
        int removableBlocks = 0;
        for (int i = 0; i < blocksRowsNum; i++) { //blocks initialize.
            for (int j = 0; j < blocksInRow; j++) {
                Block block = new Block(new Rectangle(new Point(surroundingBlocksWidth + (j * blocksWidth)
                        , firstBlockYlocation + (blocksHeight * i)), blocksWidth, blocksHeight), colors[i]);
                if (block.getHitPoints() != -2 && block.getHitPoints() != -3) { // not death or live block
                    block.setNumber(1);
                    removableBlocks++;
                }
                this.blocks.add(block);
            }
        }

        this.numberOfBlocksToRemove = (int) (removableBlocks * 0.5); //must destroy half of the blocks
        List<Point> ballsCenter = new ArrayList<>();
        ballsCenter.add(new Point(screenWidth * 0.35, screenHeight * 0.8));
        ballsCenter.add(new Point(screenWidth * 0.5, screenHeight * 0.75));
        ballsCenter.add(new Point(screenWidth * 0.65, screenHeight * 0.8));
        List<Velocity> v = new ArrayList<>();
        v.add(Velocity.fromAngleAndSpeed(10, 7));
        v.add(Velocity.fromAngleAndSpeed(-35, 8));
        v.add(Velocity.fromAngleAndSpeed(35, 7));


        this.ballsFactory = new BallsFactory(ballsCenter, ballsRadius, ballsColor,
                this.screenWidth, this.screenHeight, v);

        //clouds
        Circle c1 = new Circle(screenWidth / 9, screenHeight * 0.75, 30, Color.lightGray, "fill");
        Circle c2 = new Circle((screenWidth / 9) + 40, screenHeight * 0.71, 34, Color.lightGray, "fill");
        Circle c3 = new Circle((screenWidth / 9) + 20, screenHeight * 0.8, 34, Color.lightGray, "fill");
        Circle c4 = new Circle((screenWidth / 9) + 65, screenHeight * 0.79, 29, Color.lightGray, "fill");
        Circle c5 = new Circle((screenWidth / 9) + 110, screenHeight * 0.77, 31, Color.lightGray, "fill");
        Circle c6 = new Circle((screenWidth / 9) + 100, screenHeight * 0.71, 40, Color.lightGray, "fill");


        this.bodies.add(c1);
        this.bodies.add(c2);
        this.bodies.add(c3);
        this.bodies.add(c4);
        this.bodies.add(c5);
        this.bodies.add(c6);

        //rain
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Oval rain = new Oval((int) (screenWidth / 7.4) + (12 * j) + j
                        , (int) (screenHeight * 0.86) + (i * 15) - (j * 3)
                        , 4, 13, Color.blue);
                this.bodies.add(rain);

            }
        }

        //clouds
        Circle c7 = new Circle(screenWidth * 0.75, screenHeight * 0.75 + 20, 30, Color.lightGray, "fill");
        Circle c8 = new Circle(screenWidth * 0.75 + 40, screenHeight * 0.71 + 20, 34, Color.lightGray, "fill");
        Circle c9 = new Circle(screenWidth * 0.75 + 20, screenHeight * 0.8 + 20, 34, Color.lightGray, "fill");
        Circle c10 = new Circle(screenWidth * 0.75 + 65, screenHeight * 0.79 + 20, 29, Color.lightGray, "fill");
        Circle c11 = new Circle(screenWidth * 0.75 + 110, screenHeight * 0.77 + 20, 31, Color.lightGray, "fill");
        Circle c12 = new Circle(screenWidth * 0.75 + 100, screenHeight * 0.71 + 20, 40, Color.lightGray, "fill");
        this.bodies.add(c7);
        this.bodies.add(c8);
        this.bodies.add(c9);
        this.bodies.add(c10);
        this.bodies.add(c11);
        this.bodies.add(c12);

        //rain
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Oval rain = new Oval((int) (screenWidth * 0.77) + (12 * j) + j,
                        (int) (screenHeight * 0.9) + (i * 15) - (j * 3), 4, 13, Color.blue);
                this.bodies.add(rain);
            }
        }


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
