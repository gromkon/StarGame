package ru.gromkon.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gromkon.base.BaseScreen;
import ru.gromkon.math.Rect;
import ru.gromkon.sprite.Background;
import ru.gromkon.sprite.ButtonExit;
import ru.gromkon.sprite.ButtonPlay;
import ru.gromkon.sprite.Star;

public class MenuScreen extends BaseScreen {

    private final static int STARS_COUNT = 256;

    private final Game game;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private Star[] stars;

    private Music backgroundMusic;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);

        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        bg = new Texture("textures/background_new2.jpg");
        background = new Background(bg);
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_music.mp3"));
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        backgroundMusic.dispose();
        super.dispose();
    }



    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }


    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }
}
