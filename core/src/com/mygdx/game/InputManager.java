package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by val on 23/07/2015.
 */
public class InputManager implements InputProcessor {
    Game game;
    boolean left,right,up,down;
    public InputManager(Game game)
    {
        this.game = game;
    }

    public void Update()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            game.getCamera().Move(new Vector2(-Constants.CAMERA_SPEED ,0 ));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            game.getCamera().Move(new Vector2(Constants.CAMERA_SPEED ,0 ));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            game.getCamera().Move(new Vector2(0,Constants.CAMERA_SPEED  ));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            game.getCamera().Move(new Vector2(0,-Constants.CAMERA_SPEED ));
        }


    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 unproject = game.camera.getCamera().unproject(new Vector3(screenX, screenY,0));
        int boxX = (int)unproject.x / game.boxSize;
        int boxY = (int)unproject.y / game.boxSize;
        System.out.println("Box X : " + boxX + ", Box Y : " + boxY);
        Box b = game.getBoxAt(boxX,boxY);
        if(b != null)
        {
            b.setTextureType(EBoxGround.WATER);
        }

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
