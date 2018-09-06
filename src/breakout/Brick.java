package breakout;

import javafx.geometry.Point2D;
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
    public static final String POWERUP_BRICK = "brick3.gif";
    public static final double POWERUP_PROBABILITY = 0.1;
    public static final double HARD_BRICK_PROBABILITY = 0.2;

    private Random dice = new Random();
    private ImageView myView;
    private boolean hasPowerup;
    private boolean exists;
    private int xCoordinate;
    private int yCoordinate;
    private int myBrickWidth;
    private int myBrickHeight;
    private double myProbability;


    /**
     * Create a bouncer from a given image with random attributes.
     */
    public Brick (int x, int y, int brickWidth, int brickHeight, double probability) {
        xCoordinate = x;
        yCoordinate = y;
        myBrickHeight = brickHeight;
        myBrickWidth = brickWidth;
        myProbability = probability;
        exists = dice.nextDouble() > myProbability;
        hasPowerup = dice.nextDouble() < POWERUP_PROBABILITY;
        generateView();
    }

    /**
     * Generate the view or image for different kinds of bricks
     */
    private void generateView () {
        if (exists) {
            if ()
                    myView = new Image(this.getClass().getClassLoader().getResourceAsStream(brickImage));
        }
    }

    /**
     * Move by taking one step based on its velocity.
     *
     * Note, elapsedTime is used to ensure consistent speed across different machines.
     */
    public void move (double elapsedTime) {
        myView.setX(myView.getX() + myVelocity.getX() * elapsedTime);
        myView.setY(myView.getY() + myVelocity.getY() * elapsedTime);
    }

    /**
     * Bounce off the walls represented by the edges of the screen.
     */
    public void bounce (double screenWidth, double screenHeight) {
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (myView.getY() < 0 || myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public Node getView () {
        return myView;
    }

    // Returns an "interesting", non-zero random value in the range (min, max)
    private int getRandomInRange (int min, int max) {
        return min + dice.nextInt(max - min) + 1;
    }
}
