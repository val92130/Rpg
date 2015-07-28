package com.mygdx.game.InputHandling;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by val on 26/07/2015.
 */
public class TouchInputManager implements GestureDetector.GestureListener {
    private GameScreen game;
    public TouchInputManager(GameScreen screen)
    {
        this.game = screen;
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        if(game.getMap().isCollisionClosestCell((int)x,(int)y))
        {
            System.out.println("blocked");

        } else
        {
            game.getMap().getPlayer().moveTo(game.getCamera().getCamera().unproject(new Vector3(x,y,0)));
            System.out.println("not blocked");
        }

        System.out.println("tap");
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        this.game.getCamera().Move(new Vector2(-deltaX, deltaY));
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if(distance > initialDistance)
        {
            game.getCamera().Zoom(-(0.01 * (distance / initialDistance)));
        } else
        {
            game.getCamera().Zoom(0.01 * (initialDistance / distance));
        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}