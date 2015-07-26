package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by val on 27/07/2015.
 */
public class Character {

    private Game game;
    private Vector2 position = new Vector2(0,0);
    private Texture texture;
    private int width, height;
    private Vector2 destinationVector, destinationPoint;
    private int speed = 500;

    public Character(Game game, Texture texture, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.game = game;
    }

    public Character(Game game, Texture texture,int width, int height ,Vector2 position)
    {
        this(game,texture, width, height);
        this.position = position;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public void moveTo(Vector2 destination)
    {
        destinationPoint = destination;
        destinationVector = new Vector2(destination.x - position.x , destination.y - position.y);
        destinationVector = destinationVector.nor();
    }

    public void moveTo(Vector3 destination)
    {
        this.moveTo(new Vector2(destination.x, destination.y));
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, position.x, position.y, width, height);
    };

    public void update()
    {
        if(destinationPoint != null)
        {
            if(Math.abs(destinationPoint.x - position.x) > 3 && Math.abs(destinationPoint.y - position.y) > 3 )
            {
                position.x += destinationVector.x * Gdx.graphics.getDeltaTime() * speed;
                position.y += destinationVector.y * Gdx.graphics.getDeltaTime() * speed;
            } else
            {
                destinationPoint = null;
            }
        }
    };
}
