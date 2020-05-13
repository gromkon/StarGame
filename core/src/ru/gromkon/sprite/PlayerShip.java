package ru.gromkon.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.Sprite;
import ru.gromkon.math.Rect;

public class PlayerShip extends Sprite {

    private static final float OFFSET_BOTTOM = 0.05f;

    private final float V_LEN = 0.01f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 common;
    private Vector2 vResistX;
    private Vector2 vResistY;

    private Rect worldBounds;

    public PlayerShip(TextureRegion texture) {
        super(texture);
        touch = new Vector2();
        common = new Vector2();
        v = new Vector2();
        vResistX = new Vector2();
        vResistY = new Vector2();
    }

    @Override
    public void update(float delta) {
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
        if (getLeft() + getHalfWidth() < worldBounds.getLeft() || getRight() - getHalfWidth() > worldBounds.getRight()) {
            pos.add(vResistX);
            v.scl(0.1f);
            vResistX.scl(0.1f);
        }
        if (getBottom() + getHalfHeight() < worldBounds.getBottom() || getTop() > worldBounds.getTop()) {
            pos.add(vResistY);
            v.scl(0.1f);
            vResistY.scl(0.1f);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
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
