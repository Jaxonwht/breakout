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
    public static final double SCALE_PROBABILITY = 0.1;
    public static final double UPSCALE = 1.1;
    public static final double DOWNSCALE = 1.1;

    private Random dice;
    private ImageView myView;
    private boolean hasPowerup;
    private boolean isHard;
    private boolean exists;
    private boolean isPermanent;
    private boolean isUpscale;
    private boolean isDownscale;
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
        if (exists && dice.nextDouble() < SCALE_PROBABILITY) {
            if (dice.nextDouble() < 0.5) {
                isUpscale = true;
                isDownscale = false;
            }
            else {
                isUpscale = false;
                isDownscale = false;
            }
        }
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
     * Return whether the brick is permanent.
     * @return
     */
    public boolean getIsPermanent () {return isPermanent;}

    /**
     * Get whether the brick accelerates the ball.
     * @return
     */
    public boolean getIsUpscale () {return isUpscale;}

    /**
     * Return whether the brick decelerates the ball.
     * @return
     */
    public boolean getIsDownscale () {return isDownscale;}

    /**
     * Remove the brick.
     */
    public void remove() {
        setImage(null);
        isHard = false;
        hasPowerup = false;
        exists = false;
        isDownscale = false;
        isUpscale = false;
        isPermanent = false;
    }

    /**
     * Emulates the block being hit exactly once
     */
    public void getHit() {
        health -= 1;
        if (health == NORMAL_HEALTH) {
            if (isHard && hasPowerup) {
                setImage(POWERUP_BRICK_IMAGE);
                isHard = false;
            }
            else if (isHard && !hasPowerup) {
                setImage(NORMAL_BRICK_IMAGE);
                isHard = false;
            }
        }
        else if (health == 0) {
            remove();
        }
    }

    /**
     * Change the image of a brick without altering the position and size of the brick.
     * @param brickImage
     */
    private void setImage (String brickImage){
        // Store the geometry of the current brick in temporary variables so that can be used to create a new brick that has the same geometry.
        double tempX = myView.getX();
        double tempY = myView.getY();
        double tempWidth = myView.getFitWidth();
        double tempHeight = myView.getFitHeight();
        // Create a reference to the root node so as to replace the old ImageView node.
        Group root = (Group) myView.getParent();
        // Replace the old ImageView with the new ImageView.
        root.getChildren().remove(myView);
        if (brickImage == null) {
            myView = null;
        }
        else {
            myView = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(brickImage)));
            myView.setX(tempX);
            myView.setY(tempY);
            myView.setFitWidth(tempWidth);
            myView.setFitHeight(tempHeight);
            root.getChildren().add(myView);
        }

    }
}
