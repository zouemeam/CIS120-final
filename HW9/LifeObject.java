
public class LifeObject extends GameObj {
    public static final int WIDTH_SHOT = 12;
    public static final int HEIGHT_SHOT = 23;
    public static final int INIT_X = 130;
    public static final int INIT_Y = 130;
    public static final int INIT_VEL_X = 8;
    public static final int INIT_VEL_Y = 8;
    private GameCourt game;
    // prevent a life powerup from being used twice
    private boolean used = false;

    public LifeObject(int courtWidth, int courtHeight, GameCourt game) {
        super("life1.png", INIT_VEL_X, INIT_VEL_Y, (int) (Math.random() * 1000), 0, WIDTH_SHOT,
                HEIGHT_SHOT, courtWidth, courtHeight);
        this.game = game;
    }

    // Only collision with player is registered, the collision is resolved by
    // incrementing the
    // health of the player
    public void collidedWith(GameObj other) {
        if (used) {
            return;
        } else if (other instanceof Player) {
            game.square.life++;
            game.removeGameObj(this);

            used = true;
        }
    }

    @Override
    // Only moves vertically from top to bottom
    public void move() {
        pos_y += v_y;
        if ((pos_y < 0) || (pos_y > 600)) {
            game.toberemoved.add(this);
        }

    }
}
