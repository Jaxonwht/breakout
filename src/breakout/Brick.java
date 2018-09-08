package breakout;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;


/**
 * The destructible brick used in the game. They can reflect balls too.
 */
public class Brick {
    public static final String NORMAL_BRICK_IMAGE = "brick1.gif";
    public static final String HARD_BRICK_IMAGE = "brick2.gif";
    public static final String POWERUP_BRICK_IMAGE = "brick3.gif";
    public static final String HARD_AND_POWERUP_BRICK_IMAGE = "brick4.gif";
    public static final double POWERUP_PROBABILITY = 0.1;
    public static final double HARD_PROBABILITY = 0.2;

    private Random dice = new Random();
    private ImageView myView;
    private boolean hasPowerup;
    private boolean isHard;
    private boolean exists;


    /**
     * Create a bouncer from a given image with random attributes.
     */
    public Brick (double x, double y, double brickWidth, double brickHeight, double probability) {
        exists = dice.nextDouble() < probability;
        hasPowerup = exists && dice.nextDouble() < POWERUP_PROBABILITY;
        isHard = exists && dice.nextDouble() < HARD_PROBABILITY;
        generateView(x, y, brickWidth, brickHeight);
    }

    /**
     * Generate the view or image for different kinds of bricks. There are three appearances of a brick, has powerup and ishard, has powerup and is not hard, and does not have powerup and is hard.
     */
    private void generateView (double x, double y, double brickWidth, double brickHeight) {
        // Only creates myView if events happen with the probability.
        if (exists) {
            var brickImage = NORMAL_BRICK_IMAGE;
            if (isHard && hasPowerup) {brickImage = HARD_AND_POWERUP_BRICK_IMAGE;}
            else if (isHard && !hasPowerup) {brickImage = HARD_BRICK_IMAGE;}
            else if (!isHard && hasPowerup) {brickImage = POWERUP_BRICK_IMAGE;}
            myView = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(brickImage)));
            // Set the position of the created brick.
            myView.setX(x);
            myView.setY(y);
            // Set the size of the created brick.
            myView.setFitHeight(brickHeight);
            myView.setFitWidth(brickWidth);
        }
        else {
            myView = null;
        }
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public Node getView () {
        return myView;
    }

    /**
     * Returns the existence status of the brick.
     */
    public boolean getExists () {return exists;}

    /**
     * Returns whether the brick is hard.
     */
    public boolean getIsHard () {return isHard;}

    /**
     * Returns whether the brick has powerup.
     */
    public boolean getHasPowerup () {return hasPowerup;}
}
