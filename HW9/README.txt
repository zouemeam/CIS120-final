=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: ___50011175____
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. Inheritance/Subtyping
  
- What specific feature of your game will be implemented using this concept?

	We will use an inheritance hierarchy to model different types of game objects in the Space 
	Invaders game

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

	Since there are different types of game objects in the Space Invaders game it makes sense to
	have a GameObj class. It doesn't make sense to have GameObj as an interface as opposed to a 
	class because game objects shares many common methods, such as displaying game objects and 
	checking whether a moving object has intersected with other game objects. If GameObj is an
	interface many of the same code would have to be repeated. Examples of game objects include the 
	player, enemies, and boss. Many implementations of those game objects are exactly the same. Each 
	game object will also handle collision differently. For example, if the player collides with an
	enemy the game is over. However, if enemy collides with each other the game continues. Using 
	inheritance/subtyping allows us to use dynamic dispatch to make sure that collisions are 
	resolved correctly.

  2. Using I/O
 - What specific feature of your game will be implemented using this concept?

	We will use I/O to keep track of high score. 

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

	Every time we close and start the game we create a new instance of the Space Invaders game. 
	All data such as high score from previous instance of the Space Invaders game will be lost. That
	is why it makes sense to use I/O to write high scores out to a file and read them back in each
    the game starts. In other words, there is no way of keeping high score without using I/O. 

  3. JUnit Testing
- What specific feature of your game will be implemented using this concept?
 
 	We will test the collision of moving objects such as player, enemies, and their missiles
    in the game.

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

	It makes sense to implement this feature with this concept because JUnit testing is a java 
	library designed for this purpose. It makes sense to test collision because this is the core of
	the game logic. If collision detection does not work correctly, then erasing enemies from the 
	game after they are hit will also not work correctly. Without the ability to clear enemies away, 
	score and high score system will not work correctly. Furthermore, the player may not lose health
    after he is hit and the game will never end. Some of the edge cases we have considered are
    collision with the side of the window and making sure that collision between bomb and missiles
    are ignored. My test cases are not redundant because collisions between different types of game
    objects should be handled differently. 
  4. Modeling state using collections

- What specific feature of your game will be implemented using this concept?

	I am going to model the state of all enemies in the game using ArrayList 
	
- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

	It makes sense to implement this feature with this concept because arraylist allows for indexing
	while collections such as set does not. Set collection will also delete any duplicate aliens 
	while providing no method to identify a particular alien and update its state. I choose
	arraylist over array because array is not resizable, while arraylist allows for easy addition 
	and removal of elements. This allows me to delete any enemies that are hit by player's missiles, 
	clearing up unnecessary data. Lastly, it does not make sense to model enemies as a map because 
	enemies are instances and not key and value pairings. 
=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
-  Appearance.java: Used to instantiate the look of each game object. Each game object contains a 
  private Appearance field. Whenever a game object needs to draw itself, the draw method of its 
  private Appearance field is called.   
-  AppearanceResource.java: A singleton of HashMap used to map game object type to an Appearance, so
  the same image does not need to be read multiple times. 
-  Boss.java: Used to instantiate the boss in game. Overrides the move method in GameObj class, so 
  that the boss can move in an elliptical path. Implements the collidedWith method in GameObj class, 
  so that the boss will only resolve collisions with the player or player attacks(PlayerFire.java).
  Also contains trytofire method, which can be used to instantiate BossFire objects. Stores the 
  health of the boss, when boss health reaches zero player beats the game.  
-  BossFire.java: Used to instantiate attacks from boss. Overrides the move method in GameObj class, 
  so that boss attacks always aim at the player and can bounce off the wall. Implements the 
  collidedWith method in GameObj class, so that the boss attacks will only resolve collisions with
  the player or player attacks(PlayerFire.java). 
-  Direction.java: Contains enumeration called Direction, which is used in GameObj.java to indicate
 the direction an object should move after it collides with another object.
