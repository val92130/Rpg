package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.SplashScreen;

public class MyGdxGame extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}

}
