Introduction
------------
This document provides an overview of the design aspect of the project.

Design
------

### Overview
The purpose of this project is to create a classic Breakout games with the basic features such as lives that the player is left with, powerups, random ball speeds and player-controlled movement of the paddle.

### Brief Description of Classes
* Class Breakout extends JavaFx's Application and is the main class to be run that stages the game. It includes setting up stage, scene, and root nodes for the game. Also the handling of key input events and mouse input events are tethered to the scene created in this class. Transformations, which involve the movement of the ball and powerups,  are also written in this class. This class has several methods that algorithmically generate lives and bricks according to the level number as well.
* Classes Brick, CenterText, Life, Paddle and Powerup are organized in a conceptually parallel manner. They are all "objects" that have an appearance in the game, represented by an ImageView object that can interact with other JavaFx nodes. They can be easily added to or removed from the Group node root of of the scene of the game. Addition to the root requires access to the root, which may cause annoying issues sometimes, while removal of nodes does not rely on access to the root node as I can simply do ((Group) child.getParent).getChildren().remove(child). In this game I have one root node, and the rest nodes are all one level below the root node. For this first project, I do not see the motivation to create a more complex layout of nodes yet.
* Class Level has more than an imagery representation in the game. It contains information about the current level the player is at, which will influence the the algorithm of generating all the bricks and whether the game will determine the player as having won a game or simply passed a level.
* Physics is supposed to cluster the movements of all the classes together, such as motion and collision. However it still needs more development because now it is just a class of various static methods that take different objects as parameters.
* Interface Circular is an attempt to make Physics class more functional and reduce repetitions of code. It is already a bit late when I realize there is much similarity between Brick, Paddle, Powerup and they can be grouped under an interface so that Class Physics can act on them easily. Therefore, I only have time to write a watered down version called Circular that conceptually summarizes Bouncer and Powerup to a certain extent.
* Class Cheat contains various methods that act on the tangible classes in the game such as Bouncer, Brick, CenterText, Life, Paddle and Powerup. It is initialized with the all the objects that are present in the root node together when the root node initialized.

Extensibility
---

### Overview
Extensibility of this project is good in certain respect and poor in others. For example, it is very easy to add new cheats to the game, while it is difficult to add a new kind of bricks to the game. It is thus best to make overhaul to the game before adding new features according to the style of existing frameworks. I will broadly divide features into two categories that are easy and hard to implement respectively.

###### Easy to Implement
* *Change the appearances of various objects* <br/>
At the start of each class or Breakout, there are public static final Strings that describe the file names of the images used. New images are to put in the resources root of the project. Then one does not need to include paths to the images in these static final Strings.
* *Add new cheats to the game* <br/>
In Cheat, add new methods that take bouncer, bricks or any other objects under the root node as parameters and take action on them. Each Cheat class is instantiated together with every new root initialized, and the developer can safely add codes to Cheat. Though additionally, one has to add codes in Breakout's handleKeyInput or handleMouseInput methods that call the newly added method in Cheat. If the developer wishes to add cheats that effect more dramatic changes to the game other than changing the characteristics of the objects, there is much more to do.
* *Modify how objects physically interact with each other* <br/>
In Physics, one has to modify the methods that describe the collision mechanism or motion of objects. However, sometimes motion of objects is not described in Physics but in the individual classes of each objects that are moved. For example, the scale of velocities of Bouncer is written only in Bouncer and not in Physics. The developer has to look at Breakout's step method and see where the motions of the objects are defined if not defined in Physics.
* *Change the initial sizes of the various objects* <br/>
The initial sizes of the objects are either defined in the static final variables of the object class or Breakout. Then one can change the values of these variables just as they change the images of these objects mentioned in the first point.
* *Add a powerup to the game* <br/>
First one needs to add a final static String in Powerup to indicate another image to be used for a new kind of Powerup. However, to make the new powerup take effect, one has to go into Breakout's step method and add a rule specifying the course of action to take if the paddle catches a powerup of this new type.

