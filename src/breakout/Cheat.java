package breakout;

import javafx.scene.Group;

import java.util.List;

/**
 * A class that implements the cheats for this game.
 */
public class Cheat {
    private Group myRoot;
    private Paddle myPaddle;
    private List<Brick> myBricks;
    private Bouncer myBouncer;
    private List<Life> myLives;

    public Cheat (Group root, Paddle paddle, List<Brick> bricks, Bouncer bouncer, List<Life> lives) {
        myRoot = root;
        myPaddle = paddle;
        myBricks = bricks;
        myBouncer = bouncer;
        myLives = lives;
    }

    /**
     * Cheat to scale the length the paddle.
     */
    public void scalePaddle (double scale) {
        myPaddle.reset(myPaddle.getView().getX() + (1 - scale) / 2 * myPaddle.getView().getFitWidth(), myPaddle.getView().getY(), scale * myPaddle.getView().getFitWidth(), myPaddle.getView().getFitHeight());
    }

    /**
     * Cheat to reset the length of the paddle to its default.
     */
    public void resetPaddleLength () {
        double scale = Paddle.PADDLE_WIDTH / myPaddle.getView().getFitWidth();
        scalePaddle(scale);
    }
    /**
     * Remove all the bricks and therefore jump to the next level.
     */
    public void skip () {
        for (Brick brick : myBricks) {
            if (brick.getExists()) {
                brick.remove();
            }
        }
    }

    /**
     * Speed up the ball.
     */
    public void speedUp () {
        myBouncer.scaleVelocity(Breakout.UPSCALE);
    }

    /**
     * Speed down the ball.
     */
    public void speedDown () {
        myBouncer.scaleVelocity(Breakout.DOWNSCALE);
    }

    public void addLife () {
        Life newLife = new Life(myLives.size());
        myLives.add(newLife);
        myRoot.getChildren().add(newLife.getView());
    }
}
