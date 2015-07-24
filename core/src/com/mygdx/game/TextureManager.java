package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by val on 23/07/2015.
 */
public class TextureManager {

    private Texture grassTexture, waterTexture, dirtTexture;
    public TextureManager()
    {

    }

    public void loadTextures()
    {
        grassTexture = new Texture(Gdx.files.internal("grass.jpg"));
        waterTexture = new Texture(Gdx.files.internal("water.jpg"));
        dirtTexture = new Texture(Gdx.files.internal("dirt.jpg"));
    }

    public Texture getTexture(Box b)
    {
        switch(b.getTextureType())
        {
            case WATER:
                return waterTexture;
            case DIRT:
                return dirtTexture;
            case GRASS:
                return grassTexture;
            default:
                return null;
        }
    }
}
