import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * loop that run animation class.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;
    private KeyboardSensor ks;

    /**
     * Constructor.
     *
     * @param gui : gui.
     * @param ks  : keyboard sensor.
     */
    public AnimationRunner(GUI gui, KeyboardSensor ks) {
        this.framesPerSecond = 60;
        this.sleeper = new biuoop.Sleeper();
        this.gui = gui;
        this.ks = ks;
    }

    /**
     * loop that runs the animation .
     *
     * @param animation : animation will be run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        do {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            animation.doOneFrame(d);
            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        } while (!animation.shouldStop());
    }
}