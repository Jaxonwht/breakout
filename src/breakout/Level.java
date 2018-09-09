package breakout;

import javafx.scene.text.Text;

public class Level {
    private Text text;
    private int myLevel;

    public Level (int level) {
        myLevel = level;
        text = new Text();
        text.setText("Level: " + myLevel);
        text.setX(Breakout.WIDTH - 2 * Breakout.TOP_ROW);
        text.setY(0);
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
}
