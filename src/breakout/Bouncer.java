package breakout;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;


/**
 * Simple bouncer based on an image that moves and bounces.
 *
 * @author Robert C. Duvall
 */
public class Bouncer implements  Circular{
    public static final double BOUNCER_SPEED = 0.2 * Math.min(Breakout.WIDTH, Breakout.HEIGHT);
    public static final double BOUNCER_SIZE = 0.01 * Math.min(Breakout.WIDTH, Breakout.HEIGHT);

    private ImageView myView;
    private Point2D myVelocity;
    private Random dice;

    /**
     * Create a bouncer from a given image with random attributes.
     */
    public Bouncer (Image image) {
        dice = new Random();
        myView = new ImageView(image);
        // Initializes its geometry and velocity.
        reset();
    }

    /**
     * Set the geometry and velocity of the bouncer to its default.
     */
    public void reset () {
        // make sure it stays a circle
        myView.setFitWidth(BOUNCER_SIZE);
        myView.setFitHeight(BOUNCER_SIZE);
        // make sure it stays within the bounds
        myView.setX(Breakout.WIDTH / 2 - BOUNCER_SIZE / 2);
        myView.setY(Breakout.HEIGHT - BOUNCER_SIZE - Paddle.PADDLE_HEIGHT);
        // turn speed into velocity that can be updated on bounces
        setVelocity(getRandomInRange(0.5 * BOUNCER_SPEED, 1.5 * BOUNCER_SPEED), -1 * BOUNCER_SPEED);
    }

    /**
     * Scale the velocity of the ball by a factor.
     * @param scale
     */
    public void scaleVelocity (double scale) {
        myVelocity = new Point2D(scale * myVelocity.getX(), scale * myVelocity.getY());
    }

    /**
     * Set velocity for the Bouncer object.
     */
    public void setVelocity (double x, double y) {
        myVelocity = new Point2D(x, y);
    }

    /**
     * Get velocity for the Bouncer object.
     */
    public Point2D getVelocity () {
        return myVelocity;
    }

    /**
     * Reverse the velocity of the bouncer in the x direction.
     */
    public void reverseXDirection () {
        myVelocity = new Point2D(-1 * myVelocity.getX(), myVelocity.getY());
    }

    /**
     * Reverse the velocity of the bouncer in the y direction.
     */
    public void reverseYDirection () {
        myVelocity = new Point2D(myVelocity.getX(), -1 * myVelocity.getY());
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public ImageView getView () {
        return myView;
    }

    /**
     * Returns an "interesting", non-zero random value in the range (min, max)
     * @param min
     * @param max
     * @return
     */
    private double getRandomInRange (double min, double max) {
        double temp = min + dice.nextDouble() * (max - min);
        if (dice.nextDouble() < 0.5) {
            return temp;
        }
        else {
            return -1 * temp;
        }
    }
}
