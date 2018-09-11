game
====
This project implements the game of Breakout.

Name: Haotian Wang

### Timeline
---
Start Date: 09/03/2018

Finish Date: 09/09/2018

Hours Spent: 16

### Resources Used
---
* [Image for the red heart](https://www.kisspng.com/png-heart-red-valentines-day-font-dark-red-heart-png-c-121777/)
* [Oracle documentation on javafx.scene.node](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html)
* [Window, Scene and Node coordinates in JavaFX](https://blog.crisp.se/2012/08/29/perlundholm/window-scene-and-node-coordinates-in-javafx)
* [Yanbo Fang's Breakout on GitHub](https://github.com/yanbofang/breakout_game/blob/master/src/breakout_game/Breakout_Game.java)
* [Handle mouse event anywhere with JavaFX](https://stackoverflow.com/questions/18597939/handle-mouse-event-anywhere-with-javafx)
* [How to properly switch scenes/change root node of scene in JavaFX without FXML?](https://stackoverflow.com/questions/43761138/how-to-properly-switch-scenes-change-root-node-of-scene-in-javafx-without-fxml)
* [In Javafx 2, how to always draw something on top?](https://stackoverflow.com/questions/16778633/in-javafx-2-how-to-always-draw-something-on-top)
* [Using Text and Text Effects in JavaFX](https://docs.oracle.com/javafx/2/text/jfxpub-text.htm)
* [How to switch scenes in JavaFX](https://stackoverflow.com/questions/37200845/how-to-switch-scenes-in-javafx)
* [Oracle documentation on ArrayList](https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html#remove(int))
* [Java - Interfaces](https://www.tutorialspoint.com/java/java_interfaces.htm)
* [Iterating through a Collection, avoiding ConcurrentModificationException when removing in loop](https://stackoverflow.com/questions/223918/iterating-through-a-collection-avoiding-concurrentmodificationexception-when-re)

### Running the Program
---
#### Main class:
Breakout

#### Data files needed:
* ball.gif
* brick1.gif
* brick2.gif
* brick3.gif
* brick4.gif
* paddle.gif
* [red_heart.jpg](https://www.kisspng.com/png-heart-red-valentines-day-font-dark-red-heart-png-c-121777/)
* pong_beep.wav
* extraballpower.gif
* laserpower.gif

#### Key/Mouse inputs:
* Left -> Paddle moves to the left.
* Right -> Paddle moves to the right.
* P -> Pause and resume the game.
* Escape -> Close the stage and thus the game because the application has only one stage.
* Click mouse -> Pause and resume the game.
* Space -> Start the game.
* R -> Reset the game.

#### Cheat keys:
* S -> Destroy all the bricks and therefore jump to the next level.
* D -> Speed down the ball.
* U -> Speed up the ball.
* M -> Add one life for the player instantly.
* - -> Reduce the length of the paddle.
* = -> Increse the length of the paddle.
* o -> Reset the length of the paddle.

#### Known Bugs:
* When the ball bounces close to the corner of a brick, my algorithm may not correctly determine whether the ball bounces with the top/bottom edges or the left/right edges.
* I don't know how to place the CenterText at the real center of the screen.
* When the ball hits near the common edge of two bricks, it will likely remove both bricks and not get bounced. The problem lies in the Physics class.

#### Extra credit:
* I have created starting text, winning text, losing text and ending text for the game that appear at appropriate times. There are four status of the game besides normal playing. Starting, Winning, Ending and Congratulation. In each case the screen applies different treatment to the nodes.
    * Lose: The paddle fails to catch the ball.
    * Win: The player passes the current level.
    * End: The player loses all the lives.
    * Congratulation: The player passes FINAL_LEVEL.
* The ball makes collision sounds with bricks and the paddle.
* Bricks with higher health change to normal color when they reach 1 health.
* With the collision sound feature, sometimes there is lag in the game.
* I Will display congratulation text after the player breaks all levels.
* There are bricks that scale the velocity of the ball.

### Notes
---
* I removed the difference between screenwidth and scenewidth. In other words, I assumed the player will not adjust the window size after the game starts. This removes a lot of redundant variables.
* There are a few places that I should implement methods to interfaces or super classes rather than specific classes in the future, for example, implementing methods to Node rather than to Group for extensibility.
* My scene transitions are not good enough. Originally I planned to have a RootController class that transitions between different roots for the same scene, when the player wins, loses etc. However, I realized it is too difficult for this class to access the various objects from root, because they are defined only in Breakout class. This is a design flaw that I should have dealt with better at the start of the project. But then again, at that time, I had no idea how to use JavaFx and relied heavily on codes from lab_bounce. All these are for future reference.
* Not enough time to implement a score system or more powerups that I really like such as a real laser.
* There are two kinds of powerups for now. One is to add one life, and another is to slow the ball significantly.

### Impressions
---
* There is too much inter-dependency between different classes. I am not that familiar with software design and the time is not plenty, so for now I do not know how to improve it significantly.
* I may need to implement a lot more abstract classes in my next project.
