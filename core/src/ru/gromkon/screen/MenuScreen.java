package ru.gromkon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;

    private Vector2 pos;
    private Vector2 newPos;

    private Vector2 v;
    private float speed;
    private float wayLen;
    private boolean isNewPos;

    private int countSteps;


    private Vector2 touch;

    @Override
    public void show() {
        super.show();

        img = new Texture("badlogic.jpg");
        pos = new Vector2();

        newPos = new Vector2();
        speed = 1.0f;

        wayLen = 0;
        countSteps = 0;
        v = new Vector2();
        isNewPos = false;

        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (isNewPos) {
            if (newPos.x != pos.x && newPos.y != pos.y) {
                wayLen = newPos.cpy().sub(pos).len();
                countSteps = (int)Math.floor(wayLen / speed) + 1;
                v.x = (newPos.x - pos.x) / countSteps;
                v.y = (newPos.y - pos.y) / countSteps;
            }
            isNewPos = false;
        }

        if (countSteps > 1) {
            pos.add(v);
            countSteps--;
        } else if (countSteps == 1) {
            pos.set(newPos);
            countSteps--;
        }



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
        newPos.set(touch);
        isNewPos = true;
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