-  Enemy.java: Used to instantiate enemies. Overrides the move method in GameObj class, so that 
enemies will always move horizontal until they hit the wall. When enemies hit the wall they will 
advance toward the bottom of the game court. Implements the collideWith method in GameObj class, so 
that enemies will only resolve collision with the player or player attacks(PlayerFire.java). Also 
contains trytofire method, which can be used to instantiate EnemyFire objects.  
-  EnemyFire.java: Used to instantiate attacks from enemies. Override the move method in GameObj 
class, so that enemy attacks always moves vertically downward. Implements the collideWith method in 
GameObj class, so that enemy attacks will only resolve collisions with the player or player attacks
(PlayerFire.java). 
-  Game.java: Implements GUI Elements in the game such as the Jframe that the game runs in, the 
reset and shop buttons, and the status panel, which shows "Running" when game is in progress and 
notifies the player when he loses or beats the game. Also contains the Main method that actually 
starts and run the game.  
-  GameCourt.java: Contains the main logic of the game. Contains a timer that runs the tick method
every 35 milliseconds. The tick method updates the location of all game objects and check game
objects against one another for collision. Tick method also maintains the counter of powerups such 
as agility, invincibility, and rapid fire. GameCourt also contains methods getHighScore and 
checkScore. These two methods will be invoked by tick when player loses or beats the game. GameCourt
keeps track of all game objects using two arrayLists. gameObjects arrayList contains all game 
objects in the current frame, and toberemoved arrayList contains all gameObjects that will be 
removed in the next frame.    
-  GameObj.java: Contains methods that are available to all game objects. Such as intersect, bounce, 
  and move. Also contains a colldedWith abstract method that all subtypes need to implement. 
-  LifeObject.java: Used to instantiate power ups that will increase player's health. Overrides move
method in GameObj, so that LifeObjects only move vertically down. Implements collidedWith method 
in GameObj class, so that LifeObjects only resolve collisions with the player.  
-  Player.java: Used to instantiate the player in game. Stores the health and score of the player.
 Score is used to check for high score when player loses or beats the game. If player's health 
 reaches zero, the game is over. 
-  PlayerFire.java: Used to instantiate attacks from the player. Implements the collidedWith method
in GameObj class, so that player attacks only resolve collisions with Enemy, EnemyFire, Boss, and 
BossFire objects. Override the move method in GameObj class, so that player attacks only moves 
vertically up.    
  
- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?
  
  I ended up keeping all components of my plan except level design using I/O file parsing. I ended 
  up not implementing a level system in my game because this way high score will not be capped. Game
  continues as long as the boss and the player do not die. I think the game is more interesting and 
  challenging this way. 

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  One significant stumbling block is the proper resolution of collisions between game objects. I 
  have to make sure player ignores his own attacks, registers attacks from enemy and boss, and 
  registers collisions with enemy and boss to declare game over. I also need to make sure enemy, 
  boss and their attacks do not register each other. My solution is to declare the collidedWith
  abstract method in GameObj class that every subtype needs to implement. Using dynamic dispatch,
  collisions between different game objects can be resolved correctly. Another significant stumbling
  block is the implementation of shop because I need to make sure that the look and attack pattern 
  of the player is updated after the player makes purchase.  

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think there is a good separation of functionality in my design. I created a class for each type 
  of game object that will handle their respective behavior. I also created Appearance and 
  AppearanceResource classes to explicitly handle the drawing part of my game because reading images 
  from files every time they are needed is very inefficient. Private state encapsulation of my 
  classes is not very well because there are very few states that I can keep private. For example, I
  need access to player score in GameCourt class to run the checkScore method. I also need access to
  player's health in GameCourt class to determine when the game should end. GameCourt class also 
  needs access to boss's health to determine whether the player has win the game. Similarly, 
  EnemyFire class needs access to player health to decrement it when the player is attacked. These 
  prevent my design to have good private state encapsulation. Taking these into consideration, I 
  would refactor highScore and getHighScore methods into my player class and make score a private 
  field instead of public. I would also resolve any collisions with the player inside the 
  collidedWith method of the Player class. This way I can make life a private field as well. I also 
  think Enemy and Boss class are rather similar. Maybe I can create a supertype for these two 
  objects.    


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

  I did not use other libraries and tutorials while implementing my game. I found all of my images 
  using Google. Below are links to some of the images I used. 
  "background1.jpg" : http://galleryhip.com/infinity-wallpaper-tumblr.html
  "background2.jpg" : http://www.desktopwallpaperhd.net/wallpapers-space-universe-photography-images-201250.html
  "tempest1.png" : http://voidwar.deviantart.com/art/Starcraft-Protoss-Tempest-459897333
  "shipAA-icon.png" : http://ko.appszoom.com/android_games/action/starship-fighters_hjsmc.html
  "SHIPB-icon.png" : http://ftparmy.com/cat/360-graphics-54/ 
  "SHIPC-icon.png" : http://ftparmy.com/cat/360-graphics-54/
  "shipBB-icon.png" : http://ftparmy.com/cat/360-graphics-54/
  "shipCC-icon.png" : http://ftparmy.com/cat/360-graphics-54/
 

