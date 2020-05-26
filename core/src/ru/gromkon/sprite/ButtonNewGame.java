package ru.gromkon.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gromkon.base.ScaledButton;
import ru.gromkon.math.Rect;
import ru.gromkon.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

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
    public void action() {
        gameScreen.setStartOptions();
        gameScreen.setState(State.PLAYING);
    }
}
