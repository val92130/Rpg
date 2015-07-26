package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by val on 23/07/2015.
 */
public class Camera {
    OrthographicCamera cam;
    private int width, height;
    private float effectiveViewportWidth;
    private float effectiveViewportHeight;
    private Game game;
    public Camera(Game game)
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

    public void Update()
    {
        cam.update();
    }


    public void Zoom(double ammount)
    {
        if(cam.zoom + ammount <= 0.4 || cam.zoom + ammount > 2.5 )
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

    private void correctBoundaries()
    {
        Vector2 camPos = new Vector2(cam.position.x - (effectiveViewportWidth / 2), cam.position.y + (effectiveViewportHeight/2));
        if(camPos.x  < 0)
        {
            cam.position.x = effectiveViewportWidth / 2;
            System.out.println("inferieur a 0");
        }

        int mapWidth = game.getMap().getGroundLayer().getWidth() * Constants.TILE_SIZE * game.getMap().getScaleRatio();
        int mapHeight = game.getMap().getGroundLayer().getHeight() * Constants.TILE_SIZE * game.getMap().getScaleRatio();
        if(cam.position.x + (effectiveViewportWidth / 2) > mapWidth)
        {
            cam.position.x = mapWidth - (effectiveViewportWidth / 2);
            System.out.println("depassement a droite");
        }

        if(cam.position.y + (effectiveViewportHeight / 2) > mapHeight)
        {
            System.out.println("depassement en haut");
            cam.position.y = mapHeight - (effectiveViewportHeight / 2);
        }

        if(cam.position.y - (effectiveViewportHeight / 2) < 0)
        {
            System.out.println("depassement en bas");
            cam.position.y = (effectiveViewportHeight / 2);
        }
    }

    public float getEffectiveViewportWidth() {
        return effectiveViewportWidth;
    }

    public float getEffectiveViewportHeight() {
        return effectiveViewportHeight;
    }
}
