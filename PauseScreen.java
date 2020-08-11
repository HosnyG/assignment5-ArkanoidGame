import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * PauseScreen class.
 * pause the screen and display a appropriate message on the screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Constructor.
     *
     * @param k : keyboard sensor.
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * draw pause message on the screen.
     *
     * @param d : given surface.
     */
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.darkGray);
        d.drawCircle(400, 300, 105);
        d.setColor(Color.white);
        d.fillCircle(400, 300, 104);
        d.setColor(Color.blue);
        d.fillCircle(400, 300, 96);
        d.setColor(Color.gray);
        d.fillCircle(400, 300, 93);
        d.setColor(Color.black);
        d.fillCircle(400, 300, 90);
        d.setColor(Color.blue);
        d.fillRectangle(350, 242, 40, 115);
        d.fillRectangle(410, 242, 40, 115);
        d.setColor(Color.BLUE);
        d.drawText(247, 472, "Press space to continue", 30);
        d.setColor(Color.black);
        d.drawText(245, 470, "Press space to continue", 30);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    /**
     * @return true if pressed continue , flase otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}