package breakout;

/**
 * A class that contains all the static methods that
 */
public class Physics {
    /**
     * Bounceer bounces off the walls of the scene.
     * @param b
     * @param screenWidth
     * @param screenHeight
     */
    public static void bounceWithWall (Bouncer b, double screenWidth, double screenHeight){
        if (b.getView().getX() < 0 || b.getView().getX() > screenWidth - b.getView().getBoundsInLocal().getWidth()) {
            b.setVelocity(-1 * b.getVelocity().getX(), b.getVelocity().getY());
        }
        if (b.getView().getY() < Breakout.TOP_ROW || b.getView().getY() > screenHeight - b.getView().getBoundsInLocal().getHeight()) {
            b.setVelocity(b.getVelocity().getX(), -1 * b.getVelocity().getY());
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
}

