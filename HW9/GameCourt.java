
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
    // Keeps track of game objects in the current frame
    ArrayList<GameObj> gameObjects = new ArrayList<GameObj>();
    // Keeps track of game objects to be removed in the next frame
    ArrayList<GameObj> toberemoved = new ArrayList<GameObj>();
    // the state of the game logic
    public Player square; // the player, keyboard control
    public Boss boss;
    public int enemyCount;// keeps track the number of enemies in the gamecourt
    // public Timer timer;
    public boolean playing = false; // whether the game is running
    private JLabel status; // Current status text (i.e. Running...)

    // Game constants
    public static final int COURT_WIDTH = 1000;
    public static final int COURT_HEIGHT = 600;
    public static int SQUARE_VELOCITY = 4;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    public static int charge = 0;
    // powerups
    // player can only fire again after tick method is called twenty times.
    public int chargeConstraint = 20;
    public boolean timeFreeze = false;
    public boolean rapidFire = false;
    public boolean invincible = false;
    public boolean agility = false;
    // The duration of powerups is measured in the number of times the tick
    // method is called.
    public int agilityLapsed;
    public int rapidFireLapsed;
    public int timeFreezeLapsed;
    public int invincibleLapsed;
    // Indicates whether the game is paused or not
    public boolean Pause = false;
    private String highscore = "";
    // shop
    public boolean inShop = false;
    // indicates whether the player wins the game
    public boolean win = false;
    // indicates whether the player losese the game
    public boolean lose = false;
    // image for the background
    BufferedImage img1 = null;

    {
        try {
            img1 = ImageIO.read(new File("background1.jpg"));
        } catch (IOException e) {
        }
    }

    public BufferedImage backgroundImg = img1;
    // image for the background
    BufferedImage img2 = null;

    {
        try {
            img2 = ImageIO.read(new File("background2.jpg"));
        } catch (IOException e) {
        }
    }

    // initialize the game by instantiating enemies, boss, and the player. Set
    // all relevant values
    // to default value
    private void initEntities() {

        square = new Player(COURT_WIDTH, COURT_HEIGHT, this);
        boss = new Boss(COURT_WIDTH, COURT_HEIGHT, this);
        gameObjects.add(boss);
        gameObjects.add(square);
        Pause = false;
        win = false;
        lose = false;
        for (int row = 0; row < 5; row++) {
            for (int x = 0; x < 12; x++) {
                GameObj alien = new Enemy(COURT_WIDTH, COURT_HEIGHT, 100 + (x * 70),
                        (50) + row * 40, this);
                gameObjects.add(alien);
                enemyCount++;

            }
        }
    }

    // The attack method for the player which instantiates PlayerFire objects
    // depend on the current
    // player type.
    public void trytofire() {
        if (charge < chargeConstraint) {
            return;
        } else if (square.type.equals("ship.gif")) {
            gameObjects.add(
                    new PlayerFire(square.pos_x, square.pos_y, COURT_WIDTH, COURT_HEIGHT, this));
            charge = 0;
        } else if (square.type.equals("shipAA.png")) {
            gameObjects.add(new PlayerFire(square.pos_x - 10, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            gameObjects.add(new PlayerFire(square.pos_x + 10, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            charge = 0;

        } else if (square.type.equals("shipBB.png")) {
            gameObjects.add(new PlayerFire(square.pos_x + 4, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            gameObjects.add(new PlayerFire(square.pos_x - 13, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            gameObjects.add(new PlayerFire(square.pos_x + 21, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            charge = 0;
        } else if (square.type.equals("shipCC.png")) {
            gameObjects.add(new PlayerFire(square.pos_x - 2, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            gameObjects.add(new PlayerFire(square.pos_x + 18, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            gameObjects.add(new PlayerFire(square.pos_x - 16, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            gameObjects.add(new PlayerFire(square.pos_x + 32, square.pos_y, COURT_WIDTH,
                    COURT_HEIGHT, this));
            charge = 0;
        }
    }

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        initEntities();
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });

        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key
        // events will be handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long
        // as an arrow key is pressed, by changing the square's
        // velocity accordingly. (The tick method below actually
        // moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // P to pause the game
                if (e.getKeyCode() == KeyEvent.VK_P) {

                    if (inShop) {
                        
                        return;
                    } else {
                        Pause = true;
                        playing = false;
                        // repaint();
                    }

                }
                // R to resume the game
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    if (inShop) {
                        return;
                    } else {
                        playing = true;
                        Pause = false;
                    }
                }
                // D to use agility powerup
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    if (square.money >= 50) {
                        agility = true;
                        agilityLapsed = 600;
                        SQUARE_VELOCITY = 10;
                        square.money -= 50;
                    }
                }
                // W to use invincible powerup
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    if (square.money >= 50) {
                        invincible = true;
                        invincibleLapsed = 500;
                        square.money -= 50;
                    }
                }
                // S to use rapidFire powerup
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    if (square.money >= 50) {
                        rapidFire = true;
                        rapidFireLapsed = 300;
                        square.money -= 50;
                    }
                }
                // A to use timeFreeze powerup
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    if (square.money >= 50) {
                        timeFreeze = true;
                        timeFreezeLapsed = 6000;
                        square.money -= 50;
                    }
                }
                // Space to fire
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    trytofire();
                }
                // Direction keys to control the movement of the player
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    square.v_x = -SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    square.v_x = SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    square.v_y = SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    square.v_y = -SQUARE_VELOCITY;
            }

            public void keyReleased(KeyEvent e) {
                square.v_x = 0;
                square.v_y = 0;
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    // Reset the game by clearing all relevant variables in the game court, such
    // as the gameObject
    // arrayList.
    public void reset() {

        playing = true;
        enemyCount = 0;
        win = false;
        lose = false;
        gameObjects.clear();
        initEntities();
        status.setText("Running...");
        agility = false;
        rapidFire = false;
        invincible = false;
        timeFreeze = false;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    // All updates to current game objects are in this method
    void tick() {
        // Only updates when it is confirmed that the game is playing
        if (playing) {
            // if highscore is empty string, retrieve high score from file
            if (highscore.equals("")) {
                highscore = this.getHighScore();
            }
            // LifeObjects drop with the probability of 0.05 on every call of
            // tick
            if (Math.random() < 0.05) {
                gameObjects.add(new LifeObject(1000, 600, this));
            }
            // update the location of all game objects in game, enemies and boss
            // will attempt
            // to fire

            for (int i = 0; i < gameObjects.size(); i++) {
                GameObj entity = (GameObj) gameObjects.get(i);
                if ((timeFreeze) && (entity instanceof Enemy)) {
                    timeFreezeLapsed -= 1;
                    if (timeFreezeLapsed <= 0) {
                        timeFreeze = false;
                    }
                    continue;
                } else {
                    entity.move();
                }

                if ((entity instanceof Enemy) || (entity instanceof Boss)) {
                    entity.trytofire();
                }
            }
            // Compare all game objects against each other for collision.
            for (int p = 0; p < gameObjects.size(); p++) {
                for (int s = p + 1; s < gameObjects.size(); s++) {
                    GameObj me = (GameObj) gameObjects.get(p);
                    GameObj him = (GameObj) gameObjects.get(s);

                    if (me.intersects(him)) {
                        me.collidedWith(him);
                        him.collidedWith(me);
                    }
                }
            }
            // remove all game objects that need to be removed
            gameObjects.removeAll(toberemoved);
            // clear toberemoved arrayList so it can store new objects next
            // frame
            toberemoved.clear();
            // every call to tick increments charge by one
            charge++;
            // counter for rapidFire
            if (rapidFire) {
                chargeConstraint = 5;
                rapidFireLapsed -= 1;

                if (rapidFireLapsed <= 0) {
                    rapidFire = false;
                    chargeConstraint = 20;
                }
            }
            // counter for agility
            if (agility) {
                agilityLapsed -= 1;
                if (agilityLapsed <= 0) {
                    agility = false;
                    SQUARE_VELOCITY = 4;
                }
            }
            // counter for invincible
            if (invincible) {
                invincibleLapsed -= 1;
                if (invincibleLapsed <= 0) {
                    invincible = false;
                }
            }

            // check if the player lose the game
            if (lose) {
                status.setText("You Lose!");
                playing = false;
                checkScore();
            }
            // check if the player wins the game
            if (win) {
                status.setText("You Win!");
                playing = false;
                checkScore();
            }
            // if the enemy count is zero and the boss has yet to die,
            // instantiate 60 more enemies,
            // background image also has 50% chance of changing.
            if (enemyCount <= 0) {
                if (Math.random() < 0.5) {
                    backgroundImg = img1;
                } else {
                    backgroundImg = img2;
                }
                for (int row = 0; row < 5; row++) {
                    for (int x = 0; x < 12; x++) {
                        GameObj alien = new Enemy(COURT_WIDTH, COURT_HEIGHT, 100 + (x * 70),
                                (50) + row * 40, this);
                        gameObjects.add(alien);
                    }
                }
                enemyCount = 60;

            }
            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawImage(backgroundImg, 0, 0, null);
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObj entity = (GameObj) gameObjects.get(i);
            entity.draw(g);
        }
        // Display all necessary information for user to play the game
        g.drawString("Score:" + square.score, 75, 10);
        g.drawString("Life:" + square.life, 200, 10);
        g.drawString("Money:" + square.money, 400, 10);
        g.drawString("Highscore: " + highscore, 75, 20);
        g.drawString("Boss Health: " + boss.life, 600, 10);
        g.drawString("Enemy Count: " + enemyCount, 800, 10);
        if (Pause) {
            g.drawString("GAME PAUSED, PRESS R TO RESUME", 400, 400);
        }
        repaint();
        if (timeFreeze) {
            g.drawString("Time Freeze in effect", 10, 520);
        }
        if (invincible) {
            g.drawString("Invincible in effect", 10, 540);
        }
        if (rapidFire) {
            g.drawString("Rapid Fire in effect", 10, 560);
        }
        if (agility) {
            g.drawString("Agility in effect", 10, 580);
        }

    }

    // method for adding game objects to the toberemoved arrayList.
    public void removeGameObj(GameObj entity) {
        toberemoved.add(entity);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

    // highscore management
    public void checkScore() {
        if (highscore.equals("")) {
            return;
        }
        if (square.score > Integer.parseInt(highscore.split(":")[1])) {
            String name = JOptionPane
                    .showInputDialog("You have reached highscore, what is your name?");
            highscore = name + ":" + square.score;
            File scoreFile = new File("highscore.txt");
            if (!scoreFile.exists()) {
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            FileWriter filewriter = null;
            BufferedWriter writer = null;
            try {
                filewriter = new FileWriter(scoreFile);
                writer = new BufferedWriter(filewriter);
                writer.write(this.highscore);
            } catch (Exception e) {
                // error
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    // get highscore
    public String getHighScore() {
        // format: user: highscore
        FileReader readfile = null;
        BufferedReader reader = null;
        try {
            readfile = new FileReader("highscore.txt");
            reader = new BufferedReader(readfile);
            return reader.readLine();
        } catch (Exception e) {
            return "nobody:0";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
