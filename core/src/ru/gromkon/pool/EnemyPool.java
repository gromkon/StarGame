package ru.gromkon.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.gromkon.base.SpritesPool;
import ru.gromkon.math.Rect;
import ru.gromkon.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound bulletSound;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet_enemy.mp3"));
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(worldBounds, bulletPool, explosionPool, bulletSound);
    }


    @Override
    public void dispose() {
        super.dispose();
        bulletSound.dispose();
    }
}
