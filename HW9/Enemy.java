
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a yellow circle, starting in the upper left
 * corner of the game court.
 * 
 */
public class Enemy extends GameObj {

    public static final int ENEMY_WIDTH = 47;
    public static final int ENEMY_HEIGHT = 51;
    public static final int INIT_POS_Y = 170;
    public static final int INIT_VEL_X = 2;
    public static final int INIT_VEL_Y = 3;
    public static double percent = 0.001;
    // Keeps a private field for the gameCourt it belongs to
    private GameCourt game;

    public Enemy(int courtWidth, int courtHeight, int ini_x, int ini_y, GameCourt game) {
        super("enemy.png", INIT_VEL_X, INIT_VEL_Y, ini_x, ini_y, ENEMY_WIDTH, ENEMY_HEIGHT,
                courtWidth, courtHeight);
        this.game = game;
    }

    // Enemy will move horizontally until it reaches the wall, upon hitting the
    // wall, the enemy
    // will advance downward.
    @Override
    public void move() {
        pos_x += v_x;
        if ((pos_x >= 1000) || (pos_x <= 0)) {
            pos_y += 30;
            v_x = -v_x;

        }
        if (pos_y >= 600) {
            game.playing = false;
        }
    }

    // If enemy collides with the player the game is over. Collision with other
    // game objects are
    // handled elsewhere.
    public void collidedWith(GameObj other) {
        if (other instanceof Player) {
            if (game == null) {
            } else {
                game.toberemoved.add(this);
                game.lose = true;
            }
        }
    }

    // Enemy will employ attack from its location. Enemy attacks only travel
    // vertically downward.
    public void trytofire() {
        boolean openFire = Math.random() < percent;
        if (openFire) {
            game.gameObjects.add(new EnemyFire(pos_x + 10, pos_y + 10, 1000, 600, this.game));
        }
    }

}
