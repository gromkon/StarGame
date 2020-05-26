package ru.gromkon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.gromkon.base.BaseScreen;
import ru.gromkon.math.Rect;
import ru.gromkon.pool.BulletPool;
import ru.gromkon.pool.EnemyPool;
import ru.gromkon.pool.ExplosionPool;
import ru.gromkon.sprite.Background;
import ru.gromkon.sprite.Bullet;
import ru.gromkon.sprite.ButtonNewGame;
import ru.gromkon.sprite.EnemyShip;
import ru.gromkon.sprite.GameOver;
import ru.gromkon.sprite.PlayerShip;
import ru.gromkon.sprite.Star;
import ru.gromkon.sprite.State;
import ru.gromkon.utils.EnemyEmitter;


public class GameScreen extends BaseScreen {

    private final static int STARS_COUNT = 128;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private PlayerShip playerShip;
    private BulletPool bulletPool;

    private EnemyEmitter enemyEmitter;
    private EnemyPool enemyPool;

    private ExplosionPool explosionPool;

    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;

    private Star[] stars;

    private Music gameMusic;

    private State state;

    @Override
    public void show() {
        super.show();

        bg = new Texture("textures/background_new2.jpg");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);

        playerShip = new PlayerShip(atlas, bulletPool, explosionPool);

        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool);

        gameOver = new GameOver(atlas);

        buttonNewGame = new ButtonNewGame(atlas, this);

        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game_music.mp3"));
        gameMusic.setVolume(0.03f);
        gameMusic.setLooping(true);
        gameMusic.play();

        state = State.PLAYING;

    }

    public void setStartOptions() {
        List<EnemyShip> enemyShips = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip: enemyShips) {
            enemyShip.endGame();
        }
        enemyPool.freeAllDestroyed();
        List<Bullet> bullets = bulletPool.getActiveObjects();
        for (Bullet bullet: bullets) {
            bullet.destroy();
        }
        bulletPool.freeAllDestroyed();

        enemyEmitter.setStartOptions();

        playerShip.setStartOptions();
    }


    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        gameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        free();
        draw();
    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
            playerShip.update(delta);
        }
    }

    private void checkCollision() {
        if (state != State.PLAYING) {
        return;
        }

        int COLLISION_DAMAGE = 20;
        List<EnemyShip> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemy: enemyList) {
            float minDist = enemy.getHalfWidth() + playerShip.getHalfWidth();
            if (playerShip.pos.dst(enemy.pos) < minDist) {
                enemy.takeDamage(COLLISION_DAMAGE);
                playerShip.takeDamage(COLLISION_DAMAGE);
                continue;
            }
            for (Bullet bullet: bulletList) {
                if (bullet.getOwner() == playerShip) {
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.takeDamage(bullet.getDamage());
                        bullet.destroy();
                    }
                }
            }
        }
        for (Bullet bullet: bulletList) {
            if (bullet.getOwner() != playerShip && !bullet.isDestroyed()) {
                if (playerShip.isBulletCollision(bullet)) {
                    playerShip.takeDamage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        if (playerShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }


    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            playerShip.draw(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void dispose() {
        bg.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        atlas.dispose();
        playerShip.dispose();
        gameMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            playerShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            playerShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            playerShip.touchDragged(touch, pointer);
        }
        return false;
    }
}
