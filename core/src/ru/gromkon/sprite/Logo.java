package ru.gromkon.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.Sprite;
import ru.gromkon.math.Rect;

public class Logo extends Sprite {

    private final float V_LEN = 0.01f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 common;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        touch = new Vector2();
        common = new Vector2();
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
    }

    @Override
    public void update(float delta) {
        common.set(touch);
        if (common.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
            v.setZero();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.sub(pos).setLength(V_LEN));
        return false;
    }

}
