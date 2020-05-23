package ru.gromkon.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.math.Rect;
import ru.gromkon.math.Rnd;
import ru.gromkon.pool.BulletPool;
import ru.gromkon.pool.ExplosionPool;
import ru.gromkon.sprite.Bullet;
import ru.gromkon.sprite.Explosion;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected Vector2 v;

    protected ExplosionPool explosionPool;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletStartPos;
    protected int damage;
    protected int hp;
    protected float bulletHeight;
    protected float bulletReloadInterval;
    protected float bulletReloadTimer;

    private Sound bulletSound;
    private float bulletSoundVolume;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);

        v = new Vector2();

        bulletV = new Vector2();
        bulletStartPos = new Vector2();
        bulletSound = null;
        bulletSoundVolume = 0.03f;
    }

    public Ship(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;

        v = new Vector2();

        bulletV = new Vector2();
        bulletStartPos = new Vector2();
        bulletSoundVolume = 0.15f;
    }

    protected void setBulletSound(Sound bulletSound) {
        this.bulletSound = bulletSound;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    protected void checkShoot(float delta, Vector2 bulletStartPos) {
        bulletReloadTimer += delta;
        if (bulletReloadTimer >= bulletReloadInterval) {
            shoot(bulletStartPos);
            bulletReloadTimer = 0f;
        }
    }

    private void shoot(Vector2 bulletStartPos) {
        Bullet bullet = bulletPool.obtain();
        bullet.set(
                this,
                bulletRegion,
                bulletStartPos,
                bulletV,
                bulletHeight,
                worldBounds,
                damage
        );
        bulletSound.play(bulletSoundVolume);
    }
}
