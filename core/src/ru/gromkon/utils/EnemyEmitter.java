package ru.gromkon.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.math.Rect;
import ru.gromkon.math.Rnd;
import ru.gromkon.pool.EnemyPool;
import ru.gromkon.sprite.EnemyShip;
import ru.gromkon.sprite.PlayerShip;

public class EnemyEmitter {

//------------------- ENEMY_SMALL -------------------
    private static final String ENEMY_SMALL_TYPE = "small";

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 5;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_SMALL_BULLET_VX = 0;
    private static final float ENEMY_SMALL_BULLET_VY = -0.2f;
    private static final float ENEMY_SMALL_BULLET_RELOAD_INTERVAL = 3f;

    private static final float ENEMY_SMALL_VX = 0f;
    private static final float ENEMY_SMALL_VY = -0.1f;

//------------------- ENEMY_SMALL -------------------


//------------------- ENEMY_MEDIUM -------------------
    private static final String ENEMY_MEDIUM_TYPE = "medium";

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 10;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.025f;
    private static final float ENEMY_MEDIUM_BULLET_VX = 0;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final float ENEMY_MEDIUM_BULLET_RELOAD_INTERVAL = 2.5f;

    private static final float ENEMY_MEDIUM_VX = 0f;
    private static final float ENEMY_MEDIUM_VY = -0.08f;

//------------------- ENEMY_MEDIUM -------------------


//------------------- ENEMY_BIG -------------------
    private static final String ENEMY_BIG_TYPE = "big";

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 20;
    private static final int ENEMY_BIG_HP = 10;

    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VX = 0;
    private static final float ENEMY_BIG_BULLET_VY = -0.15f;
    private static final float ENEMY_BIG_BULLET_RELOAD_INTERVAL = 3.5f;

    private static final float ENEMY_BIG_VX = 0f;
    private static final float ENEMY_BIG_VY = -0.075f;

//------------------- ENEMY_BIG -------------------

    private Rect worldBounds;

    private PlayerShip playerShip;

    private float generateIntervalSmall;
    private float generateTimerSmall;

    private float generateIntervalMedium;
    private float generateTimerMedium;

    private float generateIntervalBig;
    private float generateTimerBig;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private Vector2 enemySmallV;
    private Vector2 enemyMediumV;
    private Vector2 enemyBigV;

    private final TextureRegion enemyBulletRegion;

    private final EnemyPool enemyPool;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, PlayerShip playerShip) {
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        enemyBulletRegion = atlas.findRegion("bulletEnemy");

        this.enemyPool = enemyPool;

        this.playerShip = playerShip;

        enemySmallV = new Vector2(ENEMY_SMALL_VX, ENEMY_SMALL_VY);
        enemyMediumV = new Vector2(ENEMY_MEDIUM_VX, ENEMY_MEDIUM_VY);
        enemyBigV = new Vector2(ENEMY_BIG_VX, ENEMY_BIG_VY);

        generateIntervalSmall = 2f;
        generateTimerSmall = 0f;

        generateIntervalMedium = 11f;
        generateTimerMedium = 0f;

        generateIntervalBig = 17f;
        generateTimerBig = 0f;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateBig(delta);
        generateMedium(delta);
        generateSmall(delta);
    }

    private void generateSmall(float delta) {
        generateTimerSmall += delta;
        if (generateTimerSmall >= generateIntervalSmall) {
            generateTimerSmall = 0f;
            EnemyShip enemy = enemyPool.obtain();
            enemy.set(
                    playerShip,
                    ENEMY_SMALL_TYPE,
                    enemySmallRegions,
                    enemySmallV,
                    enemyBulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VX,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_BULLET_RELOAD_INTERVAL,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_BULLET_DAMAGE,
                    ENEMY_SMALL_HP
            );
            enemy.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth()
                    );
            enemy.setBottom(worldBounds.getTop());
        }
    }

    private void generateMedium(float delta) {
        generateTimerMedium += delta;
        if (generateTimerMedium >= generateIntervalMedium) {
            generateTimerMedium = 0f;
            generateTimerSmall -= 0.5f;
            EnemyShip enemy = enemyPool.obtain();
            enemy.set(
                    playerShip,
                    ENEMY_MEDIUM_TYPE,
                    enemyMediumRegions,
                    enemyMediumV,
                    enemyBulletRegion,
                    ENEMY_MEDIUM_BULLET_HEIGHT,
                    ENEMY_MEDIUM_BULLET_VX,
                    ENEMY_MEDIUM_BULLET_VY,
                    ENEMY_MEDIUM_BULLET_RELOAD_INTERVAL,
                    ENEMY_MEDIUM_HEIGHT,
                    ENEMY_MEDIUM_BULLET_DAMAGE,
                    ENEMY_MEDIUM_HP
            );
            enemy.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth()
            );
            enemy.setBottom(worldBounds.getTop());
        }
    }

    private void generateBig(float delta) {
        generateTimerBig += delta;
        if (generateTimerBig >= generateIntervalBig) {
            generateTimerBig = 0f;
            generateTimerMedium -= 6f;
            generateTimerSmall -= 3f;
            EnemyShip enemy = enemyPool.obtain();
            enemy.set(
                    playerShip,
                    ENEMY_BIG_TYPE,
                    enemyBigRegions,
                    enemyBigV,
                    enemyBulletRegion,
                    ENEMY_BIG_BULLET_HEIGHT,
                    ENEMY_BIG_BULLET_VX,
                    ENEMY_BIG_BULLET_VY,
                    ENEMY_BIG_BULLET_RELOAD_INTERVAL,
                    ENEMY_BIG_HEIGHT,
                    ENEMY_BIG_BULLET_DAMAGE,
                    ENEMY_BIG_HP
            );
            enemy.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth()
            );
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public void setStartOptions() {
        generateTimerSmall = 0f;
        generateTimerMedium = 0f;
        generateTimerBig = 0f;
    }

    public static String getEnemySmallType() {
        return ENEMY_SMALL_TYPE;
    }

    public static String getEnemyMediumType() {
        return ENEMY_MEDIUM_TYPE;
    }

    public static String getEnemyBigType() {
        return ENEMY_BIG_TYPE;
    }
}
