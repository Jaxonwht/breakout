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

    public void skip () {
        for (Brick brick : myBricks) {
            if (brick.getExists()) {
                brick.remove();
            }
        }
    }
}
