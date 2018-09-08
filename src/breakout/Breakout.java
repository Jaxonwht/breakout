package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple Breakout game by Haotian
 */
public class Breakout extends Application {
    public static final String TITLE = "Breakout by Haotian";
    public static final double WIDTH = 900;
    public static final double HEIGHT = 800;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final double HIGH_PROBABILITY = 0.9;
    public static final int BRICKS_PER_ROW = 10;
    public static final double TOP_ROW = 0.05 * HEIGHT;


    // objects used in this game
    private Scene myScene;
    private List<Brick> myBricks = new ArrayList<>();
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
    private Scene setupGame (double width, double height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        var bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(bouncerImage, width, height);
        root.getChildren().add(myBouncer.getView());
        // make some bricks
        generateBricks(root,7, width, height);
        // respond to input
        //scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    /**
     * Generate bricks based on an algorithm.
     * @param layer Number of rows of bricks.
     * @param width Width of the screen of playing.
     * @param height Height of the screen of playing.
     */
    private void generateBricks (Group root, int layer, double width, double height) {
        var probabilities = new ArrayList<Double>();
        for (int i = 0; i < layer; i++) {
            probabilities.add(HIGH_PROBABILITY - HIGH_PROBABILITY / layer * i);
        }
        double brickWidth = width / BRICKS_PER_ROW;
        double brickHeight =  0.5 * height / layer;
        for (int x = 0; x < width - brickWidth; x += brickWidth) {
            for (int i = 0; i < layer; i++) {
                double y = TOP_ROW + i * brickHeight;
                var myBrick = new Brick(x, y, brickWidth, brickHeight / 3 * 2, probabilities.get(i));
                if (myBrick.getView() != null) root.getChildren().add(myBrick.getView());
                myBricks.add(myBrick);
            }
        }
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes
        Physics.move(myBouncer, elapsedTime);

        // with images can only check bounding box
        for (Brick brick : myBricks) {
            Physics.bounceWithBrick (myBouncer, brick);
        }

        // bounce off all the walls
        //myBouncer.bounce(myScene.getWidth(), myScene.getHeight());
        Physics.bounceWithWall(myBouncer, myScene.getWidth(), myScene.getHeight());
    }

    // What to do each time a key is pressed
    /*
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
    */

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
