import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * level 3 class.
 */
public class Level3 implements LevelInformation {
    private int screenWidth;
    private int screenHeight;
    private int numberOfBalls;
    private int paddleSpeed;
    private List<Point> ballsCenter;
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
    public Level3(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.blocks = new ArrayList<>();
        this.ballsCenter = new ArrayList<>();
        this.bodies = new ArrayList<>();
        this.initialize();

    }

    /**
     * initialize the sprites and the bodies.
     */
    private void initialize() {

        this.levelName = "Green 3";
        this.numberOfBalls = 2;
        int ballsRadius = 5;
        Color ballsColor = Color.white;
        this.paddleHeight = (screenHeight / 27);
        this.paddleWidth = (screenWidth / 8);
        this.paddleSpeed = 11;
        int surroundingBlocksWidth = 30;
        this.paddleColor = new Color(1.0f, 0.699f, 0.000f);
        this.paddleUpperLeft = new Point(screenWidth / 2.2, screenHeight - surroundingBlocksWidth
                - this.paddleHeight);
        Color backgroundColor = new Color(0.000f, 0.420f, 0.000f);
        this.background = new Block(new Rectangle(new Point(0, 0), screenWidth + 1
                , screenHeight + 2), backgroundColor);
        this.background.setNumber(-1);

        Color[] colors = {Color.darkGray, Color.red, Color.yellow, Color.blue, Color.white};
        int blocksRowsNum = 5;
        int blocksInFirstRow = 10;
        int blocksWidth = screenWidth / 16;
        int blockHeight = screenHeight / 20;
        int firstBlockYlocation = screenHeight / 4;
        int removableBlocks = 0;
        Block[][] stairs = new Block[blocksRowsNum][];
        //blocks initialize.
        for (int i = 0; i < blocksRowsNum; i++, blocksInFirstRow--) {
            int number;
            for (int j = 0; j < blocksInFirstRow; j++) {
                if (i == 0) {
                    number = 2; //top row.
                } else {
                    number = 1;
                }
                stairs[i] = new Block[blocksInFirstRow];
                Point upperLeft = new Point(screenWidth - (blocksWidth * (j + 1)) - surroundingBlocksWidth
                        , firstBlockYlocation + (blockHeight * i));
                stairs[i][j] = new Block(new Rectangle(upperLeft, blocksWidth, blockHeight), colors[i % 5]);
                if (stairs[i][j].getHitPoints() != -2 && stairs[i][j].getHitPoints() != -3) { // not death or live block
                    stairs[i][j].setNumber(number);
                    removableBlocks++;
                }
                this.blocks.add(stairs[i][j]);
            }

        }
        this.numberOfBlocksToRemove = (int) (removableBlocks * 0.5); //must destroy half of them.
        this.ballsCenter.add(new Point(screenWidth / 1.93, screenHeight * 0.9));
        this.ballsCenter.add(new Point(screenWidth / 1.93, screenHeight * 0.9));
        List<Velocity> v = new ArrayList<>();
        v.add(Velocity.fromAngleAndSpeed(45, 9));
        v.add(Velocity.fromAngleAndSpeed(-45, 9));
        this.ballsFactory = new BallsFactory(this.ballsCenter, ballsRadius, ballsColor,
                screenWidth, screenHeight, v);

        //bodies.
        //tower.
        Block base = new Block(new Rectangle(new Point(surroundingBlocksWidth + screenWidth / 16, screenHeight * 0.7)
                , screenWidth / 6, screenHeight * 0.3), Color.darkGray);
        base.setNumber(-1);
        this.bodies.add(base);
        Block base2 = new Block(new Rectangle(new Point(base.getCollisionRectangle().getUpperLine()
                .middle().getX() - base.getWidth() / 6
                , base.getCollisionRectangle().getUpperLeft().getY() - base.getHeight() / 2.5), base.getWidth() / 3
                , base.getHeight() / 2.5), Color.gray);
        base2.setNumber(-1);
        this.bodies.add(base2);
        Block pole = new Block(new Rectangle(new Point(base2.getCollisionRectangle().getUpperLine().middle().getX()
                - base2.getWidth() / 6, base2.getCollisionRectangle().getUpperLeft().getY() - screenHeight / 3)
                , base2.getWidth() / 3, screenHeight / 3), Color.lightGray);
        pole.setNumber(-1);
        this.bodies.add(pole);

        double windowWidth = base.getWidth() / 10;
        double windowHeight = base.getHeight() / 7;
        double b = base.getWidth() / 12;
        double h = base.getHeight() / 20;
        for (int i = 0; i < 6; i++) { //windows of tower.
            for (int j = 0; j < 5; j++) {
                Block window = new Block(new Rectangle(new Point(base.getCollisionRectangle().getUpperLeft().getX() + b
                        + (windowWidth + b) * j,
                        base.getCollisionRectangle().getUpperLeft().getY() + h + (windowHeight + h) * i), windowWidth
                        , windowHeight), Color.white);
                window.setNumber(-1);
                this.bodies.add(window);
            }
        }

        //circles on the top of the tower.
        Circle c1 = new Circle(pole.getCollisionRectangle().getUpperLine().middle().getX()
                , pole.getCollisionRectangle().getUpperLine().middle().getY() - 17, 17, Color.black, "fill");
        Circle c2 = new Circle(pole.getCollisionRectangle().getUpperLine().middle().getX()
                , pole.getCollisionRectangle().getUpperLine().middle().getY() - 17, 12, Color.darkGray, "fill");
        Circle c3 = new Circle(pole.getCollisionRectangle().getUpperLine().middle().getX()
                , pole.getCollisionRectangle().getUpperLine().middle().getY() - 17, 7, Color.red, "fill");
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
