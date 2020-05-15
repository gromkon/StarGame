package ru.gromkon.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.Sprite;
import ru.gromkon.math.Rect;

public class Logo extends Sprite {

    private final float V_LEN = 0.01f;

    private boolean needMove;

    private Vector2 newPos;
    private Vector2 v;
    private Vector2 common;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));

        needMove = false;
        newPos = new Vector2();
        common = new Vector2();
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        this.pos.set(worldBounds.pos);
    }

    public void moveTo(Vector2 touch) {
        newPos.set(touch.x + halfWidth, touch.y + halfHeight);
        System.out.println("pos " + pos);

        v.set(newPos.cpy().sub(pos));
        v.scl(V_LEN);

        needMove = true;
    }

    public void nextMove() {
        common.set(newPos);
        if (common.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(newPos);
            v.setZero();
            needMove = false;
        }
    }

    public boolean isNeedMove() {
        return needMove;
    }
}
