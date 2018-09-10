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
    private CenterText myCenterText;

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
        myScene = setupGame(BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    /**
     * Create the game's "scene": what shapes will be in the game and their starting properties.
     * @param background
     * @return
     */
    private Scene setupGame (Paint background) {
        // Setup the root used for the game.
        Group root = setUpRoot();
        // create a place to see the shapes
        var scene = new Scene(root, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput());
        return scene;
    }

    private Group setUpRoot () {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // Make a few hearts representing the health that the player has and add the hearts to root.
        generateLives(root, myHealth);
        // make some shapes and set their properties
        var bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(bouncerImage);
        root.getChildren().add(myBouncer.getView());
        // Make a paddle.
        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new Paddle(paddleImage);
        root.getChildren().add(myPaddle.getView());
        // make some bricks and add existing bricks to root
        generateBricks(root, NUMBER_OF_LAYERS + myLevel.getLevel());
        // Make level text appearing on the top right corner of the scene.
        root.getChildren().add(myLevel.getTextNode());
        // Make a starting text for the game.
        myCenterText = new CenterText(CenterText.STARTING_TEXT);
        root.getChildren().add(myCenterText.getTextNode());
        return root;
    }

    /**
     * Generate the Life objects needed for the game and add them to root.
     * @param root
     * @param myHealth
     */
    private void generateLives (Group root, int myHealth) {
        for (int i = 0; i < myHealth; i++) {
            Life myLife = new Life(i + 1);
            myLives.add(myLife);
            root.getChildren().add(myLife.getView());
        }
    }

    /**
     * Generate bricks based on an algorithm.
     * @param layer Number of rows of bricks.
     */
    private void generateBricks (Group root, int layer) {
        var probabilities = new ArrayList<Double>();
        for (int i = 0; i < layer; i++) {
            probabilities.add(HIGH_PROBABILITY - 0.5 * HIGH_PROBABILITY / layer * i);
        }
        double brickWidth = WIDTH / BRICKS_PER_ROW;
        double brickHeight = (RATIO_OF_BRICKS + 0.1 * myLevel.getLevel()) * HEIGHT / layer;
        for (int x = 0; x <= WIDTH - brickWidth; x += brickWidth) {
            for (int i = 0; i < layer; i++) {
                double y = TOP_ROW + i * brickHeight;
                var myBrick = new Brick(x, y, brickWidth, brickHeight / 3 * 2, probabilities.get(i));
                if (myBrick.getView() != null) root.getChildren().add(myBrick.getView());
                myBricks.add(myBrick);
            }
        }
    }

    /**
     * Return whether the player has passed the current level.
     * @return
     */
    private boolean hasPassedLevel () {
        for (Brick brick : myBricks){
            if (brick.getExists() && !brick.getIsPermanent()){
                return false;
            }
        }
        return true;
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
        Physics.bounceWithWall(myBouncer);
        // Bounce off the paddle
        Physics.bounceWithPaddle(myBouncer, myPaddle);
        // End the game if the player uses up all lives.
        if (myHealth == 0) {
            animation.stop();
            myCenterText = new CenterText(CenterText.ENDING_TEXT);
            ((Group) myScene.getRoot()).getChildren().add(myCenterText.getTextNode());
        }
        // Stop the paddle and ball if the paddle fails to catch the ball.
        if (myBouncer.getView().getY() > Breakout.HEIGHT) {
            myHealth -= 1;
            myLives.remove(myLives.size() - 1).remove();
            animation.stop();
            if (myHealth == 0) {
                myCenterText = new CenterText(CenterText.ENDING_TEXT);
            }
            else {
                myCenterText = new CenterText(CenterText.LOSING_TEXT);
            }
            ((Group) myScene.getRoot()).getChildren().add(myCenterText.getTextNode());
        }
        // Restart the game in a higher level if the player clears the current level.
        if (hasPassedLevel()) {
            animation.stop();
            // Display congratulation if the player finishes the game.
            if (myLevel.getLevel() == FINAL_LEVEL) {
                myCenterText = new CenterText(CenterText.CONGRATULAION);
                myScene.setRoot(new Group(myCenterText.getTextNode()));
            }
            // If the player does not finish the game, start the game in a higher level.
            else {
                myCenterText = new CenterText(CenterText.WINNING_TEXT);
                ((Group) myScene.getRoot()).getChildren().add(myCenterText.getTextNode());
            }
        }
    }

    /**
     * What to do each time a key is pressed
     * @param code
     */
    private void handleKeyInput (KeyCode code) {
        // Restart everything if R is pressed.
        if (code == KeyCode.R) {
            myHealth = INITIAL_LIVES;
            myLevel = new Level(1);
            myScene.setRoot(setUpRoot());
        }
        // Pause the game when pressing p.
        else if (code == KeyCode.P) {
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
        // Start the game when Space is pressed.
        else if (code == KeyCode.SPACE) {
            if (myCenterText == null) {}
            // If the player finishes or ends the game, reset everything.
            else if (myCenterText.getTextNode().getText() == CenterText.CONGRATULAION || myCenterText.getTextNode().getText() == CenterText.ENDING_TEXT) {
                myCenterText.clear();
                myLevel = new Level(1);
                myHealth = INITIAL_LIVES;
                myScene.setRoot(setUpRoot());
            }
            // If the player finishes the current level, proceed to next level, keeping number of lives unchanged.
            else if (myCenterText.getTextNode().getText() == CenterText.WINNING_TEXT) {
                myCenterText.clear();
                myLevel.increaseLevel();
                myScene.setRoot(setUpRoot());
                myCenterText.clear();
                animation.play();
            }
            // Restart game if lost
            else if (myCenterText.getTextNode().getText() == CenterText.LOSING_TEXT) {
                // Reset the bouncer and the paddle if lost.
                myBouncer.reset();
                myPaddle.reset();
                myCenterText.clear();
                animation.play();
            }
            // Deleting the center text and start the game now.
            else if (myCenterText.getTextNode().getText() == CenterText.STARTING_TEXT) {
                myCenterText.clear();
                animation.play();
            }
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
