package breakout;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

/**
 * An interface to lump Powerup and Bouncer together so Physics can act on them both.
 */
public interface Circular {
    ImageView getView();
    Point2D getVelocity();
}
