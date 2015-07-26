package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by val on 26/07/2015.
 */
public class Player {

    private Vector2 velocity = new Vector2(0,0);
    private float speed = 60;
    private float gravity = 60 * 1.8f;
    private Vector2 position = new Vector2(0,0);
    Game game;

    public Player(Game game)
    {
        this.game = game;
    }

    public void draw(SpriteBatch batch)
    {
        //
    }

    public void update()
    {
        velocity.y -= gravity * Gdx.graphics.getDeltaTime();

        if(velocity.y > speed)
        {
            velocity.y = speed;
        } else if(velocity.y < -speed)
        {
            velocity.y = -speed;
        }

        this.position.x = this.position.x + velocity.x * Gdx.graphics.getDeltaTime();
        this.position.x = this.position.y + velocity.y * Gdx.graphics.getDeltaTime();
    }
}
