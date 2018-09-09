package breakout;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Life {
    public static final double LIFE_SIZE = Breakout.TOP_ROW * 0.9;
    public static final String LIFE_IMAGE = "red_heart.jpg";

    // Some field variables for the class.
    private ImageView myView;
    private int myLabel;

    /**
     * Constructor for Life using screen width and screen heights as parameters.
     * @param screenWdith
     * @param screenHeight
     */
    public Life (double screenWdith, double screenHeight, int label){
        // Set that this life object represents the label-th life
        myLabel = label;
        // Set the image for Life
        myView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(LIFE_IMAGE)));
        // Set the geometry for Life.
        myView.setX((label - 1) * Breakout.TOP_ROW);
        myView.setY(0);
        myView.setFitWidth(LIFE_SIZE);
        myView.setFitHeight(LIFE_SIZE);
    }

    /**
     * Returns an ImageView object for the Life class for interaction with other objects.
     * @return
     */
    public ImageView getView () {
        return myView;
    }

    /**
     * Returns the index of the Life object.
     * @return
     */
    public int getLabel () {
        return myLabel;
    }

    /**
     * Remove the Life object from its parent.
     */
    public void remove () {
        ((Group) myView.getParent()).getChildren().remove(myView);
    }
}
