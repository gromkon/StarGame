package ru.gromkon.pool;

import ru.gromkon.base.SpritesPool;
import ru.gromkon.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
