import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;


/**
 * A GameLevel.
 * hold the sprites and the collidables, and will be in charge of the animation.
 *
 * @author : Ganaiem Hosny
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter remainedBlocks;
    private Counter remainedBalls;
    private Counter score;
    private Counter numOfLives;
    private int screenWidth;
    private int screenHeight;
    private int flag = 0;
    private Paddle paddle;
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation l;
    private KeyboardSensor ks;


    /**
     * Constructor.
     *
     * @param l            : specific level.
     * @param ar           : animation runner.
     * @param ks           : keyboard sensor.
     * @param gui          : screen.
     * @param score        : initial score of the game.
     * @param screenWidth  : screen width.
     * @param screenHeight : screen height.
     */
    public GameLevel(LevelInformation l, AnimationRunner ar, KeyboardSensor ks, GUI gui
            , Counter score, int screenWidth, int screenHeight) {
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.remainedBlocks = new Counter();
        this.remainedBalls = new Counter();
        this.score = score;
        this.gui = gui;
        this.runner = ar;
        this.ks = ks;
        this.l = l;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.numOfLives = new Counter();
        this.numOfLives.increase(7);
    }

    /**
     * add collidable to the game's environment.
     *
     * @param c : collidable to be add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add Sprite to the game's sprites.
     *
     * @param s : Sprite to be add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * creating surroundingBlocks in the game , all are equal in width and color.
     *
     * @param thickness        : surroundingBlocks width.
     * @param color            : surroundingBlocks color.
     * @param scoreBlockHeight : score block height.
     * @param br               : ball remover listener.
     */
    private void surroundingBlocks(double thickness, java.awt.Color color, double scoreBlockHeight, BallRemover br) {
        //initialize the array of the four blocks.
        Block[] surroundingBlocks = new Block[4];
        //creating the blocks according to screen lengths.
        surroundingBlocks[0] = new Block(new Rectangle(new Point(0, scoreBlockHeight), screenWidth, thickness), color);
        surroundingBlocks[1] = new Block(new Rectangle(new Point(thickness, screenHeight)
                , screenWidth - thickness * 2, thickness), color);
        surroundingBlocks[2] = new Block(new Rectangle(new Point(0, thickness + scoreBlockHeight), thickness
                , screenHeight - thickness - scoreBlockHeight), color);
        surroundingBlocks[3] = new Block(new Rectangle(new Point(screenWidth - thickness
                , thickness + scoreBlockHeight), thickness, screenHeight - thickness - scoreBlockHeight), color);


        // adding the blocks to the game and give him a number.
        for (int i = 0; i < surroundingBlocks.length; i++) {
            if (i == 1) {
                surroundingBlocks[i].setNumber(-2);
            } else {
                surroundingBlocks[i].setNumber(-1);
            }
            surroundingBlocks[i].addToGame(this);
            surroundingBlocks[i].addHitListener(br);
        }
    }


    /**
     * Initialize a new game: create the Blocks and two balls (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        int ballsNum = this.l.numberOfBalls();
        int paddleWidth = this.l.paddleWidth();
        int paddleHeight = this.l.getPaddleHeight();
        int paddleSpeed = this.l.paddleSpeed();
        Point paddleUpperLeft = this.l.getPaddleUpperLeft();
        Color paddleColor = this.l.getPaddleColor();

        double surroundingBlocksThickness = 30;
        Color surroundingBlocksColor = Color.gray;
        int scoreBlockHeight = (int) surroundingBlocksThickness;
        if (this.flag == 0) {
            Block background = (Block) this.l.getBackground();
            background.addToGame(this);
            List<Sprite> bodies = this.l.getBodies();
            for (int i = 0; i < bodies.size(); i++) {
                this.addSprite(bodies.get(i));
            }
        }
        this.remainedBalls.increase(ballsNum);
        BlockRemover listener1 = new BlockRemover(this, this.remainedBlocks);
        BallRemover listener2 = new BallRemover(this, this.remainedBalls);
        ScoreTrackingListener listener3 = new ScoreTrackingListener(this.score);


        this.surroundingBlocks(surroundingBlocksThickness, surroundingBlocksColor, scoreBlockHeight, listener2);

        BallsFactory f1 = this.l.getBallsFactory();
        List<Ball> balls = f1.createBalls(this.l.numberOfBalls());
        for (int i = 0; i < this.l.numberOfBalls(); i++) {
            balls.get(i).addToGame(this);
            balls.get(i).setEnvironment(this.environment);
        }
        BallAdder listener4 = new BallAdder(this, this.remainedBalls, f1);

        if (flag == 0) { //blocks are stay
            List<Block> blocks = new ArrayList<>(this.l.blocks());
            for (int i = 0; i < blocks.size(); i++) {
                blocks.get(i).addToGame(this);
                blocks.get(i).addHitListener(listener1);
                blocks.get(i).addHitListener(listener2);
                blocks.get(i).addHitListener(listener3);
                blocks.get(i).addHitListener(listener4);

            }
            this.remainedBlocks.increase(this.l.numberOfBlocksToRemove());
            this.paddle = new Paddle(new Rectangle(paddleUpperLeft, paddleWidth, paddleHeight), paddleColor
                    , this.ks, paddleSpeed);

            this.paddle.setBoundaries(surroundingBlocksThickness, screenWidth);
            this.paddle.addToGame(this);
            this.flag++;
        } else if (flag != 0) { //re position the paddle to center.
            this.paddle.removeFromGame(this);
            this.paddle = new Paddle(new Rectangle(paddleUpperLeft, paddleWidth, paddleHeight), paddleColor
                    , this.ks, paddleSpeed);
            paddle.setBoundaries(surroundingBlocksThickness, screenWidth);
            paddle.addToGame(this);
        }

        Block scoreBlock = new Block(new Rectangle(new Point(0, 0), screenWidth, scoreBlockHeight), Color.white);
        scoreBlock.addToGame(this);
        scoreBlock.setNumber(-1);
        ScoreIndicator s = new ScoreIndicator(this.score, scoreBlock.getWidth(), scoreBlock.getHeight());
        LivesIndicator livesIndicator = new LivesIndicator(this.numOfLives, scoreBlock.getWidth()
                , scoreBlock.getHeight());
        LevelNameIndicator n = new LevelNameIndicator(this.l.levelName(), scoreBlock.getWidth()
                , scoreBlock.getHeight());
        s.addToGame(this);
        livesIndicator.addToGame(this);
        n.addToGame(this);


    }

    /**
     * draw the sprites and the bodies.
     *
     * @param d : given surface.
     */
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (this.remainedBlocks.getValue() == 0) { //100 point after destroy all the blocks.
            this.score.increase(100);
            this.running = false;
        }
        if (this.remainedBalls.getValue() == 0) { //-1 live if all the ball fallen down.
            this.numOfLives.decrease(1);
            this.running = false;
        }
        if (this.ks.isPressed("p")) { //pause the screen.
            this.runner.run(new PauseScreen(this.ks));
        }

    }

    /**
     * run the game , start the animation loop.
     */
    public void playOneTurn() {

        this.runner.run(new CountdownAnimation(2, 3, this.sprites, screenWidth, screenHeight));
        this.running = true;
        this.runner.run(this);
        this.initialize();
    }

    /**
     * remove given collidable.
     *
     * @param c : collidable will be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * remove given sprite from the collection.
     *
     * @param s : sprite will be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * set remained blocks in the game.
     *
     * @param newRemainedBlocks : remained blocks counter.
     */
    public void setRemainedBlocks(Counter newRemainedBlocks) {
        this.remainedBlocks = newRemainedBlocks;
    }

    /**
     * set reamined balls in the game.
     *
     * @param newRemainedBalls : remained balls counter.
     */
    public void setRemainedBalls(Counter newRemainedBalls) {
        this.remainedBalls = newRemainedBalls;
    }

    /**
     * @return : game environment ( collidables collection).
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * @return true if should stop , false other wise.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * @return counter of remained blocks in the game.
     */
    public Counter getRemainedBlocks() {
        return this.remainedBlocks;
    }

    /**
     * @return current lives in the game.
     */
    public Counter getNumOfLives() {
        return this.numOfLives;
    }

    /**
     * @return current score.
     */
    public Counter getScore() {
        return this.score;
    }
}