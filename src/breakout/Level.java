package breakout;

import javafx.scene.text.Text;

public class Level {
    public static final double XCOORDINATE = Breakout.WIDTH - 2.5 * Breakout.TOP_ROW;
    public static final double YCOORDINATE = 0.8 * Breakout.TOP_ROW;

    private Text text;
    private int myLevel;

    public Level (int level) {
        myLevel = level;
        text = new Text();
        text.setText("Level: " + myLevel);
        text.setX(XCOORDINATE);
        text.setY(YCOORDINATE);
    }

    /**
     * Return the Text object of the level class.
     * @return
     */
    public Text getTextNode () {
        return text;
    }

    /**
     * Return the level int value.
     * @return
     */
    public int getLevel () {
        return myLevel;
    }

    /**
     * Increases the level of the game.
     */
    public void increaseLevel () {
        myLevel += 1;
    }
}
