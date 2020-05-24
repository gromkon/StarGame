package ru.gromkon.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gromkon.base.Sprite;
import ru.gromkon.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.06f);
        setTop(0.1f);
    }
}
