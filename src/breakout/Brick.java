package breakout;

import javafx.scene.Group;
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
    public static final String PERMANENT_BRICK_IMAGE = "brick7.gif";
    public static final double POWERUP_PROBABILITY = 0.1;
    public static final double HARD_PROBABILITY = 0.2;
    public static final double PERMANENT_PROBABILITY = 0.05;
    public static final int NORMAL_HEALTH = 1;
    public static final int HARD_HEALTH = 3;
    public static final int PERMANENT_HEALTH = Integer.MAX_VALUE;

    private Random dice;
    private ImageView myView;
    private boolean hasPowerup;
    private boolean isHard;
    private boolean exists;
    private boolean isPermanent;
    private int health;


    /**
     * Create a bouncer from a given image with random attributes.
     */
    public Brick (double x, double y, double brickWidth, double brickHeight, double probability) {
        dice = new Random();
        exists = dice.nextDouble() < probability;
        hasPowerup = exists && dice.nextDouble() < POWERUP_PROBABILITY;
        isHard = exists && dice.nextDouble() < HARD_PROBABILITY;
        isPermanent = exists && dice.nextDouble() < PERMANENT_PROBABILITY;
        generateView(x, y, brickWidth, brickHeight);
    }

    /**
     * Generate the view or image for different kinds of bricks. There are three appearances of a brick, has powerup and ishard, has powerup and is not hard, and does not have powerup and is hard.
     */
    private void generateView (double x, double y, double brickWidth, double brickHeight) {
        // Only creates myView if events happen with the probability.
        if (exists) {
            // Creates three kinds of bricks, powerups, hard and permanent bricks.
            var brickImage = NORMAL_BRICK_IMAGE;
            health = NORMAL_HEALTH;
            if (isHard && hasPowerup) {
                brickImage = HARD_AND_POWERUP_BRICK_IMAGE;
                health = HARD_HEALTH;
            }
            else if (isHard && !hasPowerup) {
                brickImage = HARD_BRICK_IMAGE;
                health = HARD_HEALTH;
            }
            else if (!isHard && hasPowerup) {brickImage = POWERUP_BRICK_IMAGE;}
            if (isPermanent) {
                brickImage = PERMANENT_BRICK_IMAGE;
                health = PERMANENT_HEALTH;
            }
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

    /**
     * Emulates the block being hit exactly once
     */
    public void getHit() {
        health -= 1;
        if (health == 0) {
            ((Group)myView.getParent()).getChildren().remove(myView);
            myView = null;
            isHard = false;
            hasPowerup = false;
            exists = false;
        }
    }
}
