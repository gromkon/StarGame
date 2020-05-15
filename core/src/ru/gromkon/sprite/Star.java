package ru.gromkon.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.Sprite;
import ru.gromkon.math.Rect;
import ru.gromkon.math.Rnd;

public class Star extends Sprite {

    private static final float MAX_SCALE_MIN_VALUE = 0.8f;
    private static final float MAX_SCALE_MAX_VALUE = 1.5f;
    private static final float MIN_SCALE_MIN_VALUE = 0.3f;
    private static final float MIN_SCALE_MAX_VALUE = 0.6f;
    private static final float SET_START_SCALE_MIN_VALUE = MIN_SCALE_MAX_VALUE + 0.1f;
    private static final float SET_START_SCALE_MAX_VALUE = MAX_SCALE_MIN_VALUE - 0.1f;
    private static final float DELTA_SCALE = 0.01f;

    private static final float VX_MIN_SPEED = -0.005f;
    private static final float VX_MAX_SPEED = 0.005f;
    private static final float VY_MIN_SPEED = -0.2f;
    private static final float VY_MAX_SPEED = -0.1f;

    private Vector2 v;
    private Rect worldBounds;

    private float sign;
    private float maxScale;
    private float minScale;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));

        v = new Vector2();
        float vx = Rnd.nextFloat(VX_MIN_SPEED, VX_MAX_SPEED);
        float vy = Rnd.nextFloat(VY_MIN_SPEED, VY_MAX_SPEED);
        v.set(vx, vy);

        worldBounds = new Rect();

        setScale(Rnd.nextFloat(SET_START_SCALE_MIN_VALUE, SET_START_SCALE_MAX_VALUE));
        sign = -1f;
        maxScale = Rnd.nextFloat(MAX_SCALE_MIN_VALUE, MAX_SCALE_MAX_VALUE);
        minScale = Rnd.nextFloat(MIN_SCALE_MIN_VALUE, MIN_SCALE_MAX_VALUE);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.01f);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        setScale(getScale() + sign * DELTA_SCALE);
        if (getScale() <= minScale || getScale() >= maxScale) {
            sign = -sign;
        }
        pos.mulAdd(v, delta);
        checkBounds();
    }

    private void checkBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }
}
