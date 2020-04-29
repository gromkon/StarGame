package ru.gromkon;

import com.badlogic.gdx.Game;

import ru.gromkon.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
