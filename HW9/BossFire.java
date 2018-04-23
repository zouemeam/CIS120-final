
public class BossFire extends GameObj {
    public static final int WIDTH_SHOT = 37;
    public static final int HEIGHT_SHOT = 30;
    public static final int INIT_X = 130;
    public static final int INIT_Y = 130;
    public static final int INIT_VEL_X = 10;
    public static final int INIT_VEL_Y = 10;
    private GameCourt game;
    // This is to prevent a Boss attack from employing twice before it is
    // removed.
    private boolean used = false;

    public BossFire(int ini_x, int ini_y, int speed_x, int speed_y, int courtWidth, int courtHeight,
            GameCourt game) {
        super("boss_charge.png", speed_x, speed_y, ini_x + 10, ini_y + 10, WIDTH_SHOT, HEIGHT_SHOT,
                courtWidth, courtHeight);
        this.game = game;
    }

    // Collision with playerFire and player is registered. If player has
    // invincible, the collision
    // will be ignored. If player health reaches zero, the method notifies that
    // the game is over.
    public void collidedWith(GameObj other) {
        if (used) {
            return;
        }
        if ((other instanceof Player) || (other instanceof PlayerFire)) {
            game.removeGameObj(this);
            used = true;
            if ((game.invincible) && (other instanceof Player)) {
                return;
            } else if (other instanceof Player) {
                game.square.life -= 1;
                if (game.square.life < 1) {
                    game.removeGameObj(other);
                    game.lose = true;
                }
                return;
            } else {
                game.removeGameObj(other);
            }
            used = true;
        }
    }

    // Has the ability to move both horizontally and vertically. It can also
    // bounce off walls.
    @Override
    public void move() {
        pos_x += v_x;
        pos_y += v_y;

        this.bounce(this.hitWall());
        if ((pos_y < 0) || (pos_y > 600)) {
            game.toberemoved.add(this);
        }
    }

}
