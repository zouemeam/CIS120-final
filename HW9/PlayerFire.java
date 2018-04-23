
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
 * A game object displayed using an image.
 * 
 * Note that the image is read from the file when the object is constructed, and
 * that all objects created by this constructor share the same image data (i.e.
 * img is static). This important for efficiency: your program will go very
 * slowing if you try to create a new BufferedImage every time the draw method
 * is invoked.
 */
public class PlayerFire extends GameObj {
    public static final int PLAYERFIRE_WIDTH = 12;
    public static final int PLAYERFIRE_HEIGHT = 23;
    public static final int INIT_X = 130;
    public static final int INIT_Y = 130;
    public static final int INIT_VEL_X = 10;
    public static final int INIT_VEL_Y = 10;
    private GameCourt game;
    // prevent player fire from employing twice
    private boolean used = false;

    public PlayerFire(int ini_x, int ini_y, int courtWidth, int courtHeight, GameCourt game) {
        super("shot.gif", INIT_VEL_X, INIT_VEL_Y, ini_x + 10, ini_y + 10, PLAYERFIRE_WIDTH,
                PLAYERFIRE_HEIGHT, courtWidth, courtHeight);
        this.game = game;
    }

    // Collision with enemies, boss, and enemy fire is handled here. Collision
    // with enemy and enemy
    // fire is resolved by removing them from gameObject arrayList. Collision
    // with boss is resolved
    // decrementing the health of the boss. If boss's health reaches zero, the
    // player wins the game.
    // Player's score will also increment with every enemy killed and has a
    // small probability of
    // receiving money for the kill.
    public void collidedWith(GameObj other) {
        if (used) {
            return;
        } else if ((other instanceof Enemy) || (other instanceof EnemyFire)) {
            game.removeGameObj(this);
            if (other instanceof Enemy) {
                if (!game.toberemoved.contains(other)) {
                    game.enemyCount--;
                }
                game.square.score += 10;
                if (Math.random() < 0.5) {
                    game.square.money += 20;
                }
            }
            game.removeGameObj(other);
            used = true;
        } else if (other instanceof Boss) {
            game.boss.life--;
            if (game.boss.life <= 0) {
                game.win = true;
            }
            game.square.score += 50;
            game.square.money += 100;
            used = true;
            game.removeGameObj(this);
        }
    }

    // Player fire only moves vertically from bottom up.
    @Override
    public void move() {
        pos_y -= v_y;
        if ((pos_y < 0) || (pos_y > 600)) {
            game.toberemoved.add(this);
        }

    }

}