###### Hard to Implement
* *Add more kinds of bricks* <br/>
There is only one Brick class and different kinds of blocks are represented by different boolean values of a Brick object, such as isHard, isPermanent and hasPowerup. If one wants to add a different kinds of bricks, besides adding a boolean variable for the class, he has to look through the whole Brick class and see if the logic works out well. In each method of Brick, the developer has to check whether introducing a new boolean variable has unintended consequences on other kinds of bricks. For example, to define a new appearance for the new type of brick, one has to go to generateView method of Brick and check whether the boolean logic works correctly.
* *Changes how the bricks are generated for each level* <br/>
This is not hard per se. The difficulty lies in understanding the algorithms used right now to generate bricks. One has to go to Breakout's generateBricks method and try to understand the algorithm there and adjust relevant parameters. If the developer wishes to discard the whole algorithm and decides to read level information from a file, it is easy to do, though. Just replace generateBricks with another method that takes root, and the brick layout file as two parameters.
* *Add a score system to the game* <br/>
Simply speaking, there is no work done to create a scoring system for the game. One has to code from scratch for this whole feature. My advice to add such a feature is to pass a newly created Score class to each block, such that each time the getHit method of Brick object is called, the Score object's method to increase score is called. In each root, only one Score object is instantiated.

Design Choices
--------------
### Overview
This section is about the major design choices or trade-offs that I faced during development. I will give an justification for the major choices made and an evaluation of such choices.

### Detailed Analysis

###### Used boolean variables to indicate different kinds of bricks and powerups
* *Explanation* <br/>
I used boolean variables to indicate different kinds of bricks and powerups. For example, bricks with and without powerups, hard or normal are all represented by a single class called Brick. Each brick has boolean variables such as isHard and hasPowerup.
* *Justification* <br/>
I was not confident to use abstract classes or interfaces at the start of the project and using boolean variables seemed like a viable alternative.
* *Evaluation* <br/>
It is easy for a project of such a small scale to use boolean variables to distinguish between various types of objects. However it is hard to scale because each time a new type of object is added, one has to go through the whole boolean logic again and see if there is any unintended consequences. What happens if a brick is hard, it has powerups, it is special and it is not permanent at the same time. This is just an illustration of the headaches that one could encounter if boolean variables are used. Another significant drawback of this method is that a lot of course of actions to take for these bricks have to be written in the main Breakout class rather than the individual classes or Physics even if I want to, this is because the boolean logic to identify each brick sometimes has to be written together with a variable that is not accessible from the Brick class.

###### Removed variables such as myScene and myWidth from almost all classes
* *Explanation* <br/>
I removed instance variables representing the widths and heights of the screen from all classes. Also I removed the distinction between screen and scene. In other words, when the player dynamically drags the window and changes the size of the game after it starts, all the game logic will ignore the new window size and use the width and height that the game is initially started with.
* *Justification* <br/>
I passed screen widths and screen heights as parameters to constructors of different classes because they needed these two numbers. It got confusing especially when different methods needed to use these numbers as well, while there was not much benefit of doing so apart from occasionally I needed to distinguish between size of screen and size of scene. I decided the trouble outweighed the benefits and removed all screen width variables all together, leaving only two static final variables SCREEN_WIDTH and SCREEN_HEIGHT in Breakout.
* *Evaluation* <br/>
I think this is a good decision because it simplifies a lot of cumbersome constructors and method declarations.

###### Did not implement a separate scene controller class
* *Explanation* <br/>
I did not write a separate scene controller class that controlled the transition between scenes, for example, when the player cleared one level and entered the next level. Instead, the codes were merged into the main Breakout code.
* *Justification* <br/>
In my plan, actually I wanted to write such a separate class. However difficulties arose when I actually wanted to implement such a transition class. To the best of my knowledge, in JavaFx, the way of transition is either reorganizing all the nodes so that they appear to compose a new level or using Scene.setRoot(new Root). I chose the second way. The reason I did not simply put the code into a separate scene transition class was twofold: there had already been so much code in place that I feared such a redesign would break my Breakout class, and I was not quite sure what the relationship between animation and stage/scene/root was.
* *Evaluation* <br/>
This decision makes Breakout unnecessarily bulky and reduces readability of Breakout. In addition, Breakout is doing too many things on its own at this point, especially compared to some other Breakout implementations available on Github. The moral of the story is that for my next project I should really plan ahead and not rely on any given functional code.
