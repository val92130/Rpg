package com.mygdx.game.TimeEvents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by val on 30/07/2015.
 */
public class AmbientEventManager {

    private GameScreen game;
    private ShaderProgram ambientShader;
    private  float ambientIntensity = .7f;
    private Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);
    private Vector3 oldAmbientColor = new Vector3(1, 1, 1);
    private boolean isNightTime = false;
    private long startTime, currentTime;
    long elapsedSeconds = 0;
    public int timeSpeed = 1;

    public AmbientEventManager(GameScreen game)
    {
        startTime = System.nanoTime();
        currentTime = System.nanoTime();

        this.game = game;
        ambientShader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh.glsl"), Gdx.files.internal("shaders/ambient.fsh.glsl"));
        if(!ambientShader.isCompiled())
        {
            System.out.println(ambientShader.getLog());
        }
        game.getMap().getMapRenderer().getBatch().setShader(ambientShader);

    }

    public void render(SpriteBatch batch)
    {
        /* NIGHT SIMULATION */
        ambientShader.begin();
        if(isNightTime)
        {
            oldAmbientColor = oldAmbientColor.lerp(ambientColor, 0.01f);
            ambientShader.setUniformf("ambientColor", oldAmbientColor.x, oldAmbientColor.y,
                    oldAmbientColor.z, ambientIntensity);
        } else
        {
            oldAmbientColor = oldAmbientColor.lerp(new Vector3(1,1,1), 0.01f);
            ambientShader.setUniformf("ambientColor", oldAmbientColor.x, oldAmbientColor.y,
                    oldAmbientColor.z, 1);

        }
        ambientShader.end();
        /* END NIGHT SIMULATION */

    }

    public void update()
    {
        /* TIME SIMULATION */
        if(((currentTime - startTime) / 1000 ) >= 1000000   )
        {
            startTime = System.nanoTime();
            elapsedSeconds += timeSpeed;
        }

        currentTime = System.nanoTime();

        /* END TIME SIMULATION */
    }

    public void setNightTime(boolean value)
    {
        isNightTime = value;
    }

    public boolean getNightTime()
    {
        return isNightTime;
    }

    public long getCurrentTime()
    {
        return elapsedSeconds;
    }
}
