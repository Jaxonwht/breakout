package breakout;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Powerup implements Circular{
    public static final String ADD_LIFE = "extraballpower.gif";
    public static final String SLOW = "laserpower.gif";
    public static final double POWERUP_SPEED = 0.1 * Math.min(Breakout.WIDTH, Breakout.HEIGHT);
    public static final double POWERUP_SIZE = 0.03 * Math.min(Breakout.WIDTH, Breakout.HEIGHT);

    private ImageView myView;
    private Point2D myVelocity;
    private String myType;

    public Powerup (String name, double xCoordinate, double yCoordinate) {
        myType = name;
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(myType));
        myView = new ImageView(image);
        myView.setX(xCoordinate);
        myView.setY(yCoordinate);
        myView.setFitWidth(POWERUP_SIZE);
        myView.setFitHeight(POWERUP_SIZE);
        myVelocity = new Point2D(0, POWERUP_SPEED);
    }

    /**
     * Return the ImageView object for interaction with other JavaFx nodes.
     * @return
     */
    public ImageView getView () {
        return myView;
    }

    /**
     * Return the velocity of the powerup object.
     * @return
     */
    public Point2D getVelocity () {return myVelocity;}

    /**
     * Return the type of the powerup.
     * @return
     */
    public String getType () {
        return myType;
    }

    /**
     * Remove the powerup image.
     */
    public void remove() {
        ((Group) myView.getParent()).getChildren().remove(myView);
        myView = null;
        myVelocity = null;
    }
}
