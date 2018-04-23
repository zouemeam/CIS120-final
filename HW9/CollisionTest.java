import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.JLabel;

public class CollisionTest {
    JLabel status = new JLabel("Running...");

    @Test
    public void collissionBetweenEnemies() {
        GameCourt court1 = new GameCourt(status);
        court1.playing = false;
        court1.gameObjects.clear();
        Enemy enemy1 = new Enemy(100, 100, 5, 6, court1);
        Enemy enemy2 = new Enemy(100, 100, 5, 6, court1);
        court1.gameObjects.add(enemy1);
        court1.gameObjects.add(enemy2);
        court1.playing = true;
        court1.tick();
        assertTrue("Contains enemy 1", court1.gameObjects.contains(enemy1));
        assertTrue("Contains enemy 2", court1.gameObjects.contains(enemy2));
    }

    @Test
    public void collissionBetweenEnemiesAndBoss() {
        GameCourt court1 = new GameCourt(status);
        court1.playing = false;
        court1.gameObjects.clear();
        Enemy enemy = new Enemy(100, 100, 5, 6, court1);
        Boss boss = new Boss(100, 100, court1);
        court1.gameObjects.add(enemy);
        court1.gameObjects.add(boss);
        court1.playing = true;
        court1.tick();
        assertTrue("Contains enemy", court1.gameObjects.contains(enemy));
        assertTrue("Contains boss", court1.gameObjects.contains(boss));
    }

    @Test
    public void collissionBetweenEnemiesAndEnemyFire() {
        GameCourt court1 = new GameCourt(status);
        court1.playing = false;
        court1.gameObjects.clear();
        Enemy enemy = new Enemy(100, 100, 5, 6, court1);
        EnemyFire fire = new EnemyFire(5, 6, 100, 100, court1);
        court1.gameObjects.add(enemy);
        court1.gameObjects.add(fire);
        court1.playing = true;
        court1.tick();
        assertTrue("Contains enemy", court1.gameObjects.contains(enemy));
        assertTrue("Contains fire", court1.gameObjects.contains(fire));
    }

