package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by val on 06/08/2015.
 */
public interface ICharacter {

    void render(SpriteBatch spriteBatch);
    void update();
    public Vector2 getPosition();
    public void setPosition(Vector2 position);
}
