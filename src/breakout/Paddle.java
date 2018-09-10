package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The paddle that the user creates to bounce the ball.
 */
public class Paddle {
    public static final double PADDLE_WIDTH = 0.1 * Breakout.WIDTH;
    public static final double PADDLE_HEIGHT = 0.01 * Breakout.HEIGHT;

    private ImageView myView;

    /**
     * Construct a Paddle object looking like paddleImage, and in a screen of width width and height height.
     * @param paddleImage
     */
    public Paddle (Image paddleImage) {
        // Set the ImageView object of the paddle for interaction with the bouncer.
        myView = new ImageView(paddleImage);
        // Set the geometry of the paddle.
        reset(Breakout.WIDTH / 2 - PADDLE_WIDTH / 2, Breakout.HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    /**
     * Return an ImageView object of the paddle object for interaction with other JavaFx nodes.
     * @return
     */
    public ImageView getView () {
        return myView;
    }

    /**
     * Set the paddle to its original geometry.
     */
    public void reset (double x, double y, double width, double height) {
        // Set the size of the paddle.
        myView.setFitHeight(height);
        myView.setFitWidth(width);
        // Set the starting position of the paddle.
        myView.setX(x);
        myView.setY(y);
    }
}
