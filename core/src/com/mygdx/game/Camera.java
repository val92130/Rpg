package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    public Camera(Game game)
    {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(width, height);
        cam.position.set(0,0, 0);
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
        if(cam.zoom + ammount <= 0.4 )
        {
            return;
        }
        cam.zoom += ammount;
        effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        System.out.println(cam.zoom);
        System.out.println("Height : " + effectiveViewportHeight);
    }


    public void Move(Vector2 offset)
    {
        cam.translate(offset);
    }

    public float getEffectiveViewportWidth() {
        return effectiveViewportWidth;
    }

    public float getEffectiveViewportHeight() {
        return effectiveViewportHeight;
    }
}
