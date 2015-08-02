package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.GameScreen;

import java.util.Map;

/**
 * Created by val on 27/07/2015.
 */
public class Character {

    private GameScreen game;
    private Vector2 position = new Vector2(0,0);
    private Texture texture;
    private int width, height;
    private Vector2 destinationVector, destinationPoint;
    private int speed = 500;
    Texture redCrossTexture, greenCrossTexture;
    boolean followingPath = false;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Character(GameScreen game, Texture texture, int width, int height)
    {
        redCrossTexture = new Texture(Gdx.files.internal("red-cross.png"));
        greenCrossTexture = new Texture(Gdx.files.internal("green-cross.png"));
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

    public Vector2 getPosition()
    {
        return position;
    }
    public Vector2 getDestinationPoint(){
        return this.destinationPoint;
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
        if(destinationPoint != null)
        {
            batch.draw(greenCrossTexture, destinationPoint.x, destinationPoint.y, 50,50);
        }
        batch.draw(texture, position.x, position.y, width, height);

    };



    public void checkCollision()
    {
        for(Map.Entry<TiledMapTileLayer.Cell, Vector2> entry : game.getMap().getCollisionCells().entrySet())
        {
            Vector2 v = entry.getValue();
            v = new Vector2(v.x * game.getMap().getScaleRatio() * Constants.TILE_SIZE,v.y * game.getMap().getScaleRatio() * Constants.TILE_SIZE );
            Rectangle r = new Rectangle(v.x, v.y, Constants.TILE_SIZE * game.getMap().getScaleRatio(), Constants.TILE_SIZE * game.getMap().getScaleRatio());

            Rectangle hitBox = new Rectangle(position.x, position.y, this.width , this.height / 5 );

            /* HITBOX DRAWING
            shapeRenderer.setProjectionMatrix(game.getCamera().getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
            shapeRenderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
            shapeRenderer.rect(position.x, position.y, width, height);

            shapeRenderer.end();

            */

            // if we collide
            if(r.overlaps(hitBox))
            {

                if(hitBox.x > r.x )
                {
                    // collide left
                    this.position.x += 3;
                    System.out.println("Collide left");
                }

                if(hitBox.x < r.x)
                {
                    // collide right
                    this.position.x -= 3;
                    System.out.println("Collide right");
                }

                if(hitBox.y > r.y)
                {
                    // collide down
                    this.position.y += 3;
                    System.out.println("Collide down");
                }

                if(hitBox.y < r.y)
                {
                    // collide up
                    this.position.y -= 3;
                    System.out.println("Collide up");
                }


                destinationPoint = null;

            }
        }
    }

    public void update()
    {

        this.checkCollision();
        followingPath = destinationPoint != null;
        if(destinationPoint != null)
        {
            if(Math.abs(destinationPoint.x - position.x) > 3 || Math.abs(destinationPoint.y - position.y) > 3 )
            {
                position.x += destinationVector.x * Gdx.graphics.getDeltaTime() * speed;
                position.y += destinationVector.y * Gdx.graphics.getDeltaTime() * speed;
            } else
            {
                destinationPoint = null;
            }
        }
    };

    public boolean isFollowingPath()
    {
        return followingPath;
    }
}
