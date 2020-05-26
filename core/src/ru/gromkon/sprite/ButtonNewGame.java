package ru.gromkon.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gromkon.base.ScaledButton;
import ru.gromkon.math.Rect;
import ru.gromkon.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private static final float DELTA_SCALE = 0.00005f;

    private static final float MIN_SCALE = 0.0275f;
    private static final float MAX_SCALE = 0.03f;

    private float signScale;

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.03f);
    }

    @Override
    public void update(float delta) {
        if (getHeight() >= MAX_SCALE) {
            signScale = -1f;
        } else if (getHeight() <= MIN_SCALE) {
            signScale = 1f;
        }
        setHeightProportion(getHeight() + signScale * DELTA_SCALE);
    }

    @Override
    public void action() {
        gameScreen.setStartOptions();
        gameScreen.setState(State.PLAYING);
    }
}
