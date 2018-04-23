
public class Boss extends GameObj {
    public static final int ENEMY_WIDTH = 150;
    public static final int ENEMY_HEIGHT = 155;
    public static final int INIT_POS_X = 430;
    public static final int INIT_POS_Y = 100;
    public static final int INIT_VEL_X = 2;
    public static final int INIT_VEL_Y = 3;
    public static double speed = 0.04;
    public static double angle = 0;
    public static double percent = 0.05;
    public int life = 50;
    private GameCourt game;

    // Only needs courtWidth, courtHeight, and a game reference to instantiate
    // this object
    public Boss(int courtWidth, int courtHeight, GameCourt game) {
        super("tempest1.png", INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, ENEMY_WIDTH,
                ENEMY_HEIGHT, courtWidth, courtHeight);
        this.game = game;
    }

    // Moves in an elliptical path
    @Override
    public void move() {
        angle += speed;
        double radiusX = 490;
        double radiusY = 150;
        pos_x = INIT_POS_X + (int) (radiusX * Math.sin(angle));
        pos_y = INIT_POS_Y + (int) (radiusY * Math.cos(angle));

    }

    // Game ends when collision with player is detected
    // Collision of boss with other game objects are handled elsewhere
    public void collidedWith(GameObj other) {
        if (other instanceof Player) {
            if (game == null) {
            } else {
                game.toberemoved.add(other);
                game.lose = true;
            }
        }
    }

    // If boss openFire the attacks will be aimed at the player. Boss's attacks
    // have the
    // ability to bounce off walls
    public void trytofire() {
        boolean openFire = Math.random() < percent;
        if (openFire) {
            double delta_x = (double) (this.game.square.pos_x - this.pos_x);
            double delta_y = (double) (this.game.square.pos_y - this.pos_y);
            double mag = Math.sqrt(delta_x * delta_x + delta_y * delta_y);
            int speed_x = (int) (10 * (delta_x / mag));
            int speed_y = (int) (10 * (delta_y / mag));
            game.gameObjects.add(
                    new BossFire(pos_x + 10, pos_y + 10, speed_x, speed_y, 1000, 600, this.game));

        }
    }

}
