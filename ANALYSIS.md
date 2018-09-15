CompSci 308: Game Analysis
===================

This is the link to the assignment: [Game](http://www.cs.duke.edu/courses/compsci308/current/assign/01_game/)

This is the link to my project: [Project](https://coursework.cs.duke.edu/CompSci308_2018Fall/game_hw186)

Project Journal
=======

### Time Review

* I spent in total about 25 hours developing the game. I planned for the project on Sep 1. The actual development started on Sep 4 and ended on Sep 10. Among the 25 hours, 9 hours were spent planning and researching on JavaFx and software design.
* For this first project, time was not spent optimally.
    * I spent about 4 hours just trying to understand the architecture of JavaFx in the context of lab_bounce, as I feared starting writing code directly could affect my later development. Although that was a legitimate concern, my heavy choice of relying on lab_bounce as the starting code kind of resulted in the exact problems that I had worried about.
    * Another issue in my time allocation was that I did not plan in enough detail about what abstract classes, interfaces or classes I would have in the completed project. Instead, I had a very intuitive idea about the objects that I needed from a visual perspective such as paddles and balls. This made planning easier but cost me more time when I wanted to extend functionality.
    * Debugging sometimes was not efficient use of time when I spent long time trying to debug some very specific cheats instead of resolving larger issues like collision physics and scene transition. This was due to a lack of an actionable plan specific to hour slots as well.
* I committed and pushed really frequently. If I deemed some changes I made as deserving a separate sentence to describe, I would commit the change. This change could be as small as "decreasing the growth factor that increases the length of the paddle when the player presses some cheat key." I also pushed my changes almost every time I committed changes.
* The easiest for me was probably writing out classes for the easily perceivable objects like "balls", "paddles" and "powerups". The hardest thing was to (1) code the scene transitions and (2) refactored the code into an elegant way. Initially I tried to create a SceneController class dedicated to switching scenes and spent a lot of time on that, but in the end I gave up all these efforts because already too much information was entangled in the main Breakout class. That chunk of time in effect contributed nothing to my overall project, though it did increase my understanding of Java though. This was an ironical issue, namely, due to lack of good planning, the most efficient time I spent was on obvious things that could potentially increase the inter-dependency of my classes and the least efficient time was spent on core issues like dealing with code smells.


### Commits

* In all I made 43 commits, so on average 6 commits per day. Each commit ranged from 20 to 80 additions or deletions. Sometimes the commit could go up to 150 additions or deletions.
* I think my commit descriptions are pretty good. They describe exactly the changes I made, though sometimes insufficient. The reason was that after I committed some change A1, after which there was a minor debug for A1 that I did not mention in the commit for the next change A2. Luckily that happened rarely because I was intentionally dealing with that bad habit. However admittedly, it will probably still be hard for someone else other than me to get an overall sense of the development of the game because I used specific words that were my class names or variable names or my design for the game. In README.md, I do have instructions for how to play the game, but there is no design blueprint for the project there, so it will probably be hard for someone else to understand my commits.
* Two examples of my commits
    * [Save the project before ruining it.](https://coursework.cs.duke.edu/CompSci308_2018Fall/game_hw186/commit/6bf3cf0934b6923e00286b170cf295e80b2a4617)
        * The purpose was to remove all the screen width, screen height, scene width and scene height parameters in the game and keep only the two SCREEN_WIDTH and SCREEN_HEIGHT static variables. The motivation was that I felt frustrated passing these variables around between different classes and I saw little value in distinguishing between scene width and screen widths.
        * I packaged the code into one commit because I felt this could significantly destroy the project and it would be great to have a backup.
        * The problem with this commit is that it tells very little about the actual changes or why I made the changes. It is one of the bad examples in my commit messages. The commit size is reasonable, though, and there is a very clear theme across the edits I made in this commit.
    * [Press R to reset the game.](https://coursework.cs.duke.edu/CompSci308_2018Fall/game_hw186/commit/627f4caf3c8794cb3f9310b36b01267175640df7)
        * The purpose of the commit was to design a (common-sense) functionality of pressing R to restart the game at any point in the game. However it does include debugs that I did later when this functionality clashed with other functionalities.
        * These changes were packaged together because I felt they obviously were made to add a "function" intuitively. Removing these changes will not affect earlier development. However I did not take into consideration about how much damage its removal would do to later development.
        * The size of commit is manageable, 80 additions and 23 deletions.


### Conclusions

* I underestimated the size of the project. I was thinking more like 9 or 10 hours development. That caused me to develop insufficiently for the project before starting out. Now I have a better sense of JavaFx and an overall outline of the development process. In the future I will leave enough buffer for potentially very long time for research.
* The part about scene transitions required the most editing. I was initially undecided between changing scenes or roots and between implementing a separate class for transition and coding the whole transition in the main Breakout class. This back-and-forth process cost me a lot of time. Also my efforts of writing a separate class for scene transition turned out to be nothing because so many variables had already been embedded in the lab_bounce framework that I started with.
* I would change the normal Brick class that has so many boolean variables such as isHard, isPermanent, hasPowerup to a group of classes. This group of classes would have one abstract class called Brick and a few more classes such as HardBrick and PowerupBrick that extend the abstract Brick class.
* I will start drawing a project road map of all the classes that I am going to implement. I will keep committing changes in clear and instructive commit messages. I will stop adding new features without worrying about how much trouble such decisions would affect later development of addition of new features.

Design Review
=======

### Status
###### Consistency
Naming conventions and descriptiveness are generally consistent. There are clear /** */ comments and inline // comments when necessary. However, my comments about what each methods return are consistently bad. They do not tell a story describing what the returned variable is. Also there is too little comment on the restrictions of each method.

###### Readability
The code is readable. There may be the minor annoyance that there are too many static final variables in different classes and a reader may lose track of what is what. Apart from that, the code is readable. When there could be confusion, they are divided into chunks with inline comments to explain the role of each chunk.

###### Dependency
Dependency of code is really bad. The code commits all sorts of crimes by allowing a lot of variables to be passed around as global variables and get method variables. It can be really confusing at times.

###### Examples
* ```
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
                     var myBrick = new Brick(x, y, brickWidth, brickHeight / 3 * 2, probabilities.get(i), myPowerups);
                     if (myBrick.getView() != null) root.getChildren().add(myBrick.getView());
                     myBricks.add(myBrick);
                 }
             }
         }
    ```
    * This block of code is in Breakout. It generates rows of bricks algorithmically. The probability of brick appearing decreases as it gets closer to the paddle, so there is a nice distribution of bricks that gets less and less dense approaching the paddle. Also it takes level as a parameter because at a higher level, the game will generate more rows of bricks and also a denser distribution of bricks.
    * This code is kind of hard to understand. It merges math concepts and code together. Comments should probably give an English description of the algorithm used first. Also variables passed around probably do not make much sense because of their names. This is also probably because I have too many static final variables.

* ```
    /**
     * Remove the brick.
     */
    public void remove() {
        // Release powerups if there is any.
        if (hasPowerup) {
            Powerup powerup;
            if (dice.nextDouble() < 0.5) {
                powerup = new Powerup(Powerup.ADD_LIFE, myView.getX(), myView.getY());
            }
            else {
                powerup = new Powerup(Powerup.SLOW, myView.getX(), myView.getY());
            }
            ((Group) myView.getParent()).getChildren().add(powerup.getView());
            myPowerups.add(powerup);
        }
        setImage(null);
        isHard = false;
        exists = false;
        isDownscale = false;
        isUpscale = false;
        isPermanent = false;
        hasPowerup = false;
    }
    ```
    * This block of code does more than what the comment says. It destroys the brick that calls this method and at the same time generates a Powerup object at the exact location where the brick is destroyed, if the brick's hasPowerup variable is true.
    * I would imagine another developer find this piece of code incredibly hard to read. Although the inline comment does talk about "Release powerups if there is any", this feature should really not be included in this the remove method of Brick, but I have no better alternative. Setting the boolean variables to false after is probably weird too, if one is not familiar with the way I define a brick.

### Design
###### Design Overview
The purpose of this project is to create a classic Breakout games with the basic features such as lives that the player is left with, powerups, random ball speeds and player-controlled movement of the paddle.
* Class Breakout extends JavaFx's Application and is the main class to be run that stages the game. It includes setting up stage, scene, and root nodes for the game. Also the handling of key input events and mouse input events are tethered to the scene created in this class. Transformations, which involve the movement of the ball and powerups,  are also written in this class. This class has several methods that algorithmically generate lives and bricks according to the level number as well.
* Classes Brick, CenterText, Life, Paddle and Powerup are organized in a conceptually parallel manner. They are all "objects" that have an appearance in the game, represented by an ImageView object that can interact with other JavaFx nodes. They can be easily added to or removed from the Group node root of of the scene of the game. Addition to the root requires access to the root, which may cause annoying issues sometimes, while removal of nodes does not rely on access to the root node as I can simply do ((Group) child.getParent).getChildren().remove(child). In this game I have one root node, and the rest nodes are all one level below the root node. For this first project, I do not see the motivation to create a more complex layout of nodes yet.
* Class Level has more than an imagery representation in the game. It contains information about the current level the player is at, which will influence the the algorithm of generating all the bricks and whether the game will determine the player as having won a game or simply passed a level.
* Physics is supposed to cluster the movements of all the classes together, such as motion and collision. However it still needs more development because now it is just a class of various static methods that take different objects as parameters.
* Interface Circular is an attempt to make Physics class more functional and reduce repetitions of code. It is already a bit late when I realize there is much similarity between Brick, Paddle, Powerup and they can be grouped under an interface so that Class Physics can act on them easily. Therefore, I only have time to write a watered down version called Circular that conceptually summarizes Bouncer and Powerup to a certain extent.
* Class Cheat contains various methods that act on the tangible classes in the game such as Bouncer, Brick, CenterText, Life, Paddle and Powerup. It is initialized with the all the objects that are present in the root node together when the root node initialized.

###### How to Add a New Level
Increase the FINAL_LEVEL variable at the start of Breakout by 1.

###### Justification for Overall Design
The overall design for the first project was chosen mainly because it was easy and intuitive. Having classes corresponding to "stuff" that I could see in a typical Breakout game was a convenient starting place. Slightly abstract concepts like Cheat and Powerup were also easily made into classes. However, I made these classes in a way more like "creating them as I am writing code for the main class", as seen from my commit history. The dependency issues and lack of extensibility are all attributed to this bad development habit.

###### Examples
* Powerups
    * Powerup, Cheat and Breakout together implement this feature.
    * Powerup is a class mainly for the appearance and movement of the powerup icons that spawn from the destruction of certain bricks. In Breakout's step method, it specifies what will happen to different nodes if the paddle come into contact with the icons of powerups. Sometimes in the step method, a method from Cheat will be called because the powerup does exactly the same thing as some already implemented cheat.

* Levels
    * Level and Breakout together implement this feature.
    * Level just contains the int level and a Text node that appears on the top right corner of the scene. Breakout will have one Level object in each root. When there are methods that need to use the level information such as generateBricks, it will simply get the int information from the Level object. The Text node on the top right corner of the scene will update accordingly as well. Frankly now thinking about it, I feel the Level class should do more.

### Alternate Designs

###### Examples
* Used boolean variables to indicate different kinds of bricks and powerups
  * *Explanation* <br/>
  I used boolean variables to indicate different kinds of bricks and powerups. For example, bricks with and without powerups, hard or normal are all represented by a single class called Brick. Each brick has boolean variables such as isHard and hasPowerup.
  * *Justification* <br/>
  I was not confident to use abstract classes or interfaces at the start of the project and using boolean variables seemed like a viable alternative.
  * *Evaluation* <br/>
  It is easy for a project of such a small scale to use boolean variables to distinguish between various types of objects. However it is hard to scale because each time a new type of object is added, one has to go through the whole boolean logic again and see if there is any unintended consequences. What happens if a brick is hard, it has powerups, it is special and it is not permanent at the same time. This is just an illustration of the headaches that one could encounter if boolean variables are used. Another significant drawback of this method is that a lot of course of actions to take for these bricks have to be written in the main Breakout class rather than the individual classes or Physics even if I want to, this is because the boolean logic to identify each brick sometimes has to be written together with a variable that is not accessible from the Brick class.
  * *Alternative* <br/>
  I will have an abstract class called AbstractBrick and a few more subclasses that extend AbstractBrick, such as HardBrick, NormalBrick, PermanentBrick etc. This of course will increase the lines of code when there is only one kind of brick. However it makes adding new bricks a lot easier and safer. I do not need to go back to Breakout frequently to check if something will go wrong every time I want to add a new brick either.

* Did not implement a separate scene controller class
  * *Explanation* <br/>
  I did not write a separate scene controller class that controlled the transition between scenes, for example, when the player cleared one level and entered the next level. Instead, the codes were merged into the main Breakout code.
  * *Justification* <br/>
  In my plan, actually I wanted to write such a separate class. However difficulties arose when I actually wanted to implement such a transition class. To the best of my knowledge, in JavaFx, the way of transition is either reorganizing all the nodes so that they appear to compose a new level or using Scene.setRoot(new Root). I chose the second way. The reason I did not simply put the code into a separate scene transition class was twofold: there had already been so much code in place that I feared such a redesign would break my Breakout class, and I was not quite sure what the relationship between animation and stage/scene/root was.
  * *Evaluation* <br/>
  This decision makes Breakout unnecessarily bulky and reduces readability of Breakout. In addition, Breakout is doing too many things on its own at this point, especially compared to some other Breakout implementations available on Github. The moral of the story is that for my next project I should really plan ahead and not rely on any given functional code.
  * *Alternative* <br/>
  Have a separate SceneController class or even transfer these features from Breakout to Level because they really do not belong in Breakout. Apart from scene transition, methods such as generateBricks and generateLives should be written in Level or a new SceneController class as much as possible.

###### Three Bugs
* When the ball bounces close to the corner of a brick, my algorithm may not correctly determine whether the ball bounces with the top/bottom edges or the left/right edges.
* I don't know how to place the CenterText at the real center of the screen.
* When the ball hits near the common edge of two bricks, it will likely remove both bricks and not get bounced. The problem lies in the Physics class.

