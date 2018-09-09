package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
    public static final double WIDTH = 500;
    public static final double HEIGHT = 500;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final double HIGH_PROBABILITY = 0.9;
    public static final int BRICKS_PER_ROW = 10;
    public static final double TOP_ROW = 0.05 * HEIGHT;
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final int NUMBER_OF_LAYERS = 5;
    public static final double PADDLE_SPEED = WIDTH / 30;
    public static final int INITIAL_LIVES = 3;
    public static final int FINAL_LEVEL = 3;
    public static final double RATIO_OF_BRICKS = 0.4;

    // objects used in this game
    private Stage primaryStage;
    private Scene myScene;
    private List<Brick> myBricks = new ArrayList<>();
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private int myHealth;
    private List<Life> myLives = new ArrayList<>();
    private Timeline animation;
    private Level myLevel;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // Set the myLevel object.
        myLevel = new Level(1);
        // Set the lives that the player has.
        myHealth = INITIAL_LIVES;
        // Set the primary stage of the game.
        primaryStage = stage;
        // Use a GameController class to control the game.
        // attach scene to the stage and display it
        myScene = setupGame(WIDTH, HEIGHT, BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * Create the game's "scene": what shapes will be in the game and their starting properties.
     * @param width
     * @param height
     * @param background
     * @return
     */
    private Scene setupGame (double width, double height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // Make a few hearts representing the health that the player has and add the hearts to root.
        generateLives(root, myHealth, width, height);
        // make some shapes and set their properties
        var bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(bouncerImage, width, height);
        root.getChildren().add(myBouncer.getView());
        // Make a paddle.
        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new Paddle(paddleImage, width, height);
        root.getChildren().add(myPaddle.getView());
        // make some bricks and add existing bricks to root
        generateBricks(root, NUMBER_OF_LAYERS + myLevel.getLevel(), width, height);
        // Make level text appearing on the top right corner of the scene.
        root.getChildren().add(myLevel.getTextNode());
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput());
        return scene;
    }

    /**
     * Generate the Life objects needed for the game and add them to root.
     * @param root
     * @param myHealth
     * @param width
     * @param height
     */
    private void generateLives (Group root, int myHealth, double width, double height) {
        for (int i = 0; i < myHealth; i++) {
            Life myLife = new Life(width, height, i + 1);
            myLives.add(myLife);
            root.getChildren().add(myLife.getView());
        }
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
        double brickHeight = (RATIO_OF_BRICKS + 0.1 * myLevel.getLevel()) * height / layer;
        for (int x = 0; x < width - brickWidth; x += brickWidth) {
            for (int i = 0; i < layer; i++) {
                double y = TOP_ROW + i * brickHeight;
                var myBrick = new Brick(x, y, brickWidth, brickHeight / 3 * 2, probabilities.get(i));
                if (myBrick.getView() != null) root.getChildren().add(myBrick.getView());
                myBricks.add(myBrick);
            }
        }
    }

    /**
     * Change properties of shapes to animate them
     * Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
     * @param elapsedTime
     */
    private void step (double elapsedTime) {
        // update attributes
        Physics.move(myBouncer, elapsedTime);

        // with images can only check bounding box
        for (Brick brick : myBricks) {
            Physics.bounceWithBrick (myBouncer, brick);
        }

        // bounce off all the walls
        Physics.bounceWithWall(myBouncer, myScene.getWidth(), myScene.getHeight());

        // Bounce off the paddle
        Physics.bounceWithPaddle(myBouncer, myPaddle);
    }

    /**
     * What to do each time a key is pressed
     * @param code
     */
    private void handleKeyInput (KeyCode code) {
        // Pause the game when pressing p.
        if (code == KeyCode.P) {
            if (animation.getStatus() == Animation.Status.PAUSED) {
                animation.play();
            }
            else if (animation.getStatus() == Animation.Status.RUNNING) {
                animation.pause();
            }
        }
        // Exit the game if escape key is pressed.
        else if (code == KeyCode.ESCAPE) {
            primaryStage.close();
        }
        // Controls the left and right movement of the paddle
        else if (code == KeyCode.RIGHT && myPaddle.getView().getBoundsInParent().getMaxX() <= myScene.getWidth()) {
            myPaddle.getView().setX(myPaddle.getView().getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.LEFT && myPaddle.getView().getBoundsInParent().getMinX() >= 0) {
            myPaddle.getView().setX(myPaddle.getView().getX() - PADDLE_SPEED);
        }
    }

    /**
     * What to do when there is input from the mouse.
     */
    private void handleMouseInput () {
        // Pause and resume the game when the mouse is clicked.
        if (animation.getStatus() == Animation.Status.PAUSED) {
            animation.play();
        }
        else if (animation.getStatus() == Animation.Status.RUNNING) {
            animation.pause();
        }
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
