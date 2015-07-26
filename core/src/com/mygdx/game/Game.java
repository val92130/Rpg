package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by val on 23/07/2015.
 */
public class Game
{
    private TextureManager textureManager;
    private Camera camera;
    private KeyBoardInputManager inputManager;
    private BitmapFont font;
    private StopWatch watch;
    private FPSLogger fpsLogger = new FPSLogger();
    private GameMap map;

    private Player player;

    public Game(int boxCountPerLine, int boxSize)
    {
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(new TouchInputManager(this));
        im.addProcessor(gd);
        im.addProcessor(new KeyBoardInputManager(this));
        inputManager = new KeyBoardInputManager(this);
        Gdx.input.setInputProcessor(im);


        camera = new Camera(this);
        textureManager = new TextureManager();
        textureManager.loadTextures();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        watch = new StopWatch();
        map = new GameMap("map01.tmx", this);

    }


    public void update()
    {
        map.update();
        inputManager.Update();
        camera.Update();
    }

    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.setProjectionMatrix(camera.getCamera().combined);
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        Vector3 fontPos = new Vector3(0,0,0);
        fontPos = camera.getCamera().unproject(fontPos);
        font.draw(spriteBatch, String.valueOf(Gdx.graphics.getFramesPerSecond()) + " FPS", fontPos.x, fontPos.y);

        map.draw(spriteBatch);

        spriteBatch.end();
    }






    public TextureManager getTextureManager()
    {
        return this.textureManager;
    }

    public Camera getCamera()
    {
        return camera;
    }

    public GameMap getMap()
    {
        return map;
    }
}
