package breakout;

import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CenterText {
    public static final String WINNING_TEXT = "You Won! Press Space to Restart";
    public static final String LOSING_TEXT = "You Lost! Press Space to Restart";
    public static final String STARTING_TEXT = "Press Space to Start";
    public static final String ENDING_TEXT = "Game Over! Press Space to Restart";
    public static final double XCOORDINATE = 0.25 * Breakout.WIDTH;
    public static final double YCOORDINATE = 0.7 * Breakout.HEIGHT;
    public static final double FONT_SIZE = 0.03 * Breakout.WIDTH;

    private Text text;

    /**
     * Set the Text object for the CenterText class, putting the message on top of all other nodes and in the center of the screen.
     * @param message
     */
    public CenterText (String message) {
        text = new Text();
        text.setText(message);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE));
        text.setX(XCOORDINATE);
        text.setY(YCOORDINATE);
        text.toFront();
    }

    /**
     * Return the Text object for interaction with other javafx nodes.
     * @return
     */
    public Text getTextNode () {
        return text;
    }

    /**
     * Remove the Text object from its parent.
     */
    public void clear () {
        if (text != null) {
            ((Group) text.getParent()).getChildren().remove(text);
        }
    }
}
