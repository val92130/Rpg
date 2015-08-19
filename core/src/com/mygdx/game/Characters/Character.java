package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Constants;
import com.mygdx.game.PathFinding.Node;
import com.mygdx.game.PathFinding.PathMap;
import com.mygdx.game.Screens.GameScreen;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by val on 27/07/2015.
 */
public abstract class Character implements ICharacter {

    protected GameScreen game;
    protected Vector2 position = new Vector2(0,0);
    protected Texture texture;
    protected int width, height;
    protected int speed = 280;

    public Character()
    {

    }

    public Character(GameScreen game, Texture texture, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.game = game;
    }

    public Character(GameScreen game, Texture texture,int width, int height ,Vector2 position)
    {
        this(game,texture, width, height);
        this.position = position;
    }

    public Rectangle getArea()
    {
        return new Rectangle(this.position.x, this.position.y, this.width, this.height);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }


    public void render(SpriteBatch batch)
    {
        batch.draw(texture, position.x, position.y, width, height);

    };

    public void update()
    {

    };

}
