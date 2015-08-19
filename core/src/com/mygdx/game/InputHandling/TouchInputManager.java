package com.mygdx.game.InputHandling;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Characters.*;
import com.mygdx.game.Constants;
import com.mygdx.game.PathFinding.PathMap;
import com.mygdx.game.Screens.GameScreen;

import java.util.ArrayList;

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

        boolean npcEncountered = false;
        for(Npc n : game.getMap().getNpcs())
        {
            if(n.getArea().contains(game.getCamera().unProject(new Vector2(x,y))))
            {
                if(n.getPosition().dst(game.getMap().getPlayer().getPosition()) > Constants.TILE_SIZE * game.getMap().getScaleRatio())
                {
                    com.mygdx.game.Characters.Player player = game.getMap().getPlayer();
                    int ratio = game.getMap().getScaleRatio();
                    Vector2 v = (new Vector2(n.getPosition().x,n.getPosition().y ));
                    int vx = (int)(v.x / ratio / Constants.TILE_SIZE);
                    int vy = (int)(v.y / ratio / Constants.TILE_SIZE);
                    game.getMap().getPlayer().findPathTo(new Vector2(vx,vy - 2));
                    break;
                }
                System.out.println(n.getName());
                npcEncountered = true;
                break;
            }
        }

        // If we haven't clicked on an npc, we move the player to the selected point
        if(!npcEncountered)
        {
            com.mygdx.game.Characters.Player player = game.getMap().getPlayer();
            int ratio = game.getMap().getScaleRatio();
            Vector2 v = game.getCamera().unProject(new Vector2(x,y ));
            int vx = (int)(v.x / ratio / Constants.TILE_SIZE);
            int vy = (int)(v.y / ratio / Constants.TILE_SIZE);
            game.getMap().getPlayer().findPathTo(new Vector2(vx,vy));
        }

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
