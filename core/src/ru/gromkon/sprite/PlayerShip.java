package ru.gromkon.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.Sprite;
import ru.gromkon.math.Rect;
import ru.gromkon.pool.BulletPool;

public class PlayerShip extends Sprite {

    private static final float SHIP_SIZE = 0.15f;
    private static final float OFFSET_BOTTOM = 0.05f;

    private static final float BULLET_SPEED_X = 0;
    private static final float BULLET_SPEED_Y = 0.4f;
    private static final float BULLET_SIZE = 0.01f;
    private int damage;
    private static final int BULLET_DELAY = 20;
    private int currentBulletDelay;
    private Vector2 bulletStartPos;
    private final Sound bulletSound;

    private final float V_LEN = 0.01f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 common;
    private Vector2 vResistX;
    private Vector2 vResistY;

    private Rect worldBounds;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;

    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);

        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        damage = 1;
        bulletV = new Vector2(BULLET_SPEED_X, BULLET_SPEED_Y);
        currentBulletDelay = 0;
        bulletStartPos = new Vector2();
        this.bulletSound = bulletSound;

        touch = new Vector2();
        common = new Vector2();
        v = new Vector2();
        vResistX = new Vector2();
        vResistY = new Vector2();
    }

    @Override
    public void update(float delta) {
        checkShoot();
        checkMove();
    }

    private void checkShoot() {
        if (currentBulletDelay == BULLET_DELAY) {
            shoot();
            currentBulletDelay = 0;
        } else {
            currentBulletDelay++;
        }
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

    private void shoot() {
        bulletStartPos.set(pos).add(0, getHalfWidth());
        Bullet bullet = bulletPool.obtain();
        bullet.set(
                this,
                bulletRegion,
                bulletStartPos,
                bulletV,
                BULLET_SIZE,
                worldBounds,
                damage
        );
        bulletSound.play(0.03f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SHIP_SIZE);
        setBottom(worldBounds.getBottom() + OFFSET_BOTTOM);
        this.worldBounds = worldBounds;
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
}
