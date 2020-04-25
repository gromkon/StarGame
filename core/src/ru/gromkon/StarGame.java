package ru.gromkon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	//позволяет вырезать части текстур
	TextureRegion region;

	float x;
	float y;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		//вырезаем из точки (x, y), картинку размером (width, height)
		region = new TextureRegion(img, 50, 50, 100, 100);
		x = 0;
		y = 0;
	}

	@Override
	//срабатывает ~60раз в секунду
	public void render () {
		Gdx.gl.glClearColor(31/255f, 254/255f, 117/225f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		x += 0.5f;
		y += 0.5f;

		//начало передачи текстур
		batch.begin();

		batch.setColor(0.3f, 0.4f, 0.1f, 1f);
		batch.draw(img, 0, 0, 100, 100);

		batch.setColor(0.7f, 0.7f, 0.7f, 0.5f);
		batch.draw(region, x, y, 200, 200);


		//конец передачи текстур
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
