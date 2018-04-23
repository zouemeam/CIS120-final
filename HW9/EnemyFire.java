
public class EnemyFire extends GameObj {
    public static final int WIDTH_SHOT = 12;
    public static final int HEIGHT_SHOT = 23;
    public static final int INIT_X = 130;
    public static final int INIT_Y = 130;
    public static final int INIT_VEL_X = 10;
    public static final int INIT_VEL_Y = 10;
    private GameCourt game;
    // Keeps a private field to prevent enemy attack from employing twice before
    // removal
    private boolean used = false;

    public EnemyFire(int ini_x, int ini_y, int courtWidth, int courtHeight, GameCourt game) {
        super("enemyfire1.gif", INIT_VEL_X, INIT_VEL_Y, ini_x + 10, ini_y + 10, WIDTH_SHOT,
                HEIGHT_SHOT, courtWidth, courtHeight);
        this.game = game;
    }

    // Collision with player and player fire is registered. If player reaches
    // zero health, the game
    // is over.
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

    // Enemy fire only moves vertically downward and is removed from game court
    // when it hits the
    // bottom.
    @Override
    public void move() {
        pos_y += v_y;
        if ((pos_y < 0) || (pos_y > 600)) {
            game.toberemoved.add(this);
        }

    }

}
