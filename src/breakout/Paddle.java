package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The paddle that the user creates to bounce the ball.
 */
public class Paddle {
    public static final double PADDLE_WIDTH = 0.05 * Breakout.WIDTH;
    public static final double PADDLE_HEIGHT = 0.01 * Breakout.HEIGHT;

    private ImageView myView;

    public Paddle (Image paddleImage, double width, double height) {
        // Set the ImageView object of the paddle for interaction with the bouncer.
        myView = new ImageView(paddleImage);
        // Set the size of the paddle.
        myView.setFitHeight(PADDLE_HEIGHT);
        myView.setFitWidth(PADDLE_WIDTH);
        // Set the starting position of the paddle.
        myView.setX(width / 2 -PADDLE_WIDTH / 2);
        myView.setY(height - PADDLE_HEIGHT);
    }
}
