package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Game game;
	@Override
	public void create () {
		batch = new SpriteBatch();
		game = new Game(500,100);
	}

	@Override
	public void render () {

		game.update();
		game.render(batch);
		super.render();
	}
}