    @Test
    public void collissionBetweenEnemiesAndPlayerFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Enemy enemy = new Enemy(100, 100, 5, 6, court3);
        PlayerFire fire = new PlayerFire(5, 6, 100, 100, court3);
        court3.gameObjects.add(enemy);
        court3.gameObjects.add(fire);
        enemy.collidedWith(fire);
        fire.collidedWith(enemy);
        court3.playing = true;
        court3.tick();
        assertFalse("Do not contain enemy", court3.gameObjects.contains(enemy));
        assertFalse("Do not contain fire", court3.gameObjects.contains(fire));
    }

    @Test
    public void collissionBetweenEnemiesAndPlayer() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Enemy enemy = new Enemy(100, 100, 500, 550, court3);
        Player player = new Player(100, 100, court3);
        court3.gameObjects.add(enemy);
        court3.gameObjects.add(player);
        player.collidedWith(enemy);
        enemy.collidedWith(player);
        court3.playing = true;
        court3.tick();
        assertFalse("Do not contain enemy", court3.gameObjects.contains(enemy));
        assertFalse("Do not contain player", court3.gameObjects.contains(player));
    }

    @Test
    public void collissionBetweenEnemiesAndBossFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Enemy enemy = new Enemy(100, 100, 500, 550, court3);
        BossFire fire = new BossFire(500, 550, 3, 3, 100, 100, court3);
        court3.gameObjects.add(enemy);
        court3.gameObjects.add(fire);
        enemy.collidedWith(fire);
        fire.collidedWith(enemy);
        court3.playing = true;
        court3.tick();
        assertTrue("Contains enemy", court3.gameObjects.contains(enemy));
        assertTrue("Contains boss fire", court3.gameObjects.contains(fire));

    }

    @Test
    public void collissionBetweenBossAndBossFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Boss boss = new Boss(100, 100, court3);
        BossFire fire = new BossFire(430, 100, 3, 3, 100, 100, court3);
        court3.gameObjects.add(boss);
        court3.gameObjects.add(fire);
        boss.collidedWith(fire);
        fire.collidedWith(boss);
        court3.playing = true;
        court3.tick();
        assertTrue("Contains boss", court3.gameObjects.contains(boss));
        assertTrue("ontains boss fire", court3.gameObjects.contains(fire));

    }

    @Test
    public void collissionBetweenBossAndPlayer() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Boss boss = new Boss(100, 100, court3);
        Player player = new Player(100, 100, court3);
        court3.gameObjects.add(boss);
        court3.gameObjects.add(player);
        boss.collidedWith(player);
        player.collidedWith(boss);
        court3.playing = true;
        court3.tick();
        assertTrue("Game Over", court3.lose);
    }

    @Test
    public void collissionBetweenBossAndPlayerFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Boss boss = new Boss(100, 100, court3);
        PlayerFire fire = new PlayerFire(430, 100, 100, 100, court3);
        court3.gameObjects.add(boss);
        court3.gameObjects.add(fire);
        boss.collidedWith(fire);
        fire.collidedWith(boss);
        court3.playing = true;
        court3.tick();
        System.out.println(boss.life);
        assertTrue("Collision with boss detected", court3.boss.life == 49);
    }

    @Test
    public void collissionBetweenBossAndEnemyFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Boss boss = new Boss(100, 100, court3);
        EnemyFire fire = new EnemyFire(430, 100, 100, 100, court3);
        court3.gameObjects.add(boss);
        court3.gameObjects.add(fire);
        boss.collidedWith(fire);
        fire.collidedWith(boss);
        court3.playing = true;
        court3.tick();
        assertTrue("enemy fire exists", court3.gameObjects.contains(fire));
        assertTrue("boss did not register damange", court3.boss.life == 50);

    }

    @Test
    public void collissionBetweenPlayerAndPlayerFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Player player = new Player(100, 100, court3);
        PlayerFire fire = new PlayerFire(550, 500, 100, 100, court3);
        court3.gameObjects.add(player);
        court3.gameObjects.add(fire);
        player.collidedWith(fire);
        fire.collidedWith(player);
        court3.playing = true;
        court3.tick();
        assertTrue("player still exists", court3.gameObjects.contains(player));
        assertTrue("player fire still exists", court3.gameObjects.contains(fire));

    }

    @Test
    public void collisionBetweenPlayerAndBossFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Player player = new Player(100, 100, court3);
        BossFire fire = new BossFire(550, 500, 3, 3, 100, 100, court3);
        court3.gameObjects.add(player);
        court3.gameObjects.add(fire);
        player.collidedWith(fire);
        fire.collidedWith(player);
        court3.playing = true;
        court3.tick();
        assertTrue("player still exists", court3.gameObjects.contains(player));
        assertFalse("boss fire still exists", court3.gameObjects.contains(fire));
        assertTrue("player health decreased", court3.square.life == 2);
    }

    @Test
    public void collisionBetweenPlayerAndEnemyFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        Player player = new Player(100, 100, court3);
        EnemyFire fire = new EnemyFire(550, 500, 100, 100, court3);
        court3.gameObjects.add(player);
        court3.gameObjects.add(fire);
        player.collidedWith(fire);
        fire.collidedWith(player);
        court3.playing = true;
        court3.tick();
        assertTrue("player still exists", court3.gameObjects.contains(player));
        assertFalse("enemy fire still exists", court3.gameObjects.contains(fire));
        assertTrue("player health decreased", court3.square.life == 2);
    }

    @Test
    public void collisionBetweenPlayerFireAndEnemyFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        PlayerFire pfire = new PlayerFire(100, 100, 100, 100, court3);
        EnemyFire efire = new EnemyFire(100, 100, 100, 100, court3);
        court3.gameObjects.add(pfire);
        court3.gameObjects.add(efire);
        efire.collidedWith(pfire);
        pfire.collidedWith(efire);
        court3.playing = true;
        court3.tick();
        assertFalse("enemy fire still exists", court3.gameObjects.contains(efire));
        assertFalse("player fire still exists", court3.gameObjects.contains(pfire));

    }
    @Test 
    public void collisionBetweenPlayerFireAndBossFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        PlayerFire pfire = new PlayerFire(100, 100, 100, 100, court3);
        BossFire efire = new BossFire(100, 100,3,3, 100, 100, court3);
        court3.gameObjects.add(pfire);
        court3.gameObjects.add(efire);
        efire.collidedWith(pfire);
        pfire.collidedWith(efire);
        court3.playing = true;
        court3.tick();
        assertFalse("boss fire does not exists", court3.gameObjects.contains(efire));
        assertFalse("player fire does not exists", court3.gameObjects.contains(pfire));
    }
    @Test
    public void collisionBetweenBossFireAndEnemyFire() {
        GameCourt court3 = new GameCourt(status);
        court3.playing = false;
        court3.gameObjects.clear();
        EnemyFire pfire = new EnemyFire(100, 100, 100, 100, court3);
        BossFire efire = new BossFire(100, 100,3,3, 100, 100, court3);
        court3.gameObjects.add(pfire);
        court3.gameObjects.add(efire);
        efire.collidedWith(pfire);
        pfire.collidedWith(efire);
        court3.playing = true;
        court3.tick();
        assertTrue("boss fire still exists", court3.gameObjects.contains(efire));
        assertTrue("enemy fire does not exists", court3.gameObjects.contains(pfire));
    }
    

}
