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
import com.mygdx.game.GameMap;
import com.mygdx.game.PathFinding.Node;
import com.mygdx.game.PathFinding.PathMap;
import com.mygdx.game.Screens.GameScreen;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by val on 27/07/2015.
 */
public class Player extends Character {


    private Vector2 destinationVector, destinationPoint;
    Texture redCrossTexture, greenCrossTexture;
    boolean followingPath = false;
    private ArrayList<Node> destinationPath = null;

    public Player(GameScreen game, Texture texture, int width, int height)
    {
        super(game,texture,width,height);
        redCrossTexture = new Texture(Gdx.files.internal("red-cross.png"));
        greenCrossTexture = new Texture(Gdx.files.internal("green-cross.png"));
        destinationVector = Vector2.Zero;
    }

    public Player(GameScreen game, Texture texture,int width, int height ,Vector2 position)
    {
        this(game, texture, width, height);
        this.position = position;

    }

    public Vector2 getPosition()
    {
        return position;
    }
    public Vector2 getDestinationPoint(){
        return this.destinationPoint;
    }
    public ArrayList<Node> getFollowedPath()
    {
        return this.destinationPath;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public void moveTo(Vector2 destination)
    {
        Vector2 dest = new Vector2(destination.x, destination.y);
        Vector2 curr = new Vector2(position.x, position.y);
        double diffx = Math.abs(curr.x-dest.x);
        double diffy = Math.abs(curr.y-dest.y);

        System.out.println("diffx " + diffx + " , diffy " + diffy);

        if(diffx > diffy)
        {
            if(destination.x > this.position.x )
            {
                System.out.println("right");
            }
            if(destination.x < this.position.x )
            {
                System.out.println("left");
            }
        }


        destinationPoint = destination;
        destinationVector = new Vector2(destination.x - position.x , destination.y - position.y);
        destinationVector = destinationVector.nor();
    }

    public void moveTo(Vector3 destination)
    {
        this.moveTo(new Vector2(destination.x, destination.y));
    }

    public void findPathTo(Vector2 cellCoordDestination)
    {
        PathMap p = new PathMap(game.getMap(), game.getMap().worldToCellCoords(this.position),
                new Vector2(cellCoordDestination.x,cellCoordDestination.y));
        ArrayList<Node> nodes = p.findPath();
        if(nodes != null)
        {
            destinationPath = nodes;
        }

    }

    public void render(SpriteBatch batch)
    {
        if(destinationPoint != null)
        {
            batch.draw(greenCrossTexture, destinationPoint.x, destinationPoint.y, 50,50);
        }
        super.render(batch);
    };



    public void checkCollision()
    {
        for(Map.Entry<TiledMapTileLayer.Cell, Vector2> entry : game.getMap().getCollisionCells().entrySet())
        {
            Vector2 v = entry.getValue();
            v = new Vector2(v.x * game.getMap().getScaleRatio() * Constants.TILE_SIZE,v.y * game.getMap().getScaleRatio() * Constants.TILE_SIZE );
            Rectangle r = new Rectangle(v.x, v.y, Constants.TILE_SIZE * game.getMap().getScaleRatio(), Constants.TILE_SIZE * game.getMap().getScaleRatio());

            Rectangle hitBox = new Rectangle(position.x, position.y, this.width , this.height / 5 );

            Rectangle col = new Rectangle();
            if(GameMap.intersect(hitBox, r, col ))
            {
                if(col != null)
                {
                    System.out.println(col);
                }

            }

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
        super.update();
        this.checkCollision();
        if(destinationPath != null && !followingPath)
        {
            if(destinationPath.size() == 0)
            {
                destinationPath = null;
            } else
            {
                Node dest = destinationPath.get(0);
                this.moveTo(game.getMap().cellToWorldCoords(new Vector2(dest.getX(), dest.getY())));
                destinationPath.remove(dest);
            }

        }
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
