package breakout;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * A class that contains all the static methods that determine the motion of the bouncer when interacting with various objects.
 */
public class Physics {
    public static final String COLLISION_SOUND_FILE = "pong_beep.wav";

    public static void beep () {
        String mediaFile = Physics.class.getClassLoader().getResource(COLLISION_SOUND_FILE).toExternalForm();
        MediaPlayer musicPlayer = new MediaPlayer(new Media(mediaFile));
        musicPlayer.play();
    }
    /**
     * Bounceer bounces off the walls of the scene.
     * @param b
     * @param screenWidth
     * @param screenHeight
     */
    public static void bounceWithWall (Bouncer b, double screenWidth, double screenHeight){
        if (b.getView().getX() < 0 || b.getView().getX() > screenWidth - b.getView().getBoundsInLocal().getWidth()) {
            b.setVelocity(-1 * b.getVelocity().getX(), b.getVelocity().getY());
            beep();
        }
        if (b.getView().getY() < Breakout.TOP_ROW || b.getView().getY() > screenHeight - b.getView().getBoundsInLocal().getHeight()) {
            b.setVelocity(b.getVelocity().getX(), -1 * b.getVelocity().getY());
            beep();
        }
    }

    /**
     * Bounce off bricks in the game.
     * @param bouncer
     * @param brick
     */
    public static void bounceWithBrick (Bouncer bouncer, Brick brick){
        if (brick.getExists() && bouncer.getView().getBoundsInParent().intersects(brick.getView().getBoundsInParent())) {
            if (bouncer.getView().getBoundsInParent().getMinX() >= brick.getView().getBoundsInParent().getMinX() && bouncer.getView().getBoundsInParent().getMaxX() <= brick.getView().getBoundsInParent().getMaxX()){
                bouncer.setVelocity(bouncer.getVelocity().getX(), -1 * bouncer.getVelocity().getY());
            }
            else {
                bouncer.setVelocity(bouncer.getVelocity().getX() * -1, bouncer.getVelocity().getY());
            }
            beep();
            brick.getHit();
        }
    }

    /**
     * Move by taking one step based on its velocity.
     * Note, elapsedTime is used to ensure consistent speed across different machines.
     * @param bouncer
     * @param elapsedTime
     */
    public static void move (Bouncer bouncer, double elapsedTime){
        bouncer.getView().setX(bouncer.getView().getX() + bouncer.getVelocity().getX() * elapsedTime);
        bouncer.getView().setY(bouncer.getView().getY() + bouncer.getVelocity().getY() * elapsedTime);
    }

    /**
     * A method that describes the motion of bouncer bouncing off the paddle
     * @param bouncer
     * @param paddle
     */
    public static void bounceWithPaddle (Bouncer bouncer, Paddle paddle) {

    }
}

