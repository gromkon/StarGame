package ru.gromkon.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.Ship;
import ru.gromkon.math.Rect;
import ru.gromkon.pool.BulletPool;
import ru.gromkon.pool.ExplosionPool;

public class PlayerShip extends Ship {

    private static final float SHIP_SIZE = 0.15f;
    private static final float OFFSET_BOTTOM = 0.05f;

    private static final float BULLET_HEIGHT = 0.01f;
    private static final float BULLET_VX = 0;
    private static final float BULLET_VY = 0.6f;
    private static final float BULLET_RELOAD_INTERVAL = 0.33f;
    private static final int BULLET_DAMAGE = 1;
    private Sound bulletSound;

    private static final int HP = 100;

    private final float V_LEN = 0.005f;

    private Vector2 touch;
    private Vector2 common;
    private Vector2 vResistX;
    private Vector2 vResistY;

    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);

        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV.set(BULLET_VX, BULLET_VY);
        bulletHeight = BULLET_HEIGHT;
        bulletReloadInterval = BULLET_RELOAD_INTERVAL;
        bulletReloadTimer = 0f;
        damage = BULLET_DAMAGE;
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.mp3"));
        super.setBulletSound(bulletSound);

        this.explosionPool = explosionPool;

        hp = HP;

        touch = new Vector2();
        common = new Vector2();
        vResistX = new Vector2();
        vResistY = new Vector2();
    }

    @Override
    public void update(float delta) {
        bulletStartPos.set(pos).add(0, getHalfWidth());
        checkShoot(delta, bulletStartPos);

        checkMove();
    }

    private void checkMove() {
        common.set(touch);
        if (common.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
            v.setZero();
            vResistX.setZero();
            vResistY.setZero();
        }
        checkBounds();
    }

    private void checkBounds() {
        if (getLeft() + getHalfWidth() < worldBounds.getLeft() ||
                getRight() - getHalfWidth() > worldBounds.getRight()) {
            pos.add(vResistX);
            v.scl(0.1f);
            vResistX.scl(0.1f);
        }
        if (getBottom() + getHalfHeight() < worldBounds.getBottom() ||
                getTop() > worldBounds.getTop()) {
            pos.add(vResistY);
            v.scl(0.1f);
            vResistY.scl(0.1f);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SHIP_SIZE);
        setBottom(worldBounds.getBottom() + OFFSET_BOTTOM);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        calcSpeed(touch);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        calcSpeed(touch);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        calcSpeed(touch);
        return false;
    }

    private void calcSpeed(Vector2 touch) {
        this.touch.set(touch);
        v.set(touch.sub(pos).setLength(V_LEN));
        vResistX.set(-v.x, 0);
        vResistY.set(0, -v.y);
    }

    public void dispose() {
        bulletSound.dispose();
    }
}
