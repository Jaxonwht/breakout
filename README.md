game
====

This project implements the game of Breakout.

Name: Haotian Wang

### Timeline

Start Date: 09/03/2018

Finish Date: 09/09/2018

Hours Spent: 16

### Resources Used
Image for the red heart
https://www.kisspng.com/png-heart-red-valentines-day-font-dark-red-heart-png-c-121777/

Oracle documentation on javafx.scene.node
https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html

Window, Scene and Node coordinates in JavaFX
https://blog.crisp.se/2012/08/29/perlundholm/window-scene-and-node-coordinates-in-javafx

Yanbo Fang's Breakout on GitHub
https://github.com/yanbofang/breakout_game/blob/master/src/breakout_game/Breakout_Game.java

Handle mouse event anywhere with JavaFX
https://stackoverflow.com/questions/18597939/handle-mouse-event-anywhere-with-javafx

How to properly switch scenes/change root node of scene in JavaFX without FXML?
https://stackoverflow.com/questions/43761138/how-to-properly-switch-scenes-change-root-node-of-scene-in-javafx-without-fxml

In Javafx 2, how to always draw something on top?
https://stackoverflow.com/questions/16778633/in-javafx-2-how-to-always-draw-something-on-top
### Running the Program

Main class: Breakout

Data files needed: ball.gif, brick1.gif, brick2.gif, brick3.gif, brick4.gif, paddle.gif, red_heart.jpg (https://www.kisspng.com/png-heart-red-valentines-day-font-dark-red-heart-png-c-121777/), pong_beep.wav

Key/Mouse inputs:
Left -> Paddle moves to the left.
Right -> Paddle moves to the right.
P -> Pause and resume the game.
Escape -> Close the stage and thus the game because the application has only one stage.
Click mouse -> Pause and resume the game.
Space -> Start the game.

Cheat keys:

Known Bugs:
When the ball bounces close to the corner of a brick, my algorithm may not correctly determine whether the ball bounces with the top/bottom edges or the left/right edges.

I don't know how to place the CenterText at the real center of the screen.

Extra credit:
I have created starting text, winning text, losing text and ending text for the game that appear at appropriate times.

The ball makes collision sounds with bricks and the paddle.

### Notes


### Impressions
There is too much inter-dependency between different classes. I am not that familiar with software design and the time is not plenty, so for now I do not know how to improve it significantly.

I may need to implement a lot more abstract classes in my next project.
