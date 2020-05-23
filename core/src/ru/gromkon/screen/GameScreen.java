package ru.gromkon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.BaseScreen;
import ru.gromkon.math.Rect;
import ru.gromkon.pool.BulletPool;
import ru.gromkon.pool.EnemyPool;
import ru.gromkon.pool.ExplosionPool;
import ru.gromkon.sprite.Background;
import ru.gromkon.sprite.PlayerShip;
import ru.gromkon.sprite.Star;
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

    private Star[] stars;

    private Music gameMusic;

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

        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game_music.mp3"));
        gameMusic.setVolume(0.03f);
        gameMusic.setLooping(true);
        gameMusic.play();

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
        playerShip.update(delta);
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
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        playerShip.draw(batch);
        batch.end();
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
        playerShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        playerShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        playerShip.touchDragged(touch, pointer);
        return false;
    }
}
