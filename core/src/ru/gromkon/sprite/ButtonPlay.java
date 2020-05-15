package ru.gromkon.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gromkon.base.ScaledButton;
import ru.gromkon.math.Rect;
import ru.gromkon.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private static final float OFFSET_LEFT = 0.02f;
    private static final float OFFSET_BOTTOM = 0.02f;

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.13f);
        setBottom(worldBounds.getBottom() + OFFSET_BOTTOM);
        setLeft(worldBounds.getLeft() + OFFSET_LEFT);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
