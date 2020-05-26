package ru.gromkon.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.Ship;
import ru.gromkon.math.Rect;
import ru.gromkon.math.Rnd;
import ru.gromkon.pool.BulletPool;
import ru.gromkon.pool.ExplosionPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        super(worldBounds, bulletPool, explosionPool);
        super.setBulletSound(bulletSound);
    }

    @Override
    public void update(float delta) {
//        if (isOutside(worldBounds)) {
//            destroy();
//        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
        super.update(delta);
        bulletStartPos.set(pos).add(0, -getHalfWidth());
        checkShoot(delta, bulletStartPos);

    }

    public void set(
            TextureRegion[] regions,
            Vector2 v,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVX,
            float bulletVY,
            float reloadInterval,
            float height,
            int damage,
            int hp
    ) {
        this.regions = regions;
        this.v = v;
        this.v0.set(v.cpy().add(v).add(v));
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(bulletVX, bulletVY);
        this.bulletReloadInterval = reloadInterval;
        bulletReloadTimer = Rnd.nextFloat(0, 2f);
        setHeightProportion(height);
        this.damage = damage;
        this.hp = hp;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft() ||
                bullet.getLeft() > getRight() ||
                bullet.getBottom() > getTop() ||
                bullet.getTop() < pos.y);
    }
}
