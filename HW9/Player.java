
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */
public class Player extends GameObj {
    public static int PLAYER_WIDTH = 20;
    public static int PLAYER_HEIGHT = 20;
    public static final int INIT_X = 500;
    public static final int INIT_Y = 550;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public int life = 3;
    public int money = 300;
    public int score = 0;
    private GameCourt game;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters
     */
    // Note that player class does not contain the trytofire method because it
    // needs keyboard inputs,
    // hence, it is placed in the GameCourt class
    public Player(int courtWidth, int courtHeight, GameCourt game) {
        super("ship.gif", INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, PLAYER_WIDTH, PLAYER_HEIGHT,
                courtWidth, courtHeight);
        this.game = game;
    }

    // Only collision with enemy is registered. Collision with enemy fire is
    // handled in EnemyFire
    // class. When collision with enemy is detected the game is over.
    public void collidedWith(GameObj other) {
        if (other instanceof Enemy) {
            if (game == null) {
            } else {
                game.toberemoved.add(other);
                game.toberemoved.add(this);
                game.lose = true;
                game.playing = false;
                game.enemyCount -= 1;
            }
        }
    }
}
