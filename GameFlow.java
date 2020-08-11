import biuoop.DrawSurface;
import biuoop.GUI;
import java.util.List;
import java.awt.Color;
import biuoop.KeyboardSensor;

/**
 * moving from one level to the next in the game.
 */
public class GameFlow {
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private GUI gui;
    private Counter score;
    private int screenWidth;
    private int screenHeight;


    /**
     * Constructor.
     *
     * @param ar           : animation runner.
     * @param ks           : keyboard sensor.
     * @param gui          : screen.
     * @param screenWidth  : screen width.
     * @param screenHeight : screen height.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, int screenWidth, int screenHeight) {
        this.ar = ar;
        this.ks = ks;
        this.gui = gui;
        this.score = new Counter();
        this.score.increase(0); //initial score.
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;


    }

    /**
     * runs the levels.
     *
     * @param levels : list of levels.
     */
    public void runLevels(List<LevelInformation> levels) {
        DrawSurface d = this.gui.getDrawSurface();
        int counter = 0;

        for (LevelInformation levelInfo : levels) { //runs the levels.

            GameLevel level = new GameLevel(levelInfo, this.ar, this.ks, this.gui, this.score, screenWidth
                    , screenHeight);
            level.initialize();


            while (level.getRemainedBlocks().getValue() > 0 && level.getNumOfLives().getValue() >= 0) {
                level.playOneTurn();
            }
            counter++;
            //game over message.
            if (level.getNumOfLives().getValue() == -1) { //the lives ends.
                d.setColor(new Color(0.902f, 0.902f, 0.980f));
                d.fillRectangle(0, 0, screenWidth, screenHeight);
                d.setColor(new Color(0.345f, 0.000f, 0.000f));
                d.drawText((screenWidth / 5), this.screenHeight / 2, "Game Over", 100);
                d.drawText(this.screenWidth / 7, (int) (this.screenHeight * 0.66), "Your score is "
                        + level.getScore()
                        .getValue(), 80);
                d.drawText((int) (screenWidth * 0.01), (int) (screenHeight * 0.98), "Press space to exit", 20);
                this.gui.show(d);
                break;
            }
            //win message.
            if (counter == levels.size()) {
                d.setColor(new Color(0.902f, 0.902f, 0.980f));
                d.fillRectangle(0, 0, screenWidth, screenHeight);
                d.setColor(new Color(0.196f, 0.804f, 0.196f));
                d.drawText((int) (screenWidth / 4.2), this.screenHeight / 2, "You Win !", 100);
                d.drawText((int) (this.screenWidth / 7.5), (int) (this.screenHeight * 0.66), "Your score is "
                        + level.getScore()
                        .getValue(), 80);
                d.drawText((int) (screenWidth * 0.01), (int) (screenHeight * 0.98), "Press space to exit", 20);
                this.gui.show(d);
                break;
            }

        }

        //close the screen if pressed space key after win or lost message.
        while (true) {
            if (this.ks.isPressed(ks.SPACE_KEY)) {
                this.gui.close();
            }
        }

    }

}