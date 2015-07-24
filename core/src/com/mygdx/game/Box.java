package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by val on 23/07/2015.
 */
public class Box {

    private int x;
    private int y;
    private int width;
    private int height;
    private Game game;
    private EBoxGround textureType;
    public Box(Game game, int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.game = game;
        this.textureType = EBoxGround.GRASS;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EBoxGround getTextureType() {
        return textureType;
    }

    public void setTextureType(EBoxGround textureType) {
        this.textureType = textureType;
    }

    public void Draw(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(game.getTextureManager().getTexture(this), this.x, this.y, this.width, this.height);
    }

    public void Update()
    {

    }


}
