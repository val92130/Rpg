package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by val on 23/07/2015.
 */
public class InputManager implements InputProcessor {
    Game game;
    boolean left,right,up,down;
    Vector2 touchPos;
    public InputManager(Game game)
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
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touchPos = new Vector2(screenX, screenY);
        Vector3 unproject = game.camera.getCamera().unproject(new Vector3(screenX, screenY,0));
        int boxX = (int)unproject.x / game.boxSize;
        int boxY = (int)unproject.y / game.boxSize;
        Box b = game.getBoxAt(boxX,boxY);
        if(b != null)
        {
            //b.setTextureType(EBoxGround.WATER);
        }

        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 offset = new Vector2(-(screenX - touchPos.x), screenY - touchPos.y);
        this.game.camera.Move(offset);
        touchPos = new Vector2(screenX, screenY);
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
