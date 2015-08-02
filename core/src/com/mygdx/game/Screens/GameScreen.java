package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Camera;
import com.mygdx.game.GameMap;
import com.mygdx.game.InputHandling.KeyBoardInputManager;
import com.mygdx.game.InputHandling.TouchInputManager;
import com.mygdx.game.StopWatch;
import com.mygdx.game.TextureManager;
import com.mygdx.game.TimeEvents.AmbientEventManager;

/**
 * Created by val on 28/07/2015.
 */
public class GameScreen implements Screen {

    private TextureManager textureManager;
    private Camera camera;
    private KeyBoardInputManager inputManager;
    private BitmapFont font;
    private StopWatch watch;
    private FPSLogger fpsLogger = new FPSLogger();
    private GameMap map;
    private SpriteBatch spriteBatch, uiBatch;
    private AmbientEventManager ambientEventManager;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        uiBatch = new SpriteBatch();
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
        ambientEventManager = new AmbientEventManager(this);
    }

    @Override
    public void render(float delta) {
        this.update();

        spriteBatch.setProjectionMatrix(camera.getCamera().combined);
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.draw(spriteBatch);
        ambientEventManager.render(spriteBatch);

        uiBatch.begin();
        Vector3 fontPos = new Vector3(0,Gdx.graphics.getHeight(),0);
        font.draw(uiBatch, String.valueOf(Gdx.graphics.getFramesPerSecond()) + " FPS", fontPos.x, fontPos.y);
        font.draw(uiBatch, "Time : " + ambientEventManager.getCurrentTime(), fontPos.x , fontPos.y - font.getLineHeight());
        uiBatch.end();
    }

    public void update()
    {
        ambientEventManager.update();
        map.update();
        inputManager.Update();
        camera.update();
    }

    public AmbientEventManager getAmbientEventManager()
    {
        return this.ambientEventManager;
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize");
        camera.getCamera().setToOrtho(false, width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
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
