package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by val on 23/07/2015.
 */
public class Camera {
    OrthographicCamera cam;
    private int width, height;
    private float effectiveViewportWidth;
    private float effectiveViewportHeight;
    private GameScreen game;
    private Vector2 destVector;
    private boolean flag = false;

    public Camera(GameScreen game)
    {
        this.game = game;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(width, height);
        cam.position.set(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), 0);
        cam.update();
        effectiveViewportHeight = cam.viewportHeight;
        effectiveViewportWidth = cam.viewportWidth;
    }

    public OrthographicCamera getCamera()
    {
        return cam;
    }

    public void update()
    {
        cam.update();
        correctBoundaries();

        if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Constants.KEY_MOVE_LEFT) ||  Gdx.input.isKeyPressed(Constants.KEY_MOVE_RIGHT) ||
                Gdx.input.isKeyPressed(Constants.KEY_MOVE_DOWN) || Gdx.input.isKeyPressed(Constants.KEY_MOVE_UP))
        {
            destVector = null;
        }


        if(game.getMap().getPlayer().isFollowingPath()) {

            if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Constants.KEY_MOVE_LEFT) ||  Gdx.input.isKeyPressed(Constants.KEY_MOVE_RIGHT) ||
            Gdx.input.isKeyPressed(Constants.KEY_MOVE_DOWN) || Gdx.input.isKeyPressed(Constants.KEY_MOVE_UP) )
            {
                flag = true;
            }
            if(destVector == null && !flag)
            {
                if(game.getMap().getPlayer().getFollowedPath() != null)
                {
                    if(game.getMap().getPlayer().getFollowedPath().size() > 0)
                    {
                        destVector = new Vector2(game.getMap().getPlayer().getFollowedPath().get( game.getMap().getPlayer().getFollowedPath().size() -1).getWorldCoordsVector().x,game.getMap().getPlayer().getFollowedPath().get( game.getMap().getPlayer().getFollowedPath().size() -1).getWorldCoordsVector().y) ;
                    }
                } else
                {
                    destVector = game.getMap().getPlayer().getDestinationPoint();
                }

            }

        } else
        {
            flag = false;
        }


        if(destVector != null)
        {
            double diff = Math.abs((cam.position.x - destVector.x) + (cam.position.y - destVector.y));
            boolean lerpFinished = diff <= 1 || correctBoundaries();
            if(lerpFinished )
            {
                destVector = null;
            } else
            {
                Vector3 dest = cam.position.lerp(new Vector3(destVector.x, destVector.y, 0), 2f * Gdx.graphics.getDeltaTime());
                cam.position.x = dest.x;
                cam.position.y = dest.y;

            }
        }

        correctBoundaries();

    }

    public Vector2 unProject(Vector2 screenCoords)
    {
        Vector3 t = cam.unproject(new Vector3(screenCoords.x, screenCoords.y, 0));
        return new Vector2(t.x, t.y);
    }

    public Vector2 project(Vector2 worldCoords)
    {
        Vector3 t = cam.project(new Vector3(worldCoords.x, worldCoords.y, 0));
        return new Vector2(t.x, t.y);
    }

    public void Zoom(double ammount)
    {
        if(cam.zoom + ammount <= 1 || cam.zoom + ammount > 2.5 )
        {
            return;
        }
        cam.zoom += ammount;
        effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        effectiveViewportHeight = cam.viewportHeight * cam.zoom;
        this.correctBoundaries();
    }


    public void Move(Vector2 offset)
    {
        cam.translate(offset);
        this.correctBoundaries();
    }

    private boolean correctBoundaries()
    {
        boolean flag = false;
        Vector2 camPos = new Vector2(cam.position.x - (effectiveViewportWidth / 2), cam.position.y + (effectiveViewportHeight/2));
        if(camPos.x  < 0)
        {
            flag = true;
            cam.position.x = effectiveViewportWidth / 2;
        }

        int mapWidth = game.getMap().getGroundLayer().getWidth() * Constants.TILE_SIZE * game.getMap().getScaleRatio();
        int mapHeight = game.getMap().getGroundLayer().getHeight() * Constants.TILE_SIZE * game.getMap().getScaleRatio();
        if(cam.position.x + (effectiveViewportWidth / 2) > mapWidth)
        {
            flag = true;
            cam.position.x = mapWidth - (effectiveViewportWidth / 2);
        }

        if(cam.position.y + (effectiveViewportHeight / 2) > mapHeight)
        {
            flag = true;
            cam.position.y = mapHeight - (effectiveViewportHeight / 2);
        }

        if(cam.position.y - (effectiveViewportHeight / 2) < 0)
        {
            flag = true;
            cam.position.y = (effectiveViewportHeight / 2);
        }
        return  flag;
    }

    public float getEffectiveViewportWidth() {
        return effectiveViewportWidth;
    }

    public float getEffectiveViewportHeight() {
        return effectiveViewportHeight;
    }
}
