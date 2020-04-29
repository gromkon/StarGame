package ru.gromkon.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    // срабатывает сразу после конструктора
    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
    }

    // ~60 раз в секунду
    // delta - показывает, сколько времени прошло
    // с момента срабатывания прошлого метода render
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(31/255f, 254/255f, 117/225f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // при изменении экрана
    @Override
    public void resize(int width, int height) {
        System.out.println("resize. width = " + width + ", height = " + height);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {

    }

    // срабатывает в самом конце
    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    // автоматически не срабатывает
    // как правило, вызывают из метода hide
    @Override
    public void dispose() {
        batch.dispose();
        System.out.println("dispose");
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown. keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp. keycode = " + keycode);
        return false;
    }

    // срабатывает много раз, пока клавиша нажата
    // character содержит нажатый символ
    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped. character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown. screenX = " + screenX + "; screenY = " + screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp. screenX = " + screenX + "; screenY = " + screenY);
        return false;
    }

    // когда нажали на экран, протащили палец (мышь) и отпустили в другой точке
    // срабатывает очень часто
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged. screenX = " + screenX + "; screenY = " + screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    // amount = 1 (вниз) / -1 (вверх)
    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled. amount = " + amount);
        return false;
    }
}
