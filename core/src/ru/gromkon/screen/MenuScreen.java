package ru.gromkon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;

    private Vector2 pos;
    private Vector2 v;
    private Vector2 gravity;

    private Vector2 touch;

    @Override
    public void show() {
        super.show();

        img = new Texture("badlogic.jpg");
        pos = new Vector2();
        v = new Vector2(1, 2);
        gravity = new Vector2(0, -0.05f);

        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // обработка данных
        pos.add(v);
        v.add(gravity);

        // начало передачи текстур
        batch.begin();

//		batch.setColor(0.3f, 0.4f, 0.1f, 1f);
        batch.draw(img, pos.x, pos.y);

        // конец передачи текстур
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // для событий (0, 0) находится в верхнем левом углу
        // для отрисовки (0, 0) находится в нижнем левом углу
        // поэтому, что бы координаты события и отрисовки совпадали
        // мы из высоты экрана вычитаем координаты событий
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touchDown touch = " + touch);
        pos.set(touch);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
