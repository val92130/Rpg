package com.mygdx.game.InputHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by val on 23/07/2015.
 */
public class KeyBoardInputManager implements InputProcessor {
    GameScreen game;
    boolean left,right,up,down;
    Vector2 touchPos;
    public KeyBoardInputManager(GameScreen game)
    {
        this.game = game;
    }

    public void Update()
    {
        if(Gdx.input.isKeyPressed(Constants.KEY_MOVE_LEFT))
        {
            game.getCamera().Move(new Vector2(-Constants.CAMERA_SPEED * Gdx.graphics.getDeltaTime() ,0 ));
        }

        if(Gdx.input.isKeyPressed(Constants.KEY_MOVE_RIGHT))
        {
            game.getCamera().Move(new Vector2(Constants.CAMERA_SPEED* Gdx.graphics.getDeltaTime() ,0 ));
        }

        if(Gdx.input.isKeyPressed(Constants.KEY_MOVE_UP))
        {
            game.getCamera().Move(new Vector2(0,Constants.CAMERA_SPEED* Gdx.graphics.getDeltaTime()  ));
        }

        if(Gdx.input.isKeyPressed(Constants.KEY_MOVE_DOWN))
        {
            game.getCamera().Move(new Vector2(0,-Constants.CAMERA_SPEED* Gdx.graphics.getDeltaTime() ));
        }

    }

    public boolean keyDown(int keycode) {
        if(keycode == Constants.KEY_TOGGLE_NIGHT)
        {
            game.getAmbientEventManager().setNightTime(!game.getAmbientEventManager().getNightTime());
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {

        if(amount == -1)
        {
            game.getCamera().Zoom(-Constants.CAMERA_ZOOM_SPEED);
        } else
        {
            game.getCamera().Zoom(Constants.CAMERA_ZOOM_SPEED);
        }
        return false;
    }
}
