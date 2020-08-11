import biuoop.GUI;

import java.util.ArrayList;
import java.util.List;

/**
 * arkanoid game.
 *
 * @author : Ganaiem Hosny
 */
public class Ass5Game {

    /**
     * main method.
     *
     * @param args : levels number from 1 to 4 ,ignore other wise.
     */
    public static void main(String[] args) {
        int screenWidth = 800;
        int screenHeight = 600;

        GUI gui = new GUI("Arkanoid Game", screenWidth, screenHeight);
        biuoop.KeyboardSensor ks = gui.getKeyboardSensor();
        AnimationRunner ar = new AnimationRunner(gui, ks);
        GameFlow g = new GameFlow(ar, ks, gui, screenWidth, screenHeight);
        List<LevelInformation> levels = new ArrayList<>();
        if (args.length == 0) { //default levels order.
            levels.add(new Level1(screenWidth, screenHeight));
            levels.add(new Level2(screenWidth, screenHeight));
            levels.add(new Level3(screenWidth, screenHeight));
            levels.add(new Level4(screenWidth, screenHeight));
        } else { // order the levels according to the arguments.
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("1")) {
                    levels.add(new Level1(screenWidth, screenHeight));
                } else if (args[i].equals("2")) {
                    levels.add(new Level2(screenWidth, screenHeight));
                } else if (args[i].equals("3")) {
                    levels.add(new Level3(screenWidth, screenHeight));
                } else if (args[i].equals("4")) {
                    levels.add(new Level4(screenWidth, screenHeight));
                }
            }
        }
        g.runLevels(levels);
    }
}