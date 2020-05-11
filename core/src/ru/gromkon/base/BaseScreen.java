package ru.gromkon.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.math.MatrixUtils;
import ru.gromkon.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    protected Rect screenBounds;
    protected Rect worldBounds;
    protected Rect glBounds;

    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorld;

    private Vector2 touch;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();

        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);

        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();

        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(31/255f, 254/255f, 117/225f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = (float) width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }


    public void resize(Rect worldBounds) {
        System.out.println("worldBounds worldBounds.height = " + worldBounds.getHeight() +
                "; worldBounds.width = " + worldBounds.getWidth());
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }

    private boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("touchDown " + touch);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }

    private boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.println("touchUp " + touch);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    private boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged " + touch);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
