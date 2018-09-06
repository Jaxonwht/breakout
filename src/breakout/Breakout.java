package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple Breakout game by Haotian
 */
public class Breakout extends Application {
    public static final String TITLE = "Breakout by Haotian";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final double HIGH_PROBABILITY = 0.8;
    public static final int BRICKS_PER_ROW = 10;
    public static final int ROWS_HEIGHT = (int) 0.6 * HEIGHT;


    // objects used in this game
    private Scene myScene;
    private Brick myBrick;
    private List<Brick> myBricks;
    private Bouncer myBouncer;
    private Powerup myPowerup;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(WIDTH, HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        var myBouncer = new Bouncer(paddleImage, width, height);
        root.getChildren().add(myBouncer.getView());
        // make some bricks
        generateBricks(root,4, width, height);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    /**
     * Generate bricks based on an algorithm.
     * @param layer Number of rows of bricks.
     * @param width Width of the screen of playing.
     * @param height Height of the screen of playing.
     */
    private void generateBricks (Group root, int layer, int width, int height) {
        var probabilities = new ArrayList<Double>();
        for (int i = 0; i < layer; i++) {
            probabilities.add(HIGH_PROBABILITY / layer * (i + 1));
        }
        int brickWidth = width / BRICKS_PER_ROW;
        int brickHeight = ROWS_HEIGHT / layer;
        for (int x = brickWidth; x < width - brickWidth; x += brickWidth) {
            for (int i = 0; i < layer; i++) {
                int y = height - brickHeight * (i + 1);
                var myBrick = new Brick(x, y, brickWidth, brickHeight, probabilities.get(i));
                root.getChildren().add(myBrick.getView());
            }
        }
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes
        myBouncer.move(elapsedTime);
        myMover.setRotate(myMover.getRotate() - 1);
        myGrower.setRotate(myGrower.getRotate() + 1);

        // check for collisions
        // with shapes, can check precisely
        var intersect = Shape.intersect(myMover, myGrower);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            myMover.setFill(HIGHLIGHT);
        }
        else {
            myMover.setFill(MOVER_COLOR);
        }
        // with images can only check bounding box
        for (Brick brick : myBricks) {
            if (myGrower.getBoundsInParent().intersects(b.getView().getBoundsInParent())) {
                myGrower.setFill(HIGHLIGHT);
            }
        }

        // bounce off all the walls
        for (Bouncer b : myBouncers) {
            b.bounce(myScene.getWidth(), myScene.getHeight());
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myMover.setX(myMover.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myMover.setX(myMover.getX() - MOVER_SPEED);
        }
        else if (code == KeyCode.UP) {
            myMover.setY(myMover.getY() - MOVER_SPEED);
        }
        else if (code == KeyCode.DOWN) {
            myMover.setY(myMover.getY() + MOVER_SPEED);
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (myGrower.contains(x, y)) {
            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
        }
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
