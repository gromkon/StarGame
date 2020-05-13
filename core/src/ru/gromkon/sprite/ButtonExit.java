package ru.gromkon.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gromkon.base.ScaledButton;
import ru.gromkon.math.Rect;

public class ButtonExit extends ScaledButton {

    private static final float OFFSET_RIGHT = 0.02f;
    private static final float OFFSET_BOTTOM = 0.02f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        setBottom(worldBounds.getBottom() + OFFSET_BOTTOM);
        setRight(worldBounds.getRight() - OFFSET_RIGHT);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
