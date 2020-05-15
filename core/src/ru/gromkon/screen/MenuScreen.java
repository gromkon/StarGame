package ru.gromkon.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.BaseScreen;
import ru.gromkon.math.Rect;
import ru.gromkon.sprite.Background;
import ru.gromkon.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;

    private Background background;
    private Logo logo;

    private Vector2 pos;

    @Override
    public void show() {
        super.show();

        img = new Texture("badlogic.jpg");
        bg = new Texture("textures/background.jpg");

        logo = new Logo(img);
        background = new Background(bg);

        pos = new Vector2();
        pos.set(0, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (logo.isNeedMove()) {
            logo.nextMove();
        }

        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // для событий (0, 0) находится в верхнем левом углу
        // для отрисовки (0, 0) находится в нижнем левом углу
        // поэтому, что бы координаты события и отрисовки совпадали
        // мы из высоты экрана вычитаем координаты событий
        logo.moveTo(new Vector2(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld));

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
